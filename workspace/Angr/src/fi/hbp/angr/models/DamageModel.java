package fi.hbp.angr.models;

public abstract class DamageModel {
    protected float health;

    /**
     * Reset health to a default value
     */
    public void resetHealth() {
        health = 1f;
    }

    /**
     * Set object health
     * @param health Health in scale 0..1
     */
    public void setHealth(float health) {
        this.health = health;
    }

    /**
     * Get object health status
     * @return Object health in scale 0..1
     */
    public float getHealth() {
        return health;
    }

    /**
     * Collision force
     * @param Force Force value in Box2D scale.
     */
    public abstract void hit(float force);

    /**
     * Get points earned from destroying this model
     * @return points
     */
    public abstract int getPoints();

    @Override
    public String toString() {
        return String.valueOf(health);
    }
}
