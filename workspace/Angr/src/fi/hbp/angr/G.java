package fi.hbp.angr;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;

/**
 * Globals
 */
public class G {
    /**
     * Debug mode
     *
     * If this is set to true game will output some additional data and
     * enables debug camera functionality. Debug camera is enabled by
     * pressing d while in the game stage.
     */
    public static final boolean DEBUG = true;

    /**
     * Global static assets manager
     *
     * @note As there is no universal support for threaded  access to the
     * OpenGL context from multiple threads and as its exclusively prohibited
     * in gdx a global asset manager is needed. This asset manager makes it
     * easier to load new assets asynchronously without blocking rendering.
     */
    private static AssetManager assetManager = new AssetManager();

    /**
     * Conversion from Box2D to the sprite relative/pixel coordinates
     */
    public static final float BOX_TO_WORLD = 100.0f;

    /**
     * Conversion from sprite relative/pixel coordinates to Box2D coordinates
     */
    public static final float WORLD_TO_BOX = 1.0f / BOX_TO_WORLD;

    /**
     * Get global static assets manager
     * @return Global asset manager.
     */
    public static AssetManager getAssetManager() {
        return assetManager;
    }

    /**
     * Game scoreboard
     */
    public static Scoreboard scoreboard = null;

    /**
     * Game pause button
     */
    public static int pauseButton = Keys.P;
    public static int pauseButton1 = Keys.BACK;
}
