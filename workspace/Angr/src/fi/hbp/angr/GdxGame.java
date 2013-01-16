package fi.hbp.angr;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import fi.hbp.angr.screens.GameScreen;
import fi.hbp.angr.screens.SplashScreen;

public class GdxGame extends Game {
    @Override
    public void create() {
        GameScreen gs = new GameScreen();
        Screen splash = new SplashScreen(this, gs);
        setScreen(splash);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
