package fi.hbp.angr.logic;

import com.badlogic.gdx.math.MathUtils;

/**
 * Game state.
 */
public class GameState {
    /**
     * Grenade counter.
     */
    public class Grenades {
        /**
         * Amount of grenades at the beginning.
         */
        public final int originalCount;
        /**
         * Current count.
         */
        private int count;

        /**
         * Constructor for Grenades class.
         * @param amount Amount of grenades available.
         */
        public Grenades(int amount) {
            this.originalCount = amount;
            this.count = amount;
        }

        /**
         * Decrement amount of grenades by one.
         */
        public void decrement() {
            if (count > 0)
                count--;
        }

        /**
         * Returns current grenade count.
         * @return current grenade count.
         */
        public int getCount() {
            return count;
        }
    }

    private int score = 0;
    private int highScore = 0;
    private int badgeScale = 1;
    private int enemyCount = 0;
    private Grenades grenades = new Grenades(0);

    /**
     * Initialize game state object.
     * @param highScore high score on the current level.
     * @param badgeScale points needed to achieve one badge.
     * @param enemies enemy count on the current level at the beginning of the game.
     * @param grenades amount of grenades available to clear the current level.
     */
    public void init(int highScore, int badgeScale, int enemies, int grenades) {
        this.highScore = highScore;
        this.badgeScale = (badgeScale > 0) ? badgeScale : 1;
        this.enemyCount = enemies;
        this.grenades = new Grenades(grenades);
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

    public Grenades getGrenades() {
        return this.grenades;
    }

    /**
     * Get number of achieved badges.
     * @return number of badges between 0..3,
     */
    public int getBadges() {
        return MathUtils.clamp(score / badgeScale, 0, 3);
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
     * @return true if game continues, false if game is end.
     */
    public boolean update() {
        if (enemyCount <= 0) {
            return false;
        }
        if (grenades.getCount() == 0) {
            return false;
        }
        return true;
    }

    public void countFinalScore() {
        if (score > highScore) {
            highScore = score;
        }
    }

    @Override
    public String toString() {
        return "Score: " + score;
    }
}
