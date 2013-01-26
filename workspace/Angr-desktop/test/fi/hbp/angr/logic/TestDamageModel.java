package fi.hbp.angr.logic;

import fi.hbp.angr.models.DamageModel;

public class TestDamageModel extends DamageModel {
    @Override
    public boolean isEnemy() {
        return true;
    }

    @Override
    public void hit(float force) {
        this.setHealth(0);
    }

    @Override
    public int getPoints() {
        return 10;
    }
}
