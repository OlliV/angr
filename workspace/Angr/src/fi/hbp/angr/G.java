package fi.hbp.angr;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;

import fi.hbp.angr.logic.endOfGameAction;

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

    public ArrayList<endOfGameAction> gameOverActions = new ArrayList<endOfGameAction>();
    public ArrayList<endOfGameAction> gameWinActions = new ArrayList<endOfGameAction>();

    /**
     * Get global static assets manager
     * @return Global asset manager.
     */
    public static AssetManager getAssetManager() {
        return assetManager;
    }
}
