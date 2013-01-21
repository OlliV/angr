package fi.hbp.angr.models.items;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import fi.hbp.angr.G;
import fi.hbp.angr.models.AssetContainer;

public class Grenade extends Actor implements InputProcessor {
    private static final String MODEL_NAME = "grenade";
    private static final String TEXTURE_PATH = "data/" + MODEL_NAME + ".png";
    private Stage stage;
    private World world;
    public Body body;
    private Vector2 modelOrigin;
    private Sprite sprite;

    /* Input processing/Controls */
    protected MouseJoint mouseJoint = null;
    private Vector3 testPoint = new Vector3();
    private Vector2 startPoint = new Vector2();
    private Body groundBody;
    private Body hitBody = null;

    public static void preload() {
        G.getAssetManager().load(TEXTURE_PATH, Texture.class);
    }

    public static void initAssets(AssetContainer as, BodyEditorLoader bel) {
        as.texture = G.getAssetManager().get(
                bel.getImagePath(MODEL_NAME),
                Texture.class);

        as.bd = new BodyDef();
        as.bd.type = BodyType.DynamicBody;
        as.bd.active = true;
        as.bd.position.set(0, 0);

        as.fd = new FixtureDef();
        as.fd.density = 3.0f;
        as.fd.friction = 0.3f;
        as.fd.restitution = 0.3f;

        //as.fd.filter.categoryBits = CollisionFilterMasks.GRENADE;
        //as.fd.filter.maskBits = CollisionFilterMasks.ENEMY | CollisionFilterMasks.WALL | CollisionFilterMasks.GRENADE;
    }

    public Grenade(Stage stage, World world, BodyEditorLoader bel, AssetContainer as, float x, float y, float angle) {
        this.stage = stage;
        this.world = world;

        as.bd.position.set(new Vector2(x * G.WORLD_TO_BOX, y * G.WORLD_TO_BOX));
        body = world.createBody(as.bd);
        body.setUserData(this);
        sprite = new Sprite(as.texture);

        bel.attachFixture(body,
                          MODEL_NAME,
                          as.fd,
                          sprite.getWidth() * G.WORLD_TO_BOX);
        modelOrigin = bel.getOrigin(MODEL_NAME, sprite.getWidth()).cpy();
        sprite.setOrigin(modelOrigin.x, modelOrigin.y);
        sprite.setPosition(x, y);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));

        // we also need an invisible zero size ground body
        // to which we can connect the mouse joint
        BodyDef bodyDef = new BodyDef();
        groundBody = world.createBody(bodyDef);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        Vector2 pos = body.getPosition();
        sprite.setPosition(pos.x * G.BOX_TO_WORLD - modelOrigin.x,
                           pos.y * G.BOX_TO_WORLD - modelOrigin.y);
        sprite.setOrigin(modelOrigin.x, modelOrigin.y);
        sprite.setRotation((float)(body.getAngle() * MathUtils.radiansToDegrees));
        sprite.draw(batch, parentAlpha);
    }

    QueryCallback callback = new QueryCallback() {
        @Override
        public boolean reportFixture (Fixture fixture) {
            // if the hit point is inside the fixture of the body
            // we report it
            if (fixture.testPoint(testPoint.x, testPoint.y)) {
                hitBody = fixture.getBody();
                return false;
            } else
                return true;
        }
    };

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Translate the mouse coordinates to world coordinates
        stage.getCamera().unproject(testPoint.set(screenX, screenY, 0));
        testPoint.x *= G.WORLD_TO_BOX;
        testPoint.y *= G.WORLD_TO_BOX;

        hitBody = null;
        world.QueryAABB(callback,
                        testPoint.x - 0.0001f,
                        testPoint.y - 0.0001f,
                        testPoint.x + 0.0001f,
                        testPoint.y + 0.0001f);
        if (hitBody == groundBody) hitBody = null;

        if (hitBody == null)
            return false;

        // Ignore kinematic bodies, they don't work with the mouse joint
        if (hitBody.getType() == BodyType.KinematicBody)
            return false;

        if (hitBody.equals(this.body)) {
                MouseJointDef def = new MouseJointDef();
                def.bodyA = groundBody;
                def.bodyB = hitBody;
                def.collideConnected = true;
                def.target.set(testPoint.x, testPoint.y);
                def.maxForce = 1000.0f * hitBody.getMass();

                startPoint.set(testPoint.x, testPoint.y);
                mouseJoint = (MouseJoint)world.createJoint(def);
                hitBody.setAwake(true);

                return true;
        }
        return false;
    }
    /** another temporary vector **/
    Vector2 target = new Vector2();

    @Override
    public boolean touchDragged (int x, int y, int pointer) {
            // If a mouse joint exists we simply update
            // the target of the joint based on the new
            // mouse coordinates
            if (mouseJoint != null) {
                stage.getCamera().unproject(testPoint.set(x, y, 0));
                testPoint.x = MathUtils.clamp(testPoint.x * G.WORLD_TO_BOX, startPoint.x - 3, startPoint.x + 3);
                testPoint.y = MathUtils.clamp(testPoint.y * G.WORLD_TO_BOX, startPoint.y - 3, startPoint.y + 3);
                mouseJoint.setTarget(target.set(testPoint.x, testPoint.y));
                return true;
            }
            return false;
    }

    @Override public boolean touchUp (int x, int y, int pointer, int button) {
            // If a mouse joint exists we simply destroy it
            if (mouseJoint != null) {
                    world.destroyJoint(mouseJoint);
                    mouseJoint = null;

                    float fx = (startPoint.x - testPoint.x) * 40;
                    float fy = (startPoint.y - testPoint.y) * 40;
                    hitBody.applyLinearImpulse(new Vector2(fx, fy), hitBody.getPosition());

                    return true;
            }
            return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }
}
