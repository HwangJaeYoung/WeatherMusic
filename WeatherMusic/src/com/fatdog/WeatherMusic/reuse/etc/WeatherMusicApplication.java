package com.fatdog.WeatherMusic.reuse.etc;

import android.app.Application;
import android.media.MediaPlayer;

public class WeatherMusicApplication extends Application {
	private MediaPlayer mp;

	@Override
	public void onCreate() {
		super.onCreate();
		mp = new MediaPlayer( );
	}

	public MediaPlayer getMediaPlayer( ) {
		return mp;
	}
}