package com.unkarjedy.platformer.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.unkarjedy.platformer.model.Player;
import com.unkarjedy.platformer.utils.TexturePool;

/**
 * Created by Dima Naumenko on 03.07.2015.
 */
public class HUDRenderer {
    private Player player;
    SpriteBatch sb;
    Texture heart;

    private float livesYPos;
    private static final int GAP_SIZE = 5;

    {
        sb = new SpriteBatch();
        heart = TexturePool.getTexture("heart.png");
        livesYPos = Gdx.graphics.getHeight() - heart.getHeight();
    }

    public HUDRenderer(Player player) {
        this.player = player;
    }

    public void render() {
        sb.begin();
        for(int life = 0; life < player.getLives(); life++){
            sb.draw(heart, life * heart.getWidth() + GAP_SIZE, livesYPos, heart.getWidth(), heart.getHeight());
        }
        sb.end();
    }
}
