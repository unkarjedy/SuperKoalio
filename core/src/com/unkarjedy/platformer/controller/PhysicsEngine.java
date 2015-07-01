package com.unkarjedy.platformer.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.unkarjedy.platformer.model.GameLevel;
import com.unkarjedy.platformer.model.GameObject;
import com.unkarjedy.platformer.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dima Naumenko on 01.07.2015.
 */
public class PhysicsEngine {

    Vector2 gravity = new Vector2(0, -42);

    GameLevel level;
    Array<Rectangle> tiles = new Array<Rectangle>();

    List<GameObject> gameObjects = new ArrayList<>();
    Player player;

    private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject() {
            return new Rectangle();
        }
    };

    public void update(float dt) {
        for (GameObject obj : gameObjects) {
            obj.applyAccel(gravity, dt);
            obj.update(dt);
        }

        player.applyAccel(gravity, dt);
        player.getVelocity().x *= Player.DAMPING;

        detectCollisions(dt);

        player.update(dt);
    }

    private void detectCollisions(float dt) {

        Vector2 step = player.getVelocity().cpy().scl(dt);

        Rectangle playerRect = rectPool.obtain();
        playerRect.set(player.getPosition().x, player.getPosition().y, player.getWidth(), player.getHeight());

        int startX, startY, endX, endY;

        /* X collisions  */
        if (player.getVelocity().x > 0) {
            startX = endX = (int) (player.getPosition().x + player.getWidth() + step.x);
        } else {
            startX = endX = (int) (player.getPosition().x + step.x);
        }
        startY = (int) (player.getPosition().y);
        endY = (int) (player.getPosition().y + player.getHeight());
        getTiles(startX, startY, endX, endY, tiles);

        playerRect.x += step.x;
        for (Rectangle tile : tiles) {
            if (playerRect.overlaps(tile)) {
                player.getVelocity().x = 0;
                break;
            }
        }
        playerRect.x -= step.x;


        /* Y collisions  */
        if (player.getVelocity().y > 0) {
            startY = endY = (int) (player.getPosition().y + player.getHeight() + step.y);
        } else {
            startY = endY = (int) (player.getPosition().y + step.y);
        }

        startX = (int) (player.getPosition().x);
        endX = (int) (player.getPosition().x + player.getWidth());
        getTiles(startX, startY, endX, endY, tiles);
        playerRect.y += step.y;
        for (Rectangle tile : tiles) {
            if (playerRect.overlaps(tile)) {
                if (player.getVelocity().y > 0) {
                    player.getPosition().y = tile.y - player.getHeight();
                    player.setState(Player.State.Falling);
                } else {
                    player.getPosition().y = tile.y + tile.height;
                    player.setGrounded(true);
                }
                player.getVelocity().y = 0;
                break;
            }
        }
    }

    public void setLevel(GameLevel level) {
        this.level = level;
    }

    public void addGameObject(Player player) {
        gameObjects.add(player);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private void getTiles(int startX, int startY, int endX, int endY, Array<Rectangle> tiles) {
        TiledMapTileLayer layer = (TiledMapTileLayer) level.getMap().getLayers().get(1);
        rectPool.freeAll(tiles);
        tiles.clear();
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    Rectangle rect = rectPool.obtain();
                    rect.set(x, y, 1, 1);
                    tiles.add(rect);
                }
            }
        }
    }
}
