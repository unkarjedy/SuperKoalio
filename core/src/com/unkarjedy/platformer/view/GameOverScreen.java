package com.unkarjedy.platformer.view;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.unkarjedy.platformer.utils.ResourceManager;

/**
 * Created by Dima Naumenko on 04.07.2015.
 */
public class GameOverScreen implements Screen {

    private Game game;

    private OrthographicCamera camera;
    private GlyphLayout glyphLayout;
    private BitmapFont font;
    private SpriteBatch sb;

    public GameOverScreen(Game game) {
        this.game = game;
    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        font.draw(sb, glyphLayout, -glyphLayout.width / 2, 0);
        sb.end();
    }

    @Override
    public void show() {
        sb = new SpriteBatch();
        camera = new OrthographicCamera();

        glyphLayout = new GlyphLayout();
        font = ResourceManager.manager.get("PressStart2P.fnt");
        glyphLayout.setText(font, "Game over");
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
//        camera.position.set(width / 2, height / 2, 0);
        camera.update();


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
