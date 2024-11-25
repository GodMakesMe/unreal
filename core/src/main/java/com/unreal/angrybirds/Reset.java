package com.unreal.angrybirds;

import com.badlogic.gdx.Screen;

public class Reset implements Screen {
    Main Game;
    public Reset(Main game){
        Game = game;
    }

    @Override
    public void show() {
        Game.removeAllSavesGamesAndScores();
        Game.setScreen(new Exit(Game));
    }

    @Override
    public void render(float delta) {

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

    }
}
