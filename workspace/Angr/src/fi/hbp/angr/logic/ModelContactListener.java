package fi.hbp.angr.logic;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fi.hbp.angr.ItemDestruction;
import fi.hbp.angr.models.Destructible;

public class ModelContactListener implements ContactListener
{
    private ItemDestruction itdes;
    private ScoreCounter score;

    public ModelContactListener(ItemDestruction itdes, ScoreCounter score) {
        this.itdes = itdes;
        this.score = score;
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
            ((Destructible)actor).getDatamageModel().hit(impulse.getNormalImpulses()[0]);
            if (((Destructible)actor).getDatamageModel().getHealth() < 0.01f) {

                if (!itdes.contains(actor)) {
                    /* Update score counter */
                    score.addPoints(((Destructible)actor).getDatamageModel().getPoints());

                    /* Destroy actor */
                    itdes.add(actor);
                }
            }
        }
    }

    @Override
    public void preSolve(Contact arg0, Manifold arg1) {
        // TODO Auto-generated method stub

    }
}
