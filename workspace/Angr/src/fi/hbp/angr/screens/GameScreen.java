package fi.hbp.angr.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;

import fi.hbp.angr.BodyFactory;
import fi.hbp.angr.Preloadable;
import fi.hbp.angr.hud.Hud;
import fi.hbp.angr.hud.HudScoreCounter;
import fi.hbp.angr.models.levels.Level;
import fi.hbp.angr.stage.GameStage;

/**
 * Screen used to show the actual game contents.
 */
public class GameScreen implements Screen, Preloadable {
    private GameStage stage;
    Level level;
    private Hud hud = new Hud();

    /**
     * Start the game
     * @param level the game level to be shown.
     */
    public GameScreen(Level level) {
        this.level = level;
    }

    @Override
    public void preload() {
        level.preload();
        BodyFactory.preload();
    }

    @Override
    public void unload() {
        level.unload();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0.75f, 1f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
        hud.draw();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void show() {
        int xsize = Gdx.graphics.getWidth() * 2;
        int ysize = Gdx.graphics.getHeight() * 2;
        stage = new GameStage(xsize, ysize);

        /* Create input multiplexer */
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);
        inputMultiplexer.addProcessor(stage);

        /* Create score counter for HUD */
        HudScoreCounter score;
        score = new HudScoreCounter(stage.getGameState());
        hud.addActor(score);

        // Create and add map/level actor
        BodyFactory bf = new BodyFactory(stage, inputMultiplexer);
        level.show(bf, stage.getGameState());
        stage.addActor(level);
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
        stage.dispose();
        this.unload();
    }
}
