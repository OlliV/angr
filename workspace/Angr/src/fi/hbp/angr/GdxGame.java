package fi.hbp.angr;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import fi.hbp.angr.logic.GameState;
import fi.hbp.angr.models.levels.Level;
import fi.hbp.angr.screens.GameScreen;
import fi.hbp.angr.screens.MainMenuScreen;
import fi.hbp.angr.screens.PauseScreen;
import fi.hbp.angr.screens.SummaryScreen;

public class GdxGame extends Game {
    /**
     * Main menu.
     */
    private MainMenuScreen mainMenu;
    /**
     * Actual game is on this screen.
     */
    private GameScreen gameScreen;
    /**
     * Game summary/score is shown on this screen.
     */
    private SummaryScreen gameEnd;
    /**
     * Pause screen.
     */
    private PauseScreen pauseScreen;

    @Override
    public void create() {
        if (G.scoreboard == null) {
            Gdx.app.exit();
        }

        mainMenu = new MainMenuScreen(this);
        gameScreen = new GameScreen(this);
        gameEnd = new SummaryScreen(this);
        pauseScreen = new PauseScreen(this);

        setScreen(mainMenu);
    }

    /**
     * Show main menu.
     */
    public void showMainMenu() {
        setScreen(mainMenu);
    }

    /**
     * Load a next level.
     * @param level level that should be loaded.
     */
    public void nextLevel(Level level) {
        gameScreen.loadLevel(level);
    }

    /**
     * Show pause screen.
     * @param currentScreen currently showing screen for getting back.
     */
    public void showPauseScreen(Screen currentScreen) {
        pauseScreen.setLastScreen(currentScreen);
        setScreen(pauseScreen);
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
