package com.unreal.angrybirds;

import com.badlogic.gdx.physics.box2d.Body;

import java.io.Serializable;

//import java.io.Serializable;

public interface Ability extends Serializable {
    void triggerAbility(Bird myBirdLovely);
}
