package fi.hbp.angr;



public class CameraFilter {
    private float xout;
    private float yout;
    private float kp;

    CameraFilter(float propotion) {
        kp = propotion;
    }

    public void init(float x, float y) {
        xout = x;
        yout = y;
    }

    public void updateX(float x, float dt) {
        xout = pi(xout, x, dt);
    }

    public void updateY(float y, float dt) {
        yout = pi(yout, y, dt);
    }

    private float pi(float out, float setpoint, float dt) {
        float error = (setpoint - out);
        float integral = out + error * dt;
        return kp * error + integral;
    }

    public float getX() {
        return xout;
    }

    public float getY() {
        return yout;
    }
}
