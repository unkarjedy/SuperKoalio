package com.unkarjedy.platformer.controller;

import com.unkarjedy.platformer.model.GameLevel;

/**
 * Created by Dima Naumenko on 03.07.2015.
 */
public interface CollisionListner {

    void onLevelCollided(GameLevel.LayerType type);

}
