package com.unkarjedy.platformer.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Dima Naumenko on 06.07.2015.
 */
public class Enemy extends Person {

    public Enemy() {
        super();
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("textures/solbrain.pack"));
        for (TextureRegion region : atlas.getRegions())
            region.flip(true, false);
        stand = new Animation(0, atlas.findRegion("1"));
        jump = fall = new Animation(0, atlas.findRegion("3"));

        TextureRegion[] walkLeftFrames = new TextureRegion[6];
        for (int i = 0; i < 6; i++) {
            walkLeftFrames[i] = atlas.findRegion(((i + 6) + ""));
        }
        walk = new Animation(0.15f, walkLeftFrames);
        walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        width = stand.getKeyFrame(0).getRegionWidth();
        height = stand.getKeyFrame(0).getRegionHeight();
    }
}
