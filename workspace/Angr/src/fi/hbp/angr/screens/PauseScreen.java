package fi.hbp.angr.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fi.hbp.angr.GdxGame;

public class PauseScreen extends AbstMenuScreen implements Button.ButtonAction {
    /**
     * GdxGame.
     */
    private final GdxGame game;
    /**
     * Resume game button.
     */
    private Button resumeButton;
    /**
     * Return to the main menu button.
     */
    private Button mainMenuButton;
    /**
     * Last active screen, presumably gameScreen.
     */
    private Screen lastScreen;

    public PauseScreen(GdxGame game) {
        this.game = game;
    }

    @Override
    public void draw(SpriteBatch batch) {
        resumeButton.draw(batch, "Resume", 0, 0);
        mainMenuButton.draw(batch, "Main menu", 0, 50);
    }

    public void setLastScreen(Screen lastScreen) {
        this.lastScreen = lastScreen;
    }

    @Override
    public void onShow() {
        resumeButton = new Button(getCamera(), getFont(), this, 0);
        mainMenuButton = new Button(getCamera(), getFont(), this, 1);
    }

    @Override
    public void buttonAction(int id) {
        if (id == 0) {
            game.setScreen(lastScreen);
        } else if (id == 1) {
            game.showMainMenu();
        }
    }

}
