package fi.hbp.angr.logic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.GdxNativesLoader;

import fi.hbp.angr.models.DamageModel;
import fi.hbp.angr.models.Destructible;


public class ModelContactListenerTest {
    World world;
    GameState gameState;
    ModelContactListener listener;

    protected class TestActor extends Actor implements Destructible  {
        DamageModel dm;
        boolean destroyed = false;
        Body body;

        public TestActor(Body body, boolean enableDM) {
            this.body = body;
            body.setUserData(this);
            if (enableDM) {
                dm = new TestDamageModel();
                dm.setHealth(1.0f);
            }
        }

        @Override
        public DamageModel getDatamageModel() {
            return dm;
        }

        @Override
        public void setDestroyed() {
            destroyed = true;
        }

        @Override
        public boolean isDestroyed() {
            return destroyed;
        }

        @Override
        public Body getBody() {
            return body;
        }
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        GdxNativesLoader.load();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        world = new World(new Vector2(0.0f, -9.8f), true);
        gameState = new GameState();
        gameState.addPoints(100, false);
        listener = new ModelContactListener(gameState);
        world.setContactListener(listener);

        spawnBox(0.0f, 0.0f, BodyType.StaticBody, false);
    }

    protected TestActor spawnBox(float x, float y, BodyType bt, boolean enDM) {
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(1.0f, 1.0f);
        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.density = 1.0f;
        BodyDef bd = new BodyDef();
        bd.type = bt;
        bd.position.set(x, y);
        bd.active = true;
        Body body = world.createBody(bd);
        body.createFixture(fd);

        TestActor actor = new TestActor(body, enDM);
        return actor;
    }

    @After
    public void tearDown() throws Exception {
        listener = null;
        gameState = null;
    }

    @Test
    public void testModelContactListener() {
        assertThat(listener.gameState.getScore(), equalTo(100));
    }

    @Test
    public void testPostSolveScore() {
        int timeout = 15000;
        TestActor actor = spawnBox(0.0f, 3.0f, BodyType.DynamicBody, true);

        assertThat(((Destructible)actor).getDatamageModel().getHealth(), equalTo(1.0f));

        do {
            world.step(0.1f, 6, 2);
            actor.isDestroyed();
            if (--timeout == 0) {
                fail("No contact");
            }
        } while (world.getContactCount() == 0);
        world.step(0.1f, 6, 2);
        world.step(0.1f, 6, 2);

        assertThat(((Destructible)actor).getDatamageModel().getHealth(), equalTo(0.0f));
        assertThat(listener.gameState.getScore(), equalTo(110));
    }

    @Test
    public void testPostSolveNoScore() {
        int timeout = 15000;
        TestActor actor = spawnBox(0.0f, 3.0f, BodyType.DynamicBody, false);

        do {
            world.step(60.0f, 6, 2);
            if (--timeout == 0) {
                fail("No contact");
            }
        } while (world.getContactCount() == 0);

        assertThat(listener.gameState.getScore(), equalTo(100));
    }
}
