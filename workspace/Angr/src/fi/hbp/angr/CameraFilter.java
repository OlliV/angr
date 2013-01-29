package fi.hbp.angr;

import com.badlogic.gdx.math.MathUtils;



/**
 * Camera position filter.
 */
public class CameraFilter {
    private float itx = 0;
    private float ity = 0;

    private float xout;
    private float yout;

    private final float kp;
    private final float ki;

    /**
     * Constructor for CameraFilter.
     * @param kp proportional gain.
     */
    CameraFilter(float kp, float ki) {
        this.kp = kp;
        this.ki = ki;
    }

    /**
     * Initialize output with values of x and y.
     * @param x value of x_out.
     * @param y value of y_out.
     */
    public void init(float x, float y) {
        xout = x;
        yout = y;
    }

    /**
     * Update x axis filter.
     * @param x setpoint value of x.
     * @param dt delta time.
     */
    public void updateX(float x, float dt) {
        float error = (x - xout);
        itx += error * dt;
        xout = kp * error + ki * itx;
    }

    /**
     * Update y axis filter.
     * @param y setpoint value of y.
     * @param dt delta time.
     */
    public void updateY(float y, float dt) {
        float error = (y - yout);
        ity += error * dt;
        yout = kp * error + ki * ity;
    }

    /**
     * Get filtered x axis point.
     * @return point on x axis.
     */
    public float getX() {
        return MathUtils.round(xout / 2.0f) * 2.0f;
    }

    /**
     * Get filtered y axis point.
     * @return point on y axis.
     */
    public float getY() {
        return MathUtils.round(yout / 2.0f) * 2.0f;
    }
}
