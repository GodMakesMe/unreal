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

import java.util.ArrayList;
import java.util.Iterator;

public class MarsLevel  implements Screen {
    private Main Game;
    private OrthographicCamera camera;
    private Stage stage;
    private SpriteBatch batch;
    private Sprite sprite;
    private Player player;
//    private ImageButton Nextbutton;
//    private Pixmap nextButtonPixmap;
    private ImageButton PauseButton;
    private Pixmap pauseButtonPixmap;

    private ImageButton RedBirdButton;
    private Pixmap redBirdButtonPixmap;
    private ImageButton YellowBirdButton;
    private Pixmap yellowBirdButtonPixmap;
    private ImageButton BlueBirdButton;
    private Pixmap blueBirdButtonPixmap;
    private ImageButton BombBirdButton;
    private Pixmap bombBirdButtonPixmap;
    private ImageButton WhiteBirdButton;
    private Pixmap whiteBirdButtonPixmap;

    private Sprite SlingShotFront;

    private Bird SpaceBird;
    private float BirdX;
    private float BirdY;

    private World world;
    private Box2DDebugRenderer debugRenderer;

    private BodyDef bodyDef;
    private FixtureDef FixtureDef;

    private ArrayList<Piggy> PigList;
    private ArrayList<Piggy> bodiesToDestroy = new ArrayList<>();

    BitmapFont Scorefont;

    private int birdsAvailable;


    public MarsLevel(Main game) {
        this.Game = game;
        Scorefont = new BitmapFont(Gdx.files.internal("angrybirds.fnt"));
        Scorefont.setColor(Color.BLACK);
//        Scorefont.getData().setScale(1.2f);
        player = new Player();
        birdsAvailable = 3;
    }
//    public static float (float pixels) {
//        return pixels / 100;
//    }

    public void markForRemoval(Piggy pig) {
        if (pig != null && !bodiesToDestroy.contains(pig)) {
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
                        Array<Fixture> fixtures = pig.getPiggyBody().getFixtureList();
                        while (fixtures.size > 0) {
                            pig.getPiggyBody().destroyFixture(fixtures.first());
                            player.setScore(player.getScore() + pig.getScore());
                            if(player.getScore()>=50000){
                                Game.setScreen(new SpaceLevelEnd(Game,player));
                            }
                        }
                        world.destroyBody(pig.getPiggyBody());
                        iterator.remove();
                    }
                }
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
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2, 0); // Set camera position to center
        camera.update();
        sprite = new Sprite(new Texture("assets/MarsLevel.png"));
        batch = new SpriteBatch();

        world = new World(new Vector2(0, -3.73f), false);
        world.setContactListener(new CollisionDetector());

        debugRenderer = new Box2DDebugRenderer();

        PigList = new ArrayList<>();

        PigList.add(new Piggy("First Piggy",1,null,"assets/MushPig.png",world,"Mars",1000,100,47,47,10000));
        PigList.add(new Piggy("Second Piggy",1,null,"assets/ProfPig.png",world,"Mars",1000,50,47,47,10000));
        PigList.add(new Piggy("Third Piggy",1,null,"assets/KingPig.png",world,"Mars",1100,50,47,57,10000));
        PigList.add(new Piggy("Fourth Piggy",1,null,"assets/CorpPig.png",world,"Mars",1100,100,47,43,10000));
        PigList.add(new Piggy("Fifth Piggy",1,null,"assets/FirstPiggy.png",world,"Mars",1050,150,47,40,10000));

        world.setGravity(new Vector2(0, 0f));
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
        Game.clickHandling(PauseButton, pauseButtonPixmap, new SpacePauseScreen(Game, "assets/MarsBig.png","Mars"));

        RedBirdButton= createButton("assets/RedBird.png","assets/HoverRedBird.png",141, (int) (720 -39-78.3), (int) 78.3, (int) 78.3);
        redBirdButtonPixmap = new Pixmap(Gdx.files.internal("assets/RedBird.png"));
        stage.addActor(RedBirdButton);
        Game.clickHandling(RedBirdButton, redBirdButtonPixmap, null);
        RedBirdButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (SpaceBird != null) {
                    world.destroyBody(SpaceBird.getBirdBody());
                }
                SpaceBird = new Bird("Red Bird", 8, null, "assets/RedBirdMain.png",world,"Mars");
                birdsAvailable--;
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
                SpaceBird = new Bird("Yellow Bird", 4, null, "assets/YellowBirdMain.png",world,"Mars");
                birdsAvailable--;
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
                SpaceBird = new Bird("Blue Bird", 2, null, "assets/BlueBirdMain.png",world,"Mars");
                birdsAvailable--;
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
//                SpaceBird = new Bird("Bomb Bird", 8, null, "assets/BombBirdMain.png",world,"Mars");
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
//                SpaceBird = new Bird("White Bird", 6, null, "assets/WhiteBirdMain.png",world,"Mars");
//                birdsAvailable--;
//                BirdX  = SpaceBird.getX();
//                BirdY = SpaceBird.getY();
//                return true;
//            }
//
//        });

//        world = SpaceBird.getWorldInstance();

        bodyDef = new BodyDef();
        FixtureDef = new FixtureDef();

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
        GroundShape1.createChain(new Vector2[] {new Vector2((271),(325)),new Vector2((280),(325))});

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
            Game.setScreen(new SpaceLevelEnd(Game,player));
        }
        cleanupDestroyedBodies();

        for (Piggy pig : PigList) {
            if (pig != null && pig.isRemoved()) {
                markForRemoval(pig);
            }
        }
        debugRenderer.render(world,camera.combined);
        if (SpaceBird != null) {
            SpaceBird.updateSprite();

        }
        for(Piggy pig: PigList){
            if(pig != null  && !pig.isRemoved()) {
                pig.updateSprite();
            }
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
        batch.end();
        if (SpaceBird != null) {
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
}
