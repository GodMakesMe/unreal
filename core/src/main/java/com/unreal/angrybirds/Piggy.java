package com.unreal.angrybirds;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;

public class Piggy implements Serializable {
    private String Name;
    private int Mass;
    private transient Ability PiggyAbility;
    private int Health;
    private transient Sprite PiggySprite;
    private transient Texture PiggyTexture;
    private float x;
    private float y;
    private float pigWidth;
    private float pigHeight;
    private transient Body PiggyBody;
    private transient BodyDef PiggyBodydef;
    private transient World worldInstance;
    private boolean isRemoved = false;
    private int score;
    private float angle;
    protected boolean dead = false;
    private String pigPath;
    private transient Music PiggySpawnSFX;
    private transient Music PiggyDamageSFX;
    private transient Music PiggyDeathSFX;
    public Piggy(String Name, int Mass,Ability PiggyAbility,String PiggyPath,World world,String Planet,int x,int y,int width,int height,int score) {
        this.Name = Name;
        this.Mass = Mass;
        this.PiggyAbility = PiggyAbility;
        this.Health = 100;
        this.worldInstance = world;
        this.score = score;
        this.pigPath = PiggyPath;
        PiggyTexture = new Texture(pigPath);
        PiggySprite = new Sprite(PiggyTexture);
        pigWidth = width; pigHeight = height;
        PiggySprite.setSize(pigWidth, pigHeight);
        PiggySprite.setOrigin(0, 0f);
//        worldInstance.setGravity(new Vector2(0, -3.73f));
//        this.x = 268;
//        this.y = 720-PiggySprite.getHeight()-320;
        this.PiggyBodydef = new BodyDef();
        this.PiggyBodydef.type = BodyDef.BodyType.DynamicBody;
        this.PiggyBodydef.position.x = x;
        this.PiggyBodydef.position.y =y;
        this.PiggyBody = world.createBody(PiggyBodydef);
        PiggySprite.setPosition(x, y);
        PolygonShape PiggyShape = new PolygonShape();
        PiggyShape.setAsBox(PiggySprite.getWidth()/2,PiggySprite.getHeight()/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = PiggyShape;
//        fixtureDef.density = (float) this.getMass() /2;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.05f;
        fixtureDef.restitution = 0.1f;
        this.PiggyBody.setGravityScale(1f);
        Fixture fixture = this.PiggyBody.createFixture(fixtureDef);
        this.PiggyBody.setFixedRotation(false);
        this.PiggyBody.setUserData(this);
        fixture.setUserData(this);
//        this.PiggyBody.applyAngularImpulse(500,true);
//        this.PiggyBody.applyTorque(100f, true);
        PiggyShape.dispose();
        PiggySpawnSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/PiggySpawn.mp3"));
        PiggySpawnSFX.play();
        PiggyDamageSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/PiggyCollision.mp3"));
        PiggyDeathSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/PiggyDeath.mp3"));
    }

    public void playDamageSound() {
        PiggyDamageSFX.play();
    }
    public void playDeathSound() {
        PiggyDeathSFX.play();
    }

    public String getName() {
        return Name;
    }
    public int getMass() {
        return Mass;
    }

    public int getScore() {
        return score;
    }

    public Ability getPiggyAbility() {
        return PiggyAbility;
    }
    public int getHealth() {
        return Health;
    }
    public Sprite getPiggySprite() {
        return PiggySprite;
    }
    public Texture getPiggyTexture() {
        return PiggyTexture;
    }
    public void setHealth(int health) {
        Health = health;
    }
    public void setPiggyAbility(Ability PiggyAbility) {
        PiggyAbility = PiggyAbility;
    }
    public static float pixelstometers(float pixels) {
        return pixels / 100;
    }

    public static float meterstopixels(float meters) {
        return meters * 100;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }

    public Body getPiggyBody() {
        return PiggyBody;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public boolean pigdied(){
        return dead;
    }

    public void setPiggyTexture(Texture piggyTexture) {
        PiggyTexture = piggyTexture;
    }

    public void updateSprite() {
        if(PiggyBody != null) {
            x = PiggyBody.getPosition().x;
            y = PiggyBody.getPosition().y;
            angle = PiggyBody.getAngle();
            getPiggySprite().setPosition(x-pigWidth/2,y-pigHeight/2);
            PiggyBody.setTransform(PiggyBody.getPosition().x, PiggyBody.getPosition().y, PiggyBody.getAngle());
            PiggySprite.setRotation((float) Math.toDegrees(angle));
            PiggySprite.setOrigin(PiggySprite.getWidth()/2, PiggySprite.getHeight()/2);
            if(PiggyBody.getPosition().y<0){
                this.markForRemoval();
            }
        }
        else{
            getPiggySprite().getTexture().dispose();
        }
//        if (PiggyBody.getLinearVelocity().y < -1) {
//            this.PiggyBody.applyTorque(5f, true);
//        }
    }
    public void processSerialization(Ability piggyAbility, World world){
        this.Name = Name;
        this.Mass = Mass;
        this.PiggyAbility = PiggyAbility;
        this.Health = 100;
        this.worldInstance = world;
        this.score = score;
//        this.pigPath = PiggyPath;
        if (PiggyTexture == null) PiggyTexture = new Texture(pigPath);
        if (PiggySprite == null) PiggySprite = new Sprite(PiggyTexture);
        PiggySprite.setSize(pigWidth, pigHeight);
        PiggySprite.setOrigin(0, 0f);
//        worldInstance.setGravity(new Vector2(0, -3.73f));
//        this.x = 268;
//        this.y = 720-PiggySprite.getHeight()-320;
        this.PiggyBodydef = new BodyDef();
        this.PiggyBodydef.type = BodyDef.BodyType.DynamicBody;
        this.PiggyBodydef.position.x = x;
        this.PiggyBodydef.position.y = y;
        this.PiggyBody = world.createBody(PiggyBodydef);
        PiggySprite.setPosition(x, y);
        PolygonShape PiggyShape = new PolygonShape();
        PiggyShape.setAsBox(PiggySprite.getWidth()/2,PiggySprite.getHeight()/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = PiggyShape;
//        fixtureDef.density = (float) this.getMass() /2;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.05f;
        fixtureDef.restitution = 0.1f;
        this.PiggyBody.setGravityScale(1f);
        Fixture fixture = this.PiggyBody.createFixture(fixtureDef);
        this.PiggyBody.setFixedRotation(false);
        this.PiggyBody.setUserData(this);
        fixture.setUserData(this);
//        this.PiggyBody.applyAngularImpulse(500,true);
//        this.PiggyBody.applyTorque(100f, true);
        PiggyShape.dispose();
        PiggySpawnSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/PiggySpawn.mp3"));
//        PiggySpawnSFX.play();
        PiggyDamageSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/PiggyCollision.mp3"));
        PiggyDeathSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/PiggyDeath.mp3"));
    }
    public void applyTorqueOnImpact(float angleOfImpact) {
        if (angleOfImpact > 45 && angleOfImpact < 135) {
            this.PiggyBody.applyTorque(10f, true);
        }
    }

    public void markForRemoval(){
        isRemoved = true;
    }
    public World getWorldInstance() {
        return worldInstance;
    }
    public void selfdestroy(){
        dead = true;
        getPiggyBody().setUserData(null);
        worldInstance.destroyBody(PiggyBody);
//        this.PiggyBody = null;
    }
}
