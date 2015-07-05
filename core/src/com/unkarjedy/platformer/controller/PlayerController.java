package com.unkarjedy.platformer.controller;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.unkarjedy.platformer.controller.physics.TilesCollisionDetector;
import com.unkarjedy.platformer.model.GameLevel;
import com.unkarjedy.platformer.model.Player;
import com.unkarjedy.platformer.utils.ResourceManager;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.maps.tiled.TiledMapTileLayer.*;
import static com.unkarjedy.platformer.controller.physics.TilesCollisionDetector.*;
import static com.unkarjedy.platformer.controller.physics.TilesCollisionDetector.Collision.*;
import static com.unkarjedy.platformer.model.GameLevel.*;
import static com.unkarjedy.platformer.model.GameLevel.LayerType.*;
import static com.unkarjedy.platformer.model.GameLevel.LayerType.HAZZARDS;

/**
 * Created by Dima Naumenko on 02.07.2015.
 */
public class PlayerController extends GameObjectController {

    private Player player;
    private GameLevel level;
    private boolean jumpingPressed;
    private long jumpPressedTime;

    private List<PlayerStateListner> playerStateListners = new ArrayList<>();

    TilesCollisionDetector hazardsColliderDetector;
    TilesCollisionDetector starsColliderDetector;

    private Sound jumpSound;
    private Sound hurtSound;
    private Sound starCollected;

    private long hazardLastHit;
    private Cell lastHazzardCell;

    {
        jumpSound = ResourceManager.get("jump.wav", Sound.class);
        hurtSound = ResourceManager.get("hurt.wav", Sound.class);
        starCollected = ResourceManager.get("coin.wav", Sound.class);
    }


    public PlayerController(Player player, GameLevel level) {
        super(player);
        this.player = player;
        this.level = level;

        hazardsColliderDetector = new TilesCollisionDetector(level.getHazardsLayer());
        starsColliderDetector = new TilesCollisionDetector(level.getStarsLayer());
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
        checkIfIsfalling();
        clampVelocities();

        resolvePlayerHazardsCollisions(dt);
        resolvePlayerStarsCollisions(dt);

        clipPlayerLevelPosition();
        checkIfPlayerFellInHall();

        checkIfFinishReached();
    }

    private void checkIfFinishReached() {
        if(level.getFinish().overlaps(player.getBoundingRect())){
            for(PlayerStateListner listner : playerStateListners){
                listner.playerReachedLevelFinish();
            }
        }
    }


    private void checkIfPlayerFellInHall() {
        if(player.getPosition().y < 0){
            playerIsHit(true);
        }
    }

    private void clipPlayerLevelPosition() {
        Vector2 playerPosition = player.getPosition();

        float playerLeftClip = 0;
        float playerRightClip = level.getWallsLayer().getWidth() - player.getWidth();

        if (playerPosition.x < playerLeftClip) {
            playerPosition.x = playerLeftClip;
            player.getVelocity().x = 0;
        }
        if (playerPosition.x > playerRightClip) {
            playerPosition.x = playerRightClip;
            player.getVelocity().x = 0;
        }
    }

    private void clampVelocities() {
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

    private void checkIfIsfalling() {
        if (player.getState() != Player.State.Falling) {
            if (player.getVelocity().y < 0) {
                player.setState(Player.State.Falling);
                player.setGrounded(false);
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
    public void onLevelTileCollided(LayerType type, CollideTile tile, boolean isXAxis) {
        if (WALLS == type) {
            if (!isXAxis) {
                if (player.getVelocity().y > 0) {
                    player.setState(Player.State.Falling);
                } else {
                    player.setGrounded(true);
                }
            }
        }
        if (HAZZARDS == type) {
            if (System.currentTimeMillis() - hazardLastHit > 1000 ||
                    tile.cell != lastHazzardCell) {
                hazardLastHit = System.currentTimeMillis();
                lastHazzardCell = tile.cell;
                playerIsHit(false);
            }
        }

        if (STARS == type) {
            level.getStarsLayer().setCell((int) tile.rect.x, (int) tile.rect.y, null);
            starCollected.play();
            for(PlayerStateListner playerStateListner : playerStateListners)
                playerStateListner.playerGetsCoin();
        }

    }

    public void playerIsHit(boolean respawn) {
        player.decreaseLives();
        hurtSound.play();

        if (player.getLives() > 0) {
            for(PlayerStateListner playerStateListner : playerStateListners)
                playerStateListner.playerIsHit(respawn);
        } else {
            for(PlayerStateListner playerStateListner : playerStateListners)
                playerStateListner.playerDead();
        }
    }

    public void addPlayerStateListner(PlayerStateListner playerStateListner) {
        playerStateListners.add(playerStateListner);
    }


    private void resolvePlayerHazardsCollisions(float dt) {
        Collision collision = hazardsColliderDetector.detectCollisions(player, dt);
        if(collision.hasCollision())
            onLevelTileCollided(HAZZARDS, collision.getAnyTile(), false);
    }


    private void resolvePlayerStarsCollisions(float dt) {
        Collision collision = starsColliderDetector.detectCollisions(player, dt);
        if(collision.hasCollision())
            onLevelTileCollided(STARS, collision.getAnyTile(), false);
    }
}
