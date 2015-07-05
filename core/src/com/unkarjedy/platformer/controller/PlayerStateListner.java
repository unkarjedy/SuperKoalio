package com.unkarjedy.platformer.controller;

/**
 * Created by Dima Naumenko on 03.07.2015.
 */
public interface PlayerStateListner {

    void playerDead();
    void playerIsHit(boolean respawn);
    void playerGetsCoin();
    void playerReachedLevelFinish();
}
