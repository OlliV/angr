package fi.hbp.angr.logic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fi.hbp.angr.logic.BoxDamageModel;

public class BoxDamageModelTest {
    BoxDamageModel dm;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        dm = new BoxDamageModel();
    }

    @After
    public void tearDown() throws Exception {
        dm = null;
    }

    @Test
    public void testResetHealth() {
        dm.setHealth(0.0f);
        dm.resetHealth();
        assertThat(dm.getHealth(), equalTo(1.0f));
    }

    @Test
    public void testSetHealth() {
        dm.setHealth(0.5f);
        assertThat(dm.getHealth(), equalTo(0.5f));
    }

    @Test
    public void testHit() {
        dm.hit(500);
        assertThat(dm.getHealth(), not(equalTo(1f)));
    }

    @Test
    public void testGetPoints() {
        assertThat(dm.getPoints(), not(equalTo(0)));
    }

}
