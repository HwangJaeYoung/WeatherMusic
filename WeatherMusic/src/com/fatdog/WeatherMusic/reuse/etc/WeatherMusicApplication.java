package com.fatdog.WeatherMusic.reuse.etc;

import android.app.Application;
import android.media.MediaPlayer;

public class WeatherMusicApplication extends Application {
	private MediaPlayer mp; // 공통적인 미디어 플레이어객체 사용

	@Override
	public void onCreate() {
		super.onCreate();
		mp = new MediaPlayer( );
	}

	public MediaPlayer getMediaPlayer( ) { // 다른 액티비티나 프래그먼트에서 사용할 수 있다.
		return mp;
	}
}