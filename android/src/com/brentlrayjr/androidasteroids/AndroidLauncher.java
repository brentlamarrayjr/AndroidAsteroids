package com.brentlrayjr.androidasteroids;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.brentlrayjr.androidasteroids.Asteroids;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useGyroscope = true;  //default is false


//you may want to switch off sensors that are on by default if they are no longer needed.
		config.useAccelerometer = true;
		config.useCompass = true;
		initialize(new Asteroids(), config);

	}
}
