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
        gameState.init(100, 1, 1, 10);
        assertThat(gameState.getScore(), equalTo(0));
        assertThat(gameState.getHighScore(), equalTo(100));
    }

    @Test
    public void testGetScore() {
        gameState.addPoints(100, false);
        assertThat(gameState.getScore(), equalTo(100));
        gameState.addPoints(150, false);
        assertThat(gameState.getScore(), equalTo(250));
    }

    @Test
    public void testGetGrenades() {
        gameState.init(0, 0, 0, 10);
        gameState.getGrenades().decrement();
        assertThat("Test that amount of grenades if decremented.",
                gameState.getGrenades().getCount(), equalTo(9));
        assertThat(gameState.getGrenades().originalCount, equalTo(10));

        for (int i = 0; i < 15; i++) {
            gameState.getGrenades().decrement();
        }
        assertThat("Test that amount of grenades is zero.",
                gameState.getGrenades().getCount(), equalTo(0));
    }

    @Test
    public void testGetBadgesZero() {
        gameState.init(0, 100, 1, 1);
        gameState.addPoints(200, false);
        gameState.countFinalScore();

        assertThat(gameState.getBadges(), equalTo(0));
    }

    @Test
    public void testGetBadgesZeroB() {
        gameState.init(0, 100, 0, 0);
        gameState.addPoints(90, false);
        gameState.countFinalScore();

        assertThat(gameState.getBadges(), equalTo(0));
    }

    @Test
    public void testGetBadgesOne() {
        gameState.init(0, 100, 0, 0);
        gameState.addPoints(100, false);
        gameState.countFinalScore();

        assertThat(gameState.getBadges(), equalTo(1));
    }

    @Test
    public void testGetBadgesTwo() {
        gameState.init(0, 100, 0, 0);
        gameState.addPoints(200, false);
        gameState.countFinalScore();

        assertThat(gameState.getBadges(), equalTo(2));
    }

    @Test
    public void testGetBadgesThree() {
        gameState.init(0, 100, 1, 0);
        gameState.addPoints(90, true);
        gameState.addPoints(10, false);
        gameState.addPoints(5000, false);
        gameState.countFinalScore();

        assertThat(gameState.getBadges(), equalTo(3));
    }

    @Test
    public void testGameFinalized() {
        gameState.init(100, 10, 10, 5);
        gameState.countFinalScore();
        gameState.init(400, 40, 50, 10);
        assertThat(gameState.getGrenades().getCount(), equalTo(5));
    }

    @Test
    public void testGameFinalizedNotCleared() {
        boolean end;

        gameState.init(100, 10, 10, 5);
        end = gameState.countFinalScore();
        assertThat("Test that game was not cleared.", end, equalTo(false));
    }

    @Test
    public void testGameFinalizedCleared() {
        boolean end;

        gameState.init(100, 10, 10, 5);

        for (int i = 0; i < 10; i++) {
            gameState.addPoints(1, true);
            gameState.getGrenades().decrement();
        }

        end = gameState.countFinalScore();
        assertThat("Test that game was cleared.", end, equalTo(true));
    }

    @Test
    public void testGetHighScore() {
        gameState.init(500, 100, 1, 10);
        assertThat(gameState.getHighScore(), equalTo(500));
    }

    @Test
    public void testGameEndsEnemies() {
        gameState.init(0, 1, 1, 10);
        assertThat(gameState.update(), equalTo(true));
        gameState.addPoints(10, true);
        assertThat(gameState.update(), equalTo(false));
    }

    @Test
    public void testGameEndsGrenades() {
        gameState.init(0, 1, 1, 10);
        assertThat(gameState.update(), equalTo(true));
        for (int i = 0; i < 15; i++) {
            gameState.getGrenades().decrement();
        }
        assertThat(gameState.update(), equalTo(false));
    }
}
