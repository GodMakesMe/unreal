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

public class JupiterLevel  implements Screen {
    private Main Game;
    private OrthographicCamera camera;
    private Stage stage;
    private SpriteBatch batch;
    private Sprite sprite;

    private ImageButton Nextbutton;
    private ImageButton PauseButton;

    private ImageButton RedBirdButton;
    private ImageButton YellowBirdButton;
    private ImageButton BlueBirdButton;
    private ImageButton BombBirdButton;
    private ImageButton WhiteBirdButton;

    public JupiterLevel(Main game) {
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
        sprite = new Sprite(new Texture("assets/JupiterLevel.png"));
        batch = new SpriteBatch();

        sprite.setPosition(0, 0);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Nextbutton = createButton("assets/Next.png","assets/HoverNext.png",1167, (int) (720 -612-78.3), (int) 78.3, (int) 78.3);
        stage.addActor(Nextbutton);
        Nextbutton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button is clicked!!!!!!!!!");
                Game.setScreen(new SpaceLevelEnd(Game));
                return true;
            }
        });

        PauseButton = createButton("assets/Pause.png","assets/HoverPause.png",47, (int) (720 -39-78.3), (int) 78.3, (int) 78.3);
        stage.addActor(PauseButton);
        PauseButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button is clicked!!!!!!!!!");
                Game.setScreen(new SpacePauseScreen(Game,"assets/JupiterBig.png","Jupiter"));
                return true;
            }
        });

        RedBirdButton= createButton("assets/RedBird.png","assets/HoverRedBird.png",141, (int) (720 -39-78.3), (int) 78.3, (int) 78.3);
        stage.addActor(RedBirdButton);
        RedBirdButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button is clicked!!!!!!!!!");
//                Game.setScreen(new SpaceLevelScreen(Game));
                return true;
            }
        });

        YellowBirdButton= createButton("assets/YellowBird.png","assets/HoverYellowBird.png",220, (int) (720 -39-78.3), (int) 78.3, (int) 78.3);
        stage.addActor(YellowBirdButton);
        YellowBirdButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button is clicked!!!!!!!!!");
//                Game.setScreen(new SpaceLevelScreen(Game));
                return true;
            }
        });

        BlueBirdButton= createButton("assets/BlueBird.png","assets/HoverBlueBird.png",299, (int) (720 -39-78.3), (int) 78.3, (int) 78.3);
        stage.addActor(BlueBirdButton);
        BlueBirdButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button is clicked!!!!!!!!!");
//                Game.setScreen(new SpaceLevelScreen(Game));
                return true;
            }
        });

        BombBirdButton= createButton("assets/BombBird.png","assets/HoverBombBird.png",377, (int) (720 -39-78.3), (int) 78.3, (int) 78.3);
        stage.addActor(BombBirdButton);
        BombBirdButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button is clicked!!!!!!!!!");
//                Game.setScreen(new SpaceLevelScreen(Game));
                return true;
            }
        });

        WhiteBirdButton= createButton("assets/WhiteBird.png","assets/HoverWhiteBird.png",454, (int) (720 -39-78.3), (int) 78.3, (int) 78.3);
        stage.addActor(WhiteBirdButton);
        WhiteBirdButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button is clicked!!!!!!!!!");
//                Game.setScreen(new SpaceLevelScreen(Game));
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
