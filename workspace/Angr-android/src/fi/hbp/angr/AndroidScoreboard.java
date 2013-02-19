package fi.hbp.angr;

import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmAchievement;
import com.swarmconnect.SwarmLeaderboard;

public class AndroidScoreboard implements Scoreboard {

    @Override
    public void submitScore(int levelId, int score, int rank) {
        SwarmLeaderboard.submitScore(levelId, (float) score);
        if (rank == 0) {
            SwarmAchievement.unlock(10127);
        } else if (rank == 1) {
            SwarmAchievement.unlock(10129);
        } else if (rank == 2) {
            SwarmAchievement.unlock(10131);
        } else if (rank == 3) {
            SwarmAchievement.unlock(10133);
        }
    }

    @Override
    public void showHighScore() {
        Swarm.showLeaderboards();
    }

    @Override
    public void showScoreboard() {
        Swarm.showDashboard();
    }

}
