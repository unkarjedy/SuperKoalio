package com.unkarjedy.platformer.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Created by Dima Naumenko on 01.07.2015.
 */
public class GameLevel {
    private TiledMap map;

    public GameLevel(String tilemapName){
        map = new TmxMapLoader().load(tilemapName);
    }

    public TiledMap getMap() {
        return map;
    }
}


