package fi.hbp.angr.models.levels;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import fi.hbp.angr.BodyFactory;
import fi.hbp.angr.G;
import fi.hbp.angr.logic.GameState;
import fi.hbp.angr.models.CollisionFilterMasks;

/**
 * Test map/level.
 */
public class TestLevel extends Level {
    private static final String MUSIC_PATH = "data/march.mp3";

    private Music music;

    public TestLevel() {
        super("mappi");
    }

    @Override
    public void preload() {
        super.preload();
        G.getAssetManager().load(MUSIC_PATH, Music.class);
    }

    @Override
    public void unload() {
        super.unload();
        music.stop();
        G.getAssetManager().unload(MUSIC_PATH);
    }

    @Override
    public void show(BodyFactory bf, GameState gs) {
        this.bf = bf;
        this.gs = gs;
        gs.init(getHighScore(), 600, 10, 10);

        FixtureDef fd = new FixtureDef();
        fd.density = 0.6f;
        fd.friction = 0.7f;
        fd.restitution = 0.3f;
        fd.filter.categoryBits = CollisionFilterMasks.GROUND;
        super.show(fd, 555, 375);

        /* Testing *****/
        testCode();

        music = G.getAssetManager().get(MUSIC_PATH);
        music.setLooping(true);
        music.play();
    }

    private void testCode() {
        /* Testing *****/

        for (int i = 0; i < 10; i++) {
            bf.spawnBox(1000 + i * 300, 400, 0);
        }

        /*for (int i = 0; i < 5; i++) {
            bf.spawnGrenade(1000 + i * 450, 500, 90);
        }*/
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
