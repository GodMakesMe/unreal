package com.unreal.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class SpeedAbility implements Ability {
    Bird bird;
    int count = 10;
    float velocityx;
    float velocityy;
    @Override
    public void triggerAbility(Bird lovelyBird){
        bird = lovelyBird;
//        bird.getBirdBody().setGravityScale(0f);
        velocityx = lovelyBird.getBirdBody().getLinearVelocity().x*5;
        velocityy = lovelyBird.getBirdBody().getLinearVelocity().y*5;
        bird.getBirdBody().setLinearVelocity(new Vector2(lovelyBird.getBirdBody().getLinearVelocity().x*5, lovelyBird.getBirdBody().getLinearVelocity().y*5));
        bird.x_velocity *= 5;
        bird.y_velocity *= 5;
        applyUpdate();
//        bird.getBirdBody().setGravityScale(1f);
//        lovelyBird.y_velocity *= 20;
//        lovelyBird.y_velocity *= 0;
//        lovelyBird.getBirdBody().trans
        Music AbilitySFX = Gdx.audio.newMusic(Gdx.files.internal("ChuckAbilitySFX.mp3"));
        AbilitySFX.play();
    }
    public void applyUpdate(){
//        bird.getBirdBody().setGravityScale(0f);
        if (count < 0) return;
        bird.getBirdBody().setLinearVelocity(new Vector2(velocityx, velocityy));
        velocityx = bird.getBirdBody().getLinearVelocity().x;
        velocityy = bird.getBirdBody().getLinearVelocity().y;
        count--;
//        velocityx = bird.x_velocity;
//        velocityy = bird.y_velocity;
//        bird.x_velocity *= 20;
//        bird.y_velocity *= 20;
//        bird.getBirdBody().setGravityScale(1f);
    }
}
