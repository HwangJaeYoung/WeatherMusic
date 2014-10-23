package com.fatdog.WeatherMusic.ui.login_page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.fatdog.WeatherMusic.MainActivity;
import com.fatdog.WeatherMusic.reuse.etc.BackPressCloseHandler;

public class LoginActivity extends Activity implements ViewForLoginActivity.Controller  {
	private ViewForLoginActivity view;
	private BackPressCloseHandler backPressCloseHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		backPressCloseHandler = new BackPressCloseHandler(this);
		view = new ViewForLoginActivity(getApplicationContext(), this); // 뷰를 생성해 낸다.
		setContentView(view.getRoot());
	}
	
	@Override
	public void onBackPressed() {
		backPressCloseHandler.onBackPressed();
	}

	@Override
	public void onLogin() {
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(intent);
		finish();
	}
}