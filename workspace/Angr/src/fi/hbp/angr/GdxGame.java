package fi.hbp.angr;

import com.badlogic.gdx.Game;

import fi.hbp.angr.logic.GameState;
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

        gameEnd = new SummaryScreen();
    }

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
