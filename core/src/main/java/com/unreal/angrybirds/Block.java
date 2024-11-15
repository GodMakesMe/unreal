package com.unreal.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import java.io.Serializable;

public class Block implements Serializable {
    transient World worldInstance;
    int height;
    int width;
    int saveScalex;
    int saveScaley;
    int mass;
    transient Texture blockTexture;
    transient Sprite blockSprite;

    private transient Body BirdBody;
    private transient BodyDef BirdBodydef;

    Block(String imageFile, int scalex, int scaley, World world, int mass) {
        blockTexture = new Texture(imageFile);
        blockSprite = new Sprite(blockTexture);
        blockSprite.setSize(scalex, scaley);
        this.mass = mass;
        this.worldInstance = world;
        this.height = scalex;
        this.width = scaley;
        this.saveScalex = scalex;
        this.saveScaley = scaley;

    }

    public Block() {
    }

    void placeBlock(int x, int y) {
        blockSprite.setPosition(x, y);
    }
}

//public class
