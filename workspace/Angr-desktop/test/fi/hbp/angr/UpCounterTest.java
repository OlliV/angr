package fi.hbp.angr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UpCounterTest {
    UpCounter counter;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testUpdatePositive() {
        counter = new UpCounter(100, 1f, false);
        counter.update(1f);
        counter.update(1f);
        assertThat("Counts correcly for positive value.", counter.getValue(), equalTo(10));
    }

    @Test
    public void testUpdateNegative() {
        counter = new UpCounter(-100, 1f, false);
        counter.update(1f);
        counter.update(1f);
        assertThat("Counts correctly for negative value.", counter.getValue(), equalTo(-10));
    }

    @Test
    public void testIsStopped() {
        counter = new UpCounter(5, 1f, false);
        assertThat(counter.isStopped(), equalTo(false));

        counter.update(1f);
        counter.update(1f);
        counter.update(1f); /* Current implementation doesn't really need five updates
                             * as its internal step is five! */

        assertThat(counter.isStopped(), equalTo(true));
    }

    @Test
    public void testZeroValue() {
        counter = new UpCounter(0, 1f, false);
        assertThat(counter.isStopped(), equalTo(false));
        counter.update(1f);
        counter.update(1f);
        counter.update(1f);

        assertThat(counter.getValue(), equalTo(0));
        assertThat(counter.isStopped(), equalTo(true));
    }
}
