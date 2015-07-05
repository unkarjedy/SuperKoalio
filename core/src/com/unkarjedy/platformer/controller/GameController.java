package com.unkarjedy.platformer.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.unkarjedy.platformer.PlatformerGame;
import com.unkarjedy.platformer.model.GameLevel;
import com.unkarjedy.platformer.model.Player;
import com.unkarjedy.platformer.controller.physics.PhysicsEngine;
import com.unkarjedy.platformer.view.GameOverScreen;
import com.unkarjedy.platformer.view.PlayScreen;

/**
 * Created by Dima Naumenko on 02.07.2015.
 */
public class GameController implements InputProcessor, PlayerStateListner {
    private PlayerController playerController;
    private PhysicsEngine physics;
    private PlatformerGame game;
    private PlayScreen playScreen;
    private GameLevel level;

    public GameController(PlatformerGame game, PlayScreen playScreen, Player player, GameLevel level) {
        this.game = game;
        this.playScreen = playScreen;
        playerController = new PlayerController(player);
        playerController.setPlayerStateListner(this);
        this.level = level;

        initPhysicsEngine();
    }

    public void update(float dt) {
        checkPressedKeys();

        playerController.update(dt);
        clipPlayerPOsitionX();
        checkIfPlayerFellInHall();

        physics.update(dt); // collisions and forces
    }

    private void checkIfPlayerFellInHall() {
        if(playerController.getPlayer().getPosition().y < 0){
            playerController.playerIsHit();
        }
    }

    private void clipPlayerPOsitionX() {
        Vector2 playerPosition = playerController.getPlayer().getPosition();

        float playerLeftClip = 0;
        float playerRightClip = level.getWallsLayer().getWidth() - playerController.getPlayer().getWidth();

        if (playerPosition.x < playerLeftClip) {
            playerPosition.x = playerLeftClip;
            playerController.getPlayer().getVelocity().x = 0;
        }
        if (playerPosition.x > playerRightClip) {
            playerPosition.x = playerRightClip;
            playerController.getPlayer().getVelocity().x = 0;
        }
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
        physics = new PhysicsEngine(level);
        //physics.setLevel(level);
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
        playScreen.setDefaultPlayerPosition();
    }
}
