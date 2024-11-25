package com.unreal.angrybirds;

import com.badlogic.gdx.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SerializationTest {
    NormalLevel1 newScreen;
    NormalPauseScreen levelEnd;
    Main game = new Main();
    @BeforeEach
    public void setUp() {
        game = new Main();
        newScreen = new NormalLevel1(game);

    }


    @Test
    public void checkSerializationFile() {
        File preexist = new File("GameSaves/Test1.txt");
        if (preexist.exists()) {
            preexist.delete();
        }
        assertFalse(preexist.exists());
//        levelEnd = new NormalPauseScreen(game, newScreen, "Test1.txt");
        game.saveGameScreen(newScreen, "Test1.txt");
        assertTrue(game.loadGameScreen("Test1.txt") != null);
    }
    @Test
    public void checkSerializationOnPause(){
        File preexist = new File("GameSaves/NormalTest1.txt");
        if (preexist.exists()) {
            preexist.delete();
        }
        assertFalse(preexist.exists());
        levelEnd = new NormalPauseScreen(game, newScreen, "Test1.txt");
        assertTrue(game.loadGameScreen("NormalTest1.txt") != null);
    }
}

