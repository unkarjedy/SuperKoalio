package com.unkarjedy.platformer.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.unkarjedy.platformer.model.GameLevel;
import com.unkarjedy.platformer.model.Player;

import static com.unkarjedy.platformer.utils.Constants.*;

/**
 * Created by Dima Naumenko on 01.07.2015.
 */
public class WorldRenderer {

    private static float UNIT_SCALE = 1 / 16f;
    private static float viewportWidth = 30;
    private static float viewportHeight = 20;

    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private SpriteBatch sb = new SpriteBatch();;
    FPSLogger fpsLogger = new FPSLogger();

    GameLevel level;
    Player player;
    GameController gameController;

    public WorldRenderer() {
        loadLevel("level1.tmx");
        initCamera();
        createPlayer();
        setDefaultPlayerPosition();

        gameController = new GameController(this, player, level);
        Gdx.input.setInputProcessor(gameController);

        // PLACE SHIT CODE HERE
        level.getLevelSound().play();
    }

    private void loadLevel(String levelFilename) {
        level = new GameLevel(levelFilename);
        renderer = new OrthogonalTiledMapRenderer(level.getMap(), UNIT_SCALE);
    }

    public void setDefaultPlayerPosition() {
        player.setPosition(level.getPlayerSpawnPosition());
    }

    private void initCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, viewportWidth, viewportHeight);
        camera.update();
    }


    private void createPlayer() {
        player = new Player();
        player.setWidth(UNIT_SCALE * player.getWidth());
        player.setHeight(UNIT_SCALE * player.getHeight());
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float dt = Gdx.graphics.getDeltaTime();

        update(dt);

        renderLevel();
        renderPlayer(dt);

        fpsLogger.log();
    }

    private void update(float dt) {
        if (dt == 0) return;
        if (dt > MIN_DELTA_TIME)
            dt = MIN_DELTA_TIME;

        gameController.update(dt);

        camera.position.x = player.getPosition().x;
        camera.update();
    }

    private void renderLevel() {
        Gdx.gl.glClearColor(0.7f, 0.7f, 1.0f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        renderer.render();
    }

    private void renderPlayer(float dt) {
//        player.render(dt);
        TextureRegion frame = player.getFrame();

        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        if (player.isFacesRight()){
            sb.draw(frame, player.getPosition().x, player.getPosition().y, player.getWidth(), player.getHeight());
        }
        else{
            sb.draw(frame, player.getPosition().x + player.getWidth(), player.getPosition().y, -player.getWidth(), player.getHeight());
        }
        sb.end();
    }

    public void resize(int width, int height) {
        viewportWidth = viewportHeight * width / height;
        initCamera();
    }

}
