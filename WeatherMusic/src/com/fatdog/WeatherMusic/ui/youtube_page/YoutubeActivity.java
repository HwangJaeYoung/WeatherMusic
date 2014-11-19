package com.fatdog.WeatherMusic.ui.youtube_page;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

import com.fatdog.WeatherMusic.reuse.etc.BackPressCloseHandler;
import com.fatdog.WeatherMusic.ui.login_page.ViewForLoginActivity;

public class YoutubeActivity extends Activity {
	
	private ViewForYoutubeActivity view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);

		view = new ViewForYoutubeActivity(getApplicationContext()); // 뷰를 생성해 낸다.
		setContentView(view.getRoot());
	}
}