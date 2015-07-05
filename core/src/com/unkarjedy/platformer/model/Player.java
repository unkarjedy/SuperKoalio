package com.unkarjedy.platformer.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.unkarjedy.platformer.utils.ResourceManager;

/**
 * Created by Dima Naumenko on 01.07.2015.
 */
public class Player extends GameObject {

    public static final float MAX_VELOCITY = 10f;
    public static float MAX_JUMP_SPEED = 14f;
    public static final long LONG_JUMP_PRESS = 250;
    public static final int DEFAULT_LIVES = 5;

    public enum State {
        Standing, Walking, Jumping, Falling, Dead
    }


    private int lives = DEFAULT_LIVES;
    private State state;
    private float stateTime = 0;
    private boolean facesRight = true;
    private boolean grounded = false;

    static public final Animation stand;
    static public final Animation walk;
    static public final Animation jump;


    static int tileWidth = 18;
    static int tileHeight = 26;
    static {
        Texture koalaTexture = ResourceManager.get("koalio.png", Texture.class);
        TextureRegion[] regions = TextureRegion.split(koalaTexture, tileWidth, tileHeight)[0];
        stand = new Animation(0, regions[0]);
        jump = new Animation(0, regions[1]);
        walk = new Animation(0.15f, regions[2], regions[3], regions[4]);
        walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }

    public Player() {
        position = new Vector2();
        velocity = new Vector2();
        state = State.Standing;
        facesRight = true;
        stateTime = 0;
        grounded = false;
        lives = DEFAULT_LIVES;


        width = tileWidth;
        height = tileHeight;
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        stateTime += dt;
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

    public void setState(final State state) {
        if(state != this.state)
            stateTime = 0;
        this.state = state;
    }

    public int getLives() {
        return lives;
    }

    public void decreaseLives() {
        lives--;
        if(lives < 0){
            lives = 0;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (isFacesRight()){
            sb.draw(getFrame(), getPosition().x, getPosition().y, getWidth(), getHeight());
        }
        else{
            sb.draw(getFrame(), getPosition().x + getWidth(), getPosition().y, -getWidth(), getHeight());
        }
    }
}
