package com.unreal.angrybirds;

import com.badlogic.gdx.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScoreTest {
    NormalLevel1 newScreen;
    Main game = new Main();
    @BeforeEach
    public void setUp() {
        game = new Main();
        newScreen = new NormalLevel1(game);
        newScreen.player.setScore(2398);
    }


    @Test
    public void checkScoreAfterSerialization() {
        File preexist = new File("GameSaves/ScoreTest1.txt");
        if (preexist.exists()) {
            preexist.delete();
        }
        assertFalse(preexist.exists());
        game.saveGameScreen(newScreen, "ScoreTest1.txt");
        assertTrue(game.loadGameScreen("ScoreTest1.txt") != null);
    }
}
