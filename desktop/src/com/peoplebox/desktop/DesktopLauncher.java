package com.peoplebox.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.peoplebox.Game;
import javafx.application.Application;
import javafx.stage.Stage;

public class DesktopLauncher extends Application {
	public static void main (final String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "PeopleBOX";
		cfg.width = 1280;
		cfg.height = 720;
		new LwjglApplication(new Game(), cfg);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

	}
}
