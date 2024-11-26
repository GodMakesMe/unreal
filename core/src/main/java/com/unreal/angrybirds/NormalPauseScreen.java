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

public class NormalPauseScreen  implements Screen {
    private Main Game;
    private OrthographicCamera camera;
    private Stage stage;
    private SpriteBatch batch;
    private Sprite sprite;


    private ImageButton ResumeButton;
    private Pixmap resumeButtonPixmap;
    private ImageButton SettingsButton;
    private Pixmap settingsButtonPixmap;
    private ImageButton BacktoMenuButton;
    private Pixmap backtoMenuPixmap;
    private String Level;
    private Screen PreviousScreen;
    BitmapFont Scorefont;

    public NormalPauseScreen(Main game, Screen PreviousScreen, String Level) {
        this.Game = game;
        this.PreviousScreen = PreviousScreen;
        this.Level = Level;
        Scorefont = new BitmapFont(Gdx.files.internal("angrybirds.fnt"));
        Scorefont.setColor(Color.BLACK);

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
        camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
        camera.update();
        sprite = new Sprite(new Texture("assets/NormalPause.png"));
        batch = new SpriteBatch();

        sprite.setPosition(0, 0);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        ResumeButton = createButton("assets/ResumeEarth.png","assets/HoverResumeEarth.png",234, 720 -217-67, 268, 67);
        resumeButtonPixmap = new Pixmap(Gdx.files.internal("assets/ResumeEarth.png"));
        stage.addActor(ResumeButton);
        Game.clickHandling(ResumeButton, resumeButtonPixmap, PreviousScreen);
        Game.saveGameScreen(PreviousScreen, "Normal" + Level);

        SettingsButton = createButton("assets/Settings1Earth.png","assets/HoverSettings1Earth.png",235, 720 -299-67, 268, 67);
        settingsButtonPixmap = new Pixmap(Gdx.files.internal("assets/Settings1Earth.png"));
        stage.addActor(SettingsButton);
        Game.clickHandling(SettingsButton, settingsButtonPixmap, new SettingsPage(Game, "assets/NormalPause.png", this));

        Screen screen = new EarthLevelPage(Game);
        BacktoMenuButton = createButton("assets/BacktoMenuEarth.png","assets/HoverBacktoMenuEarth.png",234, 720 -381-67, 268, 67);
        backtoMenuPixmap = new Pixmap(Gdx.files.internal("assets/BacktoMenuEarth.png"));
        stage.addActor(BacktoMenuButton);
        Game.clickHandling(BacktoMenuButton, backtoMenuPixmap, screen);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(1, 1, 1, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        sprite.draw(batch);
        batch.end();
        batch.begin();
        GlyphLayout ScoreLayout = new GlyphLayout();
        if (PreviousScreen instanceof NormalLevel1){
            ScoreLayout = new GlyphLayout(Scorefont,""+String.format("%08d", ((NormalLevel1)PreviousScreen).player.getScore()));
        }else if (PreviousScreen instanceof NormalLevel2){
            ScoreLayout = new GlyphLayout(Scorefont,""+String.format("%08d", ((NormalLevel2)PreviousScreen).player.getScore()));
        }else if (PreviousScreen instanceof NormalLevel3){
            ScoreLayout = new GlyphLayout(Scorefont,""+String.format("%08d", ((NormalLevel3)PreviousScreen).player.getScore()));
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

