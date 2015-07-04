package com.unkarjedy.platformer.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.unkarjedy.platformer.model.GameLevel;
import com.unkarjedy.platformer.model.Player;
import com.unkarjedy.platformer.view.GameOverScreen;

/**
 * Created by Dima Naumenko on 02.07.2015.
 */
public class GameController implements InputProcessor, PlayerStateListner {
    private PlayerController playerController;
    private PhysicsEngine physics;
    private Game game;
    private GameRenderer gameRenderer;
    private GameLevel level;

    public GameController(Game game, GameRenderer gameRenderer, Player player, GameLevel level) {
        this.game = game;
        this.gameRenderer = gameRenderer;
        playerController = new PlayerController(player);
        playerController.setPlayerStateListner(this);
        this.level = level;

        initPhysicsEngine();
    }

    public void update(float dt) {
        checkPressedKeys();

        playerController.update(dt);

        physics.update(dt); // collisions and forces
    }

    private void checkPressedKeys() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
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
        physics = new PhysicsEngine();
        physics.setLevel(level);
//        physics.addGameObject(player);
        physics.setPlayerController(playerController);
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
    public void onPlayerDead() {
        game.setScreen(new GameOverScreen(game));
    }

    @Override
    public void onPlayerLivesDecreased() {
        gameRenderer.setDefaultPlayerPosition();
    }
}
