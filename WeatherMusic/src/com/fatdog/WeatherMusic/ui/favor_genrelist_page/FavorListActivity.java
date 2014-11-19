package com.fatdog.WeatherMusic.ui.favor_genrelist_page;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

// 사용자가 리스트를 보고싶을 때 클릭하는 페이지
public class FavorListActivity extends Activity {
	
	private ViewForFavorListActivity view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		view = new ViewForFavorListActivity(getApplicationContext( ));
		setContentView(view.getRoot());
	}
}