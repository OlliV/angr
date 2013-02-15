package fi.hbp.angr.models.levels;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import fi.hbp.angr.G;
import fi.hbp.angr.models.CollisionFilterMasks;

/**
 * Test map/level.
 */
public class TestLevel extends Level {
    /**
     * Music file path.
     */
    private static final String MUSIC_PATH = "data/march.mp3";
    /**
     * Game music.
     */
    private Music music;

    /**
     * Constructor for this level.
     */
    public TestLevel() {
        super("mappi", 5);
    }

    @Override
    public void doOnPreload() {
        G.getAssetManager().load(MUSIC_PATH, Music.class);
    }

    @Override
    public void doOnUnload() {
        music.stop();
        G.getAssetManager().unload(MUSIC_PATH);
    }

    @Override
    public void doOnShow() {
        /* Initialize game state                 */
        gs.init(getHighScore(), /* High score    */
                550,            /* Badge scaling */
                10,             /* Enemy count   */
                10);            /* Grenade count */

        /* Create terrain fixture definition */
        FixtureDef fd = new FixtureDef();
        fd.density = 0.6f;
        fd.friction = 0.7f;
        fd.restitution = 0.3f;
        fd.filter.categoryBits = CollisionFilterMasks.GROUND;

        /* Show map/terrain */
        super.showMap(fd, 555, 375);

        /* Testing *****/
        testCode();

        /* Start playing music */
        music = G.getAssetManager().get(MUSIC_PATH);
        music.setLooping(true);
        music.play();
    }

    private void testCode() {
        /* Testing *****/

        for (int i = 0; i < 10; i++) {
            bf.spawnBox(1000 + i * 300, 400, 0);
        }
    }
}
