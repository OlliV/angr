package fi.hbp.angr.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import fi.hbp.angr.logic.GameState;

/**
 * HudScoreCounter used to show current game score status.
 */
public class HudScoreCounter implements HudActor {
    private BitmapFont font;
    private GameState gameState;

    /**
     * Constructor for HudScoreCounter.
     * @param gameState gameState object where from current score status can be
     * polled.
     */
    public HudScoreCounter(GameState gameState) {
        this.gameState = gameState;

        /* Load assets */
        FileHandle fontFile = Gdx.files.internal("fonts/BistroBlock.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        font = generator.generateFont(45);
        generator.dispose();

        font.setColor(Color.WHITE);
        font.setScale(1.0f);
    }

    @Override
    public void draw(SpriteBatch batch) {
        font.draw(batch, gameState.toString(), Gdx.graphics.getWidth() - font.getSpaceWidth() * 15, Gdx.graphics.getHeight() - 20);
    }
}
