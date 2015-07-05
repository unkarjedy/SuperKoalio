package com.unkarjedy.platformer.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Dima Naumenko on 06.07.2015.
 */
public abstract class Person extends GameObject {
    protected static float maxVelocity = 10f;
    protected static float maxJumpSpeed = 14f;
    protected static int defaultLives = 5;

    protected int lives = defaultLives;

    public enum State {
        Standing, Walking, Jumping, Falling, Dead
    }

    protected State state;
    protected float stateTime = 0;

    protected boolean facesRight = true;
    protected boolean grounded = false;

    protected Animation stand;
    protected Animation walk;
    protected Animation jump;
    protected Animation fall;
    protected Animation die;

    public Person() {
        position = new Vector2();
        velocity = new Vector2();
        state = State.Standing;
        facesRight = true;
        stateTime = 0;
        grounded = false;
        lives = defaultLives;
    }

    public static float getMaxVelocity() {
        return maxVelocity;
    }

    public static float getMaxJumpSpeed() {
        return maxJumpSpeed;
    }

    public static int getDefaultLives() {
        return defaultLives;
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
                return fall.getKeyFrame(stateTime);
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


    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        if (isFacesRight()){
            sb.draw(getFrame(), getPosition().x, getPosition().y, getWidth(), getHeight());
        }
        else{
            sb.draw(getFrame(), getPosition().x + getWidth(), getPosition().y, -getWidth(), getHeight());
        }
        sb.end();
    }
}
