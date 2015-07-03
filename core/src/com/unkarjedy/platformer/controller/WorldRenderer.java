package com.unkarjedy.platformer.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
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
    SpriteBatch sb = new SpriteBatch();;

    private GameLevel level;

    GameController gameController;
    Player player;
    PlayerController playerController;

    PhysicsEngine physics;

    public WorldRenderer() {
        level = new GameLevel("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(level.getMap(), UNIT_SCALE);

        initCamera();
        createPlayer();
        initPhysicsEngine();

        gameController = new GameController(playerController);
        Gdx.input.setInputProcessor(gameController);
    }

    private void initCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, viewportWidth, viewportHeight);
        camera.update();
    }

    private void initPhysicsEngine() {
        physics = new PhysicsEngine();
        physics.setLevel(level);
//        physics.addGameObject(player);
        physics.setPlayer(playerController);
    }

    private void createPlayer() {
        player = new Player();
        player.setPosition(new Vector2(10, 5));
        player.setWidth(UNIT_SCALE * player.getTexture().getWidth());
        player.setHeight(UNIT_SCALE * player.getTexture().getHeight());
        playerController = new PlayerController(player);
    }

    public void render(float delta) {
        float dt = Gdx.graphics.getDeltaTime();

        update(dt);

        camera.position.x = player.getPosition().x;
        camera.update();

        renderLevel();
        renderPlayer(dt);
    }

    private void update(float dt) {
        if (dt == 0) return;
        if (dt > MIN_DELTA_TIME)
            dt = MIN_DELTA_TIME;

        gameController.update(dt);
        physics.update(dt); // collisions and forces
    }

    private void renderLevel() {
        Gdx.gl.glClearColor(0.7f, 0.7f, 1.0f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        renderer.render();
    }

    private void renderPlayer(float dt) {
        TextureRegion frame = player.getFrame();

        sb = new SpriteBatch();
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
