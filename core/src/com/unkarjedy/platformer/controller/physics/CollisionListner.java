package com.unkarjedy.platformer.controller.physics;

import com.unkarjedy.platformer.model.GameLevel.LayerType;

import static com.badlogic.gdx.maps.tiled.TiledMapTileLayer.*;

/**
 * Created by Dima Naumenko on 03.07.2015.
 */
public interface CollisionListner {

    void onLevelTileCollided(LayerType type, Cell tile, boolean isXAxis);

}
