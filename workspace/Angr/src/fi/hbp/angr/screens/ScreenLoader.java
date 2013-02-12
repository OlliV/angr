package fi.hbp.angr.screens;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import fi.hbp.angr.G;
import fi.hbp.angr.Preloadable;

/**
 * Preload a given Screen.
 *
 * Creates an instance of a screen of given type and loads its assets. This
 * is useful and works only if constructor of the new screen class loads all
 * assets needed.
 */
public class ScreenLoader {
    /**
     * Game.
     */
    private final Game game;
    /**
     * Screen that will be preloaded and then switched to.
     */
    private final Screen screen;
    /**
     * Reentrant lock.
     */
    private final Lock l = new ReentrantLock();
    /**
     * Loading status.
     * true if preloading has been started.
     * This doesn't actually tell whether preloading is ready or not.
     */
    private boolean loaded = false;

    /**
     * Constructor for ScreenLoader.
     * @param game main game object.
     * @param screen screen to be loaded.
     */
    public ScreenLoader(Game game, Screen screen) {
        this.game = game;
        this.screen = screen;
    }

    /**
     * Fills preload queue.
     */
    public void start() {
        l.lock();
        try {
            if (Preloadable.class.isInstance(screen)) {
                ((Preloadable)screen).preload();
            }
            loaded = true;
        } finally {
            l.unlock();
        }
    }

    /**
     * Updates the AssetManager, keeping it loading any assets in the preload queue.
     * @return true if all loading is finished.
     */
    public boolean update() {
        return G.getAssetManager().update();
    }

    /**
     * Swap to the preloaded screen.
     */
    public void swap() {
        l.lock();
        try {
            if (loaded == false) {
                Gdx.app.error("ScreenLoader",
                        "swap() was called before preload was started.");
                Gdx.app.exit();
            }
            G.getAssetManager().finishLoading();
            game.setScreen(screen);
        } finally {
            l.unlock();
        }
    }
}
