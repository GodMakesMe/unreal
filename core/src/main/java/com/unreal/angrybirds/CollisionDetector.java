package com.unreal.angrybirds;

import com.badlogic.gdx.physics.box2d.*;

import java.util.Vector;

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
    public float getChangeInMomentum(Body bodyA , Body bodyB){
        float m1 = bodyA.getMass();
        float vx1 = bodyA.getLinearVelocity().x;
        float vy1 = bodyA.getLinearVelocity().y;
        float m2 = bodyB.getMass();
        float vx2 = bodyB.getLinearVelocity().x;
        float vy2 = bodyB.getLinearVelocity().y;
        float changeMomentumX = m1*vx1 - m2*vx2;
        float changeMomentumY = m1*vy1 - m2*vy2;
        return (float) Math.pow(changeMomentumX*changeMomentumX + changeMomentumY*changeMomentumY, 0.5);
    }
    public float getMomentum(Body bodyA) {
        float m1 = bodyA.getMass();
        float vx1 = bodyA.getLinearVelocity().x;
        float vy1 = bodyA.getLinearVelocity().y;
        return (float) Math.pow(vx1*vx1 + vy1*vy1, 0.5) * m1;
    }
    public void HandleCollisions(Piggy Piggy,Bird bird) {
        if (Piggy == null || Piggy.isRemoved()) {
            return;
        }
        Piggy.setHealth(Piggy.getHealth() - (int) (getChangeInMomentum(Piggy.getPiggyBody(), bird.getBirdBody())*0.0004F));
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
