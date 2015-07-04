package com.unkarjedy.platformer;

import com.badlogic.gdx.Game;
import com.unkarjedy.platformer.view.SplashScreen;

public class PlatformerGame extends Game {
	@Override
	public void create() {
//		setScreen(new GameScreen());
		setScreen(new SplashScreen(this));
	}

}
