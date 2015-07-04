package com.unkarjedy.platformer.model;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.unkarjedy.platformer.utils.ResourceManager;

/**
 * Created by Dima Naumenko on 01.07.2015.
 */
public class GameLevel {

    private TiledMap map;
    private Music levelMusic;
    private TiledMapTileLayer backgroundLayer;
    private TiledMapTileLayer wallsLayer;
    private TiledMapTileLayer hazardsLayer;
    private Vector2 playerSpawnPosition;

    public enum LayerType {
        WALLS,
        HAZZARDS
    }

    public GameLevel(String tilemapName){
        map = new TmxMapLoader().load(ResourceManager.levelsDir + tilemapName);
        backgroundLayer = (TiledMapTileLayer) map.getLayers().get("background");
        wallsLayer = (TiledMapTileLayer) map.getLayers().get("walls");
        hazardsLayer = (TiledMapTileLayer) map.getLayers().get("hazards");

        playerSpawnPosition = new Vector2(10, 5);
//        playerSpawnPosition = new Vector2(193, 10);

        levelMusic = ResourceManager.get("level1.mp3", Music.class); //SoundPool.getSound("level1.mp3");
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

    public Music getLevelMusic() {
        return levelMusic;
    }

}


