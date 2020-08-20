package com.peoplebox;

import android.os.Build;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useAccelerometer = false;
		cfg.useCompass = false;
		initialize(new Game(), cfg);
		String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};

		int permsRequestCode = 200;

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			requestPermissions(perms, permsRequestCode);
		}
	}
}
