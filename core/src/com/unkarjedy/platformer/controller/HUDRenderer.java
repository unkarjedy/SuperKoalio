package com.unkarjedy.platformer.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.unkarjedy.platformer.model.Player;
import com.unkarjedy.platformer.utils.ResourceManager;

/**
 * Created by Dima Naumenko on 03.07.2015.
 */
public class HUDRenderer {

    private Player player;

    private OrthographicCamera camera;
    SpriteBatch sb;
    Texture heart;
    Sprite heartSprite;

    private float livesYPos;
    private static final int GAP_SIZE = 5;

    private final Color defalutBatchColor;

    {
        sb = new SpriteBatch();
        defalutBatchColor = sb.getColor();
        heart = ResourceManager.manager.get("heart.png");
        heartSprite = new Sprite(heart);
        camera = new OrthographicCamera();
    }

    public HUDRenderer(Player player) {
        this.player = player;
    }

    public void render() {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.setColor(defalutBatchColor);
        for (int life = 0; life < player.getLives(); life++) {
            sb.draw(heartSprite, life * heart.getWidth() + GAP_SIZE, livesYPos, heart.getWidth(), heart.getHeight());
        }
        sb.setColor(Color.BLACK);
        for (int life = player.getLives(); life < player.DEFAULT_LIVES; life++) {
            sb.draw(heartSprite, life * heart.getWidth() + GAP_SIZE, livesYPos, heart.getWidth(), heart.getHeight());
        }
        sb.end();
    }

    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.position.set(width / 2, height / 2, 0);
        camera.update();

        livesYPos = Gdx.graphics.getHeight() - heart.getHeight();
    }
}
