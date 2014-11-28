package com.fatdog.WeatherMusic.ui.login_page;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
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
	private static int PARM_ONLOCATION_REQUEST_CODE = 1;
	
	private ViewForLoginActivity view;
	private BackPressCloseHandler backPressCloseHandler;
	private SharedPreferences prefs; // 첫 화면 로딩
	
	private LocationManager locationManager;
	private boolean isNetworkEnabled;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		backPressCloseHandler = new BackPressCloseHandler(this);
		view = new ViewForLoginActivity(getApplicationContext(), this); // 뷰를 생성해 낸다.
		
		prefs = PreferenceManager.getDefaultSharedPreferences(getApplication()); // 프레퍼런스 초기화
		
		setContentView(view.getRoot());
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// Network 수신가능한 상태인지 판단한다.
		isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		
		if(isNetworkEnabled == false)
			 showSettingsAlert();
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
									intent = new Intent(LoginActivity.this, FavorGenreActivity.class); // 선호장르 페이지로 이동한다.
									
								else
									intent = new Intent(LoginActivity.this, MainActivity.class); // 그냥 메인으로 이동한다.

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
		
		if(requestCode == PARM_ONLOCATION_REQUEST_CODE && resultCode == 0) {
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			// Network 수신가능한 상태인지 판단한다.
			isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			// Network를 사용한다고 했을 때
			if ((isNetworkEnabled == true)) {
				try {
					// 위치추적을 설정한 후 사용자가 바로 누르는 것을 방지하기 위해
					Thread.sleep(5000);
					 onLogin();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else {
			Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
		}
	}
	
	// 사용자가 위치추적을 사용하지 않게 설정 되어있는 경우.
	public void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle("내 위치정보 사용동의");

		// Setting Dialog Message
		alertDialog.setMessage("위치정보 환경설정을 합니다." + "\n"
				+ "무선네트워크 사용동의에 체크하셔야 합니다." + "\n" +
				 "동의 하시면 5초후 로그인 페이지로 이동합니다." + "\n"
				+ "- 무선네트워크는 필수사항 입니다. -");

		// On pressing Settings button
		alertDialog.setPositiveButton("동의",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Setting액티비티를 활성화 시킨다.
						Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivityForResult(intent, PARM_ONLOCATION_REQUEST_CODE);
					}
				});

		// on pressing cancel button
		alertDialog.setNegativeButton("동의안함",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						finish(); // 종료
					}
				});
		// Showing Alert Message
		alertDialog.show();
	}
}