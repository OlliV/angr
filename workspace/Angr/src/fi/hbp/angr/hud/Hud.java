package fi.hbp.angr.hud;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fi.hbp.angr.G;

/**
 * Drawable HUD.
 */
public class Hud {
    /**
     * Font for drawing HUD.
     */
    private BitmapFont font = new BitmapFont();
    /**
     * Batch for drawing this HUD.
     */
    private SpriteBatch hudBatch;
    /**
     * List of actors in this HUD.
     */
    private ArrayList<HudActor> actors = new ArrayList<HudActor>();

    /**
     * HudActor that can be drawn by a Hud object.
     */
    public interface HudActor {
        /**
         * Draw this HudActor now.
         * @param batch sprite batch to be used for drawing.
         */
        public void draw(SpriteBatch batch);
    }

    /**
     * Constructor for Hud.
     */
    public Hud() {
        hudBatch = new SpriteBatch();
        font.setColor(Color.RED);
        font.setScale(1.0f);
    }

    /**
     * Adds the specified HudActor to the list of actors that will
     * be drawn on the screen.
     * @param actor element that is added to the list.
     */
    public void addActor(HudActor actor) {
        actors.add(actor);
    }

    /**
     * Removes all of the elements from the HUD actor list.
     * The list will be empty after this call returns.
     */
    public void clear() {
        actors.clear();
    }

    /**
     * Draw this HUD.
     */
    public void draw() {
        hudBatch.begin();
        if (G.DEBUG)
            font.draw(hudBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 30);
        for (HudActor actor : actors) {
            actor.draw(hudBatch);
        }
        hudBatch.end();
    }

    /**
     * Resize HUD.
     * @param width of screen.
     * @param height of screen.
     */
    public void resize(int width, int height) {
        /* There must be a clever way to transform the matrix instead of this. */
        hudBatch = new SpriteBatch();
    }

}
