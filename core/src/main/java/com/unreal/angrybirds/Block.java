package com.unreal.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import java.io.Serializable;

public class Block implements Serializable {
    int health;
    transient World worldInstance;
    float height;
    float width;
    float saveScalex;
    float saveScaley;
    float mass;
    String fileImage;
    transient Texture blockTexture;
    transient Sprite blockSprite;
    float ini_pos_y;
    float ini_pos_x;
    float angle;
    float x;
    float y;
    boolean isRemoved;
    private transient Body blockBody;
    private transient BodyDef blockBodydef;
    private transient Music DamageSFX;
    private transient Music DeathSFX;
    private String Material;
    private int Frames;
    private Vector2[] fixtureCoordinates;

    Block(String Material,String imageFile, float scalex, float scaley, World world, float mass, float pos_x, float pos_y, float angle, int health) {
        this.Material = Material;
        blockTexture = new Texture(imageFile);
        fileImage = imageFile;
        blockSprite = new Sprite(blockTexture);
        blockSprite.setSize(scalex, scaley);
        this.health = health;
        this.mass = mass;
        this.worldInstance = world;
        this.height = scalex;
        this.width = scaley;
        this.saveScalex = scalex;
        this.saveScaley = scaley;
        blockBodydef = new BodyDef();
        blockBodydef.position.x = pos_x;
        blockBodydef.position.y = pos_y;
        isRemoved = false;
        blockSprite.setPosition(pos_x, pos_y);
        blockSprite.setOriginCenter();
        blockSprite.setRotation(angle);
        ini_pos_x = pos_x;
        ini_pos_y = pos_y;
        this.Frames = 0;
        this.angle = angle;
        blockBodydef.type = BodyDef.BodyType.DynamicBody;
        blockBody = worldInstance.createBody(blockBodydef);
        blockBody.setTransform(pos_x, pos_y, angle);
        PolygonShape blockShape = new PolygonShape();
        if (fixtureCoordinates == null) blockShape.setAsBox(blockSprite.getWidth()/2, blockSprite.getHeight()/2);
        else blockShape.set(fixtureCoordinates);
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
        DamageSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/"+Material+"Collision.mp3"));
        DamageSFX.setVolume(0.1f);
        DeathSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/"+Material+"Death.mp3"));
    }
    public void playDamageSound() {
        DamageSFX.setVolume(0.1f);
        if (DamageSFX != null) DamageSFX.play();
    }
    public void playDeathSound() {
        DeathSFX.play();
    }

    protected void processSerialization(World world){
        if (isRemoved) return;
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
        blockBody.setTransform(x, y, angle);
        PolygonShape blockShape = new PolygonShape();
        if (fixtureCoordinates == null) blockShape.setAsBox(blockSprite.getWidth()/2, blockSprite.getHeight()/2);
        else blockShape.set(fixtureCoordinates);
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
        DamageSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/"+Material+"Collision.mp3"));
        DeathSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/"+Material+"Death.mp3"));
    }

    public Block() {
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
    }

    void placeBlock(int x, int y) {
        blockSprite.setPosition(x, y);
    }
    void changeSize(int x, int y) {
        blockSprite.setSize(x, y);
        height = y;
        width = x;
    }

    Block setShape(Vector2[] coordinates){
        Array<Fixture> fixtures = getBlockBody().getFixtureList();
        while (fixtures.size > 0) {
            getBlockBody().destroyFixture(fixtures.first());
        }
        fixtureCoordinates = coordinates;
        PolygonShape blockShape = new PolygonShape();
        blockShape.set(fixtureCoordinates);
//        blockBody.setFixedRotation(false);
//        blockBody.setGravityScale(1);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = blockShape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.2f;
        Fixture fixture = blockBody.createFixture(fixtureDef);
        blockBody.createFixture(fixtureDef);
        blockBody.setUserData(this);
        fixture.setUserData(this);
        blockShape.dispose();

        return this;
    }
    public void updateSprite() {
        if(blockBody != null) {
            if (health <= 0){ selfdestroy(); return;}
            x = blockBody.getPosition().x;
            y = blockBody.getPosition().y;
            if (y < -getblockSprite().getHeight()/2) {selfdestroy(); return;}
            if (getBlockBody().getLinearVelocity().len() < 0.1f) {
                Frames++;
            } else {
                Frames = 0;
            }
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
    boolean isRested(){
        if (Frames >= 60) return true;
        return false;
    }

    protected Sprite getblockSprite() {
        return blockSprite;
    }
    protected Body getBlockBody(){ return blockBody;}
    public void selfdestroy(){
        if (!isRemoved) {
            playDeathSound();
            getBlockBody().setUserData(null);
            Array<Fixture> fixtures = getBlockBody().getFixtureList();
            while (fixtures.size > 0) {
                getBlockBody().destroyFixture(fixtures.first());
            }
            worldInstance.destroyBody(blockBody);
            this.blockBody = null;
            if (blockTexture != null) {
                blockTexture.dispose();
                blockTexture = null;
            }
            blockSprite = null;
            isRemoved = true;
        }
    }
}
