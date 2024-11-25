package com.unreal.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import java.io.Serializable;

public class ExplodeAbility implements Ability, Serializable {
    int counter = 80;
    Bird myBird;
    @Override
    public void triggerAbility(Bird myBirdLovely) {
        Music AbilitySFX = Gdx.audio.newMusic(Gdx.files.internal("BombAbilitySFX.mp3"));
        AbilitySFX.play();
        myBirdLovely.getBirdBody().setLinearVelocity(myBirdLovely.x_velocity*20, myBirdLovely.y_velocity*20);
        myBird = myBirdLovely;
        myBird.getBirdBody().setFixedRotation(true);
        myBirdLovely.isAbilityTriggered = true;
    }
    public void update(){
//        if (counter-- > 0) myBird.getBirdBody().setLinearVelocity(myBird.x_velocity*20, myBird.y_velocity*20);
        if (counter-- > 0) myBird.getBirdBody().setLinearVelocity(myBird.x_velocity*20*(int)Math.pow(-1,counter/20), myBird.y_velocity*20*(int)Math.pow(-1,counter/5));
        else myBird.getBirdBody().setFixedRotation(false);
    }
}
