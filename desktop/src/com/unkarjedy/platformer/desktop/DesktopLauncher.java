package com.unkarjedy.platformer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.unkarjedy.platformer.PlatformerGame;
import com.unkarjedy.platformer.desktop.gameinputs.KeyboardGameInputs;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 500;
		new LwjglApplication(new PlatformerGame(new KeyboardGameInputs()), config);
	}
}
