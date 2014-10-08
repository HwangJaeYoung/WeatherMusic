package com.fatdog.WeatherMusic;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.fatdog.WeatherMusic.domain.FutureRainWeather;
import com.fatdog.WeatherMusic.domain.FutureWeather;
import com.fatdog.WeatherMusic.reuse.etc.BackPressCloseHandler;
import com.fatdog.WeatherMusic.reuse.etc.DateCalculation;
import com.fatdog.WeatherMusic.reuse.etc.WeatherMusicApplication;
import com.fatdog.WeatherMusic.reuse.network.CurrentWeatherRequest;
import com.fatdog.WeatherMusic.reuse.network.FutureRainWeatherRequest;
import com.fatdog.WeatherMusic.reuse.network.FutureWeatherRequest;
import com.fatdog.WeatherMusic.reuse.network.HttpRequester;
import com.fatdog.WeatherMusic.reuse.network.TomorrowWeatherRequest;
import com.fatdog.WeatherMusic.ui.genre_page_one.BallardFragment;
import com.fatdog.WeatherMusic.ui.genre_page_three.HipHopFragment;
import com.fatdog.WeatherMusic.ui.navigation_drawer_menu.NavigationDrawerFragment;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
	private NavigationDrawerFragment mNavigationDrawerFragment;
	private BackPressCloseHandler backPressCloseHandler;
	private int currentMenuIndex = -1;
	
	private Geocoder coder;
	private double lattitude;
	private double longitude;
	private List<Address> list;
	private boolean isGPSEnabled;
	private boolean isNetworkEnabled;
	private LocationManager locationManager;
	private boolean startNetworkSearch = true;
	private boolean startGPSSearch = true;
	private String finalLocation;
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
	private static final long MIN_TIME_BW_UPDATES = 0;
	private LocationListener networkListener;
	private LocationListener gpsListener;
	private TextView text1;
	private TextView text2;
	private TextView text3;
	private TextView text4;
	private TextView text5;
	private TextView text6;
	private TextView text7;
	
	private MediaPlayer mp;
	private int length;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		backPressCloseHandler = new BackPressCloseHandler(this);
		setContentView(R.layout.activity_main);
		
		mp = ((WeatherMusicApplication)getApplicationContext()).getMediaPlayer();
		text7 = (TextView)findViewById(R.id.tv_text7);
		
		// 정적으로 정의된 프래그먼트이므로 여기서는 라이프사이클을 수행한 프래그먼트가 넘어오게 된다.
		mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager( ).findFragmentById(R.id.navigation_drawer);
	    mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout)findViewById(R.id.drawer_layout));
	}
	
	@Override
	protected void onResume( ) {
		super.onResume();
		//getTodayWeather( );
		//getTomorrowWeather( );
		//getFutureWeather( );
		//getFutureRainWeather( );
		
		//initLogoSplash( );
		//defineLocation( );
		
		text7.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mp.isPlaying()) {
					length = mp.getCurrentPosition();
					mp.pause();				
					
					
				}
				else {
					Toast.makeText(getApplicationContext(),"in", Toast.LENGTH_SHORT).show();
					mp.seekTo(length);
					mp.start();
					
					
				}
			
			}
		});
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
                    transaction.replace(R.id.container, new BallardFragment()).commit();
                    break;
                case 1: 
                    transaction.replace(R.id.container, new HipHopFragment()).commit();
                    break;
                default: // etc...
                    break;
            }
        }
	}

	@Override
	public void onBackPressed() {
		backPressCloseHandler.onBackPressed();
	}
	
	public void initLogoSplash() {
		lattitude = 0.0;
		longitude = 0.0;
		list = null;
		coder = new Geocoder(MainActivity.this);
	}
	
	// 위치를 추적하는 메소드
	public void defineLocation() {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// GPS가 수신가능한 상태인지 판단한다.
		isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// Network 수신가능한 상태인지 판단한다.
		isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		// 위치가 바꼈을때 사용 할 네트워크 수신자를 위한 리스너
		networkListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				// 지속적으로 onLocationChanged가 호출 될텐데 그 현상을 막기 위해서
				// startNetworkSearch의 boolean값을 이용하여 한 번만 실행하게 한다.
				if (startNetworkSearch == true) {
					startNetworkSearch = false;
					if (location != null) {
						// 새로운 위치로 업데이트 한다.
						lattitude = location.getLatitude();
						longitude = location.getLongitude();
						Log.i("js", "net enter listener");
					} else {
						Log.i("js", "net enter null");
						// 만약 위치가 바뀌지 않았을 경우 이전의 데이터를 가지고 온다.
						location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						lattitude = location.getLatitude();
						longitude = location.getLongitude();
					}
						if (lattitude != 0 && longitude != 0) // 위치정보를 받아 왔을 경우에
						searchLocation();
					else
						replaceToGPS(); // 그럼에도 불구하고 위치를 받아 오지 못했을 경우
						// Network -> GPS로 한번 더 기회를 준다.
				}
			}
			public void onStatusChanged(String provider, int status, Bundle extras) { }
			public void onProviderEnabled(String provider) { }
			public void onProviderDisabled(String provider) { }
		};

		// 위치가 바꼈을때 사용 할 GPS 수신자를 위한 리스너
		gpsListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				if (startGPSSearch == true) {
					startGPSSearch = false;
					if (location != null) {
						lattitude = location.getLatitude();
						longitude = location.getLongitude();
						Log.i("js", "gps enter listener");
					} else {
						Log.i("js", "gps enter null");
						location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						lattitude = location.getLatitude();
						longitude = location.getLongitude();
					}
						if (lattitude != 0 && longitude != 0) // 위치정보를 받아 왔을 경우에
						searchLocation();
					else 
						noLocationAlert();
						// GPS는 기회를 주지 않는다.
						// 바로 종료를 시킨다.
				}
			}
			public void onStatusChanged(String provider, int status, Bundle extras) { }
			public void onProviderEnabled(String provider) { }
			public void onProviderDisabled(String provider) { }
		};
		if (!isNetworkEnabled) {
			showSettingsAlert();
		} else {
			// 우선은 네트워크 수신자를 사용하게 한다.
			if (isNetworkEnabled) {
				Log.i("js", "if state enter network");
				locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, networkListener);
			}
		}
	}
	
	// GPS연결을 한다.
	public void replaceToGPS() {
		if (isGPSEnabled)
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, gpsListener);
		else // GPS마저도 실행이 되지 않을 때 호출
			noLocationAlert();
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

		finalLocation = si + " " + gu;
		text6.setText(finalLocation);
		
	}
	
	public void getTodayWeather( ) {
		DateCalculation date = new DateCalculation();
		
		CurrentWeatherRequest currentWeatherRequest = new CurrentWeatherRequest(getApplicationContext());
		try {
			currentWeatherRequest.getTodayWeather(getCurrentState, date.getTodayDate(), date.getHour(), 62, 126);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void getTomorrowWeather( ) {
		DateCalculation date = new DateCalculation();
		
		// 오늘 기준의 어제의 날짜를 입력한다음 어제 날짜를 기준으로한 내일모레가 결국은 내일의 날씨가 된다.
		// ex) 10.06이면 10.05를 입력하여 10.07의 상태를 구하고 이것은 10.06의 내일 날씨가 된다.
		TomorrowWeatherRequest tomorrowWeatherRequest = new TomorrowWeatherRequest(getApplicationContext());
		try {
			tomorrowWeatherRequest.getTomorrowWeather(getTomorrowState, date.getYesterdayDate(), 62, 126);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void getFutureWeather( ) {
		// 11B10101은 서울을 뜻한다. 일 2회 생성되는데 (6시, 18시)
		// 아침을 기준으로 예보하는 것이 좋을것 같아서 6시를 기준으로 한다.
		DateCalculation date = new DateCalculation();

		FutureWeatherRequest futureWeatherRequest = new FutureWeatherRequest(getApplicationContext());

		try {
			futureWeatherRequest.getFutureWeather(getFutureState, "11B10101", date.getYesterdayDate() + "0600");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void getFutureRainWeather( ) {
		// 11B00000은 서울, 경기, 인천을 뜻한다. 일 2회 생성되는데 (6시, 18시)
		// 아침을 기준으로 예보하는 것이 좋을것 같아서 6시를 기준으로 한다.
		DateCalculation date = new DateCalculation();
		
		FutureRainWeatherRequest futureRainWeatherRequest = new FutureRainWeatherRequest(getApplicationContext()); 
		try {
			futureRainWeatherRequest.getFutureRainWeather(getFutureRainState, "11B00000", date.getYesterdayDate() + "0600");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	HttpRequester.NetworkResponseListener getCurrentState = new HttpRequester.NetworkResponseListener() {
		
		@Override
		public void onSuccess(JSONObject jsonObject) {
			JSONArray jsonArray = new JSONArray( );
			String currentTemp = null;
				
			try {
				jsonArray = jsonObject.getJSONArray("item");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			for(int i = 0; i < jsonArray.length(); i++) {
				try {
					JSONObject tempJSONObject = jsonArray.getJSONObject(i);
					
					if(tempJSONObject.getString("category").equals("T1H")) {
						currentTemp = tempJSONObject.getString("obsrValue");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				text1.setText("현재 기온 : " + currentTemp);
			}
		}
		
		@Override
		public void onFail() {
			Toast.makeText(getApplicationContext(), R.string.json_error, Toast.LENGTH_SHORT).show();	
		}
	};
	
	HttpRequester.NetworkResponseListener getTomorrowState = new HttpRequester.NetworkResponseListener() {
		
		@Override
		public void onSuccess(JSONObject jsonObject) {
			DateCalculation date = new DateCalculation();
			
			JSONArray jsonArray = new JSONArray( );
			double fsctValueTMN = 0.0, fsctValueTMX = 0.0;
			
			try {
				jsonArray = jsonObject.getJSONArray("item");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			for(int i = 0; i < jsonArray.length(); i++)	{
				try {

					JSONObject checkObject = jsonArray.getJSONObject(i);

					if (checkObject.getString("fcstDate").equals(date.getTomorrowDayDate())) {
						if(checkObject.getString("category").equals("TMN") && checkObject.getString("fcstTime").equals("0600")) {
							double tempFsctValue = Double.parseDouble(checkObject.getString("fcstValue"));
						
							if(tempFsctValue > 1)
								fsctValueTMN = tempFsctValue;
						}
						else if(checkObject.getString("category").equals("TMX")) {
							double tempFsctValue = Double.parseDouble(checkObject.getString("fcstValue"));
						
							if(tempFsctValue > 1)
								fsctValueTMX = tempFsctValue;
						}
					}	
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			text2.setText("내일 최저온도" + fsctValueTMN + ", " + "내일최고온도 : " + fsctValueTMX);
		}
		
		@Override
		public void onFail( ) {
			Toast.makeText(getApplicationContext(), R.string.json_error, Toast.LENGTH_SHORT).show();
		}
	}; 
	
	HttpRequester.NetworkResponseListener getFutureState = new HttpRequester.NetworkResponseListener() {
		
		@Override
		public void onSuccess(JSONObject jsonObject) {
			JSONObject tempJSONObject = new JSONObject( );
			FutureWeather futureWeather = null;
			
			try {
				tempJSONObject = jsonObject.getJSONObject("item");
				futureWeather = new FutureWeather(tempJSONObject);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		
			text3.setText("6일 최고기온 : " + futureWeather.getTaMax3() + ", "
					 + futureWeather.getTaMax4() + ", "
					 + futureWeather.getTaMax5() + ", "
					 + futureWeather.getTaMax6() + ", "
					 + futureWeather.getTaMax7() + ", "
					 + futureWeather.getTaMax8());
			
			text4.setText("6일 최저기온 : " + futureWeather.getTaMin3() + ", "
					 + futureWeather.getTaMin4() + ", "
					 + futureWeather.getTaMin5() + ", "
					 + futureWeather.getTaMin6() + ", "
					 + futureWeather.getTaMin7() + ", "
					 + futureWeather.getTaMin8());
		}
		
		@Override
		public void onFail() {
			Toast.makeText(getApplicationContext(), R.string.json_error, Toast.LENGTH_SHORT).show();	
		}
	};
    
	HttpRequester.NetworkResponseListener getFutureRainState = new HttpRequester.NetworkResponseListener() {
		
		@Override
		public void onSuccess(JSONObject jsonObject) {
			JSONObject tempJSONObject = new JSONObject( );
			FutureRainWeather futureRainWeather = null;
			
			try {
				tempJSONObject = jsonObject.getJSONObject("item");
				futureRainWeather = new FutureRainWeather(tempJSONObject);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			text5.setText("6아침 날씨상태 : " + futureRainWeather.getWf3Am() + ", "
					 + futureRainWeather.getWf4Am() + ", "
					 + futureRainWeather.getWf5Am() + ", "
					 + futureRainWeather.getWf6Am() + ", "
					 + futureRainWeather.getWf7Am() + ", "
					 + futureRainWeather.getWf8());
		}
		
		@Override
		public void onFail() {
			Toast.makeText(getApplicationContext(), R.string.json_error, Toast.LENGTH_SHORT).show();	
			
		}
	};
	
	// 네트워크 수신상태가 안좋거나 기타의 문제가 발생시에 호출
	public void noLocationAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle("위치정보 추적 실패");

		// Setting Dialog Message
		alertDialog.setMessage("네트워크 불안정 또는 위치서비스 비활성화로 인한 오류가 발생하였습니다."
				+ "위치서비스 활성화 확인 후 재실행해 주시면 감사하겠습니다.");

		// On pressing Settings button
		alertDialog.setPositiveButton("종료",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});
		alertDialog.show();
	}

	// 사용자가 위치추적을 사용하지 않게 설정 되어있는 경우.
	public void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle("내 위치정보 사용동의");

		// Setting Dialog Message
		alertDialog.setMessage("위치정보 환경설정을 합니다." + "\n"
				+ "무선네트워크 / GPS 사용동의에 체크하셔야 합니다." + "\n"
				+ "- 무선네트워크는 필수사항 입니다. -");

		// On pressing Settings button
		alertDialog.setPositiveButton("동의",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Setting액티비티를 활성화 시킨다.
						Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivityForResult(intent, 1);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == 0) {

			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			// Network 수신가능한 상태인지 판단한다.
			isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
				
			// Network를 사용한다고 했을 때
			if ((isNetworkEnabled == true)) {
				try {
					// 위치추적을 설정한 후 사용자가 바로 누르는 것을 방지하기 위해
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			defineLocation(); // 다시 시작하기 위함.
		}
	}
}