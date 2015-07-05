package com.unkarjedy.platformer.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by Dima Naumenko on 05.07.2015.
 */
public class MemoryLogger {
    long startTime;

    public MemoryLogger() {
        startTime = TimeUtils.nanoTime();
    }

    /** Logs the current memory usage second to the console. */
    public void log () {
        if (TimeUtils.nanoTime() - startTime > 1000000000){
            Gdx.app.log("MemoryLogger", "memory usage: " +
                    (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 /1024 + "Mb" );
            startTime = TimeUtils.nanoTime();
        }
    }
}
