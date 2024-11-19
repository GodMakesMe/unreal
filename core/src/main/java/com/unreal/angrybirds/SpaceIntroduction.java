package com.unreal.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SpaceIntroduction implements Screen, Serializable {
    private transient Main Game;
    private transient OrthographicCamera camera;
    private transient Stage stage;
    private transient SpriteBatch batch;
    private transient Sprite sprite;

    private transient ImageButton Backbutton;
    private transient Pixmap backButtonPixmap;
    private transient ImageButton PlayButton;
    private transient Pixmap playButtonPixmap;
    private transient Sprite middleStar;
    private transient Sprite startStar;
    private transient Sprite endStar;
    public int stars;
    public String Planet;

    public SpaceIntroduction(Main game, String Planet) {
        this.Game = game;
        this.Planet = Planet;

    }

    public SpaceIntroduction() {
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
        sprite = new Sprite(new Texture("assets/"+Planet+"Screen.png"));
        batch = new SpriteBatch();
        startStar = new Sprite(new Texture("Star1.png"), 124, 119);
        startStar.setPosition(131, 720-122-119);
        middleStar = new Sprite(new Texture("assets/Star2.png"), 164, 157);
        middleStar.setPosition(255, 720-38-157);
        endStar = new Sprite(new Texture("assets/Star3.png"), 124, 119);
        endStar.setPosition(419, 720-110-119);
        sprite.setPosition(0, 0);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Backbutton = createButton("assets/Back.png","assets/HoverBack.png",47,720-635-55,55,55);
        backButtonPixmap = new Pixmap(Gdx.files.internal("assets/Back.png"));
        stage.addActor(Backbutton);
        Game.clickHandlingByFunction(Backbutton, backButtonPixmap, () -> {
            Game.setScreen(new SpaceLevelScreen(Game));
        });
//        Game.clickHandling(Backbutton, backButtonPixmap, new SpaceLevelScreen(Game)));

        Screen screen = null;
        Screen mainScreen = null;
        if(Planet.equals("Mercury")){
            screen = new MercuryIntroduction(Game);
            mainScreen = new MercuryLevel(Game);
        } else if (Planet.equals("Venus")) {
            screen = new VenusIntroduction(Game);
            mainScreen = new VenusLevel(Game);
        }else if (Planet.equals("Earth")) {
            screen = new EarthIntroduction(Game);
            mainScreen = new EarthLevel(Game);
        }else if (Planet.equals("Mars")) {
            screen = new MarsIntroduction(Game);
            mainScreen = new MarsLevel(Game);
        }else if (Planet.equals("Jupiter")) {
            screen = new JupiterIntroduction(Game);
            mainScreen = new JupiterLevel(Game);
        }else if (Planet.equals("Saturn")) {
            screen = new SaturnIntroduction(Game);
            mainScreen = new SaturnLevel(Game);
        }else if (Planet.equals("Uranus")) {
            screen = new UranusIntroduction(Game);
            mainScreen = new UranusLevel(Game);
        }else if (Planet.equals("Neptune")) {
            screen = new NeptuneIntroduction(Game);
            mainScreen = new NeptuneLevel(Game);
        }else if (Planet.equals("Moon")) {
            screen = new MoonIntroduction(Game);
            mainScreen = new MoonLevel(Game);
        }
        PlayButton = createButton("assets/Play.png","assets/HoverPlay.png",680,720-420-159,159,159);
        playButtonPixmap = new Pixmap(Gdx.files.internal("assets/Play.png"));
        stage.addActor(PlayButton);
        Game.clickHandling(PlayButton, playButtonPixmap, mainScreen);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(1, 1, 1, 1);
        batch.setProjectionMatrix(camera.combined);
        Player prev;
        prev = Game.loadGameScore(Planet+"LevelScore");
        batch.begin();
        sprite.draw(batch);
        if (prev != null && prev.hasWin()) {
//            Batch batch1 = new SpriteBatch();
            middleStar.draw(batch);
            startStar.draw(batch);
            endStar.draw(batch);
        }
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
