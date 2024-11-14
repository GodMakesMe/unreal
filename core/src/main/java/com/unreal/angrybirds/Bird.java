package com.unreal.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;

import java.io.Serializable;

public class Bird implements Serializable {
    private String Name;
    private int Mass;
    private transient Ability BirdAbility;
    private int Health;
    private transient Sprite BirdSprite;
    private transient Texture BirdTexture;
    private float x;
    private float y;
    private float iniX;
    private float iniY;
    private float GlobalX;
    private float GlobalY;
    private transient Body BirdBody;
    private transient BodyDef BirdBodydef;
    private boolean islaunched;
    private transient World worldInstance;
    private String Planet;
    private Vector2[] trajectoryPoints;
    private String birdPath;
    private float y_velocity;
    private float x_velocity;

    public Bird(String Name, int Mass, Ability BirdAbility,String BirdPath,World world,String Planet) {
        this.Name = Name;
        this.Mass = Mass;
        this.BirdAbility = BirdAbility;
        this.Health = 100;
        this.worldInstance = world;
        world.setGravity(new Vector2(0, 0f));
        this.birdPath = BirdPath;
        BirdTexture = new Texture(birdPath);
        BirdSprite = new Sprite(BirdTexture);
        BirdSprite.setSize(39, 38);
        BirdSprite.setOrigin(0, 0);
        this.x = 268;
        this.y = 720-BirdSprite.getHeight()-320;
        this.BirdBodydef = new BodyDef();
        this.BirdBodydef.type = BodyDef.BodyType.DynamicBody;
        this.BirdBodydef.position.x = x;
        this.BirdBodydef.position.y =y;
        this.iniX = x; this.iniY = y;
        this.BirdBody = world.createBody(BirdBodydef);
        BirdSprite.setPosition(x, y);
        this.GlobalX = 0;
        this.GlobalY = 0;
        PolygonShape BirdShape = new PolygonShape();
        BirdShape.setAsBox(BirdSprite.getWidth()/2,BirdSprite.getHeight()/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = BirdShape;
        fixtureDef.density = 2;
        System.out.println("Density of the Bird: "+fixtureDef.density);
//        fixtureDef.density = f;
        fixtureDef.friction = 0.05f;
        fixtureDef.restitution = 0.05f;
        this.islaunched = false;
        this.BirdBody.setGravityScale(1f);
        Fixture fixture = this.BirdBody.createFixture(fixtureDef);
        this.BirdBody.setUserData(this);
        fixture.setUserData(this);
        BirdShape.dispose();
        this.Planet = Planet;
        BirdBody.setGravityScale(0f);
    }

    public Bird() {
    }
    public void processSerialization(Ability birdAbility, World world){
        this.BirdAbility = BirdAbility;
        this.worldInstance = world;
        world.setGravity(new Vector2(0, 0f));
        if (BirdTexture == null) BirdTexture = new Texture(birdPath);
        if (BirdSprite == null) BirdSprite = new Sprite(BirdTexture);
        BirdSprite.setSize(39, 38);
        BirdSprite.setOrigin(0, 0);
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
        fixtureDef.density = 2;
        System.out.println("Density of the Bird: "+fixtureDef.density);
//        fixtureDef.density = f;
        fixtureDef.friction = 0.05f;
        fixtureDef.restitution = 0.05f;
        this.BirdBody.setGravityScale(1f);
        Fixture fixture = this.BirdBody.createFixture(fixtureDef);
        this.BirdBody.setUserData(this);
        fixture.setUserData(this);
        BirdShape.dispose();
        this.BirdBody.setLinearVelocity(new Vector2(x_velocity, y_velocity));
        if (!isIslaunched()) BirdBody.setGravityScale(0f);
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

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }

    public Body getBirdBody() {
        return BirdBody;
    }

    public boolean isIslaunched() {
        return islaunched;
    }

    public static float pixelstometers(float pixels) {
        return pixels / 100;
    }

    public static float meterstopixels(float meters) {
        return meters * 100;
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
    public void CreateTrajectory(Vector2 StartPos,float Velocity,float Angle,float Gravity,int points){
        this.trajectoryPoints = new Vector2[points];
        for(int i = 0;  i<100;i++){
            float t = i * 0.1f;
            float x_t = StartPos.x + Velocity * t * (float) Math.cos(Angle);
            float y_t = StartPos.y + Velocity*t*(float)Math.sin(Angle) + 0.5f * Gravity * t * t;
            trajectoryPoints[i] = new Vector2(x_t, y_t);
        }
    }

    public Vector2[] getTrajectoryPoints() {
        return trajectoryPoints;
    }
    public boolean notInOrigin(){
        return !(BirdBody.getPosition().x==iniX && BirdBody.getPosition().y==iniY);
    }

    public void DrawTrajectory(){
        ShapeRenderer ShapeRenderer =  new ShapeRenderer();
        ShapeRenderer.begin(com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Line);
        ShapeRenderer.setColor(Color.WHITE);
        Vector2[] TrajectoryPoints = this.getTrajectoryPoints();
        for(int i = 0;  i<TrajectoryPoints.length;i++){
            if (TrajectoryPoints[i] != null) {
                ShapeRenderer.circle(TrajectoryPoints[i].x, TrajectoryPoints[i].y, 1f);
            }
        }
        ShapeRenderer.end();
    }
    public void updateSprite(){
        float posX=0,posY=0;
//        && !(posX*posX+posY*posY<=1,681)
        if(!islaunched && !(GlobalY*GlobalY+GlobalX*GlobalX>=1681)){
            if(Gdx.input.isKeyPressed(Input.Keys.W)){
                posY+=0.5f;
                GlobalY+=0.5f;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.S)){
                posY-=0.5f;
                GlobalY-=0.5f;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.D)){
                posX+=0.5f;
                GlobalX+=0.5f;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.A)){
                posX-= 0.5f;
                GlobalX-=0.5f;
            }
//            System.out.println("GlobalX  "+GlobalX+"\tGlobalX  "+GlobalY);
        }else if (GlobalY*GlobalY+GlobalX*GlobalX>=1681){
            float angle = MathUtils.atan2(GlobalY,GlobalX);
            float y_s = GlobalY > 0 ? -0.8f*Math.abs(MathUtils.sin(angle)) : 0.8f*Math.abs(MathUtils.sin(angle));
            float x_s = GlobalX > 0 ? -0.8f*Math.abs(MathUtils.cos(angle)) : 0.8f*Math.abs(MathUtils.cos(angle));
            GlobalX += x_s;
            GlobalY += y_s;
            posX += x_s;
            posY += y_s;
        }
        BirdBody.setTransform(BirdBody.getPosition().x+posX,BirdBody.getPosition().y+posY,BirdBody.getAngle());
        getBirdSprite().setPosition(BirdBody.getPosition().x-BirdSprite.getWidth()/2, BirdBody.getPosition().y-BirdSprite.getHeight()/2);
//        x = BirdBody.getPosition().x - BirdSprite.getWidth()/2;
//        y = BirdBody.getPosition().y - BirdSprite.getHeight()/2;
        x = BirdBody.getPosition().x;
        y = BirdBody.getPosition().y;
        float gravity = -3.73f;
        if(Planet.equals("Mars")){
            gravity = -3.73f;
        }if (Planet.equals("Earth")){
            gravity = -9.8f;
        }if (Planet.equals("Jupiter")){
            gravity = -9.8f*3.0f;
        }if (Planet.equals("Saturn")){
            gravity = -9.8f*3.0f;
        }if (Planet.equals("Uranus")){
            gravity = -9.8f*0.8f;
        }if (Planet.equals("Neptune")){
            gravity = -9.8f*0.8f;
        }if (Planet.equals("Moon")){
            gravity = -9.8f*0.5f;
        }if (Planet.equals("Mercury")){
            gravity = -9.8f*0.9f;
        }if (Planet.equals("Venus")){
            gravity = -9.8f*0.9f;
        }
        float changeX = -BirdBody.getPosition().x+268;
        float changeY = -BirdBody.getPosition().y+720-BirdSprite.getHeight()-320;
        float angle = (float) Math.atan2(changeY, changeX);
        float dist = (float)  Math.sqrt(changeX*changeX+changeY*changeY);
        float velocity = dist*3f;
        x_velocity = BirdBody.getLinearVelocity().x;
        y_velocity = BirdBody.getLinearVelocity().y;
//        if (!islaunched) {
        CreateTrajectory(new Vector2(BirdBody.getPosition().x, BirdBody.getPosition().y), velocity, angle, gravity, 100);
//        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && !islaunched){
            worldInstance.setGravity(new Vector2(0, gravity));
//            System.out.println(Math.pow(changeX,2)+"\t"+Math.pow(changeY,2)+"\t"+dist);
            System.out.println("Launching with impulse: X=" + MathUtils.cos(angle)*velocity + ", Y=" + MathUtils.sin(angle)*velocity);
            BirdBody.setLinearVelocity(MathUtils.cos(angle)*velocity, MathUtils.sin(angle)*velocity);
            this.islaunched = true;
            BirdBody.setGravityScale(1f);
        }

    }

    public World getWorldInstance() {
        return worldInstance;
    }
}
