package com.unkarjedy.platformer.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.unkarjedy.platformer.utils.TexturePool;

/**
 * Created by Dima Naumenko on 01.07.2015.
 */
public class Player extends GameObject {

    public static float MAX_VELOCITY = 10f;
    public static float MAX_JUMP_SPEED = 14f;
    public static final float DAMPING = 0.87f;
    public static final long LONG_JUMP_PRESS = 250;

    public enum State {
        Standing, Walking, Jumping, Falling
    }

    State state;
    boolean facesRight = true;
    boolean grounded = false;

    static public final Animation stand;
    static public final Animation walk;
    static public final Animation jump;

    static {
        Texture koalaTexture = new Texture("koalio.png");
        TextureRegion[] regions = TextureRegion.split(koalaTexture, 18, 26)[0];
        stand = new Animation(0, regions[0]);
        jump = new Animation(0, regions[1]);
        walk = new Animation(0.15f, regions[2], regions[3], regions[4]);
        walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }

    public Player() {
        texture = TexturePool.getTexture("koalio_stand.png");
        position = new Vector2();
        velocity = new Vector2();
        state = State.Standing;
        facesRight = true;
        stateTime = 0;
        grounded = false;
    }


    public TextureRegion getFrame() {
        switch (state) {
            case Standing:
                return stand.getKeyFrame(stateTime);
            case Walking:
                return walk.getKeyFrame(stateTime);
            case Jumping:
            case Falling:
                return jump.getKeyFrame(stateTime);
        }

        return null;
    }


    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public boolean isFacesRight() {
        return facesRight;
    }

    public void setFacesRight(boolean facesRight) {
        this.facesRight = facesRight;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
