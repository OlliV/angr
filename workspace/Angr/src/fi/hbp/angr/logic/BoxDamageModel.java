package fi.hbp.angr.logic;


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
        if (force > 12.0f) {
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
