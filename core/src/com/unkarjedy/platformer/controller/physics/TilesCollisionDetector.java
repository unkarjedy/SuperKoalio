package com.unkarjedy.platformer.controller.physics;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.unkarjedy.platformer.controller.physics.TilesCollisionDetector.Collision.CollideTile;
import com.unkarjedy.platformer.model.GameObject;

import static com.badlogic.gdx.maps.tiled.TiledMapTileLayer.*;
import static com.unkarjedy.platformer.controller.physics.TilesCollisionDetector.Collision.*;
import static com.unkarjedy.platformer.controller.physics.TilesCollisionDetector.Collision.RIGHT;

/**
 * Created by Dima Naumenko on 05.07.2015.
 */
public class TilesCollisionDetector {

    private TiledMapTileLayer layer;

    private Array<Rectangle> tiles;
    private Array<TiledMapTileLayer.Cell> cells;
    private Collision colision;
    private Pool<Rectangle> rectPool;
    private Pool<CollideTile> collideTilePool;

    {
        tiles = new Array<Rectangle>();
        cells = new Array<TiledMapTileLayer.Cell>();
        colision = new Collision();
        rectPool = new Pool<Rectangle>() {
            @Override
            protected Rectangle newObject() {
                return new Rectangle();
            }
        };
        rectPool.peak = 8;

        collideTilePool =  new Pool<CollideTile>() {
            @Override
            protected CollideTile newObject() {
                return new CollideTile();
            }
        };
        collideTilePool.peak = 8;
    }


    public TilesCollisionDetector(TiledMapTileLayer layer) {
        this.layer = layer;
    }


    public Collision detectCollisions(GameObject obj, float dt) {
        colision.clear();

        Vector2 step = obj.getVelocity().cpy().scl(dt);

        Rectangle objRect = rectPool.obtain();
        objRect.set(obj.getPosition().x, obj.getPosition().y, obj.getWidth(), obj.getHeight());
        int startX, startY, endX, endY; // tiles search borders

        /* X collisions  */
        if (obj.getVelocity().x > 0) {
            startX = endX = (int) (obj.getPosition().x + obj.getWidth() + step.x);
        } else {
            startX = endX = (int) (obj.getPosition().x + step.x);
        }
        startY = (int) (obj.getPosition().y);
        endY = (int) (obj.getPosition().y + obj.getHeight());
        getTiles(startX, startY, endX, endY, tiles, cells, layer);

        objRect.x += step.x;

        Rectangle tile;
        for (int tileId = 0; tileId < tiles.size; tileId++) {
            tile = tiles.get(tileId);
            if (objRect.overlaps(tile)) {
                if (obj.getVelocity().x > 0) {
                    setCollidedTile(RIGHT, tileId);
                } else {
                    setCollidedTile(LEFT, tileId);
                }
                break; // MAYBE NOT BREAK
            }
        }
        objRect.x -= step.x;


        /* Y collisions  */
        if (obj.getVelocity().y > 0) {
            startY = endY = (int) (obj.getPosition().y + obj.getHeight() + step.y);
        } else {
            startY = endY = (int) (obj.getPosition().y + step.y);
        }

        startX = (int) (obj.getPosition().x);
        endX = (int) (obj.getPosition().x + obj.getWidth());
        getTiles(startX, startY, endX, endY, tiles, cells, layer);
        objRect.y += step.y;
        for (int tileId = 0; tileId < tiles.size; tileId++) {
            tile = tiles.get(tileId);
            if (objRect.overlaps(tile)) {
                if (obj.getVelocity().y > 0) {
                    setCollidedTile(UP, tileId);
                } else {
                    setCollidedTile(DOWN, tileId);
                }
                break;  // MAYBE NOT BREAK
            }
        }

        return colision;
    }

    private void setCollidedTile(int collideTileId, int tileId) {
        colision.tiles[collideTileId] = collideTilePool.obtain();
        colision.tiles[collideTileId].cell = cells.get(tileId);
        colision.tiles[collideTileId].rect = tiles.get(tileId);
    }

    private void getTiles(int startX, int startY, int endX, int endY, Array<Rectangle> tiles, Array<TiledMapTileLayer.Cell> cells, TiledMapTileLayer layer) {
        rectPool.freeAll(tiles);
        tiles.clear();
        cells.clear();
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    Rectangle rect = rectPool.obtain();
                    rect.set(x, y, 1, 1);
                    tiles.add(rect);
                    cells.add(cell);
                }
            }
        }
    }


    public static class Collision {

        public static class CollideTile {
            public Cell cell = new Cell();
            public Rectangle rect = new Rectangle();
        }

        public static final int UP = 0;
        public static final int DOWN = 1;
        public static final int LEFT = 2;
        public static final int RIGHT = 3;

        public CollideTile [] tiles;

        public Collision() {
            tiles = new CollideTile [4];
        }

        public void clear() {
            for (int i = 0; i < tiles.length; i++) {
                tiles[i] = null;
            }
        }

        CollideTile getTile(int cellId) {
            return tiles[cellId];
        }


        public CollideTile getTileX() {
            return tiles[LEFT] != null ? tiles[LEFT] : tiles[RIGHT];
        }

        public CollideTile getTileY() {
            return tiles[UP] != null ? tiles[UP] : tiles[DOWN];
        }

        public boolean hasCollision() {
            for (CollideTile tile: tiles)
                if (tile != null)
                    return true;
            return false;
        }

        public CollideTile getAnyTile() {
            for(CollideTile  tile : tiles){
                if(tile != null)
                    return tile;
            }
            return null;
        }
    }


}
