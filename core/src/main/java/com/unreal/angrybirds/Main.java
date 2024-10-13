package com.unreal.angrybirds;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private Texture buttonImage;
    private Button imageButton;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("HomePage.png"); // Change it to libgdx.png
        buttonImage = new Texture("StartButton.png");
        imageButton = new Button("Start Game");
        imageButton.setSize(buttonImage.getWidth(), buttonImage.getHeight());
//        imageButton.createImage()
    }

    @Override
    public void render() {
//        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        ScreenUtils.clear(0.10f, 0.10f, 0.1f, 1f);
        batch.begin();
        batch.draw(image, 0, 0, 5120/4, 2880/4);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
