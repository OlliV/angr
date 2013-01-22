package fi.hbp.angr.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;

import fi.hbp.angr.models.HudActor;

public class ScoreCounter implements endOfGameAction, HudActor {
    private BitmapFont font;
    private int score = 0;
    private int highScore = 0;
    private int starScale = 1;

    public void loadAssets() {
        FileHandle fontFile = Gdx.files.internal("fonts/BistroBlock.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        font = generator.generateFont(45);
        generator.dispose();

        font.setColor(Color.WHITE);
        font.setScale(1.0f);
    }

    public void addPoints(int value) {
        score += value;
    }

    public void init(int highScore, int starScale) {
        this.highScore = highScore;
        this.starScale = (starScale > 0) ? starScale : 1;
    }

    public void clear() {
        score = 0;
        highScore = 0;
    }

    public int getScore() {
        return score;
    }

    public void clearScore() {
        score = 0;
    }

    public int getStars() {
        return MathUtils.clamp(score / starScale, 0, 3);
    }

    public int getHighScore() {
        return highScore;
    }

    @Override
    public void gameOverAction() {
        this.clearScore();
    }

    @Override
    public void gameWinAction() {
        if (score > highScore) {
            highScore = score;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        font.draw(batch, "Score: " + score, Gdx.graphics.getWidth() - font.getSpaceWidth() * 15, Gdx.graphics.getHeight() - 20);
    }

    @Override
    public String toString() {
        return "Score: " + score;
    }
}
