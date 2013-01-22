package fi.hbp.angr.models.levels;

import com.badlogic.gdx.physics.box2d.FixtureDef;

import fi.hbp.angr.BodyFactory;
import fi.hbp.angr.models.CollisionFilterMasks;

public class TestLevel extends Level {
    private BodyFactory bf;

    public TestLevel() {
        super("mappi");
    }

    @Override
    public void show(BodyFactory bf) {
        this.bf = bf;

        FixtureDef fd = new FixtureDef();
        fd.density = 0.6f;
        fd.friction = 0.7f;
        fd.restitution = 0.3f;
        fd.filter.categoryBits = CollisionFilterMasks.WALL;
        super.show(bf, fd);

        /* Testing *****/
        testCode();
    }

    private void testCode() {
        /* Testing *****/

        for (int i = 0; i < 10; i++) {
            bf.spawnBox(1000 + i * 40, 800, 0);
        }

        for (int i = 0; i < 11; i++) {
            bf.spawnGrenade(1000 + i * 110, 1000, 90);
        }
    }
}