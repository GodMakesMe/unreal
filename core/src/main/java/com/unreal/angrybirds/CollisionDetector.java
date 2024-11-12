package com.unreal.angrybirds;

import com.badlogic.gdx.physics.box2d.*;

public class CollisionDetector implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Object DataA = fixtureA.getBody().getUserData();
        Object DataB = fixtureB.getBody().getUserData();

//        System.out.println("Fixture A UserData: " +DataA);
//        System.out.println("Fixture B UserData: " + DataA);
//        System.out.println("Fixture A UserData: " + DataA);
//        System.out.println("Fixture B UserData: " + DataB);

        if(DataA instanceof Bird && DataB instanceof Piggy){
            System.out.println("Collision detected!");
            HandleCollisions((Piggy)DataB,(Bird)DataA);
        }else if(DataB instanceof Bird && DataA instanceof Piggy){
            System.out.println("Collision detected!");
            HandleCollisions((Piggy)DataA,(Bird)DataB);
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
    public void HandleCollisions(Piggy Piggy,Bird bird) {
        if (Piggy == null || Piggy.isRemoved()) {
            return;
        }
        Piggy.setHealth(Piggy.getHealth() - 50);
        System.out.println("Bird hit a piggy! Piggy's new health: " + Piggy.getHealth());
        if(Piggy.getHealth() <= 0){
//            Piggy.selfdestroy();

            System.out.println("Piggy's DEAD " + Piggy.getName());
            System.out.println("Number of bodies in world: " + bird.getWorldInstance().getBodyCount());

            Piggy.markForRemoval();
//            Piggy = null;
        }
    }
}
