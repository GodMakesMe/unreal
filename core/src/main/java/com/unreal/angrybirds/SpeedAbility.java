package com.unreal.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import java.io.Serializable;

public class SpeedAbility implements Ability, Serializable {
    Bird bird;
    int count = 80;
    float velocityx;
    float velocityy;
    @Override
    public void triggerAbility(Bird lovelyBird){
        bird = lovelyBird;
//        bird.getBirdBody().setGravityScale(0f);
        velocityx = lovelyBird.getBirdBody().getLinearVelocity().x*20 + 10;
        velocityy = lovelyBird.getBirdBody().getLinearVelocity().y*20 + 10;
        bird.getBirdBody().setLinearVelocity(velocityx, velocityy);
        bird.x_velocity *= 20;
        bird.y_velocity *= 20;
        bird.y_velocity += 10;
        bird.x_velocity += 10;
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
        bird.x_velocity = velocityx;
        bird.y_velocity = velocityy;
        bird.getBirdBody().setLinearVelocity(velocityx, velocityy);

        count--;
//        velocityx = bird.x_velocity;
//        velocityy = bird.y_velocity;
//        bird.x_velocity *= 20;
//        bird.y_velocity *= 20;
//        bird.getBirdBody().setGravityScale(1f);
    }
}
