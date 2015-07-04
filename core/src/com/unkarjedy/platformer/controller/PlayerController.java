package com.unkarjedy.platformer.controller;

import com.badlogic.gdx.audio.Sound;
import com.unkarjedy.platformer.model.Player;
import com.unkarjedy.platformer.utils.ResourceManager;

import static com.badlogic.gdx.maps.tiled.TiledMapTileLayer.*;
import static com.unkarjedy.platformer.model.GameLevel.*;

/**
 * Created by Dima Naumenko on 02.07.2015.
 */
public class PlayerController extends GameObjectController {

    private Player player;
    private boolean jumpingPressed;
    private long jumpPressedTime;

    private PlayerStateListner playerStateListner;

    private Sound jumpSound;
    private Sound hurtSound;

    private long hazardLastHit;

    {
        jumpSound = ResourceManager.get("jump.wav", Sound.class);
        hurtSound = ResourceManager.get("hurt.wav", Sound.class);
    }


    public PlayerController(Player player) {
        super(player);
        this.player = player;
    }

    public void moveLeft() {
        player.getVelocity().x = -Player.MAX_VELOCITY;
        if (player.isGrounded()) {
            player.setState(Player.State.Walking);
        }
        player.setFacesRight(false);
    }

    public void moveRight() {
        player.getVelocity().x = Player.MAX_VELOCITY;
        if (player.isGrounded()) {
            player.setState(Player.State.Walking);
        }
        player.setFacesRight(true);
    }

    public void jump() {
        Player.State state = player.getState();
        if (!state.equals(Player.State.Jumping) &&
                !state.equals(Player.State.Falling)) {
            jumpingPressed = true;
            player.setGrounded(false);
            jumpPressedTime = System.currentTimeMillis();
            player.setState(Player.State.Jumping);
            player.getVelocity().y = Player.MAX_JUMP_SPEED;

            jumpSound.play();
        }
    }


    public void update(float dt) {
        updateJumping();

        if (player.getState() != Player.State.Falling) {
            if (player.getVelocity().y < 0) {
                player.setState(Player.State.Falling);
                player.setGrounded(false);
            }
        }

        // clamp the velocity to the maximum, x-axis only
        if (Math.abs(player.getVelocity().x) > Player.MAX_VELOCITY) {
            player.getVelocity().x = Math.signum(player.getVelocity().x) * Player.MAX_VELOCITY;
        }

        // clamp the velocity to 0 if it's < 1, and set the state to standing
        if (Math.abs(player.getVelocity().x) < 1) {
            player.getVelocity().x = 0;
            if (player.isGrounded()) {
                player.setState(Player.State.Standing);
            }
        }
    }

    private void updateJumping() {
        if (!jumpingPressed)
            return;

        if (jumpButtonIsPressedTooLong()) {
            jumpingPressed = false;
        } else if (player.getState().equals(Player.State.Jumping)) {
            player.getVelocity().y = Player.MAX_JUMP_SPEED;
        }
    }

    private boolean jumpButtonIsPressedTooLong() {
        return (System.currentTimeMillis() - jumpPressedTime) >= Player.LONG_JUMP_PRESS;
    }

    public void stopJump() {
        player.setState(Player.State.Falling);
        jumpingPressed = false;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void onLevelTileCollided(LayerType type, Cell cell, boolean isXAxis) {
        if (LayerType.WALLS == type) {
            if (!isXAxis) {
                if (player.getVelocity().y > 0) {
                    player.setState(Player.State.Falling);
                } else {
                    player.setGrounded(true);
                }
            }
        }
        if (LayerType.HAZZARDS == type) {
            if (System.currentTimeMillis() - hazardLastHit > 1000) {
                hazardLastHit = System.currentTimeMillis();

                player.decreaseLives();
                hurtSound.play();

                if (player.getLives() > 0) {
                    playerStateListner.onPlayerLivesDecreased();
                } else {
                    playerStateListner.onPlayerDead();
                }
            }
        }

    }

    public void setPlayerStateListner(PlayerStateListner playerStateListner) {
        this.playerStateListner = playerStateListner;
    }
}
