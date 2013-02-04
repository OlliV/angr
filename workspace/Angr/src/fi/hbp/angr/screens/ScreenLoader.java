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
    private final Game game;
    private final Screen screen;
    private Lock l = new ReentrantLock();
    private boolean loaded = false;

    public ScreenLoader(Game game, Screen screen) {
        this.game = game;
        this.screen = screen;
    }

    /**
     * Start preloading assets
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
     * Swap to the preloaded screen
     */
    public void swap() {
        l.lock();
        try {
            if (loaded == false) {
                Gdx.app.error("ScreenLoader",
                        "swap() was called before preload was completed.");
                Gdx.app.exit();
            }
            G.getAssetManager().finishLoading();
            game.setScreen(screen);
        } finally {
            l.unlock();
        }
    }
}
