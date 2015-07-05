package com.unkarjedy.platformer.controller;

import com.unkarjedy.platformer.model.GameObject;
import com.unkarjedy.platformer.controller.physics.CollisionListner;

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
