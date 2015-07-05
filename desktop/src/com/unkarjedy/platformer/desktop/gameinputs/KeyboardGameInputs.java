package com.unkarjedy.platformer.desktop.gameinputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.unkarjedy.platformer.controller.gameinputs.GameInputsController;

/**
 * Created by Dima Naumenko on 06.07.2015.
 */
public class KeyboardGameInputs implements GameInputsController {
    @Override
    public boolean moveLeftPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.LEFT);
    }

    @Override
    public boolean moveRightPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.RIGHT);
    }

    @Override
    public boolean jumpPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.UP);
    }
}
