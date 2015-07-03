package com.unkarjedy.platformer.utils;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dima Naumenko on 01.07.2015.
 */
public class TexturePool  {

    static Map<String ,Texture> textures = new HashMap<>();

    public static Texture getTexture(String filename) {
        Texture tex = textures.get(filename);
        if(tex == null){
            tex = new Texture(filename);
            textures.put(filename, tex);
        }
        return tex;
    }

}
