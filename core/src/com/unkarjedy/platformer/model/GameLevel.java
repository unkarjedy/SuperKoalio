package com.unkarjedy.platformer.model;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Created by Dima Naumenko on 01.07.2015.
 */
public class GameLevel {
    private TiledMap map;
    TiledMapTileLayer backgroundLayer;
    TiledMapTileLayer wallsLayer;
    TiledMapTileLayer hazardsLayer;

    public enum LayerType {
        WALLS,
        HAZZARDS
    }

    public GameLevel(String tilemapName){
        map = new TmxMapLoader().load(tilemapName);
        backgroundLayer = (TiledMapTileLayer) map.getLayers().get("background");
        wallsLayer = (TiledMapTileLayer) map.getLayers().get("walls");
        hazardsLayer = (TiledMapTileLayer) map.getLayers().get("hazards");
    }

    public TiledMap getMap() {
        return map;
    }

    public TiledMapTileLayer getBackgroundLayer() {
        return backgroundLayer;
    }

    public TiledMapTileLayer getWallsLayer() {
        return wallsLayer;
    }

    public TiledMapTileLayer getHazardsLayer() {
        return hazardsLayer;
    }
}


