package com.unkarjedy.platformer.controller.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.unkarjedy.platformer.controller.GameObjectController;
import com.unkarjedy.platformer.controller.PlayerController;
import com.unkarjedy.platformer.controller.physics.TilesCollisionDetector.Collision;
import com.unkarjedy.platformer.model.GameLevel;
import com.unkarjedy.platformer.model.GameLevel.LayerType;
import com.unkarjedy.platformer.model.GameObject;

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
    TilesCollisionDetector wallsColliderDetector;
    TilesCollisionDetector hazardsColliderDetector;
    PlayerController playerController;

    Array<Rectangle> tiles;
    Array<Cell> cells;
    List<GameObjectController> gameObjectControllers;
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

    public PhysicsEngine(GameLevel level) {
        setLevel(level);
    }

    public void update(float dt) {
        for (GameObjectController oc : gameObjectControllers) {
            oc.getObject().applyAccel(gravity, dt);
        }

        playerController.getPlayer().getVelocity().x *= DAMPING;

        resolveObjectWallsCollisions(playerController.getPlayer(), dt);
        resolvePlayerHazardsCollisions(dt);

        for (GameObjectController oc : gameObjectControllers) {
            oc.getObject().update(dt);
        }
    }

    private void resolvePlayerHazardsCollisions(float dt) {
        Collision collision = hazardsColliderDetector.detectCollisions(playerController.getPlayer(), dt);
        if(collision.hasCollision())
            playerController.onLevelTileCollided(LayerType.HAZZARDS, null, false);
    }

    private void resolveObjectWallsCollisions(GameObject obj, float dt) {
        Collision collision = wallsColliderDetector.detectCollisions(playerController.getPlayer(), dt);

        Cell xCollision = collision.getCellX();
        if(xCollision != null){
            playerController.onLevelTileCollided(LayerType.WALLS, xCollision, true);
            obj.getVelocity().x = 0;
        }

        Cell yCollision = collision.getCellY();
        if(yCollision != null){
            playerController.onLevelTileCollided(LayerType.WALLS, yCollision, false);
            if (obj.getVelocity().y > 0) {
                obj.getPosition().y = collision.getTileY().y - obj.getHeight();
            } else {
                obj.getPosition().y = collision.getTileY().y + collision.getTileY().height;
            }
            obj.getVelocity().y = 0;
        }
    }

    public void setLevel(GameLevel level) {
        this.level = level;
        wallsColliderDetector = new TilesCollisionDetector(level.getWallsLayer());
        hazardsColliderDetector = new TilesCollisionDetector(level.getHazardsLayer());
    }

    public void addGameObject(GameObjectController oc) {
        gameObjectControllers.add(oc);
    }

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
        addGameObject(playerController);
    }

}
