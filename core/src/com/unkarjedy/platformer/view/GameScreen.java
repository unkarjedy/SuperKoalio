package com.unkarjedy.platformer.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.unkarjedy.platformer.controller.GameRenderer;

/**
 * Created by Dima Naumenko on 01.07.2015.
 */
public class GameScreen implements Screen {
    private final Game game;
    private GameRenderer renderer;

    public GameScreen(Game game) {
        this.game = game;
        renderer = new GameRenderer(game);
    }

    @Override
    public void render(float delta) {
        renderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}