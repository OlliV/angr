package fi.hbp.angr;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class ItemDestructor implements ItemDestruction {
    private ArrayList<Actor> items = new ArrayList<Actor>();

    @Override
    public void add(Actor actor) {
        items.add(actor);
    }

    @Override
    public boolean contains(Actor actor) {
        return items.contains(actor);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public Iterator<Actor> getIterator() {
        return items.iterator();
    }

    public void clear() {
        items.clear();
    }
}
