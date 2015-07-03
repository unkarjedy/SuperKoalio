package com.unkarjedy.platformer.controller;

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

import static com.badlogic.gdx.maps.tiled.TiledMapTileLayer.*;

/**
 * Created by Dima Naumenko on 01.07.2015.
 */
public class PhysicsEngine {
    Vector2 gravity = new Vector2(0, -42);
    float DAMPING = 0.87f;

    GameLevel level;
    Array<Rectangle> tiles;
    Array<Cell> cells;
    List<GameObjectController> gameObjectControllers;
    PlayerController playerController;

    private Pool<Rectangle> rectPool;
    {
        tiles = new Array<Rectangle>();
        cells  = new Array<Cell>();
        gameObjectControllers = new ArrayList<>();

        rectPool = new Pool<Rectangle>() {
            @Override
            protected Rectangle newObject() {
                return new Rectangle();
            }
        };
        rectPool.peak = 10;
    }

    public void update(float dt) {
        for (GameObjectController oc : gameObjectControllers) {
            oc.getObject().applyAccel(gravity, dt);
        }

        playerController.getPlayer().getVelocity().x *= DAMPING;
        detectCollisions(dt, playerController, LayerType.WALLS, true);
        detectCollisions(dt, playerController, LayerType.HAZZARDS, false);

        for (GameObjectController oc : gameObjectControllers) {
            oc.getObject().update(dt);
        }
    }

    private void detectCollisions(float dt, GameObjectController oc, LayerType type, boolean isPhysical) {
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
        getTiles(startX, startY, endX, endY, tiles, cells, layer);

        playerRect.x += step.x;

        Rectangle tile;
        for (int tileId = 0; tileId < tiles.size; tileId++) {
            tile = tiles.get(tileId);
            if (playerRect.overlaps(tile)) {
                oc.onLevelTileCollided(type, cells.get(tileId), true);
                if(isPhysical){
                    obj.getVelocity().x = 0;
                }
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
        getTiles(startX, startY, endX, endY, tiles, cells, layer);
        playerRect.y += step.y;
        for (int tileId = 0; tileId < tiles.size; tileId++) {
            tile = tiles.get(tileId);
            if (playerRect.overlaps(tile)) {
                oc.onLevelTileCollided(type, cells.get(tileId), false);
                if(isPhysical){
                    if (obj.getVelocity().y > 0) {
                        obj.getPosition().y = tile.y - obj.getHeight();
                    } else {
                        obj.getPosition().y = tile.y + tile.height;
                    }
                    obj.getVelocity().y = 0;
                }
                break;  // MAYBE NOT BREAK
            }
        }
    }

    public void setLevel(GameLevel level) {
        this.level = level;
    }

    public void addGameObject(GameObjectController oc) {
        gameObjectControllers.add(oc);
    }

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
        addGameObject(playerController);
    }

    private void getTiles(int startX, int startY, int endX, int endY, Array<Rectangle> tiles, Array<Cell> cells, TiledMapTileLayer layer) {
        rectPool.freeAll(tiles);
        tiles.clear();
        cells.clear();
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    Rectangle rect = rectPool.obtain();
                    rect.set(x, y, 1, 1);
                    tiles.add(rect);
                    cells.add(cell);
                }
            }
        }
    }

}
