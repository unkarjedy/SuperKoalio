package com.unkarjedy.platformer.controller;

import com.badlogic.gdx.graphics.Camera;
import com.unkarjedy.platformer.model.GameLevel;
import com.unkarjedy.platformer.model.Player;

/**
 * Created by Dima Naumenko on 04.07.2015.
 */
public class CameraController {

    private final Camera camera;
    private final Player player;
    private final GameLevel level;

    public CameraController(Camera camera, Player player, GameLevel level) {
        this.camera = camera;
        this.player = player;
        this.level = level;
    }

    public void update(float dt) {
        followPlayer();
        clipCameraPositionX();

        camera.update();
    }

    private void clipCameraPositionX() {
        float camLeftClip = camera.viewportWidth / 2;
        float camRightClip = level.getWallsLayer().getWidth() - camLeftClip;

        if (camera.position.x < camLeftClip) {
            camera.position.x = camLeftClip;
        }
        if (camera.position.x > camRightClip) {
            camera.position.x = camRightClip;
        }
    }

    private void followPlayer() {
        camera.position.x = player.getPosition().x;
    }

}
