package com.unkarjedy.platformer.controller.physics;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.unkarjedy.platformer.model.GameObject;

import static com.badlogic.gdx.maps.tiled.TiledMapTileLayer.*;

/**
 * Created by Dima Naumenko on 05.07.2015.
 */
public class TilesCollisionDetector {

    private TiledMapTileLayer layer;

    private Array<Rectangle> tiles;
    private Array<TiledMapTileLayer.Cell> cells;
    private Collision colision;
    private Pool<Rectangle> rectPool;

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
                    colision.cells[Collision.RIGHT] = cells.get(tileId);
                    colision.tiles[Collision.RIGHT] = tile;
                } else {
                    colision.cells[Collision.LEFT] = cells.get(tileId);
                    colision.tiles[Collision.LEFT] = tile;
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
                    colision.cells[Collision.UP] = cells.get(tileId);
                    colision.tiles[Collision.UP] = tile;
                } else {
                    colision.cells[Collision.DOWN] = cells.get(tileId);
                    colision.tiles[Collision.DOWN] = tile;
                }
                break;  // MAYBE NOT BREAK
            }
        }

        return colision;
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
        public static final int UP = 0;
        public static final int DOWN = 1;
        public static final int LEFT = 2;
        public static final int RIGHT = 3;

        public Cell[] cells;
        public Rectangle[] tiles;


        public Collision() {
            cells = new Cell[4];
            tiles = new Rectangle[4];
        }

        public void clear() {
            for (int i = 0; i < cells.length; i++) {
                cells[i] = null;
                tiles[i] = null;
            }
        }

        Cell getCell(int cellId) {
            return cells[cellId];
        }

        ;

        public Cell getCellX() {
            return cells[LEFT] != null ? cells[LEFT] : cells[RIGHT];
        }

        public Cell getCellY() {
            return cells[UP] != null ? cells[UP] : cells[DOWN];
        }

        public Rectangle getTileX() {
            return tiles[LEFT] != null ? tiles[LEFT] : tiles[RIGHT];
        }

        public Rectangle getTileY() {
            return tiles[UP] != null ? tiles[UP] : tiles[DOWN];
        }

        boolean hasCollision() {
            for (Cell cell : cells)
                if (cell != null)
                    return true;
            return false;
        }
    }


}
