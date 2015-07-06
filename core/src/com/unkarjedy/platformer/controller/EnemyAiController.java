package com.unkarjedy.platformer.controller;

import com.unkarjedy.platformer.model.Enemy;
import com.unkarjedy.platformer.model.GameLevel;
import com.unkarjedy.platformer.physics.TilesCollisionDetector;
import com.unkarjedy.platformer.physics.TilesCollisionDetector.Collision;

import java.util.List;

import static com.unkarjedy.platformer.model.GameLevel.*;
import static com.unkarjedy.platformer.physics.TilesCollisionDetector.Collision.*;

/**
 * Created by Dima Naumenko on 06.07.2015.
 */
public class EnemyAiController extends PersonController {
    private Enemy enemy;

    public EnemyAiController(Enemy enemy) {
        super(enemy);
        this.enemy = enemy;
    }

    public void update(float dt) {
        move();
    }

    @Override
    public void onLevelTileCollided(LayerType type, CollideTile tile, boolean isXAxis) {
        super.onLevelTileCollided(type, tile, isXAxis);
        if(isXAxis){
            enemy.turnAround();
        }
    }
}
