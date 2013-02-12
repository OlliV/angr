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

import fi.hbp.angr.GdxGame;
import fi.hbp.angr.UpCounter;
import fi.hbp.angr.logic.GameState;
import fi.hbp.angr.models.levels.TestLevel;

/**
 * Screen that is shown when the game ends due to either win or game over.
 * TODO UI Buttons
 */
public class SummaryScreen implements Screen, Button.ButtonAction {
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
     * Textures for badges.
     */
    private TextureRegion[] badges = new TextureRegion[3];
    /**
     * Font for drawing text and score.
     */
    private BitmapFont font;
    /**
     * Restart level/Next level button.
     */
    private Button button;

    /**
     * Level cleared.
     * true if level was cleared.
     */
    private boolean levelCleared;
    /**
     * Level cleared or level failed text.
     */
    private String levelClearedText;
    /**
     * Score text.
     */
    private String scoreText;
    /**
     * Counter for score without additional points.
     */
    private UpCounter bareScoreCnt;
    /**
     * Additional points counter.
     */
    private UpCounter additionalPointsCnt;
    /**
     * Badges 0..3
     */
    private int badgeLevel;
    /**
     * Button text.
     * "Restart level" or "Next level".
     */
    private String buttonText = "Restart level";

    /**
     * Constructor for SummaryScreen class.
     */
    public SummaryScreen(GdxGame game) {
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
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        if (levelClearedText != null) {
            grenadierSprite.draw(batch);
            drawStats();
        } else {
            font.draw(batch, "ERROR: Final score not calculated!", 0.0f, 0.0f);
        }

        float btn_x = -(float)(Gdx.graphics.getWidth() / 2) + 20f;
        float btn_y = -(float)(Gdx.graphics.getHeight() / 2) + 40f;
        button.draw(batch, buttonText, btn_x, btn_y);

        batch.end();
    }

    /**
     * Draw score counters and badges
     */
    private void drawStats() {
        /* Level cleared text */
        font.draw(batch, levelClearedText,
                -(levelClearedText.length() * font.getSpaceWidth()) / 2.0f,
                font.getXHeight() + 250.0f);

        drawScore();
        drawBadges();
    }

    /**
     * Draw score counters
     */
    private void drawScore() {
        if (!bareScoreCnt.isStopped()) {
            bareScoreCnt.update(Gdx.graphics.getDeltaTime());

            String str = "" + bareScoreCnt.getValue();
            font.draw(batch, str,
                    -(str.length() * font.getSpaceWidth()) / 2.0f,
                    font.getXHeight() + 150.0f);
        } else if (!additionalPointsCnt.isStopped()) {
            additionalPointsCnt.update(Gdx.graphics.getDeltaTime());

            String str = bareScoreCnt.getValue() + " + " + additionalPointsCnt.getValue();
            font.draw(batch, str,
                    -(str.length() * font.getSpaceWidth()) / 2.0f,
                    font.getXHeight() + 150.0f);
        } else {
            String str = bareScoreCnt.getValue() + " + " + additionalPointsCnt.getValue() + scoreText;
            font.draw(batch, str,
                -(str.length() * font.getSpaceWidth()) / 2.0f,
                font.getXHeight() + 150.0f);
        }
    }

    /**
     * Show achieved badges.
     * @param lvl badge level.
     */
    private void drawBadges() {
        switch(badgeLevel) {
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

        button = new Button(camera, font, this, 0);

        loadGrenadierSprite(width, height);
        loadBadges();
    }

    /**
     * Calculate final score.
     */
    public void calcFinalScore(GameState gs) {
        int bareScore;
        int additionalPoints;

        bareScore = gs.getScore();
        levelCleared = gs.countFinalScore();
        additionalPoints = gs.getScore() - bareScore;

        if (levelCleared) {
            levelClearedText = "Level cleared!";
        } else {
            levelClearedText = "Level failed!";
        }

        bareScoreCnt = new UpCounter(bareScore, 0.01f, true);
        additionalPointsCnt = new UpCounter(additionalPoints, 0.01f, true);
        scoreText = " = " + gs.getScore();

        badgeLevel = gs.getBadges();
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
     * Load badge texture regions.
     */
    private void loadBadges() {
        Texture badgeTexture;

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

    @Override
    public void buttonAction(int id) {
        if (id == 0) {
            game.nextLevel(new TestLevel());
        }
    }
}
