package fi.hbp.angr.models;


public class BoxDamageModel extends DamageModel {
    public BoxDamageModel() {
        resetHealth();
    }

    @Override
    public void hit(float force) {
        if (force > 70)
            this.health -= force / 1000f;
    }

    @Override
    public int getPoints() {
        return 100;
    }
}
