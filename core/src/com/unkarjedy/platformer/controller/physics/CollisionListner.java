package com.unkarjedy.platformer.controller.physics;

import com.badlogic.gdx.math.Rectangle;
import com.unkarjedy.platformer.model.GameLevel.LayerType;

import static com.badlogic.gdx.maps.tiled.TiledMapTileLayer.*;
import static com.unkarjedy.platformer.controller.physics.TilesCollisionDetector.*;
import static com.unkarjedy.platformer.controller.physics.TilesCollisionDetector.Collision.*;

/**
 * Created by Dima Naumenko on 03.07.2015.
 */
public interface CollisionListner {

    void onLevelTileCollided(LayerType type, CollideTile tile, boolean isXAxis);

}
