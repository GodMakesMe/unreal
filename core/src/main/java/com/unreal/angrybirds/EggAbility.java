package com.unreal.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class EggAbility implements Ability{
    Bird egg;
    @Override
    public void triggerAbility(Bird LovelyBird){
        Music AbilitySFX = Gdx.audio.newMusic(Gdx.files.internal("MatildaAbilitySFX.mp3"));
        AbilitySFX.play();
        egg = new Bird(LovelyBird, LovelyBird.getX(), LovelyBird.getY()-35, false);
        egg.setLaunched();
        egg.isAbilityTriggered = true;
        egg.getBirdBody().setLinearVelocity(0f, -50);
        egg.getBirdSprite().setTexture(new Texture("assets/Egg.png"));

    }
    void updateEgg(){
        egg.getBirdBody().setLinearVelocity(0f, egg.getBirdBody().getLinearVelocity().y);
        egg.updateSprite();
    }
}
