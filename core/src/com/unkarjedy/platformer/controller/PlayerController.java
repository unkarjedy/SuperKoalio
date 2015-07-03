package com.unkarjedy.platformer.controller;

import com.unkarjedy.platformer.model.GameLevel;
import com.unkarjedy.platformer.model.GameObject;
import com.unkarjedy.platformer.model.Player;

/**
 * Created by Dima Naumenko on 02.07.2015.
 */
public class PlayerController extends GameObjectController {

    private Player player;
    private boolean jumpingPressed;
    private long jumpPressedTime;

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
        }
    }


    public void update(float dt) {
        if ((jumpingPressed && ((System.currentTimeMillis() - jumpPressedTime) >= Player.LONG_JUMP_PRESS))) {
            jumpingPressed = false;
        } else {
            if (jumpingPressed && player.getState().equals(Player.State.Jumping)) {
                player.getVelocity().y = Player.MAX_JUMP_SPEED;
            }
        }

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

    public void stopJump() {
        player.setState(Player.State.Falling);
        jumpingPressed = false;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void onLevelCollided(GameLevel.LayerType type) {
        if (player.getVelocity().y > 0) {
            player.setState(Player.State.Falling);
        } else {
            player.setGrounded(true);
        }
    }
}
