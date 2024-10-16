package com.unreal.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SpaceLevelScreen implements Screen {
    private Main Game;
    private OrthographicCamera camera;
    private Stage stage;
    private SpriteBatch batch;
    private Sprite sprite;

    private ImageButton Backbutton;
    private ImageButton MercuryButton;
    private ImageButton VenusButton;
    private ImageButton EarthButton;
    private ImageButton MarsButton;
    private ImageButton JupiterButton;
    private ImageButton SaturnButton;
    private ImageButton UranusButton;
    private ImageButton NeptuneButton;
    private ImageButton MoonButton;

    public SpaceLevelScreen(Main game) {
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
        sprite = new Sprite(new Texture("assets/SpaceLevelPage.png"));
        batch = new SpriteBatch();

        sprite.setPosition(0, 0);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Backbutton = createButton("assets/Back.png","assets/HoverBack.png",47,720-635-55,55,55);
        stage.addActor(Backbutton);
        Backbutton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button is clicked!!!!!!!!!");
                Game.setScreen(new SeasonPage(Game));
                return true;
            }
        });

        MercuryButton = createButton("assets/Mercury.png","assets/HoverMercury.png",148,720-167-78,78,78);
        stage.addActor(MercuryButton);
        MercuryButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button is clicked!!!!!!!!!");
                Game.setScreen(new MercuryIntroduction(Game));
                return true;
            }
        });

        VenusButton = createButton("assets/Venus.png","assets/HoverVenus.png",158,720-590-80,80,80);
        stage.addActor(VenusButton);
        VenusButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button is clicked!!!!!!!!!");
                Game.setScreen(new VenusIntroduction(Game));
                return true;
            }
        });

        EarthButton = createButton("assets/EarthP.png","assets/HoverEarthP.png",381,720-240-99,100,99);
        stage.addActor(EarthButton);
        EarthButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button is clicked!!!!!!!!!");
                Game.setScreen(new EarthIntroduction(Game));
                return true;
            }
        });

        MarsButton = createButton("assets/Mars.png","assets/HoverMars.png",433,720-590-89,91,89);
        stage.addActor(MarsButton);
        MarsButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button is clicked!!!!!!!!!");
                Game.setScreen(new MarsIntroduction(Game));
                return true;
            }
        });

        JupiterButton = createButton("assets/Jupiter.png","assets/HoverJupiter.png",600,720-366-196,198,196);
        stage.addActor(JupiterButton);
        JupiterButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button is clicked!!!!!!!!!");
                Game.setScreen(new JupiterIntroduction(Game));
                return true;
            }
        });

        SaturnButton = createButton("assets/Saturn.png","assets/HoverSaturn.png", (int) 703.57F, (int) (720-(-26.42)-266.72), (int) 328.65F, (int) 266.72F);
        stage.addActor(SaturnButton);
        SaturnButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button is clicked!!!!!!!!!");
                Game.setScreen(new SaturnIntroduction(Game));
                return true;
            }
        });

        UranusButton = createButton("assets/Uranus.png","assets/HoverUranus.png", 963, 720-481-218,218,218);
        stage.addActor(UranusButton);
        UranusButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button is clicked!!!!!!!!!");
                Game.setScreen(new UranusIntroduction(Game));
                return true;
            }
        });

        NeptuneButton = createButton("assets/Neptune.png","assets/HoverNeptune.png", 1129, 720-9-151,151,151);
        stage.addActor(NeptuneButton);
        NeptuneButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button is clicked!!!!!!!!!");
                Game.setScreen(new NeptuneIntroduction(Game));
                return true;
            }
        });

        MoonButton = createButton("assets/Moon.png","assets/HoverMoon.png", 441, 720-174-64,47,64);
        stage.addActor(MoonButton);
        MoonButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button is clicked!!!!!!!!!");
                Game.setScreen(new MoonIntroduction(Game));
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(1, 1, 1, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        sprite.draw(batch);
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
    }
}
