package com.unkarjedy.platformer.controller;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.unkarjedy.platformer.model.GameLevel;

/**
 * Created by Dima Naumenko on 03.07.2015.
 */
public interface CollisionListner {

    void onLevelTileCollided(GameLevel.LayerType type, TiledMapTileLayer.Cell tile, boolean isXAxis);

}
