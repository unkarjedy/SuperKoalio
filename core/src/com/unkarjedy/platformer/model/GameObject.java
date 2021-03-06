package com.unkarjedy.platformer.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Dima Naumenko on 01.07.2015.
 */
public abstract class GameObject {

    protected Vector2 position;
    protected Vector2 velocity;

    protected float width;
    protected float height;

    public void update(float dt) {
        velocity.scl(dt);
        position.add(velocity);
        velocity.scl(1 / dt);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void applyAccel(Vector2 accel, float dt) {
        Vector2 accelStep = accel.cpy().scl(dt);
        velocity.add(accelStep);
    }

    public Rectangle getBoundingRect() {
        return new Rectangle(position.x, position.y, width, height);
    }

    abstract public void render(SpriteBatch sb);

}
