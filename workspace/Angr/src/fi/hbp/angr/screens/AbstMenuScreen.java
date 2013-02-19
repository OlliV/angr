package fi.hbp.angr.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import fi.hbp.angr.GdxGame;

/**
 * Abstract class for easy menu creation.
 */
public abstract class AbstMenuScreen implements Screen {
    /**
     * GdxGame.
     */
    private final GdxGame game;

    /**
     * Camera for projection.
     */
    private OrthographicCamera camera;
    /**
     * Sprite batch for drawing.
     */
    private SpriteBatch batch;
    /**
     * Sprite of grenadier.
     */
    private Sprite grenadierSprite;
    /**
     * Font for drawing text and score.
     */
    private BitmapFont font;

    /**
     * Constructor for SummaryScreen class.
     */
    public AbstMenuScreen(GdxGame game) {
        this.game = game;

        /* Load assets */
        FileHandle fontFile = Gdx.files.internal("fonts/BistroBlock.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        font = generator.generateFont(45);
        generator.dispose();

        font.setColor(Color.BLACK);
        font.setScale(1.0f);
    }

    @Override
    public final void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        grenadierSprite.draw(batch);
        draw(batch);
        batch.end();
    }

    /**
     * Draws the menu screen. The SpriteBatch is configured to draw in
     * the parent's coordinate system. This draw method is convenient
     * to draw a rotated and scaled TextureRegion. SpriteBatch.begin()
     * has already been called on the SpriteBatch. If SpriteBatch.end()
     * is called to draw without the SpriteBatch then SpriteBatch.begin()
     * must be called before the method returns.
     */
    public abstract void draw(SpriteBatch batch);

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }

    /**
     * Called when this screen becomes the current screen for a Game.
     */
    public abstract void onShow();

    @Override
    public final void show() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(width, height);
        batch = new SpriteBatch();

        loadGrenadierSprite(width, height);

        onShow();
    }

    /**
     * Load grenade sprite.
     * @param width is screen width.
     * @param height is screen height.
     */
    private void loadGrenadierSprite(float width, float height) {
        Texture grenadierTexture;

        grenadierTexture = new Texture(Gdx.files.internal("data/grenadier.png"));
        grenadierTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        TextureRegion region = new TextureRegion(grenadierTexture,
                0, 0,
                grenadierTexture.getHeight(), grenadierTexture.getHeight());

        grenadierSprite = new Sprite(region);
        grenadierSprite.setSize(grenadierTexture.getWidth(), grenadierTexture.getWidth());
        grenadierSprite.setOrigin(grenadierSprite.getWidth() / 2f, grenadierSprite.getHeight() / 2f);
        grenadierSprite.setPosition(width / 2f - grenadierSprite.getWidth(), -height / 2f);
    }

    /**
     * Get camera.
     * @return current camera of this menu screen.
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     * Get default font.
     * @return bitmap font.
     */
    public BitmapFont getFont() {
        return font;
    }

    /**
     * Get GdxGame.
     * @return gdxGame.
     */
    public GdxGame getGame() {
        return game;
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
