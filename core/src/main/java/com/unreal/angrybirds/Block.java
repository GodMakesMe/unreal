package com.unreal.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Block {
    World worldInstance;
    int height;
    int width;
    int saveScalex;
    int saveScaley;
    int mass;
    Texture blockTexture;

    Block(String imageFile, int scalex, int scaley, World world, int mass) {
        blockTexture = new Texture(imageFile);

    }
}

//public class
