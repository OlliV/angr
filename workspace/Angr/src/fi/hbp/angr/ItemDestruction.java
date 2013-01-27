package fi.hbp.angr;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Interface used by game objects to destroy themselves
 */
public interface ItemDestruction {

    /**
     * Adds specified actor to the destruction list.
     * @param actor Actor that should be destroyed.
     */
    void add(Actor actor);

    /**
     *  Returns true if and only if the destruction list contains at least one
     *  element e such that (o==null ? e==null : o.equals(e)).
     * @param actor element whose presence in this list is to be tested.
     * @return true if this list contains the specified element.
     */
    boolean contains(Actor actor);
}
