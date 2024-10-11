package MapGame;

import Player.EntityPlayer;
import com.badlogic.gdx.physics.box2d.*;

public class CollisionsManager implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        String userDataA = (String) fixtureA.getUserData();
        String userDataB = (String) fixtureB.getUserData();

        if ("Player".equals(userDataA) && "Floor".equals(userDataB)) {
            EntityPlayer.isOnGround = true;
        } else if ("Floor".equals(userDataA) && "Player".equals(userDataB)) {
            EntityPlayer.isOnGround = true;
        }
    }

    @Override
    public void endContact(Contact contact) {
        EntityPlayer.isOnGround = false;
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
