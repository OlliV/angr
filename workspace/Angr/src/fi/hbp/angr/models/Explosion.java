package fi.hbp.angr.models;

import java.util.Iterator;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Explosion {
    Body body;
    private static final float maxDistance = 9.0f;
    private static final float maxForce = 55.0f;
    private Vector2 bdPos = new Vector2();

    public Explosion(Body body) {
        this.body = body;
    }

    public void doExplosion() {
        float distance;
        float force;
        float angle;

        /* Set exploding body as static so it won't fly away */
        BodyType origBdType = body.getType();
        body.setType(BodyType.StaticBody);

        Vector2 bodyPos = body.getPosition();

        /* Actual Explosion */
        Iterator<Body> bodies = body.getWorld().getBodies();
        Body bd;
        while (bodies.hasNext()) {
            bd = bodies.next();
            bdPos.set(bd.getPosition());

            distance = bodyPos.dst(bdPos);
            if(distance > maxDistance)
                continue;
                //distance = maxDistance - 0.01f;

            /* Closer objects should feel greater force */
            force = ((maxDistance - distance) / maxDistance) * maxForce;

            angle = MathUtils.atan2(bdPos.y - bodyPos.y, bdPos.x - bodyPos.x);
            /* Apply an impulse to the body, using the angle */
            bd.applyLinearImpulse(new Vector2(MathUtils.cos(angle) * force,
                                              MathUtils.sin(angle) * force),
                                  bdPos);
        }

        /* Restore body type */
        body.setType(origBdType);
    }
}
