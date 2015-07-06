package com.unkarjedy.platformer.model;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.unkarjedy.platformer.utils.ResourceManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Dima Naumenko on 01.07.2015.
 */
public class GameLevel {

    private TiledMap map;
    private Music levelMusic;

    private TiledMapTileLayer backgroundLayer;
    private TiledMapTileLayer wallsLayer;
    private TiledMapTileLayer hazardsLayer;
    private TiledMapTileLayer starsLayer;

    private Vector2 playerSpawnPosition;
    private Rectangle finish;
    private float unitScale;

    public TiledMapTileLayer getStarsLayer() {
        return starsLayer;
    }

    public Rectangle getFinish() {
        return finish;
    }

    public enum LayerType {
        WALLS,
        HAZZARDS,
        STARS
    }

    public GameLevel(String tilemapName, float unitScale) {
        this.unitScale = unitScale;
        map = new TmxMapLoader().load(ResourceManager.levelsDir + tilemapName);

        extractLayers();
        extractPlayerSpawnPosition(unitScale);

        levelMusic = ResourceManager.get("level1.mp3", Music.class);
    }

    private void extractPlayerSpawnPosition(float unitScale) {
        playerSpawnPosition = new Vector2(5, 5);
        try {
            MapObject mapObject = map.getLayers().get("meta_objects").getObjects().get("start");
            playerSpawnPosition.x = (float) mapObject.getProperties().get("x");
            playerSpawnPosition.y = (float) mapObject.getProperties().get("y");
            playerSpawnPosition.scl(unitScale);

            mapObject = map.getLayers().get("meta_objects").getObjects().get("finish");
            finish = getRect(mapObject, unitScale);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        playerSpawnPosition = new Vector2(193, 10);
    }

    private void extractLayers() {
        backgroundLayer = (TiledMapTileLayer) map.getLayers().get("background");
        wallsLayer = (TiledMapTileLayer) map.getLayers().get("walls");
        hazardsLayer = (TiledMapTileLayer) map.getLayers().get("hazards");
        starsLayer = (TiledMapTileLayer) map.getLayers().get("stars");
    }

    private Rectangle getRect(MapObject mapObject, float unitScale) {
        Rectangle rect = new Rectangle();
        rect.x = (float) mapObject.getProperties().get("x") * unitScale;
        rect.y = (float) mapObject.getProperties().get("y") * unitScale;
        rect.width = (float) mapObject.getProperties().get("width") * unitScale;
        rect.height = (float) mapObject.getProperties().get("height") * unitScale;

        return rect;
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


    public int getStarsAmount() {
        int stars = 0;
        for (int x = 0; x < starsLayer.getWidth(); x++) {
            for (int y = 0; y < starsLayer.getHeight(); y++) {
                if (starsLayer.getCell(x, y) != null)
                    stars++;
            }
        }
        return stars;
    }


    public List<Vector2> getEnemiesSpawnPositions() {
        MapLayer enemySpawnsLayer = map.getLayers().get("enemy_spawns");
        List<Vector2> enemySpawns = new ArrayList<>();
        for (MapObject mapObject : enemySpawnsLayer.getObjects()) {
            Vector2 enemyPos = new Vector2();
            ((RectangleMapObject) mapObject).getRectangle().getCenter(enemyPos);
            enemyPos.scl(unitScale);
            enemySpawns.add(enemyPos);
        }
        return enemySpawns;
    }

}


