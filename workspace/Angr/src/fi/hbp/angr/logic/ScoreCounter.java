package fi.hbp.angr.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import fi.hbp.angr.models.HudActor;

public class ScoreCounter implements endOfGameAction, HudActor {
    private BitmapFont font;
    private int score = 0;
    private int highScore = 0;
    private int starScale = 1;

    public void loadAssets() {
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.setScale(1.4f);
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
        font.draw(batch, "Score: " + score, Gdx.graphics.getWidth() - 180, Gdx.graphics.getHeight() - 20);
    }
}
