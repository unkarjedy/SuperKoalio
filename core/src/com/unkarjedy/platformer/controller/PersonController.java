package com.unkarjedy.platformer.controller;

import com.unkarjedy.platformer.model.GameLevel;
import com.unkarjedy.platformer.model.GameObject;
import com.unkarjedy.platformer.model.Person;
import com.unkarjedy.platformer.model.Player;
import com.unkarjedy.platformer.physics.TilesCollisionDetector;

import static com.unkarjedy.platformer.model.GameLevel.LayerType.WALLS;

/**
 * Created by Dima Naumenko on 06.07.2015.
 */
public class PersonController extends GameObjectController {
    
    Person person;
    
    public PersonController(Person person) {
        super(person);
        this.person = person;
    }

    public void move(){
        if(person.isFacesRight())
            moveRight();
        else
            moveLeft();
    }

    public void moveRight() {
        person.getVelocity().x = person.getMaxVelocity();
        if (person.isGrounded()) {
            person.setState(Player.State.Walking);
        }
        person.setFacesRight(true);
    }

    public void moveLeft() {
        person.getVelocity().x = -person.getMaxVelocity();
        if (person.isGrounded()) {
            person.setState(Player.State.Walking);
        }
        person.setFacesRight(false);
    }


    public void jump() {
        Player.State state = person.getState();
        if (!state.equals(Player.State.Jumping) &&
                !state.equals(Player.State.Falling)) {
            person.setGrounded(false);
            person.setState(Player.State.Jumping);
            person.getVelocity().y = person.getMaxVelocity();
        }
    }

    protected void clampVelocities() {
        // clamp the velocity to the maximum, x-axis only
        if (Math.abs(person.getVelocity().x) > person.getMaxVelocity()) {
            person.getVelocity().x = Math.signum(person.getVelocity().x) * person.getMaxVelocity();
        }

        // clamp the velocity to 0 if it's < 1, and set the state to standing
        if (Math.abs(person.getVelocity().x) < 1) {
            person.getVelocity().x = 0;
            if (person.isGrounded()) {
                person.setState(Player.State.Standing);
            }
        }
    }

    @Override
    public void onLevelTileCollided(GameLevel.LayerType type, TilesCollisionDetector.Collision.CollideTile tile, boolean isXAxis) {
        if (WALLS == type) {
            if (!isXAxis) {
                if (person.getVelocity().y > 0) {
                    person.setState(Player.State.Falling);
                } else {
                    person.setGrounded(true);
                }
            }
        }
    }
}
