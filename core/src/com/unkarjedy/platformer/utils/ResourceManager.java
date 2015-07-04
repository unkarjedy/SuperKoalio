package com.unkarjedy.platformer.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Dima Naumenko on 04.07.2015.
 */
public class ResourceManager {

    public static final AssetManager manager = new AssetManager();

    public static void load() {
        loadTextures();
        loadSounds();
        loadFonts();
    }

    private static void loadTextures() {
        manager.load("Koalaabilitylol.png", Texture.class);
        manager.load("heart.png", Texture.class);
    }

    private static void loadSounds() {
        manager.load("level1.mp3", Sound.class);
        manager.load("jump.wav", Sound.class);
        manager.load("hurt.wav", Sound.class);
    }

    private static void loadFonts() {
        manager.load("PressStart2P.fnt", BitmapFont.class);
    }
}
