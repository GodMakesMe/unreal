package com.unreal.angrybirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class HomePage implements Screen {
    public Main Game;
    private OrthographicCamera camera;
    private Stage stage;
    private SpriteBatch batch;
    private Sprite sprite;
    private ImageButton Startbutton;
    private ImageButton Settingsbutton;
    private Texture Start;
    private Texture HoverStart;
    private Texture Settings;
    private Texture HoverSettings;
    private Pixmap startButtonPixmap;
    private Pixmap settingsButtonPixmap;
    public HomePage(Main game) {
        this.Game = game;
    }
    @Override
    public void show() {
        Game.playMusic();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0); // Set camera position to center
        camera.update();
        sprite = new Sprite(new Texture("assets/HomePagewithoutbutton.png"));
        batch = new SpriteBatch();

        sprite.setPosition(0, 0);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Start = new Texture("assets/start.png");
        startButtonPixmap = new Pixmap(Gdx.files.internal("assets/start.png"));
        HoverStart = new Texture("assets/hoverStart.png");

        Settings = new Texture("assets/SettingsButton.png");
        settingsButtonPixmap = new Pixmap(Gdx.files.internal("assets/SettingsButton.png"));
        HoverSettings = new Texture("assets/HoverSettings.png");

        ImageButton.ImageButtonStyle SettingsStyle = new ImageButton.ImageButtonStyle();
        SettingsStyle.up = new TextureRegionDrawable(new TextureRegion(Settings));
        SettingsStyle.over = new TextureRegionDrawable(new TextureRegion(HoverSettings));

        ImageButton.ImageButtonStyle Startstyle = new ImageButton.ImageButtonStyle();
        Startstyle.up = new TextureRegionDrawable(new TextureRegion(Start));
        Startstyle.over = new TextureRegionDrawable(new TextureRegion(HoverStart));

        Startbutton = new ImageButton(Startstyle);
        Startbutton.setPosition(522,720-466-72);
        Startbutton.setSize(Start.getWidth()/4,Start.getHeight()/4);
        stage.addActor(Startbutton);

        Settingsbutton = new ImageButton(SettingsStyle);
        Settingsbutton.setPosition(29,720-27-55);
        Settingsbutton.setSize(55,55);
        stage.addActor(Settingsbutton);

        Game.clickHandlingByFunction(Startbutton, startButtonPixmap, new SeasonPage(Game), () -> {dispose();});
        Game.clickHandling(Settingsbutton, settingsButtonPixmap, new SettingsPage(Game, "assets/HomePagewithoutbutton.png", this));

//        Startbutton.addListener(new ClickListener() {
//            final ImageButton ButtonCopy = Startbutton;
//            final Pixmap PixmapCopy = startButtonPixmap;
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                Vector2 localCoords = new Vector2();
//                ButtonCopy.localToStageCoordinates(localCoords.set(x, y));
//                float scaleX = 1.0f;
//                float scaleY = 1.0f;
//                if ((float) ButtonCopy.getHeight() != (float) PixmapCopy.getHeight()) {
//                    scaleY = (float) ButtonCopy.getHeight() / (float) PixmapCopy.getHeight();
//                    Gdx.app.log("scaleY", scaleY + "");
//                }
//                if ((float) ButtonCopy.getWidth() != (float) PixmapCopy.getWidth()) {
//                    scaleX = (float) ButtonCopy.getWidth() / (float) PixmapCopy.getWidth();
//                    Gdx.app.log("scaleY", scaleX + "");
//                }
//
//                float buttonX = ButtonCopy.getX();  float buttonY = ButtonCopy.getY();
//                float localX = -(buttonX - localCoords.x);  float localY = -(buttonY - localCoords.y);
//
//                Gdx.app.log("Click", "Local Coordinates: (" + localX + ", " + localY + ")");
//                // Check if the pixel is opaque
//                if (isPixelOpaque((int) (localX/scaleX), (int) (localY/scaleY), PixmapCopy)) {
//                    Gdx.app.log("Click", "Button clicked inside shape!");
//                    Game.setScreen(new SeasonPage(Game));
//                } else {
//                    Gdx.app.log("Click", "Clicked outside the button shape");
//                }
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
//        Game.pauseMusic();
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        sprite.getTexture().dispose();
        Start.dispose();
        HoverStart.dispose();
    }
}
