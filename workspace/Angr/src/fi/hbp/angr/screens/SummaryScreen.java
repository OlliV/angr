package fi.hbp.angr.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fi.hbp.angr.G;
import fi.hbp.angr.GdxGame;
import fi.hbp.angr.UpCounter;
import fi.hbp.angr.logic.GameState;
import fi.hbp.angr.models.levels.TestLevel;

/**
 * Screen that is shown when the game ends due to either win or game over.
 * TODO UI Buttons
 */
public class SummaryScreen extends AbstMenuScreen implements Button.ButtonAction {
    /**
     * GdxGame.
     */
    private final GdxGame game;
    /**
     * Textures for badges.
     */
    private TextureRegion[] badges = new TextureRegion[3];

    /**
     * Return to main menu button.
     */
    private Button mainMenuButton;
    /**
     * Restart level/Next level button.
     */
    private Button levelButton;
    /**
     * Show leaderboard button.
     */
    private Button leaderboardButton;

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
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (levelClearedText != null) {
            drawStats(batch);
        } else {
            getFont().draw(batch, "ERROR: Final score not calculated!", 0.0f, 0.0f);
        }

        float btn_x = -(float)(Gdx.graphics.getWidth() / 2) + 20f;
        float btn_y = -(float)(Gdx.graphics.getHeight() / 2) + 40f;

        mainMenuButton.draw(batch, "Main menu", btn_x, btn_y + 50);
        levelButton.draw(batch, buttonText, btn_x, btn_y);

        /* At the moment Leaderboard is only available for Android devices. */
        if (Gdx.app.getType() == ApplicationType.Android) {
            leaderboardButton.draw(batch, "Leaderboard", btn_x + 500, btn_y);
        }
    }

    /**
     * Draw score counters and badges
     */
    private void drawStats(SpriteBatch batch) {
        BitmapFont font = getFont();

        /* Level cleared text */
        font.draw(batch, levelClearedText,
                -(levelClearedText.length() * font.getSpaceWidth()) / 2.0f,
                font.getXHeight() + 250.0f);

        drawScore(batch);
        drawBadges(batch);
    }

    /**
     * Draw score counters
     */
    private void drawScore(SpriteBatch batch) {
        BitmapFont font = getFont();

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
    private void drawBadges(SpriteBatch batch) {
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
    public void onShow() {
        BitmapFont font = getFont();

        mainMenuButton = new Button(getCamera(), font, this, 0);
        levelButton = new Button(getCamera(), font, this, 1);
        leaderboardButton = new Button(getCamera(), font, this, 2);

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
            // Send score to the scoreboard
            G.scoreboard.submitScore(6519, gs.getScore(), gs.getBadges());
        } else {
            levelClearedText = "Level failed!";
        }

        bareScoreCnt = new UpCounter(bareScore, 0.01f, true);
        additionalPointsCnt = new UpCounter(additionalPoints, 0.01f, true);
        scoreText = " = " + gs.getScore();

        badgeLevel = gs.getBadges();
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
    public void buttonAction(int id) {
        if (id == 0) {
            game.showMainMenu();
        } else if (id == 1) {
            game.nextLevel(new TestLevel());
        } else if (id == 2) {
            G.scoreboard.showHighScore();
        }
    }
}
