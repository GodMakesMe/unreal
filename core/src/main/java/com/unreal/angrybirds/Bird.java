package com.unreal.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;

public class Bird {
    private String Name;
    private int Mass;
    private Ability BirdAbility;
    private int Health;
    private Sprite BirdSprite;
    private Texture BirdTexture;
    private float x;
    private float y;
    private Body BirdBody;
    private BodyDef BirdBodydef;
    private boolean islaunched;

    public Bird(String Name, int Mass, Ability BirdAbility,String BirdPath,World world) {
        this.Name = Name;
        this.Mass = Mass;
        this.BirdAbility = BirdAbility;
        this.Health = 100;
        BirdTexture = new Texture(BirdPath);
        BirdSprite = new Sprite(BirdTexture);
        BirdSprite.setSize(54, 52);
        BirdSprite.setOrigin(0, 0);
        this.x = 237;
        this.y = 720-BirdSprite.getHeight()-321;
        this.BirdBodydef = new BodyDef();
        this.BirdBodydef.type = BodyDef.BodyType.DynamicBody;
        this.BirdBodydef.position.x = x;
        this.BirdBodydef.position.y =y;
        this.BirdBody = world.createBody(BirdBodydef);
        BirdSprite.setPosition(x, y);

        PolygonShape BirdShape = new PolygonShape();
        BirdShape.setAsBox(BirdSprite.getWidth()/2,BirdSprite.getHeight()/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = BirdShape;
        fixtureDef.density = (float) this.getMass() /2;
        fixtureDef.friction = 0.05f;
        fixtureDef.restitution = 0.5f;
        this.islaunched = false;
        this.BirdBody.setGravityScale(1f);
        this.BirdBody.createFixture(fixtureDef);
        BirdShape.dispose();
    }
    public String getName() {
        return Name;
    }
    public int getMass() {
        return Mass;
    }
    public Ability getBirdAbility() {
        return BirdAbility;
    }
    public int getHealth() {
        return Health;
    }
    public Sprite getBirdSprite() {
        return BirdSprite;
    }
    public Texture getBirdTexture() {
        return BirdTexture;
    }
    public void setHealth(int health) {
        Health = health;
    }
    public void setBirdAbility(Ability birdAbility) {
        BirdAbility = birdAbility;
    }
    public void MoveUp(){
        y+=10;
        this.BirdSprite.setPosition(x, y);
    }
    public void MoveDown(){
        y-=10;
        this.BirdSprite.setPosition(x, y);
    }
    public void MoveLeft(){
        x-=10;
        this.BirdSprite.setPosition(x, y);
    }
    public void MoveRight(){
        x+=10;
        this.BirdSprite.setPosition(x, y);
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }

    public Body getBirdBody() {
        return BirdBody;
    }

//    public void Move(){
//        if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
//            this.BirdBodydef.position.y+=10;
//        }
//        if(Gdx.input.isKeyJustPressed(Input.Keys.S)){
//            this.BirdBodydef.position.y-=10;
//        }
//        if(Gdx.input.isKeyJustPressed(Input.Keys.D)){
//            this.BirdBodydef.position.x-=10;
//        }
//        if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
//            this.BirdBodydef.position.x+=10;
//        }
//        this.BirdSprite.setPosition(this.BirdBodydef.position.x, this.BirdBodydef.position.y);
//    }

    public void updateSprite(){
        float posX=0,posY=0;
        if(!islaunched){
            if(Gdx.input.isKeyPressed(Input.Keys.W)){
                posY+=0.5f;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.S)){
                posY-=0.5f;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.D)){
                posX+=0.5f;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.A)){
                posX-= 0.5f;
            }
        }
        BirdBody.setTransform(BirdBody.getPosition().x+posX,BirdBody.getPosition().y+posY,BirdBody.getAngle());
        getBirdSprite().setPosition(BirdBody.getPosition().x-BirdSprite.getWidth()/2, BirdBody.getPosition().y-BirdSprite.getHeight()/2);

        float changeX = BirdBody.getPosition().x+posX;
        float changeY = BirdBody.getPosition().y-posY;
        float angle = (float) Math.atan2(changeY, changeX);
        float dist = (float)  Math.sqrt(changeX*changeX+changeY*changeY);
        float velocity = dist*0.15f;

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && !islaunched){
            System.out.println(Math.pow(changeX,2)+"\t"+Math.pow(changeY,2)+"\t"+dist);
            System.out.println("Launching with impulse: X=" + MathUtils.cos(angle)*velocity + ", Y=" + MathUtils.sin(angle)*velocity);
//            BirdBody.applyLinearImpulse( MathUtils.cos(angle)*velocity, MathUtils.sin(angle)*velocity,BirdBody.getWorldCenter().x,BirdBody.getWorldCenter().y,true);
            BirdBody.setLinearVelocity(MathUtils.cos(angle)*velocity, MathUtils.sin(angle)*velocity);
//            BirdBody.set
            this.islaunched = true;
        }

    }
}
