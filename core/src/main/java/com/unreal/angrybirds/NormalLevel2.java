package com.unreal.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public class NormalLevel2 implements Screen, Serializable {
    private Main Game;
    private transient OrthographicCamera camera;
    private transient Stage stage;
    private transient SpriteBatch batch;
    private transient Sprite sprite;
    private transient Sprite fastforwardSpriteBatch;
    protected Player player;
    //    private ImageButton Nextbutton;
//    private Pixmap nextButtonPixmap;
    private transient ImageButton PauseButton;
    private transient Pixmap pauseButtonPixmap;
    private boolean flag = false;
    private transient ImageButton RedBirdButton;
    private transient Pixmap redBirdButtonPixmap;
    private transient ImageButton YellowBirdButton;
    private transient Pixmap yellowBirdButtonPixmap;
    private transient ImageButton BlueBirdButton;
    private transient Pixmap blueBirdButtonPixmap;
    private transient ImageButton BombBirdButton;
    private transient Pixmap bombBirdButtonPixmap;
    private transient ImageButton WhiteBirdButton;
    private transient Pixmap whiteBirdButtonPixmap;

    private transient Sprite SlingShotFront;
    protected boolean isFirstLaunched;
    private Bird SpaceBird;
    private float BirdX;
    private float BirdY;

    private transient World world;
    private transient Box2DDebugRenderer debugRenderer;

    private transient BodyDef bodyDef;
    private transient com.badlogic.gdx.physics.box2d.FixtureDef FixtureDef;
    private int initialPiggyCount;

    private ArrayList<Piggy> PigList;
    private ArrayList<Piggy> bodiesToDestroy = new ArrayList<Piggy>();
    private ArrayList<Piggy> deadPiggyList = new ArrayList<Piggy>();
    private ArrayList<Block> blockList;
    transient BitmapFont Scorefont;

    private int birdsAvailable;
    boolean isSerialized;
    private int allPigScore;

    public NormalLevel2(Main game) {
        this.Game = game;
        Scorefont = new BitmapFont(Gdx.files.internal("angrybirds.fnt"));
        Scorefont.setColor(Color.WHITE);
        fastforwardSpriteBatch = new Sprite(new Texture("FastForward1.png"));
        Screen serializedLevel = null;
//        Scorefont.getData().setScale(1.2f);
        try{
            serializedLevel = Game.loadGameScreen("NormalLevel2");
        }catch(Exception e){
            Gdx.app.log("loadMessage", "Game State Not Found!!");
        }
        if (serializedLevel instanceof NormalLevel2) {
            NormalLevel2 level = (NormalLevel2) serializedLevel;
            player = level.player;
//            birdsAvailable = level.birdsAvailable;
            BirdX = level.BirdX;
            BirdY = level.BirdY;
            SpaceBird = level.SpaceBird;
            birdsAvailable = level.birdsAvailable;
            PigList = level.PigList;
            deadPiggyList = level.deadPiggyList;
            initialPiggyCount = level.initialPiggyCount;
            bodiesToDestroy = level.bodiesToDestroy;
            blockList = level.blockList;
            isSerialized = true;
            flag = level.flag;

//            Game.removeFile("NormalLevel2");
//            SpaceBird.processSerialization(null, world);
        }else {
            player = new Player();
            birdsAvailable = 2;
            player.totalBirds = birdsAvailable;
        }
    }
    public NormalLevel2(){
    }

    public ImageButton createButton(String Path,String HoverPath,int X,int Y,int W, int H){
        Texture ButtonTexture = new Texture(Path);
        Texture HoverButtonTexture = new Texture(HoverPath);
        ImageButton.ImageButtonStyle ButtonTextureStyle = new ImageButton.ImageButtonStyle();
        ButtonTextureStyle.up = new TextureRegionDrawable(new TextureRegion(ButtonTexture));
        ButtonTextureStyle.over = new TextureRegionDrawable(new TextureRegion(HoverButtonTexture));
        ImageButton button = new ImageButton(ButtonTextureStyle);
        button.setPosition(X,Y);
        button.setSize(W,H);
        return button;
    }

    public void markForRemoval(Piggy pig) {
        if (pig != null && !bodiesToDestroy.contains(pig) && !pig.dead) {
            bodiesToDestroy.add(pig);
        }
    }

    public void cleanupDestroyedBodies() {
        if (!world.isLocked() && !bodiesToDestroy.isEmpty()) {
            synchronized (bodiesToDestroy) {
                Iterator<Piggy> iterator = bodiesToDestroy.iterator();
                while (iterator.hasNext()) {
                    Piggy pig = iterator.next();
                    //pig.getPiggySprite().getTexture().dispose();
                    if (pig.getPiggyBody() != null && pig.getPiggyBody().getUserData() != null) {
                        pig.dead = true;
                        deadPiggyList.add(pig);
                        Array<Fixture> fixtures = pig.getPiggyBody().getFixtureList();
                        while (fixtures.size > 0) {
                            pig.getPiggyBody().destroyFixture(fixtures.first());

                        }
                        world.destroyBody(pig.getPiggyBody());
                        iterator.remove();
                    }
                }
            }
        }
        bodiesToDestroy.clear();
    }
    int getAllBlockScore(){
        int score = 0;
        if (blockList == null) return score;
        for (Block i : blockList){
            score += (i.mass*i.height*i.width);
        }
        return score;
    }
    public void updateScore(){
        int scoreToAdd = 0;
        for (Piggy i : deadPiggyList){
            scoreToAdd += i.getScore();
        }
        for (Block i : blockList){
            if (i.isRemoved){
                scoreToAdd += (int) (i.mass*i.height*i.width);
            }
        }
        player.setScore(scoreToAdd);
        player.birdsLeft = birdsAvailable;
    }
    boolean allBlockRested(){
        for (Block i : blockList){
            if ( i != null && !i.isRemoved && !i.isRested()){
                return false;
            }
        }
        return true;
    }
    public boolean allPigDead(){
    for (Piggy i : PigList) if (!i.dead) return false;
    player.pigsDead = initialPiggyCount;
    return true;
    }
    public void endGame(){
        if (player.getScore() >= allPigScore && allPigDead()) {
            if ((SpaceBird == null || !SpaceBird.isItLaunched()) && allBlockRested()) {
                Player oldRecord = Game.loadGameScore("NormalLevel2Score");
                if (oldRecord == null || oldRecord.getScore() < player.getScore()) {
                    Game.saveGameScore(player, "NormalLevel2Score");
                }
//                System.out.println("HElloooooo 1");
                Game.removeFile("NormalLevel2");
                Game.setScreen(new NormalLevelEnd(Game, player, "level2"));
                return;
            }
        }
        if (SpaceBird == null && birdsAvailable <= 0 && allBlockRested()) { player.pigsDead = (allPigDead()) ? initialPiggyCount : deadPiggyList.size();
            Game.removeFile("NormalLevel2");
            Game.setScreen(new NormalLevelEnd(Game,player,"level2"));
        }
    }

    void fastForward(){
        float timeStep = 1 / 60f;
        int velocityIterations = 6;
        int positionIterations = 2;
        float fastForwardFactor = 5f;
        timeStep = timeStep * (float)fastForwardFactor/2f;
        world.step(timeStep, velocityIterations, positionIterations);
        float deltaTime = Gdx.graphics.getDeltaTime();
        fastForwardFactor = 2f;
        deltaTime *= fastForwardFactor;
        world.step(deltaTime, velocityIterations, positionIterations);
    }

    public static float meterstopixels(float meters) {
        return meters * 100;
    }



    @Override
    public void show() {
        if (stage == null) stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        if (camera == null) camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2, 0);
        camera.update();
        if (sprite == null) sprite = new Sprite(new Texture("assets/NormalLevel.png"));
        if (batch == null) batch = new SpriteBatch();

        if (world == null) {
            world = new World(new Vector2(0, -9.81f*2f), false);
            world.setGravity(new Vector2(0, -9.81f*2f));
            world.setContactListener(new CollisionDetector());
            if (PigList == null && !isSerialized) {
                PigList = new ArrayList<Piggy>();
                PigList.add(new Piggy("First Piggy",5, null,"assets/MushPig.png",world,"Earth", (int) (864+47/2f), (int) (720-635-47/2f+100),47,47,5000));
                PigList.add(new Piggy("First Piggy",5, null,"assets/MushPig.png",world,"Earth", (int) (963+47/2f), (int) (720-635-47/2f+100),47,47,5000));
                PigList.add(new Piggy("Fifth Piggy",5,null,"assets/FirstPiggy.png",world,"Earth",(int) (780+47/2f), (int) (720-635-47/2f+100),47,47,20000));
                PigList.add(new Piggy("Fifth Piggy",5,null,"assets/FirstPiggy.png",world,"Earth",(int) (1051+47/2f), (int) (720-635-47/2f+100),47,47,20000));
                PigList.add(new Piggy("Second Piggy",3,null,"assets/ProfPig.png",world,"Earth",(int) (818+47/2f), (int) (720-423-47/2f+100),47,47,10000));
                PigList.add(new Piggy("Second Piggy",3,null,"assets/ProfPig.png",world,"Earth",(int) (1011+47/2f), (int) (720-423-47/2f+100),47,47,10000));
                PigList.add(new Piggy("Fourth Piggy",4,null,"assets/CorpPig.png",world,"Earth",(int) (915+47/2f), (int) (720-464-43/2f+100),47,43,9000));
                PigList.add(new Piggy("Third Piggy",7,null,"assets/KingPig.png",world,"Earth",(int) (915+47/2f), (int) (720-317-47/2f+100),47,57,20000));
//                PigList.add(new Piggy("Fifth Piggy",5,null,"assets/FirstPiggy.png",world,"Earth",(int) (1+47/2f), (int) (720-635-47/2f+100),47,47,20000));
                initialPiggyCount = PigList.size(); player.initialPigs = PigList.size();
                AtomicInteger tempScore =  new AtomicInteger();
                PigList.forEach(pig -> {tempScore.getAndAdd(pig.getScore());});
                allPigScore = tempScore.get();
                player.setWinScore(allPigScore);
            }
            if (blockList == null && !isSerialized) {
                blockList = new ArrayList<Block>();
                blockList.add(new Block("Stone","assets/StoneSmallCube.png", 21, 19, world, 2, 758+21/2f, 720-701-19/2f+100, 0, 200*5));
                blockList.add(new Block("Stone","assets/StoneSmallVer.png", 40, 19, world, 2, 915+40/2f, 720-699-21/2f+100, 0, 200*5));
                blockList.add(new Block("Stone","assets/StoneSmallCube.png", 21, 19, world, 2, 1090+21/2f, 720-701-19/2f+100, 0, 200*5));
                blockList.add(new Block("Stone","assets/StoneLongVer.png", 194, 22, world, 10f, 741+194/2f, 720-680-22/2f+100, 0, 200*5));
                blockList.add(new Block("Stone","assets/StoneLongVer.png", 194, 22, world, 10f, 935+194/2f, 720-680-22/2f+100, 0, 200*5));

                blockList.add(new Block("Glass","assets/GlassSmallHor.png", 21, 40, world, 4f, 752+21/2f, 720-644-40/2f+100, 0, 150));
                blockList.add(new Block("Glass","assets/GlassSmallHor.png", 21, 40, world, 4f, 1107+21/2f, 720-644-40/2f+100, 0, 150));
                blockList.add(new Block("Wood","assets/WoodSmallHor.png", 21, 40, world, 4f, 834+21/2f, 720-644-40/2f+100, 0, 150));
                blockList.add(new Block("Wood","assets/WoodSmallHor.png", 21, 40, world, 4f, 1022+21/2f, 720-644-40/2f+100, 0, 150));
                blockList.add(new Block("Stone","assets/StoneSmallHor.png", 21, 40, world, 4f, 924+21/2f, 720-644-40/2f+100, 0, 150));

                blockList.add(new Block("Glass","assets/GlassSmallHor.png", 21, 40, world, 4f, 752+21/2f, 720-605-40/2f+100, 0, 150));
                blockList.add(new Block("Glass","assets/GlassSmallHor.png", 21, 40, world, 4f, 1107+21/2f, 720-605-40/2f+100, 0, 150));
                blockList.add(new Block("Wood","assets/WoodSmallHor.png", 21, 40, world, 4f, 834+21/2f, 720-605-40/2f+100, 0, 150));
                blockList.add(new Block("Wood","assets/WoodSmallHor.png", 21, 40, world, 4f, 1022+21/2f, 720-605-40/2f+100, 0, 150));
                blockList.add(new Block("Stone","assets/StoneSmallHor.png", 21, 40, world, 4f, 924+21/2f, 720-605-40/2f+100, 0, 150));

                blockList.add(new Block("Glass","assets/GlassMediumVer.png", 80, 20, world, 7f, 763+80/2f, 720-586-20/2f+100, 0, 150));
                blockList.add(new Block("Glass","assets/GlassMediumVer.png", 80, 20, world, 7f, 1036+80/2f, 720-586-20/2f+100, 0, 150));
                blockList.add(new Block("Wood","assets/WoodXLongVer.png", 193, 19, world, 2, 842+193/2f, 720-586-19/2f+100, 0, 150));

                blockList.add(new Block("Stone","assets/StoneSmallHor.png", 21, 40, world, 4f, 802+21/2f, 720-548-40/2f+100, 0, 150));
                blockList.add(new Block("Stone","assets/StoneSmallHor.png", 21, 40, world, 4f, 860+21/2f, 720-548-40/2f+100, 0, 150));
                blockList.add(new Block("Stone","assets/StoneSmallHor.png", 21, 40, world, 4f, 996+21/2f, 720-548-40/2f+100, 0, 150));
                blockList.add(new Block("Stone","assets/StoneSmallHor.png", 21, 40, world, 4f, 1054+21/2f, 720-548-40/2f+100, 0, 150));

                blockList.add(new Block("Stone","assets/StoneMediumHor.png", 20, 80, world, 7f, 927+20/2f, 720-508-80/2f+100, 0, 150));

                blockList.add(new Block("Glass","assets/GlassXLongHor.png", 19,193, world, 2, 888+19/2f, 720-394-193/2f+100, 0, 150));
                blockList.add(new Block("Glass","assets/GlassXLongHor.png", 19,193, world, 2, 968+19/2f, 720-394-193/2f+100, 0, 150));

                blockList.add(new Block("Wood","assets/WoodLongVer.png", 163, 19, world, 2, 860+163/2f, 720-375-19/2f+100, 0, 150));

                blockList.add(new Block("Wood","assets/WoodSmallHor.png", 21, 40, world, 4f, 888+21/2f, 720-336-40/2f+100, 0, 150));
                blockList.add(new Block("Wood","assets/WoodSmallHor.png", 21, 40, world, 4f, 888+21/2f, 720-298-40/2f+100, 0, 150));

                blockList.add(new Block("Wood","assets/WoodSmallHor.png", 21, 40, world, 4f, 968+21/2f, 720-336-40/2f+100, 0, 150));
                blockList.add(new Block("Wood","assets/WoodSmallHor.png", 21, 40, world, 4f, 968+21/2f, 720-298-40/2f+100, 0, 150));

                blockList.add(new Block("Glass","assets/GlassWindow.png", 79,79, world, 4f, 802+79/2f, 720-471-79/2f+100, 0, 150));
                blockList.add(new Block("Glass","assets/GlassWindow.png", 79,79, world, 4f, 995+79/2f, 720-471-79/2f+100, 0, 150));

                blockList.add(new Block("Glass","assets/GlassHTriangle.png", 80, 81, world, 8f, 898+80/2f, 720-219-81/2f+100, 0, 150).setShape(new Vector2[]{new Vector2(0, 81/2f),new Vector2(40, -81/2f),new Vector2(-40, -81/2f)}));
                player.setMaxScore(getAllBlockScore() + allPigScore);

//                blockList.add(new Block("Wood","assets/WoodLeftTriangle.png", 77, 79, world, 8f, 938+77/2f+1, 720-507-79/2f+100, 0, 150).setShape(new Vector2[]{new Vector2(-77f/2f,-79f/2f), new Vector2(77f/2f,79f/2f), new Vector2(77f/2f,-79f/2f)}));
//                blockList.add(new Block("Wood","assets/WoodRightTriangle.png", 77, 79, world, 8f, 1116+77/2f-1.5f, 720-507-79/2f+100, 0, 150).setShape(new Vector2[]{new Vector2(-77f/2f,-79f/2f), new Vector2(-77f/2f,79f/2f), new Vector2(77f/2f,-79f/2f)}));
//                blockList.add(new Block("Glass","assets/GlassMediumVer.png", 160, 21, world, 2f, 986+160/2f, 720-488-21/2f+100, 0, 75));
//                blockList.add(new Block("Wood","assets/WoodSmallHor.png", 21, 40, world, 4f, 1017+21/2f, 720-451-40/2f+100, 0, 100));
//                blockList.add(new Block("Wood","assets/WoodSmallHor.png", 21, 40, world, 4f, 1017+21/2f, 720-412-40/2f+100, 0, 150));
//                blockList.add(new Block("Wood","assets/WoodSmallHor.png", 21, 40, world, 4f, 1093+21/2f, 720-451-40/2f+100, 0, 150));
//                blockList.add(new Block("Wood","assets/WoodSmallHor.png", 21, 40, world, 4f, 1093+21/2f, 720-412-40/2f+100, 0, 150));
            }
//            world.setGravity(new Vector2(0, 0f));
        }
        world.setGravity(new Vector2(0, -9.81f));
        if (isSerialized) {
            if (SpaceBird != null) SpaceBird.processSerialization(null, world);
            assert PigList != null;
            for (Piggy pig : PigList) {
                if (pig != null && !pig.dead) pig.processSerialization(null, world);
            }
            assert blockList != null;
            for (Block block : blockList) {
                if (block != null) block.processSerialization(world);
            }
            birdsAvailable++;
            isSerialized = false;
        }


        debugRenderer = new Box2DDebugRenderer();


        SlingShotFront = new Sprite(new Texture("assets/SlingShotFrontEarth.png"));
        SlingShotFront.setPosition(136, 720-488-126);
        SlingShotFront.setSize(53, 126);

        sprite.setPosition(0, 0);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        PauseButton = createButton("assets/Pause.png","assets/HoverPause.png",47, (int) (720 -39-78.3), (int) 78.4, (int) 78.4);
        pauseButtonPixmap = new Pixmap(Gdx.files.internal("assets/Pause.png"));
        stage.addActor(PauseButton);
        Game.clickHandling(PauseButton, pauseButtonPixmap, new NormalPauseScreen(Game, this, "Level2"));

        RedBirdButton= createButton("assets/RedBird.png","assets/HoverRedBird.png",141, (int) (720 -39-78.3), (int) 78.3, (int) 78.3);
        redBirdButtonPixmap = new Pixmap(Gdx.files.internal("assets/RedBird.png"));
        stage.addActor(RedBirdButton);
        Game.clickHandling(RedBirdButton, redBirdButtonPixmap, null);

        RedBirdButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (SpaceBird != null && SpaceBird.isItLaunched()) {
//                    world.destroyBody(SpaceBird.getBirdBody());
                    SpaceBird.selfdestroy();
                    if (birdsAvailable <= 0) {
                        flag = true;
                    }
                    else {
                        SpaceBird = new Bird("Red", 20, new WarCryAbility(), "assets/RedBirdMain.png",world);
                        birdsAvailable--;
                        BirdX  = SpaceBird.getX();
                        BirdY = SpaceBird.getY();

                    }
                }
                else if (SpaceBird != null && !SpaceBird.isItLaunched()) {
                    SpaceBird.selfdestroy();
                    SpaceBird = new Bird("Red", 20, new WarCryAbility(), "assets/RedBirdMain.png",world);
                    BirdX  = SpaceBird.getX();
                    BirdY = SpaceBird.getY();
                }
                else if (SpaceBird == null && birdsAvailable <= 0) {
                    flag = true;
                }else {
                    SpaceBird = new Bird("Red", 20, new WarCryAbility(), "assets/RedBirdMain.png",world);
//                else {flag = false; return true;}
//                    flag = false;
                    birdsAvailable--;
                    BirdX  = SpaceBird.getX();
                    BirdY = SpaceBird.getY();
                }

                return true;
            }

        });

        YellowBirdButton= createButton("assets/YellowBird.png","assets/HoverYellowBird.png",220, (int) (720 -39-78.3), (int) 78.3, (int) 78.3);
        yellowBirdButtonPixmap = new Pixmap(Gdx.files.internal("assets/YellowBird.png"));
        stage.addActor(YellowBirdButton);
        Game.clickHandling(YellowBirdButton, yellowBirdButtonPixmap, null);
        YellowBirdButton= createButton("assets/YellowBird.png","assets/HoverYellowBird.png",220, (int) (720 -39-78.3), (int) 78.3, (int) 78.3);
        yellowBirdButtonPixmap = new Pixmap(Gdx.files.internal("assets/YellowBird.png"));
        stage.addActor(YellowBirdButton);
        Game.clickHandling(YellowBirdButton, yellowBirdButtonPixmap, null);
        YellowBirdButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (SpaceBird != null && SpaceBird.isItLaunched()) {
//                    world.destroyBody(SpaceBird.getBirdBody());
                    SpaceBird.selfdestroy();
                    if (birdsAvailable <= 0) {
                        flag = true;
                    }
                    else {
                        SpaceBird = new Bird("Chuck", 10, new SpeedAbility(), "assets/YellowBirdMain.png",world);
                        birdsAvailable--;
                        BirdX  = SpaceBird.getX();
                        BirdY = SpaceBird.getY();

                    }
                }
                else if (SpaceBird != null && !SpaceBird.isItLaunched()) {
                    SpaceBird.selfdestroy();
                    SpaceBird = new Bird("Chuck", 10, new SpeedAbility(), "assets/YellowBirdMain.png",world);
                    BirdX  = SpaceBird.getX();
                    BirdY = SpaceBird.getY();
                }
                else if (SpaceBird == null && birdsAvailable <= 0) {
                    flag = true;
                }else {
                    SpaceBird = new Bird("Chuck", 10, new SpeedAbility(), "assets/YellowBirdMain.png",world);
//                    else {flag = false; return true;}
//                    flag = false;
                    birdsAvailable--;
                    BirdX  = SpaceBird.getX();
                    BirdY = SpaceBird.getY();
                }
                return true;
            }
        });

        BlueBirdButton= createButton("assets/BlueBird.png","assets/HoverBlueBird.png",299, (int) (720 -39-78.3), (int) 78.3, (int) 78.3);
        blueBirdButtonPixmap = new Pixmap(Gdx.files.internal("assets/BlueBird.png"));
        stage.addActor(BlueBirdButton);
        Game.clickHandling(BlueBirdButton, blueBirdButtonPixmap, null);
        BlueBirdButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (SpaceBird != null && SpaceBird.isItLaunched()) {
//                    world.destroyBody(SpaceBird.getBirdBody());
                    SpaceBird.selfdestroy();
                    if (birdsAvailable <= 0) {
                        flag = true;
                    }
                    else {
                        SpaceBird = new Bird("Blue", 8, new SplitAbility(), "assets/BlueBirdMain.png",world);
                        birdsAvailable--;
                        BirdX  = SpaceBird.getX();
                        BirdY = SpaceBird.getY();

                    }
                }
                else if (SpaceBird != null && !SpaceBird.isItLaunched()) {
                    SpaceBird.selfdestroy();
                    SpaceBird = new Bird("Blue", 8, new SplitAbility(), "assets/BlueBirdMain.png",world);
                    BirdX  = SpaceBird.getX();
                    BirdY = SpaceBird.getY();
                }
                else if (SpaceBird == null && birdsAvailable <= 0) {
                    flag = true;
                }else {
                    SpaceBird = new Bird("Blue", 8, new SplitAbility(), "assets/BlueBirdMain.png",world);
                    birdsAvailable--;
                    BirdX  = SpaceBird.getX();
                    BirdY = SpaceBird.getY();
                }
                return true;
            }

        });

        BombBirdButton= createButton("assets/BombBirdNotAvailable.png","assets/BombBirdNotAvailable.png",377, (int) (720 -39-78.3), (int) 78.3, (int) 78.3);
        stage.addActor(BombBirdButton);
        WhiteBirdButton= createButton("assets/WhiteBirdNotAvailable.png","assets/WhiteBirdNotAvailable.png",454, (int) (720 -39-78.3), (int) 78.3, (int) 78.3);
        stage.addActor(WhiteBirdButton);
//        BombBirdButton= createButton("assets/BombBird.png","assets/HoverBombBird.png",377, (int) (720 -39-78.3), (int) 78.3, (int) 78.3);
//        bombBirdButtonPixmap = new Pixmap(Gdx.files.internal("assets/BombBird.png"));
//        stage.addActor(BombBirdButton);
//        Game.clickHandling(BombBirdButton, bombBirdButtonPixmap, null);
//        BombBirdButton.addListener(new InputListener() {
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                if (SpaceBird != null && SpaceBird.isItLaunched()) {
//                    SpaceBird.selfdestroy();
//                    if (birdsAvailable <= 0) {
//                        flag = true;
//                    }
//                    else {
//                        SpaceBird = new Bird("Bomb", 8, new ExplodeAbility(), "assets/BombBirdMain.png",world);
//                        birdsAvailable--;
//                        BirdX  = SpaceBird.getX();
//                        BirdY = SpaceBird.getY();
//
//                    }
//                }
//                else if (SpaceBird != null && !SpaceBird.isItLaunched()) {
//                    SpaceBird.selfdestroy();
//                    SpaceBird = new Bird("Bomb", 8, new ExplodeAbility(), "assets/BombBirdMain.png",world);
//                    BirdX  = SpaceBird.getX();
//                    BirdY = SpaceBird.getY();
//                }
//                else if (SpaceBird == null && birdsAvailable <= 0) {
//                    flag = true;
//                }else {
//                    SpaceBird = new Bird("Bomb", 8, new ExplodeAbility(), "assets/BombBirdMain.png",world);
//                    birdsAvailable--;
//                    BirdX  = SpaceBird.getX();
//                    BirdY = SpaceBird.getY();
//                }
//                return true;
//            }
//
//        });
//
//        WhiteBirdButton= createButton("assets/WhiteBird.png","assets/HoverWhiteBird.png",454, (int) (720 -39-78.3), (int) 78.3, (int) 78.3);
//        whiteBirdButtonPixmap = new Pixmap(Gdx.files.internal("assets/WhiteBird.png"));
//        stage.addActor(WhiteBirdButton);
//        Game.clickHandling(WhiteBirdButton, whiteBirdButtonPixmap, null);
//        WhiteBirdButton.addListener(new InputListener() {
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                if (SpaceBird != null && SpaceBird.isItLaunched()) {
//                    SpaceBird.selfdestroy();
//                    if (birdsAvailable <= 0) {
//                        flag = true;
//                    }
//                    else {
//                        SpaceBird = new Bird("Matilda", 6, new EggAbility(), "assets/WhiteBirdMain.png",world);
//                        birdsAvailable--;
//                        BirdX  = SpaceBird.getX();
//                        BirdY = SpaceBird.getY();
//
//                    }
//                }
//                else if (SpaceBird != null && !SpaceBird.isItLaunched()) {
//                    SpaceBird.selfdestroy();
//                    SpaceBird = new Bird("Matilda", 6, new EggAbility(), "assets/WhiteBirdMain.png",world);
//                    BirdX  = SpaceBird.getX();
//                    BirdY = SpaceBird.getY();
//                }
//                else if (SpaceBird == null && birdsAvailable <= 0 && allBlockRested()) {
//                    flag = true;
//                }else {
//                    SpaceBird = new Bird("Matilda", 6, new EggAbility(), "assets/WhiteBirdMain.png",world);
//                    birdsAvailable--;
//                    BirdX  = SpaceBird.getX();
//                    BirdY = SpaceBird.getY();
//                }
//                return true;
//            }
//
//        });
        if (bodyDef == null) bodyDef = new BodyDef();
        if (FixtureDef == null) FixtureDef = new FixtureDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0,0);

        ChainShape GroundShape = new ChainShape();
        GroundShape.createChain(new Vector2[] {new Vector2((0),100),new Vector2((1280),100),new Vector2((1280),(720))});

        FixtureDef.shape = GroundShape;
        FixtureDef.friction = 0.5f;
        FixtureDef.restitution = 0.0f;


        world.createBody(bodyDef).createFixture(FixtureDef);
        GroundShape.dispose();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0,0);

        ChainShape GroundShape1 = new ChainShape();
        GroundShape1.createChain(new Vector2[] {new Vector2((175),(720-547-10)),new Vector2((177),(720-547-10))});

        FixtureDef.shape = GroundShape1;
        FixtureDef.friction = 0.5f;
        FixtureDef.restitution = 0.0f;

        world.createBody(bodyDef).createFixture(FixtureDef);
        GroundShape1.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(1, 1, 1, 1);
        batch.setProjectionMatrix(camera.combined);
        world.step(1 / 60f, 6, 2);
        for (Piggy pig : bodiesToDestroy) {
            pig.getPiggyBody().setActive(false);
        }
        if(SpaceBird != null && SpaceBird.isRemoved()){
            SpaceBird = null;
        }
//        if(birdsAvailable<=0){
//            Game.removeFile("NormalLevel2");
////            Game.removeFile("NormalLevel2Score");
//            Game.setScreen(new NormalLevelEnd(Game,player,"level2"));
//        }
        if (!isSerialized) cleanupDestroyedBodies();
        updateScore();
        endGame();
        for (Piggy pig : PigList) {
            if (pig != null && pig.isRemoved()) {
                markForRemoval(pig);
            }
        }
                //  debugRenderer.render(world,camera.combined);
        if (SpaceBird != null && !SpaceBird.isRemoved()) {
            SpaceBird.updateSprite();
//            if (SpaceBird.isItLaunched() && !flag) {
//                birdsAvailable--;
//                flag = true;
//            }
        }

        for(Piggy pig: PigList){
            if(pig != null  && !pig.isRemoved()) {
                pig.updateSprite();
            }
        }
        for (Block block : blockList){
            if (block != null && !block.isRemoved) block.updateSprite();
        }


        batch.begin();
        sprite.draw(batch);
        batch.end();

        batch.begin();

        if (SpaceBird == null && flag){
            player.pigsDead= deadPiggyList.size();
            Game.removeFile("NormalLevel2");
//            Game.removeFile("NeptuneLevelScore");
            Game.setScreen(new NormalLevelEnd(Game,player,"level2"));
//            dispose();
        }

        for(Piggy pig: PigList){
            if(pig != null && !pig.isRemoved()) {
                pig.getPiggySprite().draw(batch);
            }
        }
        for (Block block: blockList){
            if (block != null && !block.isRemoved) block.getblockSprite().draw(batch);
        }
        batch.end();
        if (SpaceBird != null && !SpaceBird.isRemoved()) {
            if(!SpaceBird.isItLaunched() && SpaceBird.notInOrigin()){
                SpaceBird.DrawTrajectory();
            }
            batch.begin();
            if (SpaceBird.getBirdSprite() != null) SpaceBird.getBirdSprite().draw(batch);
            if (SpaceBird.getBirdAbility() instanceof SplitAbility && SpaceBird.isAbilityTriggered){
                SplitAbility birdAbility = (SplitAbility)SpaceBird.getBirdAbility();
                if (birdAbility.newBlueBird1 != null && !birdAbility.newBlueBird1.isRemoved() && birdAbility.newBlueBird1.getBirdSprite() != null) birdAbility.newBlueBird1.getBirdSprite().draw(batch);
                if (birdAbility.newBlueBird1 != null && !birdAbility.newBlueBird1.isRemoved() && birdAbility.newBlueBird2.getBirdSprite() != null) birdAbility.newBlueBird2.getBirdSprite().draw(batch);
            }else if (SpaceBird.getBirdAbility() instanceof EggAbility && SpaceBird.isAbilityTriggered){
                EggAbility eggAbility = (EggAbility)SpaceBird.getBirdAbility();
                if (eggAbility.egg != null && !eggAbility.egg.isRemoved() && eggAbility.egg.getBirdSprite() != null) eggAbility.egg.getBirdSprite().draw(batch);
            }
            batch.end();
        }
        boolean draw = false;
        if (deadPiggyList.size() == initialPiggyCount){
            draw = true;

            fastForward();

        }

//            fastforwardSpriteBatch.setSize(1014, 720);
        batch.begin();
        if (draw) batch.draw(fastforwardSpriteBatch, 0, 0, 1280, 720);
        batch.end();
        batch.begin();
        SlingShotFront.draw(batch);
        batch.end();
        batch.begin();
        GlyphLayout ScoreLayout = new GlyphLayout(Scorefont,String.format("%08d", player.getScore()));
        Scorefont.draw(batch,ScoreLayout,990,780-62-55);
        batch.end();
        stage.act(delta);
        stage.draw();
                //  debugRenderer.render(world,camera.combined);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        sprite.getTexture().dispose();
    }
    public ArrayList<Piggy> getDeadPiggyList() {
        return deadPiggyList;
    }

    public void setDeadPiggyList(ArrayList<Piggy> deadPiggyList) {
        this.deadPiggyList = deadPiggyList;
    }
}
