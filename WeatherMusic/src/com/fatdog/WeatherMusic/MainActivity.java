package com.fatdog.WeatherMusic;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.fatdog.WeatherMusic.domain.WeatherInfo;
import com.fatdog.WeatherMusic.reuse.etc.BackPressCloseHandler;
import com.fatdog.WeatherMusic.reuse.etc.DateCalculation;
import com.fatdog.WeatherMusic.reuse.etc.LocationPosition;
import com.fatdog.WeatherMusic.reuse.network.CurrentWeatherRequest;
import com.fatdog.WeatherMusic.reuse.network.HttpRequester;
import com.fatdog.WeatherMusic.ui.genre_alternative.AlternativeFragment;
import com.fatdog.WeatherMusic.ui.genre_hiphop.HipHopFragment;
import com.fatdog.WeatherMusic.ui.navigation_drawer_menu.NavigationDrawerFragment;
import com.naver.wcs.WCSLogEventAPI;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
	public Context wcsContext = null;
	
	public static int LOCATION_SEARCH_END = 0;
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
	private static final long MIN_TIME_BW_UPDATES = 0;

	private NavigationDrawerFragment mNavigationDrawerFragment;
	private BackPressCloseHandler backPressCloseHandler;
	private int currentMenuIndex = -1;
	
	private Geocoder coder;
	private double lattitude = 0.0;
	private double longitude = 0.0;
	private List<Address> list;
	private LocationManager locationManager;
	private boolean startNetworkSearch = true;
	private String finalLocation;
	private LocationListener networkListener;
	
	private String skyValue;
	private String ptyValue;
	private WeatherInfo weatherInfo;
	
	private String userId;
	private String userName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		wcsContext = this;
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		backPressCloseHandler = new BackPressCloseHandler(this);
		setContentView(R.layout.activity_main);
		
		coder = new Geocoder(MainActivity.this);
		
		// 네이버 애널리틱스의 시작
		WCSLogEventAPI wcslog = WCSLogEventAPI.getInstance(wcsContext); 
		wcslog.onTrackSite((Activity)wcsContext, "MainActivity");

		mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager( ).findFragmentById(R.id.navigation_drawer);
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout)findViewById(R.id.drawer_layout));
	}
	
	@Override
	protected void onResume( ) {
		super.onResume();
		defineLocation( ); // 위치 추적의 시작		
	}
	
	@Override
	public void onNavigationDrawerItemSelected(int position) {
        int previousMenuIndex = currentMenuIndex;
        currentMenuIndex = position; // 현재 사용자가 클릭한 메뉴의 번호, 처음에는 0이 넘어온다.
        if(currentMenuIndex != previousMenuIndex) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (position) {
            	// add는 기존의 것을 그대로 놔두며 추가하고, replace는 기존의 것을 제거하고 추가한다.
            	// 동적으로 프래그먼트를 정의하며, 레이아웃에 추가 될 때 라이프사이클을 돈다.
                case 0: 
                	transaction.replace(R.id.container, new AlternativeFragment()).commit();
                    break;
                case 1: 
                	//transaction.replace(R.id.container, new HipHopFragment()).commit();
                    //break;
                default: // etc...
                    break;
            }
        }
	}
	
	// 위치를 추적하는 메소드
	public void defineLocation() {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		// 위치가 바꼈을때 사용 할 네트워크 수신자를 위한 리스너
		networkListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				// 지속적으로 onLocationChanged가 호출 될텐데 그 현상을 막기 위해서
				// startNetworkSearch의 boolean값을 이용하여 한 번만 실행하게 한다.
				if (startNetworkSearch == true) {
					startNetworkSearch = false;
					if (location != null) {
						// 새로운 위치로 업데이트 한다.
						Log.i("js", "net enter listener");
						lattitude = location.getLatitude();
						longitude = location.getLongitude();
						
					} else {
						// 만약 위치가 바뀌지 않았을 경우 이전의 데이터를 가지고 온다.
						Log.i("js", "net enter null");
						location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						lattitude = location.getLatitude();
						longitude = location.getLongitude();
					}
					
					if (lattitude != 0 && longitude != 0) // 위치정보를 받아 왔을 경우에
						searchLocation();
				}
			}
			public void onStatusChanged(String provider, int status, Bundle extras) { }
			public void onProviderEnabled(String provider) { }
			public void onProviderDisabled(String provider) { }
		};

		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, networkListener);
	}

	public void searchLocation() {
		String si = null;
		String gu = null;

		try {
			list = coder.getFromLocation(lattitude, longitude, 6);
		} catch (IOException e) {
			Log.i("js", "error");
			e.printStackTrace();
		}

		for (Address add : list) {
			if (add != null) {
				si = add.getAdminArea(); // 시 정보
				gu = add.getLocality(); // 구 정보
				if (gu == null)
					gu = add.getSubLocality(); // 창원시 처리하기 위해서
					break;
			}
		}

		finalLocation = si + " " + gu; // 최종적인 현재의 위치정보
		
		if(finalLocation == null) { // 위치정보를 못 가지고 왔을 시에
			showSettingsAlert( );	
		} else {
			LocationPosition lp = new LocationPosition(finalLocation); // nx, ny변환위해서 호출
			getTodayWeather(lp.getNX(), lp.getNY()); // 현재 날씨 정보를 가져오기 위한 통신 시작
		}
	}
	
	public void showSettingsAlert() { // 네트워크 오류 때 사용하는 메소드
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle("네트워크 연결");

		// Setting Dialog Message
		alertDialog.setMessage("네트워크에 접속할 수 없습니다." + "\n"
				+ "네트워크 연결상태를 확인해 주세요." + "\n");

		// on pressing cancel button
		alertDialog.setNegativeButton("확인",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						finish(); // 종료
					}
				});
		alertDialog.show();
	}
	
	public void getTodayWeather(int aNX, int aNY) { // 현재 위치에 따른 현재의 날씨를 가지고 온다.
		DateCalculation date = new DateCalculation();
		
		CurrentWeatherRequest currentWeatherRequest = new CurrentWeatherRequest(getApplicationContext());
		try {
			currentWeatherRequest.getTodayWeather(getCurrentState, date.getHour(), date.getTodayDate(), aNX, aNY);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	// open api에서 현재의 기상 상태를 가지고 온다.
	HttpRequester.NetworkResponseListener getCurrentState = new HttpRequester.NetworkResponseListener() {
		@Override
		public void onSuccess(JSONObject jsonObject) {
			JSONArray jsonArray = new JSONArray( );
			
			try {
				jsonArray = jsonObject.getJSONArray("item");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			for(int i = 0; i < jsonArray.length(); i++) {
				try {
					JSONObject tempJSONObject = jsonArray.getJSONObject(i);
					
					if(tempJSONObject.getString("category").equals("SKY")) // 날씨정보
						skyValue = tempJSONObject.getString("obsrValue");
					
					if(tempJSONObject.getString("category").equals("PTY")) // 강수정보
						ptyValue = tempJSONObject.getString("obsrValue");
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			// 기상청에서 데이터를 주지 않는 경우가 있기 때문에 디폴트 값
			if(skyValue == null)
				ptyValue = "1"; // 맑음 디폴트
			
			if(ptyValue == null) // 
				ptyValue = "0";
			
			DateCalculation date = new DateCalculation();
			weatherInfo = new WeatherInfo(skyValue, ptyValue, date.getHour()); // 도메인 생성한다.
			
			LOCATION_SEARCH_END = 1; // 위치추적 통신이 끝났다는 것을 프래그먼트에게 알려준다.	
		}
		
		@Override
		public void onFail() {
			Toast.makeText(getApplicationContext(), R.string.json_error, Toast.LENGTH_SHORT).show();	
		}
	};
	
	public WeatherInfo getWeatherInfo( ) { // 날씨 도메인을 가지고 온다.
		return weatherInfo;
	}
	
	@Override
	public void onBackPressed() {
		backPressCloseHandler.onBackPressed();
	}
}