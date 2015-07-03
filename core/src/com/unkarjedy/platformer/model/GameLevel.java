package com.unkarjedy.platformer.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Dima Naumenko on 01.07.2015.
 */
public class GameLevel {
    private TiledMap map;
    private Sound levelSound;
    private TiledMapTileLayer backgroundLayer;
    private TiledMapTileLayer wallsLayer;
    private TiledMapTileLayer hazardsLayer;
    private Vector2 playerSpawnPosition;

    public enum LayerType {
        WALLS,
        HAZZARDS
    }

    public GameLevel(String tilemapName){
        map = new TmxMapLoader().load(tilemapName);
        playerSpawnPosition = new Vector2(10, 5);
        backgroundLayer = (TiledMapTileLayer) map.getLayers().get("background");
        wallsLayer = (TiledMapTileLayer) map.getLayers().get("walls");
        hazardsLayer = (TiledMapTileLayer) map.getLayers().get("hazards");

        FileHandle soundFile = Gdx.files.internal("level1.mp3");
        levelSound = Gdx.audio.newSound(soundFile);
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

    public Vector2 getPlayerSpawnPosition() {
        return playerSpawnPosition.cpy();
    }

    public Sound getLevelSound() {
        return levelSound;
    }

}


