package com.unreal.angrybirds;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ButtonTest {
    static Main game;
    static ImageButton testButton;
    Pixmap PixmaptestButton;
    static Stage teststage;
    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        Gdx.gl = Mockito.mock(GL20.class);
        Gdx.gl20 = Gdx.gl;
        Gdx.gl30 = Mockito.mock(GL30.class);
        HeadlessApplicationConfiguration configuration = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new Game(){
            @Override
            public void create() {
                game = new Main();
            }
        }, configuration);
    }
    @Test
    public void testCreateButton() {
        testButton = game.createButton("start.png", "hoverStart.png", 100, 100, 202, 72);
        assertNotNull(testButton, "Button should not be null");
        assertEquals(100, testButton.getX(), "Button X position should match");
        assertEquals(100, testButton.getY(), "Button Y position should match");
        assertEquals(202, testButton.getWidth(), "Button width should match");
        assertEquals(72, testButton.getHeight(), "Button height should match");
    }
//    @Test
//    public void testClickHandling() {
//        teststage = Mockito.mock(Stage.class);
//        testButton = game.createButton("EarthP.png", "HoverEarthP.png",100,100,100,99);
//        PixmaptestButton = new Pixmap(Gdx.files.internal("EarthP.png"));
//        teststage.addActor(testButton);
//        game.clickHandling(testButton,PixmaptestButton,null);
//        InputEvent testPress = new InputEvent();
//        testPress.setType(InputEvent.Type.touchDown);
//        testPress.setStageX(80);
//        testPress.setStageY(80);
//        testButton.fire(testPress);
//        assertNotNull(testButton, "Button should remain interactive outside the opaque region");
////        assert true;
//    }
}
