package fi.hbp.angr;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Game stage
 */
public class GameStage extends Stage {
    private World world;
    private Box2DDebugRenderer renderer;
    private OrthographicCamera debugCamera;

    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveUp;
    private boolean moveDown;
    private boolean enableDebugCamera = false;

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
        if (enableDebugCamera) {
            updateDebugCamera();
            renderer.render(world, debugCamera.combined);
        }
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
            float x = MathUtils.clamp(pos.x+dx, this.getWidth()/2, this.getWidth()*2);
            float y = MathUtils.clamp(pos.y+dy, this.getHeight()/2, this.getHeight()*1);
            this.getCamera().position.set(x, y, 0);
            debugCamera.position.set(x*G.WORLD_TO_BOX, y*G.WORLD_TO_BOX, 0);
            this.getCamera().update();
            debugCamera.update();
        }
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
