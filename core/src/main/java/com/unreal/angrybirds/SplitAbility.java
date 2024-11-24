package com.unreal.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import java.io.Serializable;

public class SplitAbility implements Ability, Serializable {
    Bird newBlueBird1;
    Bird newBlueBird2;
    @Override
    public void triggerAbility(Bird myBirdLovely) {
        Music AbilitySFX = Gdx.audio.newMusic(Gdx.files.internal("BlueAbilitySFX.mp3"));
        newBlueBird1 = new Bird(myBirdLovely, myBirdLovely.getX()+10, myBirdLovely.getY()+10);
//        newBlueBird1.x_velocity = myBirdLovely.getBirdBody().getLinearVelocity().x;
//        newBlueBird1.y_velocity = myBirdLovely.getBirdBody().getLinearVelocity().y;
//        newBlueBird1.getBirdBody().setLinearVelocity(myBirdLovely.x_velocity, myBirdLovely.y_velocity);
//        newBlueBird1.updateSprite();
        newBlueBird1.setLaunched();
        newBlueBird2 = new Bird(myBirdLovely, myBirdLovely.getX()+5, myBirdLovely.getY()-20);
//        newBlueBird2.x_velocity = myBirdLovely.getBirdBody().getLinearVelocity().x;
//        newBlueBird2.y_velocity = myBirdLovely.getBirdBody().getLinearVelocity().y;
//        newBlueBird2.getBirdBody().setLinearVelocity(myBirdLovely.x_velocity, myBirdLovely.y_velocity);
//        newBlueBird1.updateSprite();
        newBlueBird2.setLaunched();
//        newBlueBird.isAbilityTriggered = true;
        AbilitySFX.play();
        newBlueBird1.isAbilityTriggered = true;
        newBlueBird2.isAbilityTriggered = true;
    }
    public void applyUpdate(){
//        System.out.println("Hello");
        newBlueBird1.updateSprite();
        newBlueBird2.updateSprite();

    }
}
