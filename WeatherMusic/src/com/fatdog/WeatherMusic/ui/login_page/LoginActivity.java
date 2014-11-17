package com.fatdog.WeatherMusic.ui.login_page;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.fatdog.WeatherMusic.MainActivity;
import com.fatdog.WeatherMusic.reuse.etc.BackPressCloseHandler;
import com.fatdog.WeatherMusic.reuse.etc.WeatherMusicApplication;
import com.fatdog.WeatherMusic.reuse.favor_genre_page.FavorGenreActivity;

public class LoginActivity extends Activity implements ViewForLoginActivity.Controller  {
	private ViewForLoginActivity view;
	private BackPressCloseHandler backPressCloseHandler;
	private SharedPreferences prefs; // 첫 화면 로딩
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		backPressCloseHandler = new BackPressCloseHandler(this);
		view = new ViewForLoginActivity(getApplicationContext(), this); // 뷰를 생성해 낸다.
		
		prefs = PreferenceManager.getDefaultSharedPreferences(getApplication()); // 프레퍼런스 초기화
		
		setContentView(view.getRoot());
	}
	
	@Override
	public void onBackPressed() {
		backPressCloseHandler.onBackPressed();
	}

	@Override
	public void onLogin() {
		view.lockButton();		
		
		Session.openActiveSession(this, true, new Session.StatusCallback() {
			// callback when session changes state
			@SuppressWarnings("deprecation")
			@Override
			public void call(Session session, SessionState state, Exception exception) {
				
				if (session.isOpened()) {
					// make request to the /me API
					Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
						// callback after Graph API response with user
						// object
						@Override
						public void onCompleted(GraphUser user, Response response) {
							view.unLockButton();
							
							if (user != null) {
								
								WeatherMusicApplication wma = (WeatherMusicApplication)getApplicationContext();
								wma.setUserId(user.getId());
								wma.setUserName(user.getName());
								
								Intent intent = null;
								Boolean check = prefs.getBoolean("favorGenre", false); // false이면 처음에 설치한 사람이다.
								
								if(check == false)
								{
									Log.i("temp", "favor");
									intent = new Intent(LoginActivity.this, FavorGenreActivity.class); // 선호장르 페이지로 이동한다.
								}
									
								else{
									intent = new Intent(LoginActivity.this, MainActivity.class); // 그냥 메인으로 이동한다.
									Log.i("temp", "main");
								}
								startActivity(intent);
								finish();
							}
						}
					});
				}
			}
		});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
		Log.i("temp", "inlogin activiy");
	}
}