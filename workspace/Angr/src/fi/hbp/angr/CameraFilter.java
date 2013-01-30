package fi.hbp.angr;

import com.badlogic.gdx.math.MathUtils;



/**
 * Camera position filter.
 */
public class CameraFilter {
    private PID xpid;
    private PID ypid;

    /**
     * Constructor for CameraFilter.
     * @param kp proportional gain.
     */
    CameraFilter(float kp, float ki, float kd) {
        xpid = new PID(kp, ki, kd);
        ypid = new PID(kp, ki, kd);
    }

    /**
     * Initialize output with values of x and y.
     * @param x value of x_out.
     * @param y value of y_out.
     */
    public void init(float x, float y) {
        xpid.reset(x);
        ypid.reset(y);
    }

    /**
     * Update x axis filter.
     * @param x setpoint value of x.
     * @param dt delta time.
     */
    public void updateX(float x, float dt) {
        xpid.update(x, dt);
    }

    /**
     * Update y axis filter.
     * @param y setpoint value of y.
     * @param dt delta time.
     */
    public void updateY(float y, float dt) {
        ypid.update(y, dt);
    }

    /**
     * Get filtered x axis point.
     * @return point on x axis.
     */
    public float getX() {
        /* Rounding should remove small oscillations from the output */
        return MathUtils.round(xpid.getOutput() / 2.0f) * 2.0f;
    }

    /**
     * Get filtered y axis point.
     * @return point on y axis.
     */
    public float getY() {
        /* Rounding should remove small oscillations from the output */
        return MathUtils.round(ypid.getOutput() / 2.0f) * 2.0f;
    }
}
