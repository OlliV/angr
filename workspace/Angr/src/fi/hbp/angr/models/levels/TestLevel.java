package fi.hbp.angr.models.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import fi.hbp.angr.BodyFactory;
import fi.hbp.angr.models.CollisionFilterMasks;

public class TestLevel extends Level {
    private BodyFactory bf;
    private Music music;

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

        music = Gdx.audio.newMusic(Gdx.files.internal("data/march.mp3"));
        music.setLooping(true);
        music.play();
    }

    private void testCode() {
        /* Testing *****/

        for (int i = 0; i < 10; i++) {
            bf.spawnBox(1000 + i * 400, 400, 0);
        }

        for (int i = 0; i < 11; i++) {
            bf.spawnGrenade(1000 + i * 450, 1000, 90);
        }
    }
}
