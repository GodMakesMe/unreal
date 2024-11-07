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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SpaceLevelEnd  implements Screen {
    private Main Game;
    private OrthographicCamera camera;
    private Stage stage;
    private SpriteBatch batch;
    private Sprite sprite;

    private ImageButton ReplayButton;
    private Pixmap replayButtonPixmap;
    private ImageButton HomeButton;
    private Pixmap homeButtonPixmap;
    private ImageButton NextLevelButton;
    private Pixmap nextLevelButtonPixmap;

    public SpaceLevelEnd(Main Game) {
        this.Game = Game;
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
        sprite = new Sprite(new Texture("assets/SpaceLevelUp.png"));
        batch = new SpriteBatch();

        sprite.setPosition(0, 0);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        ReplayButton = createButton("assets/Retry.png","assets/HoverRetry.png",506, 720 -572-67, 70, 67);
        replayButtonPixmap = new Pixmap(Gdx.files.internal("assets/Retry.png"));
        stage.addActor(ReplayButton);
        Game.clickHandling(ReplayButton, replayButtonPixmap, new SpaceLevelScreen(Game));

        HomeButton = createButton("assets/BacktoHome.png","assets/HoverBacktoHome.png",601, 720 -572-67, 70, 67);
        homeButtonPixmap = new Pixmap(Gdx.files.internal("assets/BacktoHome.png"));
        stage.addActor(HomeButton);
        Game.clickHandling(HomeButton, homeButtonPixmap, new SpaceLevelScreen(Game));

        NextLevelButton = createButton("assets/NextLevel.png","assets/HoverNextLevel.png",701, 720 -572-67, 70, 67);
        nextLevelButtonPixmap = new Pixmap(Gdx.files.internal("assets/NextLevel.png"));
        stage.addActor(NextLevelButton);
        Game.clickHandling(NextLevelButton, nextLevelButtonPixmap, new SpaceLevelScreen(Game));
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