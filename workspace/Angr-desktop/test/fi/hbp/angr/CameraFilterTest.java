package fi.hbp.angr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fi.hbp.angr.stage.CameraFilter;

public class CameraFilterTest {
    CameraFilter filt;

    @Before
    public void setUp() throws Exception {
        filt = new CameraFilter(0.1f, 2.5f, 0.001f);
    }

    @After
    public void tearDown() throws Exception {
        filt = null;
    }

    @Test
    public void testInit() {
        for (int i = 0; i < 100; i++) {
            filt.updateX(100.0f, 0.03f);
            filt.updateY(500.0f, 0.03f);
        }

        filt.init(10.0f, 20.0f);

        assertThat(filt.getX(), equalTo(10.0f));
        assertThat(filt.getY(), equalTo(20.0f));

        for (int i = 0; i < 100; i++) {
            filt.updateX(10.0f, 0.03f);
            filt.updateY(20.0f, 0.03f);
        }

        assertThat((double)filt.getX(), closeTo(10.0f, 5.0f));
        assertThat((double)filt.getY(), closeTo(20.0f, 5.0f));
    }

    @Test
    public void testUpdateX() {

        float setpoint;

        /* Up */
        setpoint = 1000.0f;
        for (int i = 0; i < 400; i++) {
            filt.updateX(setpoint, 0.025f);
        }
        assertThat((double)filt.getX(), closeTo(1000.0d, 0.5d));

        /* Down */
        setpoint = -1000.0f;
        for (int i = 0; i < 400; i++) {
            filt.updateX(setpoint, 0.025f);
        }
        assertThat((double)filt.getX(), closeTo(-1000.0d, 0.5d));
    }

    @Test
    public void testUpdateY() {
        float setpoint;

        /* Up */
        setpoint = 1000.0f;
        for (int i = 0; i < 400; i++) {
            filt.updateY(setpoint, 0.025f);
        }
        assertThat((double)filt.getY(), closeTo(1000.0d, 0.5d));

        /* Down */
        setpoint = -1000.0f;
        for (int i = 0; i < 400; i++) {
            filt.updateY(setpoint, 0.025f);
        }
        assertThat((double)filt.getY(), closeTo(-1000.0d, 0.5d));
    }

    @Test
    public void testDt() {
        float setpoint = 5000.0f;

        for (int i = 0; i < 3; i++) {
            filt.updateX(setpoint, 0.05f);
            setpoint += 200.0f;
        }

        setpoint = 5000.0f;
        filt.updateY(setpoint, 0.05f);
        setpoint += 600.0f;
        filt.updateY(setpoint, 0.1f);

        assertThat((double)filt.getX(), closeTo(2000.0f, 150.0f));
        assertThat((double)filt.getY(), closeTo(2000.0f, 150.0f));
    }
}
