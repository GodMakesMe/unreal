package com.unreal.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class EarthLevel  implements Screen {
    private Main Game;
    private OrthographicCamera camera;
    private Stage stage;
    private SpriteBatch batch;
    private Sprite sprite;

    private ImageButton Nextbutton;
    private Pixmap nextButtonPixmap;
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

    private Bird SpaceBird;
    private float BirdX;
    private float BirdY;

    private World world;
    private Box2DDebugRenderer debugRenderer;

    private BodyDef bodyDef;
    private FixtureDef FixtureDef;

    public EarthLevel(Main game) {
        this.Game = game;
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
        camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0); // Set camera position to center
        camera.update();
        sprite = new Sprite(new Texture("assets/EarthLevel.png"));
        batch = new SpriteBatch();

        world = new World(new Vector2(0, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();


        sprite.setPosition(0, 0);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Nextbutton = createButton("assets/Next.png", "assets/HoverNext.png", 1167, (int) (720 - 612 - 78.3), (int) 78.3, (int) 78.3);
        nextButtonPixmap = new Pixmap(Gdx.files.internal("assets/Next.png"));
        stage.addActor(Nextbutton);
        Game.clickHandling(Nextbutton, nextButtonPixmap, new SpaceLevelEnd(Game));

        PauseButton = createButton("assets/Pause.png", "assets/HoverPause.png", 47, (int) (720 - 39 - 78.3), (int) 78.3, (int) 78.3);
        pauseButtonPixmap = new Pixmap(Gdx.files.internal("assets/Pause.png"));
        stage.addActor(PauseButton);
        Game.clickHandling(PauseButton, pauseButtonPixmap, new SpacePauseScreen(Game, "assets/EarthBig.png", "Earth"));

        RedBirdButton = createButton("assets/RedBird.png", "assets/HoverRedBird.png", 141, (int) (720 - 39 - 78.3), (int) 78.3, (int) 78.3);
        redBirdButtonPixmap = new Pixmap(Gdx.files.internal("assets/RedBird.png"));
        stage.addActor(RedBirdButton);
        Game.clickHandling(RedBirdButton, redBirdButtonPixmap, null);
        RedBirdButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (SpaceBird != null) {
                    world.destroyBody(SpaceBird.getBirdBody());
                }
                SpaceBird = new Bird("Red Bird", 5, null, "assets/RedBirdMain.png", world);
                BirdX = SpaceBird.getX();
                BirdY = SpaceBird.getY();
                return true;
            }

        });

        YellowBirdButton = createButton("assets/YellowBird.png", "assets/HoverYellowBird.png", 220, (int) (720 - 39 - 78.3), (int) 78.3, (int) 78.3);
        yellowBirdButtonPixmap = new Pixmap(Gdx.files.internal("assets/YellowBird.png"));
        stage.addActor(YellowBirdButton);
        Game.clickHandling(YellowBirdButton, yellowBirdButtonPixmap, null);
        YellowBirdButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (SpaceBird != null) {
                    world.destroyBody(SpaceBird.getBirdBody());
                }
                SpaceBird = new Bird("Yellow Bird", 4, null, "assets/YellowBirdMain.png", world);
                BirdX = SpaceBird.getX();
                BirdY = SpaceBird.getY();
                return true;
            }

        });

        BlueBirdButton = createButton("assets/BlueBird.png", "assets/HoverBlueBird.png", 299, (int) (720 - 39 - 78.3), (int) 78.3, (int) 78.3);
        blueBirdButtonPixmap = new Pixmap(Gdx.files.internal("assets/BlueBird.png"));
        stage.addActor(BlueBirdButton);
        Game.clickHandling(BlueBirdButton, blueBirdButtonPixmap, null);
        BlueBirdButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (SpaceBird != null) {
                    world.destroyBody(SpaceBird.getBirdBody());
                }
                SpaceBird = new Bird("Blue Bird", 2, null, "assets/BlueBirdMain.png", world);
                BirdX = SpaceBird.getX();
                BirdY = SpaceBird.getY();
                return true;
            }

        });

        BombBirdButton = createButton("assets/BombBird.png", "assets/HoverBombBird.png", 377, (int) (720 - 39 - 78.3), (int) 78.3, (int) 78.3);
        bombBirdButtonPixmap = new Pixmap(Gdx.files.internal("assets/BombBird.png"));
        stage.addActor(BombBirdButton);
        Game.clickHandling(BombBirdButton, bombBirdButtonPixmap, null);
        BombBirdButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (SpaceBird != null) {
                    world.destroyBody(SpaceBird.getBirdBody());
                }
                SpaceBird = new Bird("Bomb Bird", 8, null, "assets/BombBirdMain.png", world);
                BirdX = SpaceBird.getX();
                BirdY = SpaceBird.getY();
                return true;
            }

        });

        WhiteBirdButton = createButton("assets/WhiteBird.png", "assets/HoverWhiteBird.png", 454, (int) (720 - 39 - 78.3), (int) 78.3, (int) 78.3);
        whiteBirdButtonPixmap = new Pixmap(Gdx.files.internal("assets/WhiteBird.png"));
        stage.addActor(WhiteBirdButton);
        Game.clickHandling(WhiteBirdButton, whiteBirdButtonPixmap, null);
        WhiteBirdButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (SpaceBird != null) {
                    world.destroyBody(SpaceBird.getBirdBody());
                }
                SpaceBird = new Bird("White Bird", 6, null, "assets/WhiteBirdMain.png", world);
                BirdX = SpaceBird.getX();
                BirdY = SpaceBird.getY();
                return true;
            }

        });

        bodyDef = new BodyDef();
        FixtureDef = new FixtureDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);

        ChainShape GroundShape = new ChainShape();
        GroundShape.createChain(new Vector2[]{new Vector2(835, 0), new Vector2(1280, 0)});

        FixtureDef.shape = GroundShape;
        FixtureDef.friction = 0.5f;
        FixtureDef.restitution = 0.0f;

        world.createBody(bodyDef).createFixture(FixtureDef);
        GroundShape.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(1, 1, 1, 1);
        batch.setProjectionMatrix(camera.combined);
        world.step(1 / 60f, 6, 2);
        debugRenderer.render(world,camera.combined);
        if (SpaceBird != null) {
            SpaceBird.updateSprite();
        }
        batch.begin();
        sprite.draw(batch);
        batch.end();
        if (SpaceBird != null) {
            batch.begin();
            SpaceBird.getBirdSprite().draw(batch);
            batch.end();
        }

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
    }
}
