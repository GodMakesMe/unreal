package com.unreal.angrybirds;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Stage stage;
    private Sprite sprite;
    private Texture image;
    private TextureRegion buttonImage;
    private ImageButton imageButton;

    @Override
    public void create() {
        image = new Texture("HomePage.png"); // Change it to libgdx.png
        batch = new SpriteBatch();
        sprite = new Sprite(image);
        sprite.setSize(image.getWidth()/4, image.getHeight()/4);
        stage = new Stage();
        buttonImage = new TextureRegion(new Texture("StartButton.png"));
        imageButton = new ImageButton(new TextureRegionDrawable(buttonImage));
        imageButton.setSize(100, 100);
        imageButton.setSize(buttonImage.getRegionWidth(), buttonImage.getRegionHeight());
        imageButton.setPosition(Gdx.graphics.getWidth() / 2 - imageButton.getWidth() / 2,Gdx.graphics.getHeight() / 2 - imageButton.getHeight() / 2);
        stage.addActor(imageButton);
        Gdx.input.setInputProcessor(stage);
//        imageButton.setSize(buttonImage.getWidth(), buttonImage.getHeight());
//        imageButton.createImage()
    }

    @Override
    public void render() {
//        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        ScreenUtils.clear(0.10f, 0.10f, 0.1f, 1f);
//        sprite.setPosition(0, 0);
//        sprite.setSize(5120/4, 2880/4);
//        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        batch.begin();
        sprite.draw(batch);
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
