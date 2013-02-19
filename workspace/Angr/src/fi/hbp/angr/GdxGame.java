package fi.hbp.angr;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import fi.hbp.angr.logic.GameState;
import fi.hbp.angr.models.levels.Level;
import fi.hbp.angr.screens.GameScreen;
import fi.hbp.angr.screens.MainMenuScreen;
import fi.hbp.angr.screens.SummaryScreen;

public class GdxGame extends Game {
    /**
     * Actual game is on this screen.
     */
    private GameScreen gameScreen;
    /**
     * Game summary/score is shown on this screen.
     */
    private SummaryScreen gameEnd;

    @Override
    public void create() {
        if (G.scoreboard == null) {
            Gdx.app.exit();
        }

        gameScreen = new GameScreen(this);
        gameEnd = new SummaryScreen(this);

        setScreen(new MainMenuScreen(this));
    }

    /**
     * Load a next level.
     * @param level level that should be loaded.
     */
    public void nextLevel(Level level) {
        gameScreen.loadLevel(level);
    }

    /**
     * This is called at the end of game.
     * Screen will be changed to summary screen.
     * @param gs game state object.
     */
    public void endOfGame(GameState gs) {
        gameEnd.calcFinalScore(gs);
        setScreen(gameEnd);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
