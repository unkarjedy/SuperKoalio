package com.unkarjedy.platformer.physics;

import com.unkarjedy.platformer.model.GameLevel.LayerType;

import static com.unkarjedy.platformer.physics.TilesCollisionDetector.Collision.*;

/**
 * Created by Dima Naumenko on 03.07.2015.
 */
public interface CollisionListner {

    void onLevelTileCollided(LayerType type, CollideTile tile, boolean isXAxis);

}
