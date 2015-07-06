package com.unkarjedy.platformer.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.unkarjedy.platformer.PlatformerGame;
import com.unkarjedy.platformer.controller.CameraController;
import com.unkarjedy.platformer.controller.GameController;
import com.unkarjedy.platformer.model.Enemy;
import com.unkarjedy.platformer.model.GameLevel;
import com.unkarjedy.platformer.model.LevelScore;
import com.unkarjedy.platformer.model.Player;
import com.unkarjedy.platformer.renderer.HUDRenderer;
import com.unkarjedy.platformer.utils.MemoryLogger;

import java.util.ArrayList;
import java.util.List;

import static com.unkarjedy.platformer.utils.Constants.MIN_DELTA_TIME;

/**
 * Created by Dima Naumenko on 01.07.2015.
 */
public class PlayScreen implements Screen {

    private final PlatformerGame game;

    private static float UNIT_SCALE = 1 / 16f;
    private static float viewportWidth = 30;
    private static float viewportHeight = 20;

    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private SpriteBatch sb = new SpriteBatch();;
    private HUDRenderer hudRenderer;
    private FPSLogger fpsLogger = new FPSLogger();
    private MemoryLogger memoryLogger = new MemoryLogger();

    private GameLevel level;
    private LevelScore levelScore;
    private Player player;
    private List<Enemy> enemies = new ArrayList<>();
    private GameController gameController;
    private CameraController cameraController;

    public PlayScreen(PlatformerGame game) {
        this.game = game;

        loadLevel("level1.tmx");
        createCamera();
        createPlayer();
        createEnemies();

        hudRenderer = new HUDRenderer(player, levelScore);
        gameController = new GameController(this);
        Gdx.input.setInputProcessor(gameController);

        // PLACE SHIT CODE HERE
        if(!level.getLevelMusic().isPlaying()){
            level.getLevelMusic().setLooping(true);
            level.getLevelMusic().play();
        }

        cameraController = new CameraController(camera, player, level);
    }

    private void createEnemies() {
        for(Vector2 enemyPos : level.getEnemiesSpawnPositions()){
            Enemy enemy = new Enemy();
            enemy.setPosition(enemyPos);
            enemy.setWidth(UNIT_SCALE * enemy.getWidth());
            enemy.setHeight(UNIT_SCALE * enemy.getHeight());
            enemies.add(enemy);
        }
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(Gdx.graphics.getDeltaTime());

        sb.setProjectionMatrix(camera.combined);
        renderLevel();

        player.render(sb);
        for(Enemy enemy : enemies)
            enemy.render(sb);

        hudRenderer.render();

        fpsLogger.log();
        memoryLogger.log();
    }

    private void loadLevel(String levelFilename) {
        level = new GameLevel(levelFilename, UNIT_SCALE);
        renderer = new OrthogonalTiledMapRenderer(level.getMap(), UNIT_SCALE);
        levelScore = new LevelScore(level);
    }

    public void setDefaultPlayerPosition() {
        player.setPosition(level.getPlayerSpawnPosition());
    }

    private void createCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, viewportWidth, viewportHeight);
        camera.update();
    }

    private void createPlayer() {
        player = new Player();
        player.setWidth(UNIT_SCALE * player.getWidth());
        player.setHeight(UNIT_SCALE * player.getHeight());

        setDefaultPlayerPosition();
    }

    private void update(float dt) {
        if (dt == 0) return;
        if (dt > MIN_DELTA_TIME)
            dt = MIN_DELTA_TIME;

        gameController.update(dt);
        cameraController.update(dt);
    }

    private void renderLevel() {
        Gdx.gl.glClearColor(0.7f, 0.7f, 1.0f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        renderer.render();
    }



    @Override
    public void resize(int width, int height) {
        viewportWidth = viewportHeight * width / height;
        camera.setToOrtho(false, viewportWidth, viewportHeight);
        hudRenderer.resize(width, height);
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
        level.getLevelMusic().dispose();
    }

    public Player getPlayer() {
        return player;
    }

    public GameLevel getLevel() {
        return level;
    }

    public PlatformerGame getGame() {
        return game;
    }

    public LevelScore getLevelScore() {
        return levelScore;
    }

    public HUDRenderer getHudRenderer() {
        return hudRenderer;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}