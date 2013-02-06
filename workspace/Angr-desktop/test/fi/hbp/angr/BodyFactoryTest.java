/**
 *
 */
package fi.hbp.angr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.InputMultiplexer;

import fi.hbp.angr.BodyFactory;
import fi.hbp.angr.G;

/**
 * @author hbp
 *
 */
public class BodyFactoryTest {
    static InputMultiplexer inputMultiplexer;
    BodyFactory bf;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        inputMultiplexer = new InputMultiplexer();
        BodyFactory.preload();
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        G.getAssetManager().clear();
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        //bf = new BodyFactory(inputMultiplexer);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        bf = null;
    }

    /**
     * Test method for {@link fi.hbp.angr.BodyFactory#preload()}.
     */
    @Test
    public void testPreload() {
        assertThat(G.getAssetManager().getQueuedAssets(), greaterThan(0));
    }

    /**
     * Test method for {@link fi.hbp.angr.models.BodyFactory#createGrenade(com.badlogic.gdx.scenes.scene2d.Stage, com.badlogic.gdx.physics.box2d.World, float, float, float)}.
     */
    /*@Test
    public void testCreateGrenade() {
        fail("Not yet implemented");
    }*/

}
