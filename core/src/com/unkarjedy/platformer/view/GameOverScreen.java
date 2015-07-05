package com.unkarjedy.platformer.view;


import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.unkarjedy.platformer.PlatformerGame;
import com.unkarjedy.platformer.utils.ResourceManager;

/**
 * Created by Dima Naumenko on 04.07.2015.
 */
public class GameOverScreen implements Screen {

    private PlatformerGame game;

    private OrthographicCamera camera;
    private GlyphLayout glyphLayout;
    private BitmapFont font;
    private SpriteBatch sb;

    Sprite sadKoala;

    static String[] sadKoalas = new String[]{
                "koala_sad1.jpg",
                "koala_sad2.jpg",
                "koala_sad3.jpg",
                "koala_sad4.jpg",
                "koala_sad5.jpg",
        };
    static int currentKoala = (int) (Math.random() * sadKoalas.length);

    public GameOverScreen(PlatformerGame game) {
        this.game = game;
        create();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        font.draw(sb, glyphLayout, -glyphLayout.width / 2, camera.viewportHeight * 0.25f);
        sadKoala.draw(sb);
        sb.end();

        if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY))
            game.goToMenu();
    }


    private void create() {
        sb = new SpriteBatch();
        camera = new OrthographicCamera();

        glyphLayout = new GlyphLayout();
        font = ResourceManager.get("JungleRoarRegular.fnt", BitmapFont.class);
        glyphLayout.setText(font, "Game over");
        sadKoala = new Sprite(getRandomSadKoala());
    }

    private Texture getRandomSadKoala() {
        String internalPath = ResourceManager.texturesDir + sadKoalas[currentKoala];
        currentKoala = (currentKoala + 1) % sadKoalas.length;
        return new Texture(internalPath);
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
//        camera.position.set(width / 2, height / 2, 0);
        camera.update();
        float scaleRate = camera.viewportHeight / sadKoala.getHeight() / 2;
        sadKoala.setSize(sadKoala.getWidth() * scaleRate, sadKoala.getHeight() * scaleRate);
        sadKoala.setPosition(-sadKoala.getWidth() / 2,
                -sadKoala.getHeight() + camera.viewportHeight * 0.25f - glyphLayout.height - 40);
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
