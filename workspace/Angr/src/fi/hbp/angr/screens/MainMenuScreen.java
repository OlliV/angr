package fi.hbp.angr.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fi.hbp.angr.G;
import fi.hbp.angr.GdxGame;
import fi.hbp.angr.models.levels.TestLevel;
import fi.hbp.angr.screens.Button.ButtonAction;

public class MainMenuScreen extends AbstMenuScreen implements ButtonAction {
    Button startButton;
    Button dashboardButton;

    public MainMenuScreen(GdxGame game) {
        super(game);
    }

    @Override
    public void draw(SpriteBatch batch) {
        startButton.draw(batch, "Start", 0, 0);
        dashboardButton.draw(batch, "Dashboard", 0, -50);
    }

    @Override
    public void onShow() {
        startButton = new Button(getCamera(), getFont(), this, 0);
        dashboardButton = new Button(getCamera(), getFont(), this, 1);
    }

    @Override
    public void buttonAction(int id) {
        if (id == 0) {
            getGame().nextLevel(new TestLevel());
        } else if (id == 1) {
            G.scoreboard.showScoreboard();
        }
    }

}
