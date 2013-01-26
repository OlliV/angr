package fi.hbp.angr.logic;

import com.badlogic.gdx.math.MathUtils;

public class GameState {
    private int score = 0;
    private int highScore = 0;
    private int starScale = 1;
    private int enemyCount = 0;

    public void init(int highScore, int starScale, int enemies) {
        this.highScore = highScore;
        this.starScale = (starScale > 0) ? starScale : 1;
        this.enemyCount = enemies;
    }

    public void addPoints(int value, boolean enemyDestroyed) {
        score += value;
        if (enemyDestroyed)
            enemyCount--;
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

    /**
     *
     * @return true if game continues, false if game is end
     */
    public boolean update() {
        if (enemyCount <= 0) {
            if (score > highScore) {
                highScore = score;
            }
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Score: " + score;
    }
}
