package com.unkarjedy.platformer.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.unkarjedy.platformer.PlatformerGame;
import com.unkarjedy.platformer.utils.ResourceManager;

/**
 * Created by Dima Naumenko on 03.07.2015.
 */
public class SplashScreen implements Screen {

    private OrthographicCamera camera;
    private Sprite splash;
    private PlatformerGame game;
    private SpriteBatch sb;

    BitmapFont font;

    public SplashScreen(PlatformerGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(splash, 0, 0, camera.viewportWidth, camera.viewportHeight);
        int progress = (int) (100 * ResourceManager.getManager().getProgress());
        font.draw(sb, "Loading: " + progress + "%", 40, 40);
        sb.end();

        if(ResourceManager.getManager().update())
            game.setScreen(new PlayScreen(game));
    }

    @Override
    public void show() {
        ResourceManager.load();

        sb = new SpriteBatch();
        splash = new Sprite(new Texture("textures/Koalaabilitylol.png"));
        camera = new OrthographicCamera();

        font = new BitmapFont(Gdx.files.internal("fonts/JungleRoarRegular.fnt"),false);
    }


    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.position.set(width / 2, height / 2, 0);
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
        sb.dispose();
    }

}
