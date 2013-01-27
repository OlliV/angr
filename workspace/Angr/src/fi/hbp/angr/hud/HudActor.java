package fi.hbp.angr.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
