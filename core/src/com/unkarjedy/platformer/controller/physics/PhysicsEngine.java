package com.unkarjedy.platformer.controller.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.unkarjedy.platformer.controller.GameObjectController;
import com.unkarjedy.platformer.controller.PlayerController;
import com.unkarjedy.platformer.controller.physics.TilesCollisionDetector.Collision;
import com.unkarjedy.platformer.controller.physics.TilesCollisionDetector.Collision.CollideTile;
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
        applyForces(dt);

        playerController.getPlayer().getVelocity().x *= DAMPING;
        resolveObjectWallsCollisions(playerController.getPlayer(), dt);

        applyMovements(dt);
    }

    private void applyMovements(float dt) {
        for (GameObjectController oc : gameObjectControllers) {
            oc.getObject().update(dt);
        }
    }

    private void applyForces(float dt) {
        for (GameObjectController oc : gameObjectControllers) {
            oc.getObject().applyAccel(gravity, dt);
        }
    }

    private void resolveObjectWallsCollisions(GameObject obj, float dt) {
        Collision collision = wallsColliderDetector.detectCollisions(playerController.getPlayer(), dt);

        CollideTile xCollision = collision.getTileX();
        if(xCollision != null){
            playerController.onLevelTileCollided(LayerType.WALLS, xCollision, true);
            obj.getVelocity().x = 0;
        }

        CollideTile yCollision = collision.getTileY();
        if(yCollision != null){
            playerController.onLevelTileCollided(LayerType.WALLS, yCollision, false);
            if (obj.getVelocity().y > 0) {
                obj.getPosition().y = collision.getTileY().rect.y - obj.getHeight();
            } else {
                obj.getPosition().y = collision.getTileY().rect.y + collision.getTileY().rect.height;
            }
            obj.getVelocity().y = 0;
        }
    }

    public void setLevel(GameLevel level) {
        this.level = level;
        wallsColliderDetector = new TilesCollisionDetector(level.getWallsLayer());
    }

    public void addGameObject(GameObjectController oc) {
        gameObjectControllers.add(oc);
    }

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
        addGameObject(playerController);
    }

}
