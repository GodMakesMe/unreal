package com.unreal.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import java.io.Serializable;

public class WarCryAbility implements Ability, Serializable {
    @Override
    public void triggerAbility(Bird myBirdLovely) {
        Music abilitySFX = Gdx.audio.newMusic(Gdx.files.internal("RedAbilitySFX.mp3"));
        abilitySFX.setVolume(1f);
        abilitySFX.play();
    }
}
