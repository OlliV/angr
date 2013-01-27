package fi.hbp.angr;


/**
 * Camera position filter.
 */
public class CameraFilter {
    private float xout;
    private float yout;
    private final float kp;

    /**
     * Constructor for CameraFilter.
     * @param kp proportional gain.
     */
    CameraFilter(float kp) {
        this.kp = kp;
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
        xout = calcFiltOut(xout, x, dt);
    }

    /**
     * Update y axis filter.
     * @param y setpoint value of y.
     * @param dt delta time.
     */
    public void updateY(float y, float dt) {
        yout = calcFiltOut(yout, y, dt);
    }

    /**
     * Calculate filter output.
     * @param out current output value.
     * @param setpoint current setpoint.
     * @param dt delta time.
     * @return new output value.
     */
    private float calcFiltOut(float out, float setpoint, float dt) {
        float error = (setpoint - out);
        float integral = out + error * dt;
        return kp * error + integral;
    }

    /**
     * Get filtered x axis point.
     * @return point on x axis.
     */
    public float getX() {
        return xout;
    }

    /**
     * Get filtered y axis point.
     * @return point on y axis.
     */
    public float getY() {
        return yout;
    }
}
