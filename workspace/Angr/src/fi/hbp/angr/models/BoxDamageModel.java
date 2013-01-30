package fi.hbp.angr.models;

/**
 * Box damage model.
 */
public class BoxDamageModel extends DamageModel {
    /**
     * Class constructor.
     */
    public BoxDamageModel() {
        resetHealth();
    }

    @Override
    public void hit(float force) {
        if (force > 15) {
            this.health -= force / 100f;
        }

    }

    @Override
    public int getPoints() {
        return 100;
    }

    @Override
    public boolean isEnemy() {
        return true;
    }
}
