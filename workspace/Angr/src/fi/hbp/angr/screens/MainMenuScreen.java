package fi.hbp.angr.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Application.ApplicationType;
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

    /**
     * List of buttons
     */
    private ArrayList<Button> buttons = new ArrayList<Button>();

    public MainMenuScreen(GdxGame game) {
       this.game = game;
    }

    @Override
    public void draw(SpriteBatch batch) {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).draw(batch, 0, i * -50);
        }
    }

    @Override
    public void onShow() {
        buttons.clear();

        buttons.add(new Button(getCamera(), getFont(), this, "Start", 0));

        /* There is real scoreboard only for Android as desktop scoreboard is
         * yet only a mock. */
        if (Gdx.app.getType() == ApplicationType.Android) {
            buttons.add(new Button(getCamera(), getFont(), this, "Scoreboard", 1));
        }

        buttons.add(new Button(getCamera(), getFont(), this, "Exit", 2));
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
