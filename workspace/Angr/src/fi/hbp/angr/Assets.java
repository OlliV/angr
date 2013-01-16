package fi.hbp.angr;

import com.badlogic.gdx.assets.AssetManager;

/**
 * Global static assets manager
 *
 * @note As there is no universal support for threaded  access to the OpenGL
 * context from multiple threads and as its exclusively prohibited in gdx
 * a global asset manager is needed. This asset manager makes it easier to
 * load new assets asynchronously without blocking rendering.
 */
public class Assets {
    static AssetManager assetManager = new AssetManager();

    public static AssetManager getAssetManager() {
        return assetManager;
    }
}
