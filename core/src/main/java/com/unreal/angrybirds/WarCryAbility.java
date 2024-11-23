package com.unreal.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class WarCryAbility implements Ability{
    private Music AbilitySFX;
    @Override
    public void triggerAbility(Bird myBirdLovely) {
        AbilitySFX = Gdx.audio.newMusic(Gdx.files.internal("RedAbilitySFX.mp3"));
        AbilitySFX.setVolume(1f);
        AbilitySFX.play();
    }
}
