package fi.hbp.angr;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import fi.hbp.angr.models.Destructible;

/**
 * Game stage
 */
public class GameStage extends Stage {
    private World world;
    private Box2DDebugRenderer renderer;
    private OrthographicCamera debugCamera;

    /* Variables for key commands */
    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveUp;
    private boolean moveDown;
    private boolean enableDebugCamera = false;

    private CameraFilter camFilt = new CameraFilter(0.04f);
    private Body cameraFollowBody;
    private boolean destructibleCameraFollowBody = false;

    /**
     * Constructor for GameStage
     * @param width width of the game stage viewport in pixels
     * @param height height of the game stage viewport in pixels
     * @param world Box2D physics world
     */
    public GameStage(float width, float height, World world) {
        super(width, height, false);

        this.world = world;
        if (G.DEBUG) {
            renderer = new Box2DDebugRenderer(true, true, true, true, true);
            debugCamera = new OrthographicCamera(
                    this.getWidth() * G.WORLD_TO_BOX,
                    this.getHeight() * G.WORLD_TO_BOX);
            renderer.setDrawAABBs(false);
            renderer.setDrawJoints(true);
            renderer.setDrawVelocities(true);
            renderer.setDrawBodies(true);

            Vector3 pos = this.getCamera().position;
            debugCamera.position.set(pos.x*G.WORLD_TO_BOX, pos.y*G.WORLD_TO_BOX, 0);
            debugCamera.update();
        }
    }

    @Override
    public void draw() {
        super.draw();

        if (!enableDebugCamera) {
            updateCameraFollow();
        }
        else {
            updateDebugCamera();
            renderer.render(world, debugCamera.combined);
        }
    }

    public void setCameraFollow(Body body) {
        this.cameraFollowBody = body;
        this.destructibleCameraFollowBody = false;

        if (body == null)
            return;

        if (body.getUserData() != null) {
            if (Destructible.class.isInstance(cameraFollowBody)) {
                destructibleCameraFollowBody = true;
            }
        }
    }

    private void updateCameraFollow() {
        if (cameraFollowBody == null) {
            return;
        }

        if (destructibleCameraFollowBody) {
            if (((Destructible)cameraFollowBody.getUserData()).isDestroyed()) {
                this.cameraFollowBody = null;
                return;
            }
        }

        float x = cameraFollowBody.getPosition().x * G.BOX_TO_WORLD;
        float y = cameraFollowBody.getPosition().y * G.BOX_TO_WORLD;
        float dt = Gdx.graphics.getDeltaTime();
        camFilt.updateX(x, dt);
        camFilt.updateY(y, dt);

        this.getCamera().position.x = clampX(camFilt.getX());
        this.getCamera().position.y = clampY(camFilt.getY());
    }

    /**
     * Updates position of the debug camera
     */
    private void updateDebugCamera() {
        float dx = 0;
        float dy = 0;

        if(moveLeft) {
            dx -= 50;
        }
        if(moveRight) {
            dx += 50;
        }
        if(moveDown) {
            dy -= 50;
        }
        if(moveUp) {
            dy += 50;
        }

        if(dx != 0 || dy != 0) {
            Vector3 pos = this.getCamera().position;
            float x = clampX(pos.x + dx);
            float y = clampY(pos.y + dy);
            this.getCamera().position.set(x, y, 0);
            debugCamera.position.set(x * G.WORLD_TO_BOX, y * G.WORLD_TO_BOX, 0);
            this.getCamera().update();
            debugCamera.update();
        }
    }

    private float clampX(float x) {
        return MathUtils.clamp(x, this.getWidth()/2, this.getWidth()*2);
    }

    private float clampY(float y) {
        return MathUtils.clamp(y, this.getHeight()/2, this.getHeight()*1);
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(Gdx.app.getType().equals(ApplicationType.Android)) {
            return false;
        }
        if (!G.DEBUG)
            return false;

        switch(keycode) {
        case Keys.LEFT:
            moveLeft = true;
            break;
        case Keys.RIGHT:
            moveRight = true;
            break;
        case Keys.UP:
            moveUp = true;
            break;
        case Keys.DOWN:
            moveDown = true;
            break;
        case Keys.D:
            enableDebugCamera = enableDebugCamera ? false : true;
            break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(Gdx.app.getType().equals(ApplicationType.Android)) {
            return false;
        }
        switch(keycode) {
        case Keys.LEFT:
            moveLeft = false;
            break;
        case Keys.RIGHT:
            moveRight = false;
            break;
        case Keys.UP:
            moveUp = false;
            break;
        case Keys.DOWN:
            moveDown = false;
            break;
        }
        return false;
    }

    @Override
    public void dispose () {
        renderer.dispose();
        renderer = null;
    }
}
