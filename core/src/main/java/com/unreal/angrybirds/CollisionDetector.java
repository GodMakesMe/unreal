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
//            System.out.println("Collision detected!");
            HandleCollisions((Piggy)DataB,(Bird)DataA);
        }else if(DataB instanceof Bird && DataA instanceof Piggy){
//            System.out.println("Collision detected!");
            HandleCollisions((Piggy)DataA,(Bird)DataB);
        }else if (DataA instanceof Piggy && DataB instanceof Piggy){
//            System.out.println("Collision detected!");
            HandleCollisions((Piggy)DataB,(Piggy)DataA);
        }else if (((DataA instanceof Piggy || DataA instanceof Bird) && DataB instanceof Block) || (DataA instanceof Block && (DataB instanceof Piggy || DataA instanceof Piggy))){
//            System.out.println("Collision detected!");
            if (DataA instanceof Block){
                HandleCollisions((Block) DataA, DataB);
            }else{
                HandleCollisions((Block) DataB, DataA);
            }
        }
        else {
            if (DataA instanceof Piggy){
                Piggy p = (Piggy) DataA;
                p.setHealth(p.getHealth()-(int) Math.abs(p.getPiggyBody().getLinearVelocity().y*p.getMass()*0.0002F));
            }else if (DataB instanceof Piggy){
                Piggy p = (Piggy) DataB;
                p.setHealth(p.getHealth()-(int) Math.abs(p.getPiggyBody().getLinearVelocity().y*p.getMass()*0.0002F));
            }else if (DataA instanceof Bird){
                Bird p = (Bird) DataA;
                p.setHealth(p.getHealth()-(int) Math.abs(p.getBirdBody().getLinearVelocity().y*p.getMass()*0.0002F));
            }else if (DataB instanceof Bird){
                Bird p = (Bird) DataB;
                p.setHealth(p.getHealth()-(int) Math.abs(p.getBirdBody().getLinearVelocity().y*p.getMass()*0.0002F));
            }else if (DataA instanceof Block){
                Block p = (Block) DataA;
                p.health -= (int) Math.abs(p.getBlockBody().getLinearVelocity().y*p.mass*0.0002F);
            }else if (DataB instanceof Block){
                Block p = (Block) DataB;
                p.health -= (int) Math.abs(p.getBlockBody().getLinearVelocity().y*p.mass*0.0002F);
            }
            //            System.out.println("Collision detected!");
//            System.out.println("This Type of Collision is not handled for a reason.");
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
        float v1 = (float) Math.pow(bodyA.getLinearVelocity().dot(bodyA.getLinearVelocity()),0.5);
        float v2 = (float) Math.pow(bodyB.getLinearVelocity().dot(bodyB.getLinearVelocity()),0.5);
        float m2 = bodyB.getMass();
        float vx2 = bodyB.getLinearVelocity().x;
        float vy2 = bodyB.getLinearVelocity().y;
        float angle1 = (float) Math.atan2(vy1, vx1);
        float angle2 = (float) Math.atan2(vy2, vy1);
        float originx1 = bodyA.getPosition().x;
        float originy1 = bodyA.getPosition().y;
        float originx2 = bodyB.getPosition().x;
        float originy2 = bodyB.getPosition().y;
        float changeOriginY = originy2 - originy1;
        float changeOriginX = originx2 - originx1;
        float angleBetweenOrigin = (float) Math.atan2(originy2-originy1, originx2-originx1);
        float mainAngle1 = angleBetweenOrigin - angle1;
        float mainAngle2 = angleBetweenOrigin - angle2;
//        return (float) Math.abs(m1*v1*Math.cos(mainAngle1) - m2*v2*Math.cos(mainAngle2));
        float changeMomentumX = (float) (m1*vx1 - m2*vx2);
        float changeMomentumY = (float) (m1*vy1 - m2*vy2);
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
        Piggy.setHealth(Piggy.getHealth() - (int) (getChangeInMomentum(Piggy.getPiggyBody(), bird.getBirdBody())*0.0002F));
        System.out.println("Bird hit a piggy! Piggy's new health: " + Piggy.getHealth());
        if(Piggy.getHealth() <= 0){
//            Piggy.selfdestroy();
            System.out.println("Piggy's DEAD " + Piggy.getName());
            System.out.println("Number of bodies in world: " + bird.getWorldInstance().getBodyCount());

            Piggy.markForRemoval();
//            Piggy = null;
        }
    }
    public void HandleCollisions(Piggy pig1, Piggy pig2){
        if (pig1 == null || pig2 == null) {
            return;
        }
        pig1.setHealth(pig1.getHealth() - (int) (getChangeInMomentum(pig1.getPiggyBody(), pig2.getPiggyBody())*0.0002F));
        pig2.setHealth(pig2.getHealth() - (int) (getChangeInMomentum(pig2.getPiggyBody(), pig1.getPiggyBody())*0.0002F));
        System.out.println("Two Pigs collided:\t Pig1 Health:\t" + pig1.getHealth() + " Pig2 Health: " + pig2.getHealth());
        if (pig1.getHealth() <= 0){
            System.out.println("Piggy's DEAD " + pig1.getName());
            System.out.println("Number of bodies in world: " + pig1.getWorldInstance().getBodyCount());
            pig1.markForRemoval();
        }if (pig2.getHealth() <= 0){
            System.out.println("Piggy's DEAD " + pig2.getName());
            System.out.println("Number of bodies in world: " + pig2.getWorldInstance().getBodyCount());
            pig2.markForRemoval();
        }
    }
    private void HandleCollisions(Block dataA, Object dataB) {
        if (dataB instanceof Piggy){
            Piggy piggy = (Piggy) dataB;
            piggy.setHealth((int) (piggy.getHealth()- getChangeInMomentum(dataA.getBlockBody(), piggy.getPiggyBody())*0.0002F));
            dataA.health -= (int) (getChangeInMomentum(dataA.getBlockBody(), piggy.getPiggyBody())*0.0002F);
        }else if (dataB instanceof Bird){
            Bird bird = (Bird) dataB;
            bird.setHealth((int) (bird.getHealth() - getChangeInMomentum(bird.getBirdBody(), dataA.getBlockBody())*0.0002F));
            dataA.health -= (int) (getChangeInMomentum(dataA.getBlockBody(), bird.getBirdBody())*0.0002F);
        }else if (dataB instanceof Block){
            Block b = (Block) dataB;
            b.health -= (int) (getChangeInMomentum(dataA.getBlockBody(), b.getBlockBody())*0.0002F);
            dataA.health -= (int) (getChangeInMomentum(dataA.getBlockBody(), b.getBlockBody())*0.0002F);

        }
    }
}
