package com.unkarjedy.platformer.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Dima Naumenko on 04.07.2015.
 */
public class ResourceManager {

    private static final AssetManager manager = new AssetManager();

    public static final String texturesDir = "textures/";
    public static final String soundsDir = "sounds/";
    public static final String musicDir = soundsDir;
    public static final String fontsDir = "fonts/";
    public static final String levelsDir = "levels/";

    public static void load() {
        loadTextures();
        loadSounds();
        loadMusics();
        loadFonts();
    }


    private static void loadTextures() {
       String[] textureNames = {
               "Koalaabilitylol.png",
               "heart.png",
               "jungle.jpg",
               "koalio.png"
       };

        for (String fileName : textureNames) {
            manager.load(texturesDir + fileName, Texture.class);
        }
    }

    private static void loadSounds() {
        String[] soundNames = {
                "jump.wav",
                "hurt.wav",
                "coin.wav"
        };

        for (String fileName : soundNames) {
            manager.load(soundsDir + fileName, Sound.class);
        }
    }


    private static void loadMusics() {
        String[] musicNames = {
                "level1.mp3"
        };

        for (String fileName : musicNames) {
            manager.load(musicDir + fileName, Music.class);
        }
    }

    private static void loadFonts() {
        String[] fontNames = {
                "JungleRoarRegular.fnt",
        };

        for (String fileName : fontNames) {
            manager.load(fontsDir + fileName, BitmapFont.class);
        }
    }

    public static synchronized <T> T get(String fileName, Class<T> type) {
        if (isNotDirectory(fileName)) {
            fileName = new StringBuilder()
                    .append(getTypeDir(type))
                    .append(fileName)
                    .toString();
        }
        return manager.get(fileName, type);
    }

    private static <T> String getTypeDir(Class<T> type) {
        String dir = "/";
        if (type.equals(Texture.class))
            dir = texturesDir;
        if (type.equals(Sound.class))
            dir = soundsDir;
        if (type.equals(Music.class))
            dir = soundsDir;
        if (type.equals(BitmapFont.class))
            dir = fontsDir;

        return dir;
    }

    private static boolean isNotDirectory(String fileName) {
        return !fileName.contains("\\") &&
                !fileName.contains("/");
    }

    public static AssetManager getManager() {
        return manager;
    }
}
