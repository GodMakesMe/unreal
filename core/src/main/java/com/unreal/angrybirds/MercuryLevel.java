package com.unreal.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public class MercuryLevel  implements Screen, Serializable {
    private Main Game;
    private transient OrthographicCamera camera;
    private transient Stage stage;
    private transient SpriteBatch batch;
    private transient Sprite sprite;
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
    private transient FixtureDef FixtureDef;
    private int initialPiggyCount;

    private ArrayList<Piggy> PigList;
    private ArrayList<Piggy> bodiesToDestroy = new ArrayList<Piggy>();
    private ArrayList<Piggy> deadPiggyList = new ArrayList<Piggy>();
    private ArrayList<Block> blockList;
    transient BitmapFont Scorefont;

    private int birdsAvailable;
    boolean isSerialized;
    private int allPigScore;

    public MercuryLevel(Main game) {
        this.Game = game;
        Scorefont = new BitmapFont(Gdx.files.internal("angrybirds.fnt"));
        Scorefont.setColor(Color.BLACK);
        Screen serializedLevel = null;
//        Scorefont.getData().setScale(1.2f);
        try{
            serializedLevel = Game.loadGameScreen("MercuryLevel");
        }catch(Exception e){
            Gdx.app.log("loadMessage", "Game State Not Found!!");
        }
        if (serializedLevel instanceof MercuryLevel) {
            MercuryLevel level = (MercuryLevel) serializedLevel;
            player = level.player;
//            birdsAvailable = level.birdsAvailable;
            BirdX = level.BirdX;
            BirdY = level.BirdY;
            SpaceBird = level.SpaceBird;
            birdsAvailable = level.birdsAvailable;
            isSerialized = level.isSerialized;
            PigList = level.PigList;
            deadPiggyList = level.deadPiggyList;
            initialPiggyCount = level.initialPiggyCount;
            bodiesToDestroy = level.bodiesToDestroy;
            blockList = level.blockList;
            isSerialized = true;
//            Game.removeFile("MercuryLevel");
//            SpaceBird.processSerialization(null, world);
        }else {
            player = new Player();
            birdsAvailable = 3;
        }
    }

    public MercuryLevel() {
    }
//    public static float (float pixels) {
//        return pixels / 100;
//    }

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
                        // Remove all fixtures first
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


    //    // Flag to indicate loading state
//    public void cleanupDestroyedBodies() {
//        if (!world.isLocked() && !bodiesToDestroy.isEmpty()) {
//            synchronized (bodiesToDestroy) {
//                Iterator<Piggy> iterator = bodiesToDestroy.iterator();
//                while (iterator.hasNext()) {
//                    Piggy pig = iterator.next();
//
//                    // Ensure body is eligible for cleanup
//                    if (pig.getPiggyBody() != null && pig.getPiggyBody().getUserData() != null) {
//                        deadPiggyList.add(pig);
//                        pig.dead = true;
//                        Array<Fixture> fixtures = pig.getPiggyBody().getFixtureList();
//                        while (fixtures.size > 0) {
//                            pig.getPiggyBody().destroyFixture(fixtures.first());
//                        }
//
//                        // Score updates only upon actual destruction
//                        iterator.remove();
//                        world.destroyBody(pig.getPiggyBody());
//                        // If score reaches threshold, trigger level end
//
//                    }
//                }
//            }
//        }
//        bodiesToDestroy.clear();
//    }
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
    }

public void endGame(){
        if (player.getScore() >= allPigScore && deadPiggyList.size() == initialPiggyCount) {
            if ((int) SpaceBird.getBirdBody().getLinearVelocity().x <= 1 && SpaceBird.getBirdBody().getLinearVelocity().y <= 1) {
                Player oldRecord = Game.loadGameScore("MercuryLeveLScore");
                if (oldRecord == null || oldRecord.getScore() < player.getScore()) {
                    Game.saveGameScore(player, "MercuryLevelScore");
                }
                Game.removeFile("MercuryLevel");
                Game.setScreen(new SpaceLevelEnd(Game, player,"Mercury"));
            }
        }
    }



    public static float meterstopixels(float meters) {
        return meters * 100;
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

    @Override
    public void show() {
        if (stage == null) stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        if (camera == null) camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2, 0); // Set camera position to center
        camera.update();
        if (sprite == null) sprite = new Sprite(new Texture("assets/MercuryLevel.png"));
        if (batch == null) batch = new SpriteBatch();

        if (world == null) {
            world = new World(new Vector2(0, -3.73f), false);
            world.setGravity(new Vector2(0, -3.73f));
            world.setContactListener(new CollisionDetector());
            if (PigList == null && !isSerialized) {
                PigList = new ArrayList<Piggy>();
                PigList.add(new Piggy("First Piggy",5, null,"assets/MushPig.png",world,"Mercury", (int) (955+47/2f), (int) (720-617-47/2f),47,47,5000));
                PigList.add(new Piggy("Second Piggy",3,null,"assets/ProfPig.png",world,"Mercury",(int) (1042+47/2f), (int) (720-441-47/2f),47,47,10000));
                PigList.add(new Piggy("Third Piggy",7,null,"assets/KingPig.png",world,"Mercury",(int) (1038+47/2f), (int) (720-537-47/2f),47,57,20000));
//                PigList.add(new Piggy("Fourth Piggy",4,null,"assets/CorpPig.png",world,"Mercury",(int) (939+47/2f), (int) (720-615-47/2f),47,43,9000));
                PigList.add(new Piggy("Fifth Piggy",5,null,"assets/FirstPiggy.png",world,"Mercury",(int) (1129+47/2f), (int) (720-615-47/2f),47,47,20000));
                initialPiggyCount = PigList.size();
                AtomicInteger tempScore =  new AtomicInteger();
                PigList.forEach(pig -> {tempScore.getAndAdd(pig.getScore());});
                allPigScore = tempScore.get();
                player.setWinScore(allPigScore);
            }
            if (blockList == null && !isSerialized) {
                blockList = new ArrayList<Block>();
                blockList.add(new Block("Stone","assets/StoneCube.png", 38, 37, world, 2, 893+38/2f, 720-682-37/2f, 0, 200));
                blockList.add(new Block("Stone","assets/StoneCube.png", 38, 37, world, 2, 1039+38/2f, 720-682-37/2f, 0, 200));
                blockList.add(new Block("Stone","assets/StoneCube.png", 38, 37, world, 2, 1197+38/2f, 720-682-37/2f, 0, 200));
                blockList.add(new Block("Stone","assets/StoneLongVer.png", 194, 22, world, 2, 866+194/2f, 720-662-22/2f, 0, 200));
                blockList.add(new Block("Stone","assets/StoneLongVer.png", 194, 22, world, 2, 1059+194/2f, 720-662-22/2f, 0, 200));
                blockList.add(new Block("Wood","assets/WoodSmallHor.png", 21, 40, world, 0.25f, 927+21/2f, 720-625-40/2f, 0, 150));
                blockList.add(new Block("Wood","assets/WoodSmallHor.png", 21, 40, world, 0.25f, 927+21/2f, 720-586-40/2f, 0, 150));
                blockList.add(new Block("Wood","assets/WoodSmallHor.png", 21, 40, world, 0.25f, 1179+21/2f, 720-625-40/2f, 0, 150));
                blockList.add(new Block("Wood","assets/WoodSmallHor.png", 21, 40, world, 0.25f, 1179+21/2f, 720-586-40/2f, 0, 150));
                blockList.add(new Block("Wood","assets/WoodSmallHor.png", 21, 40, world, 0.25f, 1004+21/2f, 720-625-40/2f, 0, 150));
                blockList.add(new Block("Wood","assets/WoodSmallHor.png", 21, 40, world, 0.25f, 1004+21/2f, 720-586-40/2f, 0, 150));
                blockList.add(new Block("Wood","assets/WoodSmallHor.png", 21, 40, world, 0.25f, 1105+21/2f, 720-625-40/2f, 0, 150));
                blockList.add(new Block("Wood","assets/WoodSmallHor.png", 21, 40, world, 0.25f, 1105+21/2f, 720-586-40/2f, 0, 150));
                blockList.add(new Block("Wood","assets/WoodMediumHor.png", 20, 80, world, 0.25f, 1050+20/2f, 720-586-80/2f, 0, 150));
                blockList.add(new Block("Wood","assets/WoodLeftTriangle.png", 77, 79, world, 0.5f, 938+77/2f, 720-507-79/2f, 0, 150));
                blockList.add(new Block("Wood","assets/WoodRightTriangle.png", 77, 79, world, 0.5f, 1116+77/2f, 720-507-79/2f, 0, 150));
                blockList.add(new Block("Glass","assets/GlassMediumVer.png", 160, 21, world, 0.75f, 986+160/2f, 720-488-21/2f, 0, 75));
                blockList.add(new Block("Wood","assets/WoodSmallHor.png", 21, 40, world, 0.25f, 1017+21/2f, 720-451-40/2f, 0, 100));
                blockList.add(new Block("Wood","assets/WoodSmallHor.png", 21, 40, world, 0.25f, 1017+21/2f, 720-412-40/2f, 0, 150));
                blockList.add(new Block("Wood","assets/WoodSmallHor.png", 21, 40, world, 0.25f, 1093+21/2f, 720-451-40/2f, 0, 150));
                blockList.add(new Block("Wood","assets/WoodSmallHor.png", 21, 40, world, 0.25f, 1093+21/2f, 720-412-40/2f, 0, 150));
                blockList.add(new Block("Wood","assets/WoodHTriangle.png", 80, 81, world, 0.25f, 1026+80/2f, 720-329-81/2f, 0, 150));
//                blockList.add(new Block("assets/MediumGlass.png", 50, 15, world, 1, 1001, 60, 3.14f/2f, 100));
//                blockList.add(new Block("assets/MediumGlass.png", 46, 10, world, 1, 1001, 60, 3.14f/2f, 100));
//                blockList.add(new Block("assets/MediumGlass.png", 47, 10, world, 1, 1001, 60, 3.14f/2f, 100));
//                blockList.add(new Block("assets/MediumGlass.png", 49, 10, world, 1, 1001, 60, 3.14f/2f, 100));
            }
//            world.setGravity(new Vector2(0, 0f));
        }
        world.setGravity(new Vector2(0, -3.73f));
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


        SlingShotFront = new Sprite(new Texture("assets/SlingShotFront.png"));
        SlingShotFront.setPosition(257, 720-333-99);
        SlingShotFront.setSize(32, 99);

        sprite.setPosition(0, 0);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

//        Nextbutton = createButton("assets/Next.png","assets/HoverNext.png",1167, (int) (720 -612-78.3), (int) 78.3, (int) 78.3);
//        nextButtonPixmap = new Pixmap(Gdx.files.internal("assets/Next.png"));
//        stage.addActor(Nextbutton);
//        Game.clickHandling(Nextbutton, nextButtonPixmap, new SpaceLevelEnd(Game));



        PauseButton = createButton("assets/Pause.png","assets/HoverPause.png",47, (int) (720 -39-78.3), (int) 78.3, (int) 78.3);
        pauseButtonPixmap = new Pixmap(Gdx.files.internal("assets/Pause.png"));
        stage.addActor(PauseButton);
        Game.clickHandling(PauseButton, pauseButtonPixmap, new SpacePauseScreen(Game, "assets/MercuryBig.png","Mercury", this));

        RedBirdButton= createButton("assets/RedBird.png","assets/HoverRedBird.png",141, (int) (720 -39-78.3), (int) 78.3, (int) 78.3);
        redBirdButtonPixmap = new Pixmap(Gdx.files.internal("assets/RedBird.png"));
        stage.addActor(RedBirdButton);
        Game.clickHandling(RedBirdButton, redBirdButtonPixmap, null);
        RedBirdButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (SpaceBird != null) {
                    world.destroyBody(SpaceBird.getBirdBody());
                }
                if (birdsAvailable > 1) SpaceBird = new Bird("Red", 20, null, "assets/RedBirdMain.png",world,"Mercury");
                else {flag = false; return true;}
//                birdsAvailable--;
                flag = false;
                BirdX  = SpaceBird.getX();
                BirdY = SpaceBird.getY();
                return true;
            }

        });

        YellowBirdButton= createButton("assets/YellowBird.png","assets/HoverYellowBird.png",220, (int) (720 -39-78.3), (int) 78.3, (int) 78.3);
        yellowBirdButtonPixmap = new Pixmap(Gdx.files.internal("assets/YellowBird.png"));
        stage.addActor(YellowBirdButton);
        Game.clickHandling(YellowBirdButton, yellowBirdButtonPixmap, null);
        YellowBirdButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (SpaceBird != null) {
                    world.destroyBody(SpaceBird.getBirdBody());
                }
                if (birdsAvailable > 1) SpaceBird = new Bird("Chuck", 10, null, "assets/YellowBirdMain.png",world,"Mercury");
                else {flag = false; return true;}
//                birdsAvailable--;
                flag = false;
                BirdX  = SpaceBird.getX();
                BirdY = SpaceBird.getY();
                return true;
            }

        });

        BlueBirdButton= createButton("assets/BlueBird.png","assets/HoverBlueBird.png",299, (int) (720 -39-78.3), (int) 78.3, (int) 78.3);
        blueBirdButtonPixmap = new Pixmap(Gdx.files.internal("assets/BlueBird.png"));
        stage.addActor(BlueBirdButton);
        Game.clickHandling(BlueBirdButton, blueBirdButtonPixmap, null);
        BlueBirdButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (SpaceBird != null) {
                    world.destroyBody(SpaceBird.getBirdBody());
                }
                if (birdsAvailable > 1) SpaceBird = new Bird("Blue", 8, null, "assets/BlueBirdMain.png",world,"Mercury");
                else{ flag = false; return true;}
//                birdsAvailable--;
                flag = false;
                BirdX  = SpaceBird.getX();
                BirdY = SpaceBird.getY();
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
//                if (SpaceBird != null) {
//                    world.destroyBody(SpaceBird.getBirdBody());
//
//                }
//                SpaceBird = new Bird("Bomb", 8, null, "assets/BombBirdMain.png",world,"Mercury");
//                birdsAvailable--;
//                BirdX  = SpaceBird.getX();
//                BirdY = SpaceBird.getY();
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
//                if (SpaceBird != null) {
//                    world.destroyBody(SpaceBird.getBirdBody());
//                }
//                SpaceBird = new Bird("Matilda", 6, null, "assets/WhiteBirdMain.png",world,"Mercury");
//                birdsAvailable--;
//                BirdX  = SpaceBird.getX();
//                BirdY = SpaceBird.getY();
//                return true;
//            }
//
//        });

//        world = SpaceBird.getWorldInstance();

        if (bodyDef == null) bodyDef = new BodyDef();
        if (FixtureDef == null) FixtureDef = new FixtureDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0,0);

        ChainShape GroundShape = new ChainShape();
        GroundShape.createChain(new Vector2[] {new Vector2((835),0),new Vector2((1280),0),new Vector2((1280),(527))});

        FixtureDef.shape = GroundShape;
        FixtureDef.friction = 0.5f;
        FixtureDef.restitution = 0.0f;


        world.createBody(bodyDef).createFixture(FixtureDef);
        GroundShape.dispose();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0,0);

        ChainShape GroundShape1 = new ChainShape();
        GroundShape1.createChain(new Vector2[] {new Vector2((271),(325)),new Vector2((273),(325))});

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
        if(birdsAvailable<=0){
            Game.removeFile("MercuryLevel");
//            Game.removeFile("MercuryLevelScore");
            Game.setScreen(new SpaceLevelEnd(Game,player,"Mercury"));
        }
        if (!isSerialized) cleanupDestroyedBodies();
        updateScore();
        endGame();
        for (Piggy pig : PigList) {
            if (pig != null && pig.isRemoved()) {
                markForRemoval(pig);
            }
        }
        debugRenderer.render(world,camera.combined);
        if (SpaceBird != null && !SpaceBird.isRemoved()) {
            SpaceBird.updateSprite();
            if (SpaceBird.isIslaunched() && !flag) {
                birdsAvailable--;
                flag = true;
            }
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
            if(!SpaceBird.isIslaunched() && SpaceBird.notInOrigin()){
                SpaceBird.DrawTrajectory();
            }
            batch.begin();
            SpaceBird.getBirdSprite().draw(batch);
            batch.end();
        }
        batch.begin();
        SlingShotFront.draw(batch);
        batch.end();
        batch.begin();
        GlyphLayout ScoreLayout = new GlyphLayout(Scorefont,String.format("%08d", player.getScore()));
        Scorefont.draw(batch,ScoreLayout,990,780-62-55);
        batch.end();
        stage.act(delta);
        stage.draw();
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
        SpaceBird.getBirdSprite().getTexture().dispose();
    }

    public ArrayList<Piggy> getDeadPiggyList() {
        return deadPiggyList;
    }

    public void setDeadPiggyList(ArrayList<Piggy> deadPiggyList) {
        this.deadPiggyList = deadPiggyList;
    }
}
