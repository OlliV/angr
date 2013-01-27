package fi.hbp.angr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CameraFilterTest {
    CameraFilter filt;

    @Before
    public void setUp() throws Exception {
        filt = new CameraFilter(0.04f);
    }

    @After
    public void tearDown() throws Exception {
        filt = null;
    }

    @Test
    public void testInit() {
        filt.init(10.0f, 20.0f);

        assertThat(filt.getX(), equalTo(10.0f));
        assertThat(filt.getY(), equalTo(20.0f));
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

        assertThat((double)filt.getX(), closeTo(1200.0f, 180.0f));
        assertThat((double)filt.getY(), closeTo(1200.0f, 180.0f));
    }
}
