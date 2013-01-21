package fi.hbp.angr.models;

import com.badlogic.gdx.physics.box2d.Body;


/**
 * Interface for destructible models/items
 */
public interface Destructible {
    /**
     * Get damage model of this object
     * @return Damage model
     */
    DamageModel getDatamageModel();

    /**
     * Get Box2D body
     *
     * This is used to remove body from the world
     * @return Body.
     */
    Body getBody();
}
