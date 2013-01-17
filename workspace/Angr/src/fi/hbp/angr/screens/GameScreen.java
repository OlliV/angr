package fi.hbp.angr.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import fi.hbp.angr.G;
import fi.hbp.angr.GameStage;
import fi.hbp.angr.Preloadable;
import fi.hbp.angr.models.Level;

public class GameScreen implements Screen, Preloadable {
    private World world;
    private Stage stage;
    private String levelName;

    public GameScreen(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public void preload() {
        G.getAssetManager().load("data/" + levelName + ".png", Texture.class);
    }

    @Override
    public void unload() {
        G.getAssetManager().unload("data/" + levelName + ".png");
    }

    @Override
    public void render(float delta) {
        world.step(Gdx.app.getGraphics().getDeltaTime(), 10, 10);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -8), true);
        stage = new GameStage(2000, 2000, false, world);
        Gdx.input.setInputProcessor(stage);

        // Add map/level actor
        Level level = new Level(levelName, world);
        stage.addActor(level);

        // Add player
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
