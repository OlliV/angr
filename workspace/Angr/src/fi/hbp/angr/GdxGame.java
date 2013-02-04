package fi.hbp.angr;

import com.badlogic.gdx.Game;

import fi.hbp.angr.logic.GameState;
import fi.hbp.angr.models.levels.TestLevel;
import fi.hbp.angr.screens.GameEndScreen;
import fi.hbp.angr.screens.GameScreen;

public class GdxGame extends Game {
    private GameScreen gameScreen;
    private GameEndScreen gameEnd;

    @Override
    public void create() {
        gameScreen = new GameScreen(this);
        gameScreen.loadLevel(new TestLevel());

        gameEnd = new GameEndScreen();
    }

    public void endOfGame(GameState gs) {
        gameEnd.setGameState(gs);
        setScreen(gameEnd);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
