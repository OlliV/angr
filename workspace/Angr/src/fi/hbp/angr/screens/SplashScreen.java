package fi.hbp.angr.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;

public class SplashScreen extends Timer.Task implements Screen {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;

    private Timer splashTimer = new Timer();
    private Game g;
    private Class<? extends Screen> screen;
    private Screen gs;

    /**
     *
     * @param g Main game.
     * @param gs Screen to be shown after this splash screen.
     */
    public SplashScreen(Game g, Class<? extends Screen> screen) {
        this.g = g;
        this.screen = screen;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }

    /**
     * Initializes this splash screen
     */
    @Override
    public void show() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(1, height / width);
        batch = new SpriteBatch();

        texture = new Texture(Gdx.files.internal("data/libgdx.png"));
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);

        sprite = new Sprite(region);
        sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);

        // Change to Screen gs given in constructor after a short delay
        splashTimer.scheduleTask(this, 1.5f);

        // Render once now so we dont show blank screen
        this.render(0);

        /* We don't want to waste previously set delay so we load the
         * next screen here and now. */
        try {
            gs = screen.newInstance();
        } catch (InstantiationException ex) {
            Gdx.app.error("Angr", ex.getMessage());
            splashTimer.clear();
            Gdx.app.exit();
        } catch (IllegalAccessException ex) {
            Gdx.app.error("Angr", ex.getMessage());
            splashTimer.clear();
            Gdx.app.exit();
        }
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
        batch.dispose();
        texture.dispose();
    }

    @Override
    public void run() {
        g.setScreen(gs);
    }
}
