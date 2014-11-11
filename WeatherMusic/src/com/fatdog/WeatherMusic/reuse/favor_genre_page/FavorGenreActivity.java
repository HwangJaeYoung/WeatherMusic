package com.fatdog.WeatherMusic.reuse.favor_genre_page;

import com.fatdog.WeatherMusic.MainActivity;
import com.fatdog.WeatherMusic.reuse.etc.WeatherMusicApplication;
import com.fatdog.WeatherMusic.ui.login_page.LoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

public class FavorGenreActivity extends Activity implements ViewForFavorGenreActivity.Controller {
	private ViewForFavorGenreActivity view;
	private SharedPreferences prefs; // 첫 화면 로딩
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		view = new ViewForFavorGenreActivity(getApplicationContext(), this); // 뷰를 생성해 낸다.
		
		prefs = PreferenceManager.getDefaultSharedPreferences(getApplication()); // 프레퍼런스 초기화
		
		setContentView(view.getRoot());
	}

	@Override
	public void alternativeClick() {
		prefs.edit().putBoolean("favorGenre", true).apply();
		prefs.edit().putInt("favorGenreNumber", 0).apply();
		genreChoiceFinish( );
	}

	@Override
	public void hiphopClick() {
		prefs.edit().putBoolean("favorGenre", true).apply();
		prefs.edit().putInt("favorGenreNumber", 1).apply();
		genreChoiceFinish( );
	}

	@Override
	public void accousticClick() {
		prefs.edit().putBoolean("favorGenre", true).apply();
		prefs.edit().putInt("favorGenreNumber", 2).apply();
		genreChoiceFinish( );
	}

	@Override
	public void rnbClick() {
		prefs.edit().putBoolean("favorGenre", true).apply();
		prefs.edit().putInt("favorGenreNumber", 3).apply();
		genreChoiceFinish( );
	}
	
	public void genreChoiceFinish( ) {
		Intent intent = new Intent(FavorGenreActivity.this, MainActivity.class); // 그냥 메인으로 이동한다.
		startActivity(intent);
		finish();
	}
}
