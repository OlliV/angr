package fi.hbp.angr;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * List type for use with item/actor destruction service
 */
public class ItemDestructionList implements ItemDestruction {
    private ArrayList<Actor> items = new ArrayList<Actor>();

    @Override
    public void add(Actor actor) {
        items.add(actor);
    }

    @Override
    public boolean contains(Actor actor) {
        return items.contains(actor);
    }

    /**
     * Returns true if there is no actors/items to be destroyed.
     * @return true if list contains no elements
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     * @return an iterator over the elements in this list in proper sequence
     */
    public Iterator<Actor> getIterator() {
        return items.iterator();
    }

    /**
     * Removes all of the elements from this list. The list will be empty after
     * this call returns.
     */
    public void clear() {
        items.clear();
    }
}
