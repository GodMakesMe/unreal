package com.unreal.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.io.Serializable;

public class EggAbility implements Ability, Serializable {
    Bird egg;
    int count = 100;
    @Override
    public void triggerAbility(Bird LovelyBird){
        Music AbilitySFX = Gdx.audio.newMusic(Gdx.files.internal("MatildaAbilitySFX.mp3"));
        AbilitySFX.play();
        egg = new Bird(LovelyBird, LovelyBird.getX(), LovelyBird.getY()-35, false);
        egg.setLaunched();
        egg.isAbilityTriggered = true;
        egg.getBirdBody().setLinearVelocity(0f, -150);
        egg.getBirdSprite().setTexture(new Texture("assets/Egg.png"));
    }
    void updateEgg(){
        if (count-- > 0) egg.getBirdBody().setLinearVelocity(0f, -150);
        egg.updateSprite();
    }
}
