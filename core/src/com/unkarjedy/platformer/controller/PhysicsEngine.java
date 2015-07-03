package com.unkarjedy.platformer.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.unkarjedy.platformer.model.GameLevel;
import com.unkarjedy.platformer.model.GameLevel.LayerType;
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
    Array<Rectangle> tiles;
    List<GameObjectController> gameObjectControllers;
    PlayerController playerController;
    private Pool<Rectangle> rectPool;

    {
        tiles = new Array<Rectangle>();
        gameObjectControllers = new ArrayList<>();
        rectPool = new Pool<Rectangle>() {
            @Override
            protected Rectangle newObject() {
                return new Rectangle();
            }
        };
    }

    public void update(float dt) {
        for (GameObjectController oc : gameObjectControllers) {
            oc.getObject().applyAccel(gravity, dt);
        }

        playerController.getPlayer().getVelocity().x *= Player.DAMPING;
        detectCollisions(dt, playerController, LayerType.WALLS);

        for (GameObjectController oc : gameObjectControllers) {
            oc.getObject().update(dt);
        }
    }

    private void detectCollisions(float dt, PlayerController oc, LayerType type) {
        TiledMapTileLayer layer = level.getWallsLayer();
        if(LayerType.HAZZARDS == type){
            layer = level.getHazardsLayer();
        }

        GameObject obj = oc.getObject();
        Vector2 step = obj.getVelocity().cpy().scl(dt);

        Rectangle playerRect = rectPool.obtain();
        playerRect.set(obj.getPosition().x, obj.getPosition().y, obj.getWidth(), obj.getHeight());
        int startX, startY, endX, endY; // tiles search borders

        /* X collisions  */
        if (obj.getVelocity().x > 0) {
            startX = endX = (int) (obj.getPosition().x + obj.getWidth() + step.x);
        } else {
            startX = endX = (int) (obj.getPosition().x + step.x);
        }
        startY = (int) (obj.getPosition().y);
        endY = (int) (obj.getPosition().y + obj.getHeight());
        getTiles(startX, startY, endX, endY, tiles, layer);

        playerRect.x += step.x;
        for (Rectangle tile : tiles) {
            if (playerRect.overlaps(tile)) {
                obj.getVelocity().x = 0;
                break; // MAYBE NOT BREAK
            }
        }
        playerRect.x -= step.x;


        /* Y collisions  */
        if (obj.getVelocity().y > 0) {
            startY = endY = (int) (obj.getPosition().y + obj.getHeight() + step.y);
        } else {
            startY = endY = (int) (obj.getPosition().y + step.y);
        }

        startX = (int) (obj.getPosition().x);
        endX = (int) (obj.getPosition().x + obj.getWidth());
        getTiles(startX, startY, endX, endY, tiles, layer);
        playerRect.y += step.y;
        for (Rectangle tile : tiles) {
            if (playerRect.overlaps(tile)) {
                oc.onLevelCollided(type);

                if (obj.getVelocity().y > 0) {
                    obj.getPosition().y = tile.y - obj.getHeight();
                } else {
                    obj.getPosition().y = tile.y + tile.height;
                }
                obj.getVelocity().y = 0;
                break;
            }
        }
    }

    public void setLevel(GameLevel level) {
        this.level = level;
    }

    public void addGameObject(GameObjectController oc) {
        gameObjectControllers.add(oc);
    }

    public void setPlayer(PlayerController playerController) {
        this.playerController = playerController;
        addGameObject(playerController);
    }

    private void getTiles(int startX, int startY, int endX, int endY, Array<Rectangle> tiles, TiledMapTileLayer layer) {
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
