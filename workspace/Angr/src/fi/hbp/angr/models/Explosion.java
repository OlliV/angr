package fi.hbp.angr.models;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;

/**
 * Explosion model.
 */
public class Explosion {
    Body body;
    private final float maxDistance;
    private final float maxForce;
    private Vector2 bodyPos = new Vector2();
    private Vector2 hitBodyPos = new Vector2();

    /**
     * Class constructor.
     * @param body the body that sends the shock.
     */
    public Explosion(Body body) {
        this.maxDistance = 9.0f;
        this.maxForce = 80.0f;
        this.body = body;
    }

    /**
     * Class constructor with configurable maximum distance and force.
     * @param body the body that sends the shock.
     * @param maxDist the maximum distance of shock wave.
     * @param maxForce the maximum force applied.
     */
    public Explosion(Body body, float maxDist, float maxForce) {
        this.maxDistance = maxDist;
        this.maxForce = maxForce;
        this.body = body;
    }

    /**
     * Emits a shock impulse to the nearby bodies.
     */
    public void doExplosion() {
        /* Set exploding body as static so it won't fly away */
        BodyType origBdType = body.getType();
        body.setType(BodyType.StaticBody);

        bodyPos = body.getPosition();

        body.getWorld().QueryAABB(callback,
                bodyPos.x - maxDistance, bodyPos.y - maxDistance,
                bodyPos.x + maxDistance, bodyPos.y + maxDistance);

        /* Restore body type */
        body.setType(origBdType);
    }

    QueryCallback callback = new QueryCallback() {
        @Override
        public boolean reportFixture (Fixture fixture) {
            float force;
            float angle;

            Body hitBody = fixture.getBody();
            hitBodyPos.set(hitBody.getPosition());

            float distance = body.getPosition().dst(hitBodyPos);
            if (distance <= maxDistance) {
                /* Closer objects should feel greater force */
                force = ((maxDistance - distance) / maxDistance) * maxForce;

                angle = MathUtils.atan2(hitBodyPos.y - bodyPos.y, hitBodyPos.x - bodyPos.x);
                /* Apply an impulse to the body, using the angle */
                hitBody.applyLinearImpulse(
                        new Vector2(MathUtils.cos(angle) * force,
                        MathUtils.sin(angle) * force),
                        hitBodyPos);
            }
            return true; /* Keep going until all bodies in the area are checked. */
        }
    };
}
