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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingsPage implements Screen {
    private Main Game;
    private Screen previousScreen;
    private Texture BackgroundImage;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Sprite sprite;
    private Stage stage;

    SettingsPage(Main Game, String BackgroundImageDir, Screen previousScreen){
        this.Game = Game;
        BackgroundImage = new Texture(BackgroundImageDir);
        this.previousScreen = previousScreen;
    }

    SettingsPage(Main Game, Screen previousScreen){
        this.Game = Game;
        this.previousScreen = previousScreen;
        this.BackgroundImage = null;
    }

    private ImageButton ExitButton;
    private Pixmap exitButtonPixmap;

    private ImageButton GoBackButton;
    private Pixmap goBackButtonPixmap;

    private ImageButton MuteButton;
    private Pixmap muteButtonPixmap;

    private ImageButton RestartButton;
    private Pixmap restartButtonPixmap;

    private ImageButton musicButton;
    private Pixmap musicButtonPixmap;

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
    void musicHandling(){
        Game.clickHandlingByFunction(musicButton, musicButtonPixmap, () -> {
            if (Game.ost_theme.isPlaying()){
                Game.pauseMusic();
                musicButton.remove();
                musicButton = createButton("assets/Music_Logo_Mute.png", "assets/Hover_Music_Logo_Mute.png", 1072, 720-171-65, 65, 65);
                musicHandling();
                stage.addActor(musicButton);
            }
            else{
                Game.resumeMusic();
                musicButton.remove();
                musicButton = createButton("assets/Music_Logo.png", "assets/Hover_Music_Logo.png", 1072, 720-171-65, 65, 65);
                musicHandling();
                stage.addActor(musicButton);
            }
        });
    }
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0); // Set camera position to center
        camera.update();
        sprite = new Sprite(new Texture("UpperBackSettings.png"));
        batch = new SpriteBatch();
        sprite.setPosition(0, 0);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (Game.ost_theme.isPlaying()) musicButton = createButton("assets/Music_Logo.png", "assets/Hover_Music_Logo.png", 1072, 720-171-65, 65, 65);
        else musicButton = createButton("assets/Music_Logo_Mute.png", "assets/Hover_Music_Logo_Mute.png", 1072, 720-171-65, 65, 65);
        musicButtonPixmap = new Pixmap(Gdx.files.internal("assets/Music_Logo.png"));



        GoBackButton = createButton("assets/GoBackButton.png", "assets/HoverGoBackButton.png", 554, 720 -312 - 67, 268, 67);
        goBackButtonPixmap = new Pixmap(Gdx.files.internal("assets/GoBackButton.png"));
        RestartButton = createButton("assets/RestartButton.png", "assets/HoverRestartButton.png", 554, 720-394-67, 268, 67);
        restartButtonPixmap = new Pixmap(Gdx.files.internal("assets/GoBackButton.png"));
        ExitButton = createButton("assets/ExitButton.png", "assets/HoverExitButton.png", 554, 720-476-67, 268, 67);
        exitButtonPixmap = new Pixmap(Gdx.files.internal("assets/GoBackButton.png"));
        stage.addActor(GoBackButton);
        stage.addActor(RestartButton);
        stage.addActor(ExitButton);
        stage.addActor(musicButton);
        Game.clickHandling(ExitButton, exitButtonPixmap, new Exit(Game));
        Game.clickHandling(RestartButton, restartButtonPixmap, new HomePage(Game));
        Game.clickHandling(GoBackButton, goBackButtonPixmap, previousScreen);
        musicHandling();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(1, 1, 1, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if (BackgroundImage != null) batch.draw(BackgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sprite.draw(batch);
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
