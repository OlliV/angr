package fi.hbp.angr.logic;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fi.hbp.angr.models.Destructible;

/**
 * Model contact listener for damage modeling.
 */
public class ModelContactListener implements ContactListener
{
    protected final GameState gameState;

    /**
     * Contructor for ModelContactListerner.
     * @param gameState game state object.
     */
    public ModelContactListener(GameState gameState) {;
        this.gameState = gameState;
    }

    @Override
    public void beginContact(Contact arg0) {
    }

    @Override
    public void endContact(Contact arg0) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        if(impulse.getNormalImpulses()[0] > 0.01)
        {
            /* If body A was hit */
            if(contact.getFixtureA() != null)
            {
                if (Actor.class.isInstance(contact.getFixtureA().getBody().getUserData())) {
                    Actor a = (Actor) contact.getFixtureA().getBody().getUserData();
                    evalDamage(a, impulse);

                }
            }

            /* If body B was hit */
            if(contact.getFixtureB() != null)
            {
                if (Actor.class.isInstance(contact.getFixtureB().getBody().getUserData())) {
                    Actor b = (Actor) contact.getFixtureB().getBody().getUserData();
                    evalDamage(b, impulse);
                }
            }
        }
    }

    /**
     * Evaluate damage and score, and check if actor needs to be destroyed
     * @param actor Current actor
     * @param impulse Impulse
     */
    private void evalDamage(Actor actor, ContactImpulse impulse) {
        if (Destructible.class.isInstance(actor)) {
            /* Hit and check final health status */
            if (((Destructible)actor).getDatamageModel() == null)
                return;
            ((Destructible)actor).getDatamageModel().hit(impulse.getNormalImpulses()[0]);
            if (((Destructible)actor).getDatamageModel().getHealth() < 0.01f) {

                if (!((Destructible)actor).isDestroyed()) {
                    /* Update score counter */
                    gameState.addPoints(((Destructible)actor).getDatamageModel().getPoints(),
                            ((Destructible)actor).getDatamageModel().isEnemy());

                    /* Destroy actor */
                    ((Destructible)actor).setDestroyed();
                }
            }
        }
    }

    @Override
    public void preSolve(Contact arg0, Manifold arg1) {
    }
}
