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
public class HudGameStateDisplay implements Hud.HudActor {
    private BitmapFont font;
    private final GameState gameState;

    /**
     * Constructor for HudScoreGameStateDisplay.
     * @param gameState gameState object where from current score status can be
     * polled.
     */
    public HudGameStateDisplay(GameState gameState) {
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
        font.draw(batch, "Score: " + gameState.getScore(),
                (float)Gdx.graphics.getWidth() - font.getSpaceWidth() * 15.0f,
                (float)Gdx.graphics.getHeight() - 20.0f);

        GameState.Grenades grenades = gameState.getGrenades();
        font.draw(batch, "Grenades: " + grenades.getCount() + "/" + grenades.originalCount,
                15.0f,
                (float)Gdx.graphics.getHeight() - 20.0f);
    }
}
