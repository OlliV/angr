package fi.hbp.angr.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fi.hbp.angr.G;
import fi.hbp.angr.GdxGame;
import fi.hbp.angr.models.levels.TestLevel;
import fi.hbp.angr.screens.Button.ButtonAction;

public class MainMenuScreen extends AbstMenuScreen implements ButtonAction {
    /**
     * GdxGame.
     */
    private final GdxGame game;
    private Button startButton;
    private Button dashboardButton;
    private Button exitButton;

    public MainMenuScreen(GdxGame game) {
       this.game = game;
    }

    @Override
    public void draw(SpriteBatch batch) {
        startButton.draw(batch, "Start", 0, 0);
        dashboardButton.draw(batch, "Dashboard", 0, -50);
        exitButton.draw(batch, "Exit", 0, -100);
    }

    @Override
    public void onShow() {
        startButton = new Button(getCamera(), getFont(), this, 0);
        dashboardButton = new Button(getCamera(), getFont(), this, 1);
        exitButton = new Button(getCamera(), getFont(), this, 2);
    }

    @Override
    public void buttonAction(int id) {
        if (id == 0) {
            game.nextLevel(new TestLevel());
        } else if (id == 1) {
            G.scoreboard.showScoreboard();
        } else if (id == 2) {
            Gdx.app.exit();
        }
    }

}
