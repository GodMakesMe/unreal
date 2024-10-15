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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SeasonPage implements Screen {
    private Main Game;
    private OrthographicCamera camera;
    private Stage stage;
    private SpriteBatch batch;
    private Sprite sprite;

    private ImageButton Settingsbutton;
    private Texture Settings;
    private Texture HoverSettings;

    private ImageButton SpaceButton;
    private Texture Space;
    private Texture HoverSpace;

    private ImageButton EarthButton;
    private Texture Earth;
    private Texture HoverEarth;

    public SeasonPage(Main game) {
        this.Game = game;
    }
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0); // Set camera position to center
        camera.update();
        sprite = new Sprite(new Texture("assets/SeasonPage.png"));
        batch = new SpriteBatch();

        sprite.setPosition(0, 0);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Settings = new Texture("assets/Settings.png");
        HoverSettings = new Texture("assets/HoverSettings.png");
        ImageButton.ImageButtonStyle SettingsStyle = new ImageButton.ImageButtonStyle();
        SettingsStyle.up = new TextureRegionDrawable(new TextureRegion(Settings));
        SettingsStyle.over = new TextureRegionDrawable(new TextureRegion(HoverSettings));
        Settingsbutton = new ImageButton(SettingsStyle);
        Settingsbutton.setPosition(29,720-27-55);
        Settingsbutton.setSize(55,55);
        stage.addActor(Settingsbutton);
        Settingsbutton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button is clicked!!!!!!!!!");
//                Game.setScreen(new SeasonPage(Game));
                return true;
            }
        });

        Space = new Texture("assets/Space.png");
        HoverSpace = new Texture("assets/HoverSpace.png");
        ImageButton.ImageButtonStyle SpaceStyle = new ImageButton.ImageButtonStyle();
        SpaceStyle.up = new TextureRegionDrawable(new TextureRegion(Space));
        SpaceStyle.over = new TextureRegionDrawable(new TextureRegion(HoverSpace));
        SpaceButton = new ImageButton(SpaceStyle);
        SpaceButton.setPosition(664,720-205-275);
        SpaceButton.setSize(285,275);
        stage.addActor(SpaceButton);
        SpaceButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button is clicked!!!!!!!!!");
//                Game.setScreen(new SeasonPage(Game));
                return true;
            }
        });

        Earth = new Texture("assets/Earth.png");
        HoverEarth = new Texture("assets/HoverEarth.png");
        ImageButton.ImageButtonStyle EarthStyle = new ImageButton.ImageButtonStyle();
        EarthStyle.up = new TextureRegionDrawable(new TextureRegion(Earth));
        EarthStyle.over = new TextureRegionDrawable(new TextureRegion(HoverEarth));
        EarthButton = new ImageButton(EarthStyle);
        EarthButton.setPosition(362,720-205-275);
        EarthButton.setSize(285,275);
        stage.addActor(EarthButton);
        EarthButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button is clicked!!!!!!!!!");
//                Game.setScreen(new SeasonPage(Game));
                return true;
            }
        });


//        Startbutton.addListener(new InputListener(){
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                System.out.println("Button is clicked!!!!!!");
//                System.out.println("Button position: " + Startbutton.getX() + ", " + Startbutton.getY());
//                return true;
//            }
//        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(1, 1, 1, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        sprite.draw(batch);
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
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
