package fi.hbp.angr.logic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GameStateTest {
    GameState gameState;

    @Before
    public void setUp() throws Exception {
        gameState = new GameState();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAddPoints() {
        gameState.addPoints(100, false);
        assertThat(gameState.getScore(), equalTo(100));
    }

    @Test
    public void testInit() {
        gameState.init(100, 1, 1);
        assertThat(gameState.getScore(), equalTo(0));
        assertThat(gameState.getHighScore(), equalTo(100));
    }

    @Test
    public void testClear() {
        gameState.init(500, 100, 1);
        gameState.addPoints(1000, false);
        gameState.clear();
        assertThat(gameState.getScore(), equalTo(0));
        assertThat(gameState.getHighScore(), equalTo(0));
    }

    @Test
    public void testGetScore() {
        gameState.addPoints(100, false);
        assertThat(gameState.getScore(), equalTo(100));
        gameState.addPoints(150, false);
        assertThat(gameState.getScore(), equalTo(250));
    }

    @Test
    public void clearScore() {
        gameState.init(100, 1, 1);
        gameState.addPoints(400, false);
        gameState.clearScore();
        assertThat(gameState.getHighScore(), equalTo(100));
        assertThat(gameState.getScore(), equalTo(0));
    }

    @Test
    public void testGetStars() {
        gameState.init(0, 100, 1);
        gameState.addPoints(90, false);
        assertThat(gameState.getStars(), equalTo(0));
        gameState.addPoints(10, false);
        assertThat(gameState.getStars(), equalTo(1));
        gameState.addPoints(5000, false);
        assertThat(gameState.getStars(), equalTo(3));
    }

    @Test
    public void testGetHighScore() {
        gameState.init(500, 100, 1);
        assertThat(gameState.getHighScore(), equalTo(500));
    }

    @Test
    public void testGameEnds() {
        gameState.init(0, 1, 1);
        assertThat(gameState.update(), equalTo(true));
        gameState.addPoints(10, true);
        assertThat(gameState.update(), equalTo(false));
    }
}
