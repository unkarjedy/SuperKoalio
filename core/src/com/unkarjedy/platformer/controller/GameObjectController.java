package com.unkarjedy.platformer.controller;

import com.unkarjedy.platformer.model.GameLevel;
import com.unkarjedy.platformer.model.GameObject;

import java.util.Collection;

/**
 * Created by Dima Naumenko on 03.07.2015.
 */
public abstract class GameObjectController implements CollisionListner {
    GameObject obj;

    public GameObjectController(GameObject obj) {
        this.obj = obj;
    }

    final public GameObject getObject() {
        return obj;
    };

}
