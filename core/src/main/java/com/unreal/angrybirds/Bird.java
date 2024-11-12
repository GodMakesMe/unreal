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

public class Bird {
    private String Name;
    private int Mass;
    private Ability BirdAbility;
    private int Health;
    private Sprite BirdSprite;
    private Texture BirdTexture;
    private float x;
    private float y;
    private float GlobalX;
    private float GlobalY;
    private Body BirdBody;
    private BodyDef BirdBodydef;
    private boolean islaunched;
    private World worldInstance;
    private String Planet;
    private float velocity;
    private float angle;
    private Vector2[] trajectoryPoints;
    public Bird(String Name, int Mass, Ability BirdAbility,String BirdPath,World world,String Planet) {
        this.Name = Name;
        this.Mass = Mass;
        this.BirdAbility = BirdAbility;
        this.Health = 100;
        this.worldInstance = world;
        world.setGravity(new Vector2(0, 0f));
        BirdTexture = new Texture(BirdPath);
        BirdSprite = new Sprite(BirdTexture);
        BirdSprite.setSize(39, 38);
        BirdSprite.setOrigin(0, 0);
        this.x = 268;
        this.y = 720-BirdSprite.getHeight()-320;
        this.BirdBodydef = new BodyDef();
        this.BirdBodydef.type = BodyDef.BodyType.DynamicBody;
        this.BirdBodydef.position.x = x;
        this.BirdBodydef.position.y =y;
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
            float x = StartPos.x + Velocity * t * (float) Math.cos(Angle);
            float y = StartPos.y + Velocity*t*(float)Math.sin(Angle) + 0.5f * Gravity * t * t;
            trajectoryPoints[i] = new Vector2(x, y);
        }
    }

    public Vector2[] getTrajectoryPoints() {
        return trajectoryPoints;
    }
    public boolean notInOrigin(){
        return !(BirdBody.getPosition().x==x && BirdBody.getPosition().y==y);
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
            float y = GlobalY > 0 ? -0.8f*Math.abs(MathUtils.sin(angle)) : 0.8f*Math.abs(MathUtils.sin(angle));
            float x = GlobalX > 0 ? -0.8f*Math.abs(MathUtils.cos(angle)) : 0.8f*Math.abs(MathUtils.cos(angle));
            GlobalX += x;
            GlobalY += y;
            posX += x;
            posY += y;
        }
        BirdBody.setTransform(BirdBody.getPosition().x+posX,BirdBody.getPosition().y+posY,BirdBody.getAngle());
        getBirdSprite().setPosition(BirdBody.getPosition().x-BirdSprite.getWidth()/2, BirdBody.getPosition().y-BirdSprite.getHeight()/2);
        float gravity = 0;
        if(Planet.equals("Mars")){
            gravity = -3.73f;
        }
        float changeX = -BirdBody.getPosition().x+x;
        float changeY = -BirdBody.getPosition().y+y;
        float angle = (float) Math.atan2(changeY, changeX);
        float dist = (float)  Math.sqrt(changeX*changeX+changeY*changeY);
        float velocity = dist*3f;
//        if (!islaunched) {
        CreateTrajectory(new Vector2(BirdBody.getPosition().x, BirdBody.getPosition().y), velocity, angle, gravity, 100);
//        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && !islaunched){
            worldInstance.setGravity(new Vector2(0, gravity));
            System.out.println(Math.pow(changeX,2)+"\t"+Math.pow(changeY,2)+"\t"+dist);
            System.out.println("Launching with impulse: X=" + MathUtils.cos(angle)*velocity + ", Y=" + MathUtils.sin(angle)*velocity);
//            BirdBody.applyLinearImpulse( MathUtils.cos(angle)*velocity, MathUtils.sin(angle)*velocity,BirdBody.getWorldCenter().x,BirdBody.getWorldCenter().y,true);
            BirdBody.setLinearVelocity(MathUtils.cos(angle)*velocity, MathUtils.sin(angle)*velocity);
//            BirdBody.set
            this.islaunched = true;
        }

    }

    public World getWorldInstance() {
        return worldInstance;
    }
}
