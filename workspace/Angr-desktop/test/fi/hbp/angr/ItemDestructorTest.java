package fi.hbp.angr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Iterator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class ItemDestructorTest {
    ItemDestructionList itdes;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        itdes = new ItemDestructionList();
    }

    @After
    public void tearDown() throws Exception {
        itdes = null;
    }

    @Test
    public void testAdd() {
        Actor act1 = new Actor();
        Actor act2 = new Actor();

        itdes.add(act1);
        itdes.add(act2);
    }

    @Test
    public void testContains() {
        Actor act1 = new Actor();
        Actor act2 = new Actor();
        Actor act3 = new Actor();

        itdes.add(act1);
        itdes.add(act2);

        assertThat(itdes.contains(act1), equalTo(true));
        assertThat(itdes.contains(act2), equalTo(true));
        assertThat(itdes.contains(act3), equalTo(false));
    }

    @Test
    public void testIsEmpty() {
        Actor act1 = new Actor();
        Actor act2 = new Actor();

        assertThat(itdes.isEmpty(), equalTo(true));
        itdes.add(act1);
        itdes.add(act2);
        assertThat(itdes.isEmpty(), equalTo(false));
    }

    @Test
    public void testGetIterator() {
        Actor act1 = new Actor();
        Actor act2 = new Actor();
        Actor act3 = new Actor();

        itdes.add(act1);
        itdes.add(act2);
        itdes.add(act3);

        int i = 0;
        Iterator<Actor> iterator = itdes.getIterator();
        while (iterator.hasNext()) {
           iterator.next();
           i++;
        }
        assertThat(i, equalTo(3));
    }

    @Test
    public void testClear() {
        Actor act1 = new Actor();
        Actor act2 = new Actor();

        itdes.add(act1);
        itdes.add(act2);
        assertThat(itdes.isEmpty(), equalTo(false));
        itdes.clear();
        assertThat(itdes.isEmpty(), equalTo(true));
    }

}
