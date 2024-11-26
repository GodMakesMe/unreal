package com.unreal.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import java.io.Serializable;

public class Bird implements Serializable {
    private String Name;
    private int Mass;
    private Ability BirdAbility;
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
    protected float y_velocity;
    protected float x_velocity;
    private transient Music BirdSpawnSFX;
    private transient Music BirdLaunchSFX;
    private transient Music BirdHitSFX;
    private transient Music BirdDeathSFX;
    private int Frames;
    private boolean isRemoved;
    private boolean isStretched;
    private transient Music StretchSFX;
    private transient Music StretchLaunchSFX;
    public boolean isfirstCollided;
    private Vector2[] fixtureCoordinates;

    public boolean isAbilityTriggered;

    public Bird(Bird oldBirdInstance, float x, float y){
        this.worldInstance = oldBirdInstance.worldInstance;
        this.x = x;
        this.y = y;
        this.fixtureCoordinates = oldBirdInstance.fixtureCoordinates;
        this.Name = oldBirdInstance.Name;
        this.Mass = oldBirdInstance.Mass;
//        this.BirdAbility = oldBirdInstance.BirdAbility;
        this.Health = oldBirdInstance.Health;
        this.BirdSprite = oldBirdInstance.BirdSprite;
        this.BirdTexture = oldBirdInstance.BirdTexture;
        this.islaunched = oldBirdInstance.islaunched;
        this.isRemoved = oldBirdInstance.isRemoved;
        this.isStretched = oldBirdInstance.isStretched;
        this.trajectoryPoints = oldBirdInstance.trajectoryPoints;
        this.birdPath = oldBirdInstance.birdPath;
        BirdTexture = new Texture(birdPath);
        BirdSprite = new Sprite(BirdTexture);
//        this.BirdSpawnSFX = oldBirdInstance.BirdSpawnSFX;

//        this.BirdLaunchSFX = oldBirdInstance.BirdLaunchSFX;
        if (true){
            this.BirdHitSFX = oldBirdInstance.BirdHitSFX;
            this.BirdDeathSFX = oldBirdInstance.BirdDeathSFX;
        }

        this.Frames = oldBirdInstance.Frames;
        this.isfirstCollided = oldBirdInstance.isfirstCollided;
        this.isAbilityTriggered = oldBirdInstance.isAbilityTriggered;
        BirdSprite.setSize(39, 38);
        BirdSprite.setOrigin(0, 0);
        this.BirdBodydef = new BodyDef();
        this.BirdBodydef.type = BodyDef.BodyType.DynamicBody;
        this.BirdBodydef.position.x = x;
        this.BirdBodydef.position.y =y;
        this.iniX = x; this.iniY = y;
        this.BirdBody = worldInstance.createBody(BirdBodydef);
        BirdSprite.setPosition(x, y);
        this.GlobalX = 0;
        this.GlobalY = 0;
        PolygonShape BirdShape = new PolygonShape();
        if (fixtureCoordinates == null) BirdShape.setAsBox(BirdSprite.getWidth()/2,BirdSprite.getHeight()/2);
        else BirdShape.set(fixtureCoordinates);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = BirdShape;
        fixtureDef.density = 2;
        System.out.println("Density of the Bird: "+fixtureDef.density);
//        fixtureDef.density = f;
        fixtureDef.friction = 0.05f;
        fixtureDef.restitution = 0.05f;
        this.BirdBody.setFixedRotation(false);
        this.BirdBody.setGravityScale(1f);
        BirdBody.setLinearDamping(0);
        BirdBody.setAngularDamping(0);
        Fixture fixture = this.BirdBody.createFixture(fixtureDef);
        this.BirdBody.setUserData(this);
        this.BirdBody.setTransform(x, y, getBirdBody().getAngle());
        this.BirdBody.setLinearVelocity(oldBirdInstance.x_velocity, oldBirdInstance.y_velocity*0f);
        fixture.setUserData(this);
        BirdShape.dispose();
        this.Planet = oldBirdInstance.Planet;
        this.Frames = oldBirdInstance.Frames;
    }
public Bird(Bird oldBirdInstance, float x, float y, boolean noSound){
        this.worldInstance = oldBirdInstance.worldInstance;
        this.x = x;
        this.y = y;
        this.Name = oldBirdInstance.Name;
        this.Mass = oldBirdInstance.Mass;
        this.fixtureCoordinates = oldBirdInstance.fixtureCoordinates;
//        this.BirdAbility = oldBirdInstance.BirdAbility;
        this.Health = oldBirdInstance.Health;
        this.BirdSprite = oldBirdInstance.BirdSprite;
        this.BirdTexture = oldBirdInstance.BirdTexture;
        this.islaunched = oldBirdInstance.islaunched;
        this.isRemoved = oldBirdInstance.isRemoved;
        this.isStretched = oldBirdInstance.isStretched;
        this.trajectoryPoints = oldBirdInstance.trajectoryPoints;
        this.birdPath = oldBirdInstance.birdPath;
        BirdTexture = new Texture(birdPath);
        BirdSprite = new Sprite(BirdTexture);
//        this.BirdSpawnSFX = oldBirdInstance.BirdSpawnSFX;

//        this.BirdLaunchSFX = oldBirdInstance.BirdLaunchSFX;
        if (!noSound){
            this.BirdHitSFX = oldBirdInstance.BirdHitSFX;
            this.BirdDeathSFX = oldBirdInstance.BirdDeathSFX;
        }

        this.Frames = oldBirdInstance.Frames;
        this.isfirstCollided = oldBirdInstance.isfirstCollided;
        this.isAbilityTriggered = oldBirdInstance.isAbilityTriggered;
        BirdSprite.setSize(39, 38);
        BirdSprite.setOrigin(0, 0);
        this.BirdBodydef = new BodyDef();
        this.BirdBodydef.type = BodyDef.BodyType.DynamicBody;
        this.BirdBodydef.position.x = x;
        this.BirdBodydef.position.y =y;
        this.iniX = x; this.iniY = y;
        this.BirdBody = worldInstance.createBody(BirdBodydef);
        BirdSprite.setPosition(x, y);
        this.GlobalX = 0;
        this.GlobalY = 0;
        PolygonShape BirdShape = new PolygonShape();
        if (fixtureCoordinates == null) BirdShape.setAsBox(BirdSprite.getWidth()/2,BirdSprite.getHeight()/2);
        else BirdShape.set(fixtureCoordinates);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = BirdShape;
        fixtureDef.density = 2;
        System.out.println("Density of the Bird: "+fixtureDef.density);
//        fixtureDef.density = f;
        fixtureDef.friction = 0.05f;
        fixtureDef.restitution = 0.05f;
        this.BirdBody.setFixedRotation(false);
        this.BirdBody.setGravityScale(1f);
        BirdBody.setLinearDamping(0);
        BirdBody.setAngularDamping(0);
        Fixture fixture = this.BirdBody.createFixture(fixtureDef);
        this.BirdBody.setUserData(this);
        this.BirdBody.setTransform(x, y, getBirdBody().getAngle());
        this.BirdBody.setLinearVelocity(oldBirdInstance.x, oldBirdInstance.y*0f);
        fixture.setUserData(this);
        BirdShape.dispose();
        this.Planet = oldBirdInstance.Planet;
        this.Frames = oldBirdInstance.Frames;
    }

    public Bird(String Name, int Mass, Ability BirdAbility,String BirdPath,World world,String Planet) {
        this.Name = Name;
        this.Mass = Mass;
        this.BirdAbility = BirdAbility;
        this.Health = 100;
        this.worldInstance = world;
//        world.setGravity(new Vector2(0, 0f));
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
        if (fixtureCoordinates == null) BirdShape.setAsBox(BirdSprite.getWidth()/2,BirdSprite.getHeight()/2);
        else BirdShape.set(fixtureCoordinates);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = BirdShape;
        fixtureDef.density = 2;
        System.out.println("Density of the Bird: "+fixtureDef.density);
//        fixtureDef.density = f;
        fixtureDef.friction = 0.05f;
        fixtureDef.restitution = 0.05f;
        BirdBody.setFixedRotation(false);
        this.islaunched = false;
        this.BirdBody.setGravityScale(1f);
        BirdBody.setLinearDamping(0);
        BirdBody.setAngularDamping(0);
        Fixture fixture = this.BirdBody.createFixture(fixtureDef);
        this.BirdBody.setUserData(this);
        fixture.setUserData(this);
        BirdShape.dispose();
        this.Planet = Planet;
        BirdBody.setGravityScale(0f);
        BirdSpawnSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/"+Name+"SpawnSFX.mp3"));
        BirdSpawnSFX.play();
        BirdLaunchSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/"+Name+"LaunchSFX.mp3"));
        BirdHitSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/"+Name+"HitSFX.mp3"));
        BirdDeathSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/"+Name+"DeathSFX.mp3"));
        Frames=0;
        isRemoved = false;
        isStretched = false;
        StretchSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/StretchSFX.mp3"));
        StretchLaunchSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/SlingLaunchSFX.mp3"));
        isAbilityTriggered = false;
    }

    public Bird(String Name, int Mass, Ability BirdAbility,String BirdPath,World world) {
        this.Name = Name;
        this.Mass = Mass;
        this.BirdAbility = BirdAbility;
        this.Health = 100;
        this.worldInstance = world;
//        world.setGravity(new Vector2(0, 0f));
        this.birdPath = BirdPath;
        BirdTexture = new Texture(birdPath);
        BirdSprite = new Sprite(BirdTexture);
        BirdSprite.setSize(39, 38);
        BirdSprite.setOrigin(0, 0);
        this.x = 143;
        this.y = 720-BirdSprite.getHeight()-494;
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
        if (fixtureCoordinates == null) BirdShape.setAsBox(BirdSprite.getWidth()/2,BirdSprite.getHeight()/2);
        else BirdShape.set(fixtureCoordinates);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = BirdShape;
        fixtureDef.density = 2;
        System.out.println("Density of the Bird: "+fixtureDef.density);
//        fixtureDef.density = f;
        fixtureDef.friction = 0.05f;
        fixtureDef.restitution = 0.05f;
        BirdBody.setFixedRotation(false);
        this.islaunched = false;
        this.BirdBody.setGravityScale(1f);
        BirdBody.setLinearDamping(0);
        BirdBody.setAngularDamping(0);
        Fixture fixture = this.BirdBody.createFixture(fixtureDef);
        this.BirdBody.setUserData(this);
        fixture.setUserData(this);
        BirdShape.dispose();
        this.Planet = "EarthSurface";
        BirdBody.setGravityScale(0f);
        BirdSpawnSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/"+Name+"SpawnSFX.mp3"));
        BirdSpawnSFX.play();
        BirdLaunchSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/"+Name+"LaunchSFX.mp3"));
        BirdHitSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/"+Name+"HitSFX.mp3"));
        BirdDeathSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/"+Name+"DeathSFX.mp3"));
        Frames=0;
        isRemoved = false;
        isStretched = false;
        StretchSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/StretchSFX.mp3"));
        StretchLaunchSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/SlingLaunchSFX.mp3"));
        isAbilityTriggered = false;
    }

    public Bird(String Name, int Mass,float x,float y,float Vx,float Vy,World world) {
        this.Name = Name;
        this.Mass = Mass;
        this.BirdAbility = null;
        this.worldInstance = world;
        this.x = x;
        this.y = y;
        this.BirdBodydef = new BodyDef();
        this.BirdBodydef.type = BodyDef.BodyType.DynamicBody;
        this.BirdBodydef.position.x = x;
        this.BirdBodydef.position.y =y;
        this.iniX = x; this.iniY = y;
        this.BirdBody = world.createBody(BirdBodydef);
        this.GlobalX = 0;
        this.GlobalY = 0;
        PolygonShape BirdShape = new PolygonShape();
        if (fixtureCoordinates == null) BirdShape.setAsBox(1,1);
        else BirdShape.set(fixtureCoordinates);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = BirdShape;
        fixtureDef.density = 2;
        fixtureDef.friction = 0.05f;
        fixtureDef.restitution = 0.05f;
        BirdBody.setFixedRotation(false);
        this.islaunched = false;
        this.BirdBody.setGravityScale(1f);
        BirdBody.setLinearDamping(0);
        BirdBody.setAngularDamping(0);
        Fixture fixture = this.BirdBody.createFixture(fixtureDef);
        this.BirdBody.setUserData(this);
        fixture.setUserData(this);
        this.BirdBody.setLinearVelocity(Vx, Vy);
        BirdShape.dispose();
        this.Planet = "EarthSurface";
        BirdBody.setGravityScale(0f);
        Frames=0;
        isRemoved = false;
        isStretched = false;
        isAbilityTriggered = false;
    }

    public Bird() {
    }

    public void playHitSound() {
//        if ()
        if (BirdHitSFX!=null)BirdHitSFX.play();
    }
    public void playDeathSound() {
        if (BirdDeathSFX != null) BirdDeathSFX.play();
    }

    public void processSerialization(Ability birdAbility, World world){
//        this.BirdAbility = BirdAbility;
        this.worldInstance = world;
//        world.setGravity(new Vector2(0, 0f));
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
        if (fixtureCoordinates == null) BirdShape.setAsBox(BirdSprite.getWidth()/2,BirdSprite.getHeight()/2);
        else BirdShape.set(fixtureCoordinates);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = BirdShape;
        fixtureDef.density = 2;
        System.out.println("Density of the Bird: "+fixtureDef.density);
//        fixtureDef.density = f;
        fixtureDef.friction = 0.05f;
        fixtureDef.restitution = 0.05f;
        this.BirdBody.setGravityScale(1f);
        this.BirdBody.setFixedRotation(false);
        Fixture fixture = this.BirdBody.createFixture(fixtureDef);
        this.BirdBody.setUserData(this);
        fixture.setUserData(this);
        BirdShape.dispose();
        this.BirdBody.setLinearVelocity(new Vector2(x_velocity, y_velocity));
        if (!isItLaunched()) BirdBody.setGravityScale(0f);
        BirdSpawnSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/"+Name+"SpawnSFX.mp3"));
//        BirdSpawnSFX.play();
        BirdLaunchSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/"+Name+"LaunchSFX.mp3"));
        BirdHitSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/"+Name+"HitSFX.mp3"));
        BirdDeathSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/"+Name+"DeathSFX.mp3"));
        StretchSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/StretchSFX.mp3"));
        StretchLaunchSFX = Gdx.audio.newMusic(Gdx.files.internal("assets/SlingLaunchSFX.mp3"));

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

    public boolean isItLaunched() {
        return islaunched;
    }

    public void setLaunched(){
        islaunched = true;
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
        if(this.Planet.equals("EarthSurface")){
            ShapeRenderer.setColor(Color.BLACK);
        }else{
            ShapeRenderer.setColor(Color.WHITE);
        }
        Vector2[] TrajectoryPoints = this.getTrajectoryPoints();
        for(int i = 0;  i<TrajectoryPoints.length;i++){
            if (TrajectoryPoints[i] != null) {
                ShapeRenderer.circle(TrajectoryPoints[i].x, TrajectoryPoints[i].y, 1f);
            }
        }
        ShapeRenderer.end();
    }
    public void playStretchSound(){
        if(!isStretched) {
            StretchSFX.play();
            isStretched = true;
        }
    }
    Bird setShape(Vector2[] coordinates){
        Array<Fixture> fixtures = getBirdBody().getFixtureList();
        while (fixtures.size > 0) {
            getBirdBody().destroyFixture(fixtures.first());
        }
        fixtureCoordinates = coordinates;
        PolygonShape BirdShape = new PolygonShape();
        BirdShape.set(fixtureCoordinates);
//        BirdBody.setFixedRotation(false);
//        BirdBody.setGravityScale(1);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = BirdShape;
        fixtureDef.density = 2;
        fixtureDef.friction = 0.05f;
        fixtureDef.restitution = 0.05f;
        Fixture fixture = BirdBody.createFixture(fixtureDef);
        BirdBody.createFixture(fixtureDef);
        BirdBody.setUserData(this);
        fixture.setUserData(this);
        BirdShape.dispose();

        return this;
    }

//    public float max(float a, float b){
//        return Math.max(a,b);
//    }
//    public float min(float a, float b){
//        return Math.min(a,b);
//    }

    public void updateSprite(){
        float posX=0,posY=0;
//        && !(posX*posX+posY*posY<=1,681)

        if(!islaunched && !(GlobalY*GlobalY+GlobalX*GlobalX>=1681)){
            if(Gdx.input.isKeyPressed(Input.Keys.W)){
                posY+=0.5f;
                GlobalY+=0.5f;
                playStretchSound();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.S)){
                posY-=0.5f;
                GlobalY-=0.5f;
                playStretchSound();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.D)){
                posX+=0.5f;
                GlobalX+=0.5f;
                playStretchSound();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.A)){
                posX-= 0.5f;
                GlobalX-=0.5f;
                playStretchSound();
            }
        }else if (GlobalY*GlobalY+GlobalX*GlobalX>=1681){
            float angle = MathUtils.atan2(GlobalY,GlobalX);
            float y_s = GlobalY > 0 ? -0.8f*Math.abs(MathUtils.sin(angle)) : 0.8f*Math.abs(MathUtils.sin(angle));
            float x_s = GlobalX > 0 ? -0.8f*Math.abs(MathUtils.cos(angle)) : 0.8f*Math.abs(MathUtils.cos(angle));
            GlobalX += x_s;
            GlobalY += y_s;
            posX += x_s;
            posY += y_s;
        }
        if(islaunched && BirdBody != null) {
            if (BirdBody.getLinearVelocity().len() < 0.1f) {
                Frames++;
            } else {
                Frames = 0;
            }
        }
        if (Frames >= 60) {
            if (!isRemoved) playDeathSound();
            if (BirdAbility instanceof EggAbility){
                EggAbility eggAbility = (EggAbility)BirdAbility;
                if (eggAbility.egg != null) eggAbility.egg.selfdestroy();
            }else if (BirdAbility instanceof SplitAbility){
                SplitAbility splitAbility = (SplitAbility)BirdAbility;
                if (splitAbility.newBlueBird1 != null) splitAbility.newBlueBird1.selfdestroy();
                if (splitAbility.newBlueBird2 != null) splitAbility.newBlueBird2.selfdestroy();
            }
            selfdestroy();
            return;
        }else {
            if (getBirdSprite() == null || isRemoved) return;
            BirdBody.setTransform(BirdBody.getPosition().x + posX, BirdBody.getPosition().y + posY, BirdBody.getAngle());
            getBirdSprite().setPosition(BirdBody.getPosition().x - BirdSprite.getWidth() / 2, BirdBody.getPosition().y - BirdSprite.getHeight() / 2);
            BirdSprite.setOriginCenter();
            BirdSprite.setRotation((float) Math.toDegrees(BirdBody.getAngle()));
//        x = BirdBody.getPosition().x - BirdSprite.getWidth()/2;
//        y = BirdBody.getPosition().y - BirdSprite.getHeight()/2;
            x = BirdBody.getPosition().x;
            y = BirdBody.getPosition().y;
            if (y < -getBirdSprite().getHeight()/2) {
                selfdestroy();
                return;
            }
//        float gravity = -3.73f;
//        if(Planet.equals("Mars")){
//            gravity = -3.73f;
//        }if (Planet.equals("Earth")){
//            gravity = -9.8f;
//        }if (Planet.equals("Jupiter")){
//            gravity = -9.8f*3.0f;
//        }if (Planet.equals("Saturn")){
//            gravity = -9.8f*3.0f;
//        }if (Planet.equals("Uranus")){
//            gravity = -9.8f*0.8f;
//        }if (Planet.equals("Neptune")){
//            gravity = -9.8f*0.8f;
//        }if (Planet.equals("Moon")){
//            gravity = -9.8f*0.5f;
//        }if (Planet.equals("Mercury")){
//            gravity = -9.8f*0.9f;
//        }if (Planet.equals("Venus")){
//            gravity = -9.8f*0.9f;
//        }
        float velocity,angle;
            if(this.Planet.equals("EarthSurface")){
                float changeX = -BirdBody.getPosition().x + 143;
                float changeY = -BirdBody.getPosition().y + 720 - BirdSprite.getHeight() - 494;
                angle = (float) Math.atan2(changeY, changeX);
                float dist = (float) Math.sqrt(changeX * changeX + changeY * changeY);
                velocity = dist * 3f;
                x_velocity = BirdBody.getLinearVelocity().x;
                y_velocity = BirdBody.getLinearVelocity().y;
            }else {
                float changeX = -BirdBody.getPosition().x + 268;
                float changeY = -BirdBody.getPosition().y + 720 - BirdSprite.getHeight() - 320;
                angle = (float) Math.atan2(changeY, changeX);
                float dist = (float) Math.sqrt(changeX * changeX + changeY * changeY);
                velocity = dist * 3f;
                x_velocity = BirdBody.getLinearVelocity().x;
                y_velocity = BirdBody.getLinearVelocity().y;
            }
//        if (!islaunched) {
            CreateTrajectory(new Vector2(BirdBody.getPosition().x, BirdBody.getPosition().y), velocity, angle, worldInstance.getGravity().y, 100);
//        }
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && !islaunched) {
                StretchLaunchSFX.play();
                BirdLaunchSFX.play();
//            worldInstance.setGravity(new Vector2(0, gravity));
//            System.out.println(Math.pow(changeX,2)+"\t"+Math.pow(changeY,2)+"\t"+dist);
                System.out.println("Launching with impulse: X=" + MathUtils.cos(angle) * velocity + ", Y=" + MathUtils.sin(angle) * velocity);
                BirdBody.setLinearVelocity(MathUtils.cos(angle) * velocity, MathUtils.sin(angle) * velocity);
                this.islaunched = true;
                BirdBody.setGravityScale(1f);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && islaunched && !isAbilityTriggered) {
//                System.out.println(BirdBody.getLinearVelocity().x + ", " + BirdBody.getLinearVelocity().y);
                getBirdAbility().triggerAbility(this);
//                System.out.println(BirdBody.getLinearVelocity().x + ", " + BirdBody.getLinearVelocity().y);
                isAbilityTriggered = true;
            }
            if (isAbilityTriggered && !isRemoved){
                if (BirdAbility instanceof SplitAbility){
                    SplitAbility splitAbility = (SplitAbility) BirdAbility;
                    splitAbility.applyUpdate();

//                    splitAbility.newBlueBird1.getBirdSprite().draw();
                }else if (BirdAbility instanceof SpeedAbility){
                    SpeedAbility speedAbility = (SpeedAbility) BirdAbility;
                    if (!isfirstCollided) speedAbility.applyUpdate();
                }else if (BirdAbility instanceof EggAbility){
                    EggAbility eggAbility = (EggAbility) BirdAbility;
                    if (!isfirstCollided) eggAbility.updateEgg();
                }else if (BirdAbility instanceof ExplodeAbility){
                    ExplodeAbility explodeAbility = (ExplodeAbility) BirdAbility;
                    if (!isfirstCollided) explodeAbility.update();
                }
            }
        }
    }
    public void selfdestroy(){
        if(!isRemoved()){
            playDeathSound();
            try {
                BirdBody.setUserData(null);
                worldInstance.destroyBody(BirdBody);
                if (BirdAbility instanceof SplitAbility && isAbilityTriggered){
                    SplitAbility splitAbility = (SplitAbility) BirdAbility;
                    if (splitAbility.newBlueBird1 != null) splitAbility.newBlueBird1.selfdestroy();
                    if (splitAbility.newBlueBird2 != null) splitAbility.newBlueBird2.selfdestroy();
                }
                if (BirdAbility instanceof EggAbility && isAbilityTriggered){
                    EggAbility eggAbility = (EggAbility) BirdAbility;
                    if (eggAbility.egg != null) eggAbility.egg.selfdestroy();
                }
                BirdSprite = null;
//            BirdSprite.setPosition(-1000, -1000);
                isRemoved = true;

            }catch (Exception e){
                System.out.println(e);
            }
        }

//        BirdSprite = null;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public World getWorldInstance() {
        return worldInstance;
    }
    public void setPosition(Vector2 position) {

    }


}
