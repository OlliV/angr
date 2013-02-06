package fi.hbp.angr.logic;

import fi.hbp.angr.logic.DamageModel;

public class TestDamageModelForce extends DamageModel {
    @Override
    public boolean isEnemy() {
        return true;
    }

    @Override
    public void hit(float force) {
        this.health -= force / 100f;
    }

    @Override
    public int getPoints() {
        return 10;
    }
}
