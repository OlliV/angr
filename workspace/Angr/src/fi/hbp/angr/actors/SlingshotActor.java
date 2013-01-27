package fi.hbp.angr.actors;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import fi.hbp.angr.G;

/**
 * Slingshot with mouse controls.
 */
public abstract class SlingshotActor extends Actor implements InputProcessor {
    /**
     * Game stage
     */
    private Stage stage;

    private float a_max;
    private float F_impulse;

    /**
     * Body of the item
     * @note Body is not initialized by this abstract class
     */
    protected Body body;

    /**
     * Slingshot state
     */
    private boolean slingshotEnabled = true;

    /* Input processing/Controls */
    protected MouseJoint mouseJoint = null;
    private Vector3 testPoint = new Vector3();
    private Vector2 startPoint = new Vector2();
    private Body groundBody;
    private Body hitBody = null;

    /**
     * Constructor for SlingShotActor
     * @param stage Game stage
     * @param world Game (physics) world
     */
    public SlingshotActor(Stage stage, World world, float a_max, float F_impulse) {
        this.stage = stage;
        this.a_max = a_max;
        this.F_impulse = F_impulse;

        /* We also need an invisible zero size ground body
         * to which we can connect the mouse joint */
        BodyDef bodyDef = new BodyDef();
        groundBody = world.createBody(bodyDef);
    }

    QueryCallback callback = new QueryCallback() {
        @Override
        public boolean reportFixture (Fixture fixture) {
            /* If the hit point is inside the fixture of the body
            /* we report it
             */
            if (fixture.testPoint(testPoint.x, testPoint.y)) {
                hitBody = fixture.getBody();
                if (hitBody.equals(body)) {
                    return false;
                }
            }
            return true; /* Keep going until all bodies in the area are checked. */
        }
    };

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (slingshotEnabled == false)
            return false;

        /* Translate the mouse coordinates to world coordinates */
        stage.getCamera().unproject(testPoint.set(screenX, screenY, 0));
        testPoint.x *= G.WORLD_TO_BOX;
        testPoint.y *= G.WORLD_TO_BOX;

        hitBody = null;
        body.getWorld().QueryAABB(callback,
                        testPoint.x - 0.0001f,
                        testPoint.y - 0.0001f,
                        testPoint.x + 0.0001f,
                        testPoint.y + 0.0001f);
        if (hitBody == groundBody) hitBody = null;

        if (hitBody == null)
            return false;

        /* Ignore kinematic bodies, they don't work with the mouse joint */
        if (hitBody.getType() == BodyType.KinematicBody)
            return false;

        if (hitBody.equals(this.body)) {
            MouseJointDef def = new MouseJointDef();
            def.bodyA = groundBody;
            def.bodyB = hitBody;
            def.collideConnected = true;
            def.target.set(testPoint.x, testPoint.y);
            def.maxForce = a_max * hitBody.getMass();

            startPoint.set(testPoint.x, testPoint.y);
            mouseJoint = (MouseJoint)((body.getWorld()).createJoint(def));
            hitBody.setAwake(true);
        }
        return false;
    }
    /** another temporary vector **/
    Vector2 target = new Vector2();

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        /* If a mouse joint exists we simply update
         * the target of the joint based on the new
         * mouse coordinates
         * */
        if (mouseJoint != null) {
            stage.getCamera().unproject(testPoint.set(x, y, 0));
            testPoint.x = MathUtils.clamp(testPoint.x * G.WORLD_TO_BOX, startPoint.x - 3, startPoint.x + 3);
            testPoint.y = MathUtils.clamp(testPoint.y * G.WORLD_TO_BOX, startPoint.y - 3, startPoint.y + 3);
            mouseJoint.setTarget(target.set(testPoint.x, testPoint.y));
        }
        return false;
    }

    @Override public boolean touchUp(int x, int y, int pointer, int button) {
        /* If a mouse joint exists we simply destroy it */
        if (mouseJoint != null) {
            body.getWorld().destroyJoint(mouseJoint);
            mouseJoint = null;

            float fx = (startPoint.x - testPoint.x) * F_impulse;
            float fy = (startPoint.y - testPoint.y) * F_impulse;
            hitBody.applyLinearImpulse(new Vector2(fx, fy), hitBody.getPosition());
            slingshotRelease();
        }
        return false;
    }

    /**
     * Optional tasks to be done after releasing slingshot
     */
    protected abstract void slingshotRelease();

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    /**
     * Get slingshot state
     * @return true = slingshot enabled, false = slingshot disabled
     */
    public boolean getSlingshotState() {
        return slingshotEnabled;
    }

    /**
     * Set slingshot state
     * @param state true = slingshot enabled, false = slingshot disabled
     */
    public void setSlingshotState(boolean state) {
        slingshotEnabled = state;
    }

    public Body getBody() {
        return body;
    }
}
