package com.fatdog.WeatherMusic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.fatdog.WeatherMusic.ui.login_page.LoginActivity;

public class SplashActivity extends Activity {
	private static final int SPLASH_TIME_OUT = 1000; // Splash가 보여지는 시간

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this, LoginActivity.class); // 로그인 페이지로 이동한다.
				startActivity(intent);
				finish();
			}
		}, SPLASH_TIME_OUT);
	}
}
