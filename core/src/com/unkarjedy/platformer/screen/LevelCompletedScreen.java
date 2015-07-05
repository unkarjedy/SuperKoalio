package com.unkarjedy.platformer.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.unkarjedy.platformer.PlatformerGame;
import com.unkarjedy.platformer.model.LevelScore;
import com.unkarjedy.platformer.utils.ResourceManager;

/**
 * Created by Dima Naumenko on 04.07.2015.
 */
public class LevelCompletedScreen implements Screen {

    private PlatformerGame game;
    private LevelScore levelScore;

    private Stage stage;
    private Label title, score;
    private BitmapFont font;

    Image happyKoala;

    static String[] happyKoalas = new String[]{
            "koala_happy1.jpg",
            "koala_happy2.jpg",
    };
    static int currentKoala = (int) (Math.random() * happyKoalas.length);

    public LevelCompletedScreen(PlatformerGame game, LevelScore levelScore) {
        this.game = game;
        this.levelScore = levelScore;
        create();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

        if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY))
            game.goToMenu();
    }


    private void create() {
        stage = new Stage();

        font = ResourceManager.get("JungleRoarRegular.fnt", BitmapFont.class);

        title = new Label("Level Completed!", new LabelStyle(font, Color.BLACK));
        centralizeX(title);
        title.setY(stage.getHeight() * 0.7f);

        score = new Label(levelScore.buildScoreString(), new LabelStyle(font, Color.BLUE));
        centralizeX(score);
        score.setY(title.getY() - title.getHeight() - 5);

        happyKoala = new Image(getRandomHappyKoala());
        float scale = stage.getHeight() / happyKoala.getHeight() / 2;
        happyKoala.setSize(happyKoala.getWidth() * scale, happyKoala.getHeight() * scale - 5);
        centralizeX(happyKoala);
        happyKoala.setY(score.getY() - score.getHeight() - happyKoala.getHeight());

        stage.addActor(title);
        stage.addActor(score);
        stage.addActor(happyKoala);

    }

    private void centralize(Actor actor) {
        centralizeX(actor);
        centralizeY(actor);
    }

    private void centralizeX(Actor actor) {
        actor.setX((stage.getWidth() - actor.getWidth()) / 2);
    }

    private void centralizeY(Actor actor) {
        actor.setY((stage.getHeight() - actor.getHeight()) / 2);
    }

    private Texture getRandomHappyKoala() {
        String internalPath = ResourceManager.texturesDir + happyKoalas[currentKoala];
        currentKoala = (currentKoala + 1) % happyKoalas.length;
        return new Texture(internalPath);
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
//        camera.viewportWidth = width;
//        camera.viewportHeight = height;
////        camera.position.set(width / 2, height / 2, 0);
//        camera.update();
//        float scaleRate = camera.viewportHeight / happyKoala.getHeight() / 2;
//        happyKoala.setSize(happyKoala.getWidth() * scaleRate, happyKoala.getHeight() * scaleRate);
//        happyKoala.setPosition(-happyKoala.getWidth() / 2,
//                -happyKoala.getHeight() + camera.viewportHeight * 0.25f - title.height - 40);
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
