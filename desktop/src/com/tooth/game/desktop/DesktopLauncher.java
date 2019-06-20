package com.tooth.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tooth.game.ToothGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = ToothGame.WIDTH;
		config.height = ToothGame.HEIGHT;
		config.title = ToothGame.TITLE;
		new LwjglApplication(new ToothGame(), config);
	}
}
