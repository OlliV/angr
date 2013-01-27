package fi.hbp.angr.logic;

import com.badlogic.gdx.math.MathUtils;

/**
 * Game state.
 */
public class GameState {
    private int score = 0;
    private int highScore = 0;
    private int starScale = 1;
    private int enemyCount = 0;

    /**
     * Initialize game state object.
     * @param highScore high score on the current level.
     * @param starScale star scaling, points needed per star.
     * @param enemies count of enemies on the current level at the beginning
     * of the game.
     */
    public void init(int highScore, int starScale, int enemies) {
        this.highScore = highScore;
        this.starScale = (starScale > 0) ? starScale : 1;
        this.enemyCount = enemies;
    }

    /**
     * Add points achieved by the player.
     * @param value point added.
     * @param enemyDestroyed was enemy destroyed when points were achieved?
     */
    public void addPoints(int value, boolean enemyDestroyed) {
        score += value;
        if (enemyDestroyed)
            enemyCount--;
    }

    /**
     * Clears all score counters.
     */
    public void clear() {
        score = 0;
        highScore = 0;
        enemyCount = 0;
    }

    /**
     * Get current score status.
     * @return score counter value.
     */
    public int getScore() {
        return score;
    }

    /**
     * Clears the score counter.
     */
    public void clearScore() {
        score = 0;
    }

    /**
     * Get current amount of stars.
     * @return
     */
    public int getStars() {
        return MathUtils.clamp(score / starScale, 0, 3);
    }

    /**
     * Get HighScore value for the current level.
     * @return high score value.
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Update statuses.
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
