package fi.hbp.angr.screens;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import fi.hbp.angr.BodyFactory;
import fi.hbp.angr.GameStage;
import fi.hbp.angr.ItemDestructor;
import fi.hbp.angr.Preloadable;
import fi.hbp.angr.logic.ModelContactListener;
import fi.hbp.angr.logic.ScoreCounter;
import fi.hbp.angr.logic.endOfGameAction;
import fi.hbp.angr.models.Destructible;
import fi.hbp.angr.models.Hud;
import fi.hbp.angr.models.levels.Level;

public class GameScreen implements Screen, Preloadable, endOfGameAction {
    private InputMultiplexer inputMultiplexer;
    private Stage stage;
    private World world;
    Level level;
    ItemDestructor itdes;
    private ScoreCounter score = new ScoreCounter();
    private Hud hud = new Hud();

    public GameScreen(Level level) {
        this.level = level;
        score.loadAssets();
        hud.addActor(score);
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
        destroyActors();

        world.step(delta, 30, 30);
        Gdx.gl.glClearColor(0, 0.75f, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
        hud.draw();
    }

    private void destroyActors() {
        /* Remove destroyed actors/items and add points to a score counter. */
        if (!itdes.isEmpty()) {
            Iterator<Actor> it = itdes.getIterator();
            while(it.hasNext()) {
                Actor a = it.next();

                /* Update score counter */
                score.addPoints(((Destructible)a).getDatamageModel().getPoints());

                ((Destructible)a).getBody().setUserData(null);
                world.destroyBody(((Destructible)a).getBody());
                a.remove();
            }
            itdes.clear();
        }
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -8), true);

        int xsize = Gdx.graphics.getWidth() * 4;
        int ysize = Gdx.graphics.getHeight() * 4;
        stage = new GameStage(xsize, ysize, false, world);

        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);
        inputMultiplexer.addProcessor(stage);

        ModelContactListener mcl = new ModelContactListener();
        world.setContactListener(mcl);

        itdes = new ItemDestructor();

        // Create and add map/level actor
        BodyFactory bf = new BodyFactory(stage, world, inputMultiplexer, itdes);
        level.show(bf);
        score.clear();
        score.init(level.getHighScore(), level.getStarScale());
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

    @Override
    public void gameOverAction() {
        // TODO Auto-generated method stub

    }

    @Override
    public void gameWinAction() {
        // TODO Auto-generated method stub

    }
}
