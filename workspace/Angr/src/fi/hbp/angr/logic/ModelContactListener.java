package fi.hbp.angr.logic;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fi.hbp.angr.models.Destructible;

public class ModelContactListener implements ContactListener
{
    @Override
    public void beginContact(Contact arg0) {
        // TODO Auto-generated method stub
        if (!arg0.isTouching())
            return;
        if ((arg0.getFixtureA().getUserData() == null) || (arg0.getFixtureB() == null))
            return;

        Actor a = (Actor) arg0.getFixtureA().getUserData();
        Actor b = (Actor) arg0.getFixtureB().getUserData();
        if (!Destructible.class.isInstance(a) && !Destructible.class.isInstance(b))
            return;


    }

    @Override
    public void endContact(Contact arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        if(impulse.getNormalImpulses()[0] > 10)
        {
            // TODO don't damage if using slingshot?

            /* If A is the body that was hit */
            if(contact.getFixtureA() != null)
            {
                if (Actor.class.isInstance(contact.getFixtureA().getBody().getUserData())) {
                    Actor a = (Actor) contact.getFixtureA().getBody().getUserData();
                    if (Destructible.class.isInstance(a)) {
                        ((Destructible)a).getDatamageModel().hit(impulse.getNormalImpulses()[0]);
                    }
                }
            }

            /* If B is the body that was hit */
            if(contact.getFixtureB() != null)
            {
                if (Actor.class.isInstance(contact.getFixtureB().getBody().getUserData())) {
                    Actor a = (Actor) contact.getFixtureB().getBody().getUserData();
                    if (Destructible.class.isInstance(a)) {
                        ((Destructible)a).getDatamageModel().hit(impulse.getNormalImpulses()[0]);
                    }
                }
            }
        }

    }

    @Override
    public void preSolve(Contact arg0, Manifold arg1) {
        // TODO Auto-generated method stub

    }
}
