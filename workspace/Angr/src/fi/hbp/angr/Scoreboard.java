package fi.hbp.angr;

/**
 * Scoreboard interface.
 */
public interface Scoreboard {
    /**
     * Submit score to the actual scoreboard.
     * @param levelId id if the level in Swarm.
     * @param score score received from the current level.
     * @param rank rank achieved from the current level.
     */
    public void submitScore(int levelId, int score, int rank);

    /**
     * Show platform specific high score board.
     */
    public void showHighScore();

    /**
     * Show platform specific scoreboard.
     */
    public void showScoreboard();
}
