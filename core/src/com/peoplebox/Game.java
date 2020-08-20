package com.peoplebox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;

public class Game extends com.badlogic.gdx.Game {
	SpriteBatch batch;
	BitmapFont font;

	public void create() {
		batch = new SpriteBatch();
		// libGDX по умолчанию использует Arial шрифт.
		font = new BitmapFont();
		Json json = new Json();
		FileHandle file = Gdx.files.local("E/json/settings.txt");
		if (!file.exists()) {
			file= Gdx.files.internal("json/settings.txt");
		}
		//file = Gdx.files.local("E/json/settings.txt");
		MainMenuScreen.Settings settings = json.fromJson(MainMenuScreen.Settings.class, file.readString());
		this.setScreen(new MainMenuScreen(this, settings.lang));
	}

	public void render() {
		super.render(); // важно!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}
