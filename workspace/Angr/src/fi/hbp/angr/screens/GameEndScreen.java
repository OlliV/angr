package fi.hbp.angr.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
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

import fi.hbp.angr.logic.GameState;

/**
 * Screen that is shown when game ends to win or game over.
 */
public class GameEndScreen implements Screen {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture grenadierTexture;
    private Sprite grenadierSprite;
    private Texture badgeTexture;
    private TextureRegion[] badges = new TextureRegion[3];
    private BitmapFont font;

    private GameState gs;

    public GameEndScreen() {
        /* Load assets */
        FileHandle fontFile = Gdx.files.internal("fonts/BistroBlock.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        font = generator.generateFont(45);
        generator.dispose();

        font.setColor(Color.BLACK);
        font.setScale(1.0f);
    }

    public void setGameState(GameState gs) {
        this.gs = gs;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        if (gs != null) {
            grenadierSprite.draw(batch);
            drawStats();
        } else {
            font.draw(batch, "ERROR: GameState not set!", 0.0f, 0.0f);
        }
        batch.end();
    }

    private void drawStats() {
        /* Score */
        font.draw(batch, gs.toString(),
                -(gs.toString().length() * font.getSpaceWidth()) / 2.0f,
                font.getXHeight() + 150.0f);

        /* "Stars" */
        showBadges(batch, gs.getBadges());
    }

    /**
     * Show achieved badges.
     * @param batch
     * @param lvl
     */
    private void showBadges(SpriteBatch batch, int lvl) {
        switch(lvl) {
        case 3:
            batch.draw(badges[2], (float)badges[2].getRegionWidth(), 0.0f);
        case 2:
            batch.draw(badges[1], -(float)badges[1].getRegionWidth() / 2.0f, 0.0f);
        case 1:
            batch.draw(badges[0], -2f * (float)badges[0].getRegionWidth(), 0.0f);
        }
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void show() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(width, height);
        batch = new SpriteBatch();

        loadGrenadierSprite(width, height);
        loadBadges();
    }

    /**
     * Load grenade sprite.
     * @param width is screen width.
     * @param height is screen height.
     */
    private void loadGrenadierSprite(float width, float height) {
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
     * Load badge texture regions.
     */
    private void loadBadges() {
        badgeTexture = new Texture(Gdx.files.internal("data/badges.png"));

        badges[0] = new TextureRegion(badgeTexture,
                0, 0,
                badgeTexture.getHeight(), badgeTexture.getHeight());
        badges[1] = new TextureRegion(badgeTexture,
                badgeTexture.getHeight(), 0,
                badgeTexture.getHeight(), badgeTexture.getHeight());
        badges[2] = new TextureRegion(badgeTexture,
                2 * badgeTexture.getHeight(), 0,
                badgeTexture.getHeight(), badgeTexture.getHeight());
    }

    @Override
    public void hide() {
        /* No need for old game state anymore */
        gs = null;
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
        // TODO Auto-generated method stub

    }

}
