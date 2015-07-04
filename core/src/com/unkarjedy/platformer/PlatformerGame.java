package com.unkarjedy.platformer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.unkarjedy.platformer.view.MainMenu;
import com.unkarjedy.platformer.view.SplashScreen;

public class PlatformerGame extends Game {

	@Override
	public void create() {
//		setScreen(new GameScreen());
//		setScreen(new SplashScreen(this));
		setScreen(new MainMenu(this));
	}

}
