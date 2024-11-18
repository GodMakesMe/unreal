package com.unreal.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;

public class Block implements Serializable {
    int health;
    transient World worldInstance;
    float height;
    float width;
    float saveScalex;
    float saveScaley;
    int mass;
    String fileImage;
    transient Texture blockTexture;
    transient Sprite blockSprite;
    float ini_pos_y;
    float ini_pos_x;
    float angle;
    float x;
    float y;

    private transient Body blockBody;
    private transient BodyDef blockBodydef;

    Block(String imageFile, float scalex, float scaley, World world, int mass, float pos_x, float pos_y, float angle, int health) {
        blockTexture = new Texture(imageFile);
        fileImage = imageFile;
        blockSprite = new Sprite(blockTexture);
        blockSprite.setSize(scalex, scaley);
        this.health =
        this.mass = mass;
        this.worldInstance = world;
        this.height = scalex;
        this.width = scaley;
        this.saveScalex = scalex;
        this.saveScaley = scaley;
        blockBodydef = new BodyDef();
        blockBodydef.position.x = pos_x;
        blockBodydef.position.y = pos_y;
        blockSprite.setPosition(pos_x, pos_y);
        blockSprite.setOriginCenter();
        blockSprite.setRotation(angle);
        ini_pos_x = pos_x;
        ini_pos_y = pos_y;
        this.angle = angle;
        blockBodydef.type = BodyDef.BodyType.DynamicBody;
        blockBody = worldInstance.createBody(blockBodydef);
        blockBody.setTransform(pos_x, pos_y, angle);
        PolygonShape blockShape = new PolygonShape();
        blockShape.setAsBox(blockSprite.getWidth()/2, blockSprite.getHeight()/2);
        blockBody.setFixedRotation(false);
        blockBody.setGravityScale(1);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = blockShape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.1f;
        Fixture fixture = blockBody.createFixture(fixtureDef);
        blockBody.createFixture(fixtureDef);
        blockBody.setUserData(this);
        fixture.setUserData(this);
        blockShape.dispose();
        blockSprite.setRotation((float) Math.toDegrees(angle));
    }

    protected void processSerialization(World world){
        blockTexture = new Texture(fileImage);

        blockSprite = new Sprite(blockTexture);
        blockSprite.setSize(saveScalex, saveScaley);
        worldInstance = world;
        blockBodydef = new BodyDef();
        blockBodydef.position.x = x;
        blockBodydef.position.y = y;
        blockSprite.setPosition(x-blockSprite.getWidth()/2,y-blockSprite.getHeight()/2);
        blockSprite.setOriginCenter();
        blockSprite.setRotation(angle);
        blockBodydef.type = BodyDef.BodyType.DynamicBody;
        blockBody = worldInstance.createBody(blockBodydef);
        PolygonShape blockShape = new PolygonShape();
        blockShape.setAsBox(blockSprite.getWidth()/2, blockSprite.getHeight()/2);
        blockBody.setFixedRotation(false);
        blockBody.setGravityScale(1);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = blockShape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.1f;
        Fixture fixture = blockBody.createFixture(fixtureDef);
        blockBody.createFixture(fixtureDef);
        blockBody.setUserData(this);
        fixture.setUserData(this);
        blockShape.dispose();
        blockSprite.setRotation((float) Math.toDegrees(angle));
    }

    public Block() {
    }

    void placeBlock(int x, int y) {
        blockSprite.setPosition(x, y);
    }
    void changeSize(int x, int y) {
        blockSprite.setSize(x, y);
        height = y;
        width = x;
    }

    public void updateSprite() {
        if(blockBody != null) {
            x = blockBody.getPosition().x;
            y = blockBody.getPosition().y;
            angle = blockBody.getAngle();
            getblockSprite().setPosition(x-blockSprite.getWidth()/2,y-blockSprite.getHeight()/2);
            blockSprite.setOriginCenter();
            blockBody.setTransform(blockBody.getPosition().x, blockBody.getPosition().y, blockBody.getAngle());
            blockSprite.setRotation((float) Math.toDegrees(angle));
        }
        else{
            getblockSprite().getTexture().dispose();
        }
    }

    protected Sprite getblockSprite() {
        return blockSprite;
    }
    protected Body getBlockBody(){ return blockBody;}
}

//public class
