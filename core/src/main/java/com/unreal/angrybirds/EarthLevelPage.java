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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class  EarthLevelPage implements Screen {
    private Main Game;
    private OrthographicCamera camera;
    private Stage stage;
    private SpriteBatch batch;
    private Sprite sprite;

    private Texture Settings;
    private ImageButton Settingsbutton;
    private Pixmap settingsButtonPixmap;
    private Texture HoverSettings;

    private Texture Level1;
    private ImageButton Level1Button;
    private Pixmap Level1ButtonPixmap;
    private Texture HoverLevel1;

    private ImageButton Backbutton;
    private Pixmap PixmapBackbutton;

//    private Texture Level2;
//    private ImageButton Level2Button;
//    private Pixmap Level2ButtonPixmap;
//    private Texture HoverLevel2;
//
//    private Texture Level3;
//    private ImageButton Level3Button;
//    private Pixmap Level3ButtonPixmap;
//    private Texture HoverLevel3;


    public EarthLevelPage(Main game) {
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
        sprite = new Sprite(new Texture("assets/EarthLevelsPage.png"));
        batch = new SpriteBatch();

        sprite.setPosition(0, 0);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Settings = new Texture("assets/SettingsButton.png");
        settingsButtonPixmap = new Pixmap(Gdx.files.internal("assets/SettingsButton.png"));
        HoverSettings = new Texture("assets/HoverSettings.png");
        ImageButton.ImageButtonStyle SettingsStyle = new ImageButton.ImageButtonStyle();
        SettingsStyle.up = new TextureRegionDrawable(new TextureRegion(Settings));
        SettingsStyle.over = new TextureRegionDrawable(new TextureRegion(HoverSettings));
        Settingsbutton = new ImageButton(SettingsStyle);
        Settingsbutton.setPosition(29,720-27-55);
        Settingsbutton.setSize(55,55);
        stage.addActor(Settingsbutton);
        Game.clickHandling(Settingsbutton, settingsButtonPixmap, new SettingsPage(Game, "assets/EarthLevelsPage.png",this));

        for (int i = 0; i < 6; i++) {
            Level1 = new Texture("assets/Level.png");
            Level1ButtonPixmap = new Pixmap(Gdx.files.internal("assets/Level.png"));
            HoverLevel1 = new Texture("assets/HoverLevel.png");
            ImageButton.ImageButtonStyle Level1Style = new ImageButton.ImageButtonStyle();
            Level1Style.up = new TextureRegionDrawable(new TextureRegion(Level1));
            Level1Style.over = new TextureRegionDrawable(new TextureRegion(HoverLevel1));
            Level1Button = new ImageButton(Level1Style);
            Level1Button.setPosition(205 + i*148.4f, 720 - 201.79f - 116);
            Level1Button.setSize(Level1.getWidth() / 4f, Level1.getHeight() / 4f);
            stage.addActor(Level1Button);
            if(i==0){
                Game.clickHandling(Level1Button, Level1ButtonPixmap, new NormalGuidePage(Game));
            } else if (i==1) {
                Game.clickHandling(Level1Button, Level1ButtonPixmap, new NormalLevel2(Game));
            }else if(i==2){
                Game.clickHandling(Level1Button, Level1ButtonPixmap, new NormalLevel3(Game));
            }else{
                Game.clickHandling(Level1Button, Level1ButtonPixmap, new NormalLevelCommingSoon(Game));
            }
        }

        for (int i = 0; i < 6; i++) {
            Level1 = new Texture("assets/Level.png");
            Level1ButtonPixmap = new Pixmap(Gdx.files.internal("assets/Level.png"));
            HoverLevel1 = new Texture("assets/HoverLevel.png");
            ImageButton.ImageButtonStyle Level1Style = new ImageButton.ImageButtonStyle();
            Level1Style.up = new TextureRegionDrawable(new TextureRegion(Level1));
            Level1Style.over = new TextureRegionDrawable(new TextureRegion(HoverLevel1));
            Level1Button = new ImageButton(Level1Style);
            Level1Button.setPosition(205 + i*148.4f, 720 - 348.67f - 116);
            Level1Button.setSize(Level1.getWidth() / 4f, Level1.getHeight() / 4f);
            stage.addActor(Level1Button);
            Game.clickHandling(Level1Button, Level1ButtonPixmap, new NormalLevelCommingSoon(Game));
        }
        for (int i = 0; i < 6; i++) {
            Level1 = new Texture("assets/Level.png");
            Level1ButtonPixmap = new Pixmap(Gdx.files.internal("assets/Level.png"));
            HoverLevel1 = new Texture("assets/HoverLevel.png");
            ImageButton.ImageButtonStyle Level1Style = new ImageButton.ImageButtonStyle();
            Level1Style.up = new TextureRegionDrawable(new TextureRegion(Level1));
            Level1Style.over = new TextureRegionDrawable(new TextureRegion(HoverLevel1));
            Level1Button = new ImageButton(Level1Style);
            Level1Button.setPosition(205 + i*148.4f, 720 - 491.91f - 116);
            Level1Button.setSize(Level1.getWidth() / 4f, Level1.getHeight() / 4f);
            stage.addActor(Level1Button);
            Game.clickHandling(Level1Button, Level1ButtonPixmap, new NormalLevelCommingSoon(Game));
        }

        Backbutton = createButton("assets/Back.png","assets/HoverBack.png",47,720-635-55,55,55);
        PixmapBackbutton = new Pixmap(Gdx.files.internal("assets/Back.png"));
        stage.addActor(Backbutton);
        Game.clickHandling(Backbutton, PixmapBackbutton, new SeasonPage(Game));
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
        batch.begin();
        batch.draw(new Texture("OuterNumbering.png"), 224, 720-226.79f-358, 822, 358);
        batch.end();
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
