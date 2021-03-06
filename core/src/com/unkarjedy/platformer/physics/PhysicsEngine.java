package com.unkarjedy.platformer.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.unkarjedy.platformer.controller.GameObjectController;
import com.unkarjedy.platformer.controller.PlayerController;
import com.unkarjedy.platformer.physics.TilesCollisionDetector.Collision;
import com.unkarjedy.platformer.physics.TilesCollisionDetector.Collision.CollideTile;
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

        for(GameObjectController controller : gameObjectControllers){
            resolveObjectWallsCollisions(controller, dt);
            resolveObjectObjectCollisions(controller, dt);
        }

        applyMovements(dt);
    }

    private void resolveObjectObjectCollisions(GameObjectController controller, float dt) {
        Collision collision;
        for(GameObjectController secondController : gameObjectControllers){
            if(secondController == controller)
                continue;

            if(controller.getObject().getBoundingRect()
                    .overlaps(secondController.getObject().getBoundingRect())){
                // TODO
            }
        }
    }

    private void applyForces(float dt) {
        for (GameObjectController oc : gameObjectControllers) {
            oc.getObject().applyAccel(gravity, dt);
        }
    }

    private void applyMovements(float dt) {
        for (GameObjectController oc : gameObjectControllers) {
            oc.getObject().update(dt);
        }
    }
    private void resolveObjectWallsCollisions(GameObjectController oc,  float dt) {
        GameObject obj = oc.getObject();
        Collision collision = wallsColliderDetector.detectCollisions(obj, dt);

        CollideTile xCollision = collision.getTileX();
        if(xCollision != null){
            oc.onLevelTileCollided(LayerType.WALLS, xCollision, true);
            obj.getVelocity().x = 0;
        }

        CollideTile yCollision = collision.getTileY();
        if(yCollision != null){
            oc.onLevelTileCollided(LayerType.WALLS, yCollision, false);
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

    public void addGameObjectController(GameObjectController oc) {
        gameObjectControllers.add(oc);
    }

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
        addGameObjectController(playerController);
    }

}
