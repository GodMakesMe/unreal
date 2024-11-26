package com.unreal.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
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

public class SpacePauseScreen  implements Screen {
    private Main Game;
    private OrthographicCamera camera;
    private Stage stage;
    private SpriteBatch batch;
    private Sprite sprite;
    private Sprite planetSprite;

    private String PlanetPath;
    private String Planet;
    private Screen previousScreen;
    private ImageButton ResumeButton;
    private Pixmap resumeButtonPixmap;
    private ImageButton SettingsButton;
    private Pixmap settingsButtonPixmap;
    private ImageButton BacktoMenuButton;
    private Pixmap backtoMenuPixmap;
    BitmapFont Scorefont;


    public SpacePauseScreen(Main game,String PlanetPath,String Planet, Screen prevScreen) {
        this.Game = game;
        this.PlanetPath = PlanetPath;
        this.Planet = Planet;
        this.previousScreen = prevScreen;
        Scorefont = new BitmapFont(Gdx.files.internal("angrybirds.fnt"));
        Scorefont.setColor(Color.WHITE);
//        this.previousScreen.pause();
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
        sprite = new Sprite(new Texture("assets/SpacePause.png"));
        batch = new SpriteBatch();

        sprite.setPosition(0, 0);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        planetSprite = new Sprite(new Texture(PlanetPath));
        planetSprite.setPosition(752, 720-180-363);
        planetSprite.setSize(363, 363);

        ResumeButton = createButton("assets/Resume.png","assets/HoverResume.png",234, 720 -217-67, 268, 67);
        resumeButtonPixmap = new Pixmap(Gdx.files.internal("assets/Resume.png"));
        stage.addActor(ResumeButton);

        Game.clickHandling(ResumeButton, resumeButtonPixmap, previousScreen);
        Game.saveGameScreen(previousScreen, Planet+"Level");
        SettingsButton = createButton("assets/Settings1.png","assets/HoverSettings1.png",235, 720 -299-67, 268, 67);
        settingsButtonPixmap = new Pixmap(Gdx.files.internal("assets/Settings1.png"));
        stage.addActor(SettingsButton);
        Game.clickHandling(SettingsButton, settingsButtonPixmap, new SettingsPage(Game, "assets/SpacePause.png", this));

        BacktoMenuButton = createButton("assets/BacktoMenu.png","assets/HoverBacktoMenu.png",234, 720 -381-67, 268, 67);
        backtoMenuPixmap = new Pixmap(Gdx.files.internal("assets/BacktoMenu.png"));
        stage.addActor(BacktoMenuButton);
        Game.clickHandling(BacktoMenuButton, backtoMenuPixmap, new SpaceLevelScreen(Game));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(1, 1, 1, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        sprite.draw(batch);
        planetSprite.draw(batch);
        batch.end();
        batch.begin();
        GlyphLayout ScoreLayout = new GlyphLayout();
        if (previousScreen instanceof MarsLevel){
            ScoreLayout = new GlyphLayout(Scorefont,""+String.format("%08d", ((MarsLevel)previousScreen).player.getScore()));
        }else if (previousScreen instanceof JupiterLevel){
            ScoreLayout = new GlyphLayout(Scorefont,""+String.format("%08d", ((JupiterLevel)previousScreen).player.getScore()));
        }else if (previousScreen instanceof MoonLevel){
            ScoreLayout = new GlyphLayout(Scorefont,""+String.format("%08d", ((MoonLevel)previousScreen).player.getScore()));
        }else if (previousScreen instanceof EarthLevel){
            ScoreLayout = new GlyphLayout(Scorefont,""+String.format("%08d", ((EarthLevel)previousScreen).player.getScore()));
        }else if (previousScreen instanceof SaturnLevel){
            ScoreLayout = new GlyphLayout(Scorefont,""+String.format("%08d", ((SaturnLevel)previousScreen).player.getScore()));
        }else if (previousScreen instanceof VenusLevel){
            ScoreLayout = new GlyphLayout(Scorefont,""+String.format("%08d", ((VenusLevel)previousScreen).player.getScore()));
        }else if (previousScreen instanceof MercuryLevel){
            ScoreLayout = new GlyphLayout(Scorefont,""+String.format("%08d", ((MercuryLevel)previousScreen).player.getScore()));
        }else if (previousScreen instanceof UranusLevel){
            ScoreLayout = new GlyphLayout(Scorefont,""+String.format("%08d", ((UranusLevel)previousScreen).player.getScore()));
        }else if (previousScreen instanceof NeptuneLevel){
            ScoreLayout = new GlyphLayout(Scorefont,""+String.format("%08d", ((NeptuneLevel)previousScreen).player.getScore()));
        }

        Scorefont.draw(batch,ScoreLayout,296,780-125-62);
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

    }
}

