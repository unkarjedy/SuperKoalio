package com.unkarjedy.platformer.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.unkarjedy.platformer.utils.ResourceManager;

import javax.xml.soap.Text;

/**
 * Created by Dima Naumenko on 01.07.2015.
 */
public class Player extends Person {

    public static final long LONG_JUMP_PRESS = 250;

    public Player() {
        super();

        int tileWidth = 18;
        int tileHeight = 26;
        Texture koalaTexture = ResourceManager.get("koalio.png", Texture.class);
        TextureRegion[] regions = TextureRegion.split(koalaTexture, tileWidth, tileHeight)[0];
        stand = new Animation(0, regions[0]);
        jump = fall = new Animation(0, regions[1]);
        walk = new Animation(0.15f, regions[2], regions[3], regions[4]);
        walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        width = stand.getKeyFrame(0).getRegionWidth();
        height = stand.getKeyFrame(0).getRegionHeight();
    }

}
