package fi.hbp.angr;

import com.badlogic.gdx.Game;

import fi.hbp.angr.logic.GameState;
import fi.hbp.angr.models.levels.Level;
import fi.hbp.angr.models.levels.TestLevel;
import fi.hbp.angr.screens.GameScreen;
import fi.hbp.angr.screens.SummaryScreen;

public class GdxGame extends Game {
    private GameScreen gameScreen;
    private SummaryScreen gameEnd;

    @Override
    public void create() {
        gameScreen = new GameScreen(this);
        gameScreen.loadLevel(new TestLevel());

        gameEnd = new SummaryScreen(this);
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
