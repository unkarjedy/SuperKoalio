package com.unkarjedy.platformer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.unkarjedy.platformer.screen.MainMenuScreen;

public class PlatformerGame extends Game {

	MainMenuScreen mainMenu;

	@Override
	public void create() {
		mainMenu = new MainMenuScreen(this);
		goToMenu();
	}

	public void goToMenu() {
		setScreen(mainMenu);
	}

	public void exit() {
		Gdx.app.exit();
	}

}
