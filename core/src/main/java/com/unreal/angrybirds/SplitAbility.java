package com.unreal.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SplitAbility implements Ability{
    Bird newBlueBird1;
    Bird newBlueBird2;
    @Override
    public void triggerAbility(Bird myBirdLovely) {
        Music AbilitySFX = Gdx.audio.newMusic(Gdx.files.internal("BlueAbilitySFX.mp3"));
        newBlueBird1 = new Bird(myBirdLovely, myBirdLovely.getX(), myBirdLovely.getY()+10);
//        newBlueBird1.updateSprite();
        newBlueBird1.setLaunched();
        newBlueBird2 = new Bird(myBirdLovely, myBirdLovely.getX(), myBirdLovely.getY()-10);
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
