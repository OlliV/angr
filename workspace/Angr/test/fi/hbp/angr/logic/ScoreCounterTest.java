package fi.hbp.angr.logic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ScoreCounterTest {
    ScoreCounter score;

    @Before
    public void setUp() throws Exception {
        score = new ScoreCounter();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAddPoints() {
        score.addPoints(100);
        assertThat(score.getScore(), equalTo(100));
    }

    @Test
    public void testInit() {
        score.init(100, 1);
        assertThat(score.getScore(), equalTo(0));
        assertThat(score.getHighScore(), equalTo(100));
    }

    @Test
    public void testClear() {
        score.init(500, 100);
        score.addPoints(1000);
        score.clear();
        assertThat(score.getScore(), equalTo(0));
        assertThat(score.getHighScore(), equalTo(0));
    }

    @Test
    public void testGetScore() {
        score.addPoints(100);
        assertThat(score.getScore(), equalTo(100));
        score.addPoints(150);
        assertThat(score.getScore(), equalTo(250));
    }

    @Test
    public void clearScore() {
        score.init(100, 1);
        score.addPoints(400);
        score.clearScore();
        assertThat(score.getHighScore(), equalTo(100));
        assertThat(score.getScore(), equalTo(0));
    }

    @Test
    public void testGetStars() {
        score.init(0, 100);
        score.addPoints(90);
        assertThat(score.getStars(), equalTo(0));
        score.addPoints(10);
        assertThat(score.getStars(), equalTo(1));
        score.addPoints(5000);
        assertThat(score.getStars(), equalTo(3));
    }

    @Test
    public void testGetHighScore() {
        score.init(500, 100);
        assertThat(score.getHighScore(), equalTo(500));
    }

    @Test
    public void testGameOverAction() {
        score.init(100, 1);
        score.addPoints(500);
        score.gameOverAction();
        assertThat(score.getHighScore(), equalTo(100));
    }

    @Test
    public void testGameWinAction() {
        score.init(100, 1);
        assertThat(score.getHighScore(), equalTo(100));
        score.addPoints(300);
        assertThat(score.getHighScore(), equalTo(100));
        score.gameWinAction();
        assertThat(score.getHighScore(), equalTo(300));

    }

}
