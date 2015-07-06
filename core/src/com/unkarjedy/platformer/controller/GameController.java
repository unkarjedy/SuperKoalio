package com.unkarjedy.platformer.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.unkarjedy.platformer.PlatformerGame;
import com.unkarjedy.platformer.model.Enemy;
import com.unkarjedy.platformer.model.GameLevel;
import com.unkarjedy.platformer.model.LevelScore;
import com.unkarjedy.platformer.model.Player;
import com.unkarjedy.platformer.physics.PhysicsEngine;
import com.unkarjedy.platformer.screen.GameOverScreen;
import com.unkarjedy.platformer.screen.LevelCompletedScreen;
import com.unkarjedy.platformer.screen.PlayScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dima Naumenko on 02.07.2015.
 */
public class GameController implements InputProcessor, PlayerActionsListner {;

    private PlatformerGame game;
    private PlayScreen playScreen;

    private PlayerController playerController;
    private List<EnemyAiController> enemyAiControllers;
    private PhysicsEngine physics;

    private GameLevel level;
    private LevelScore levelScore;

    public GameController(PlayScreen playScreen) {
        this.game = playScreen.getGame();
        this.playScreen = playScreen;
        this.level = playScreen.getLevel();
        this.levelScore = playScreen.getLevelScore();

        playerController = new PlayerController(playScreen.getPlayer(), level);
        playerController.addPlayerStateListner(this);
        playerController.addPlayerStateListner(playScreen.getHudRenderer());

        enemyAiControllers = new ArrayList<>();
        for(Enemy enemy: playScreen.getEnemies()){{
            enemyAiControllers.add(new EnemyAiController(enemy));
        }};


        initPhysicsEngine();
    }

    public void update(float dt) {
        checkPressedKeys();

        playerController.update(dt);
        for(EnemyAiController enemyAiController : enemyAiControllers){
            enemyAiController.update(dt);
        }

        physics.update(dt); // collisions and forces
    }

    private void checkPressedKeys() {
        if ( Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            playerController.moveLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            playerController.moveRight();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            playerController.jump();
        }
    }


    private void initPhysicsEngine() {
        physics = new PhysicsEngine(level);
        physics.setPlayerController(playerController);

        for(EnemyAiController enemyAiController : enemyAiControllers){
            physics.addGameObjectController(enemyAiController);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.UP) {
//            playerController.jump();
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.UP&& playerController.getPlayer().getState() == Player.State.Jumping){
            playerController.stopJump();
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void playerDead() {
        game.setScreen(new GameOverScreen(game));
    }

    @Override
    public void playerIsHit(boolean respawn) {
        if(respawn)
            playScreen.setDefaultPlayerPosition();
    }

    @Override
    public void playerGetsCoin() {
        levelScore.collectCoin();
    }

    @Override
    public void playerReachedLevelFinish() {
        game.setScreen(new LevelCompletedScreen(game, levelScore));
    }
}
