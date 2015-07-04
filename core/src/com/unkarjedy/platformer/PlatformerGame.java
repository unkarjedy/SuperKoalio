package com.unkarjedy.platformer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.unkarjedy.platformer.view.MainMenu;
import com.unkarjedy.platformer.view.SplashScreen;

public class PlatformerGame extends Game {

	MainMenu mainMenu;

	@Override
	public void create() {
		mainMenu = new MainMenu(this);
		goToMenu();
	}

	public void goToMenu() {
		setScreen(mainMenu);
	}
}
