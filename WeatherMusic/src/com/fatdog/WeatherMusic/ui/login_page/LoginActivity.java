package com.fatdog.WeatherMusic.ui.login_page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
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
								Intent intent = new Intent(LoginActivity.this, MainActivity.class); // 로그인 페이지로 이동한다.
								intent.putExtra("userName", user.getName());
								intent.putExtra("userId", user.getId());
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
	}
}