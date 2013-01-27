package fi.hbp.angr.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;

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

import fi.hbp.angr.logic.GameState;
import fi.hbp.angr.logic.ModelContactListener;
import fi.hbp.angr.logic.TestDamageModelForce;

public class ExplosionTest {
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
                dm = new TestDamageModelForce();
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

    protected TestActor spawnBox(float x, float y, BodyType bt, boolean enDM) {
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(1.0f, 1.0f);
        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.density = 5.0f;
        BodyDef bd = new BodyDef();
        bd.type = bt;
        bd.position.set(x, y);
        bd.active = true;
        Body body = world.createBody(bd);
        body.createFixture(fd);

        TestActor actor = new TestActor(body, enDM);
        return actor;
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

        /* Create a ground box */
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(0.0f, -10.0f);
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(50.0f, 10.0f);
        groundBody.createFixture(groundBox, 0.0f);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDoExplosion() {
        int steps = 15000;

        /* Spawn actors */
        TestActor actor1 = spawnBox(1.0f, 1.1f, BodyType.DynamicBody, false);
        TestActor actor2 = spawnBox(3.0f, 1.1f, BodyType.DynamicBody, true);
        TestActor actor3 = spawnBox(6.0f, 1.1f, BodyType.DynamicBody, true);
        TestActor actor4 = spawnBox(45.0f, 1.1f, BodyType.DynamicBody, true);

        Explosion ex = new Explosion(actor1.getBody());
        ex.doExplosion();
        do {
            world.step(0.1f, 6, 2);
        } while (--steps > 0);

        /* Assert health status */
        assertThat(((Destructible)actor2).getDatamageModel().getHealth(), lessThan(0.0f));
        assertThat(((Destructible)actor2).isDestroyed(), equalTo(true));
        assertThat(((Destructible)actor4).getDatamageModel().getHealth(), greaterThan(0.3f));
        assertThat(((Destructible)actor4).isDestroyed(), not(equalTo(true)));

        /* Assert positions */
        assertThat("Explosion source keeps its position",
                (double)actor1.getBody().getPosition().x, closeTo(1.0f, 0.01f));
        assertThat("Fixture body close to explosion is moved by explosion force",
                (double)actor2.getBody().getPosition().x, not(closeTo(3.0f, 0.1f)));
        assertThat("Fixture body close to explosion is moved by explosion force",
                (double)actor3.getBody().getPosition().x, not(closeTo(6.0f, 0.1f)));
        assertThat("Bodies far away are still in original position",
                (double)actor4.getBody().getPosition().x, closeTo(45.0f, 0.01f));
    }

}
