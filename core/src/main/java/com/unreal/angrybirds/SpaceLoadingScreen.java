package com.unreal.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class SpaceLoadingScreen implements Screen {
    private Main Game;
    private OrthographicCamera camera;
    private Stage stage;
    private SpriteBatch batch;
    private Texture[] Loadingbg;
    private Sprite sprite;
    private int frameIndex = 0;
    private float elapsedTime = 0f;
    private float frameDuration = 0.5f;

    public SpaceLoadingScreen(Main Game) {
        this.Game = Game;
    }
    @Override
    public void show() {
        Loadingbg = new Texture[]{
            new Texture("assets/SpaceLoading1.png"),
            new Texture("assets/SpaceLoading2.png"),
            new Texture("assets/SpaceLoading3.png"),
            new Texture("assets/SpaceLoading4.png"),
            new Texture("assets/SpaceLoading5.png"),
            new Texture("assets/SpaceLoading6.png"),
            new Texture("assets/SpaceLoading7.png"),
        };
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
        camera.update();
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        elapsedTime+=delta;

        if(elapsedTime>=frameDuration * (frameIndex + 1)){
            frameIndex = (frameIndex + 1) % Loadingbg.length;
        }
        if (elapsedTime>3.5f) {
            Game.setScreen(new SpaceLevelScreen(Game));
//            this.dispose();
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(1, 1, 1, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(Loadingbg[frameIndex], Gdx.graphics.getWidth() / 2 - Loadingbg[frameIndex].getWidth() / 2,Gdx.graphics.getHeight() / 2 - Loadingbg[frameIndex].getHeight() / 2);
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
        for (Texture frame : Loadingbg) {
            frame.dispose();
        }

    }
}
