package fi.hbp.angr;

import com.badlogic.gdx.assets.AssetManager;

public class G {
    /**
     * Global static assets manager
     *
     * @note As there is no universal support for threaded  access to the OpenGL
     * context from multiple threads and as its exclusively prohibited in gdx
     * a global asset manager is needed. This asset manager makes it easier to
     * load new assets asynchronously without blocking rendering.
     */
    private static AssetManager assetManager = new AssetManager();

    public static final float BOX_TO_WORLD = 100.0f;
    public static final float WORLD_TO_BOX = 1.0f / BOX_TO_WORLD;
    public static final boolean DEBUG = true;

    /**
     * Get global static assets manager
     * @return Global asset manager.
     */
    public static AssetManager getAssetManager() {
        return assetManager;
    }
}
