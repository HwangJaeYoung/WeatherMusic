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
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.fatdog.WeatherMusic.domain.FutureWeather;
import com.fatdog.WeatherMusic.reuse.etc.DateCalculate;
import com.fatdog.WeatherMusic.reuse.network.CurrentWeatherRequest;
import com.fatdog.WeatherMusic.reuse.network.FutureWeatherRequest;
import com.fatdog.WeatherMusic.reuse.network.HttpRequester;
import com.fatdog.WeatherMusic.reuse.network.TomorrowWeatherRequest;

public class MainActivity extends Activity {
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
	private TextView logoText;
	private TextView text2;
	private TextView text3;
	private TextView text4;
   
	// 초기에 필요한 것들을 초기화 하는 생성자
	public void initLogoSplash() {
		lattitude = 0.0;
		longitude = 0.0;
		list = null;
		coder = new Geocoder(MainActivity.this);
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logoText = (TextView)findViewById(R.id.exam);
        text2 = (TextView)findViewById(R.id.exam2);
        text3 = (TextView)findViewById(R.id.exam3);
        text4 = (TextView)findViewById(R.id.exam4);
        initLogoSplash(); // 초기화를 시키고
        defineLocation();
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
					} else {
						// 만약 위치가 바뀌지 않았을 경우 이전의 데이터를 가지고 온다.
						location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						lattitude = location.getLatitude();
						longitude = location.getLongitude();
					}

					if (lattitude != 0 && longitude != 0) // 위치정보를 받아 왔을 경우에
						searchLocation();
					else
						// 그럼에도 불구하고 위치를 받아 오지 못했을 경우 Network -> GPS로 한번 더 기회를 준다.
						replaceToGPS();
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
					} else {
						location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						lattitude = location.getLatitude();
						longitude = location.getLongitude();
					}

					if (lattitude != 0 && longitude != 0) // 위치정보를 받아 왔을 경우에
						searchLocation();
					else 
						noLocationAlert();
						// GPS는 기회를 주지 않는다. 바로 종료를 시킨다.
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
		getTodayWeather( );
		getTomorrowWeather( );
		getFutureWeather( );
	}
	
	public void getTodayWeather( ) {
		DateCalculate date = new DateCalculate();
		date.calculateDate();
		
		CurrentWeatherRequest currentWeatherRequest = new CurrentWeatherRequest(getApplicationContext());
		try {
			currentWeatherRequest.getTodayWeather(getCurrentState, date.getTomorrow(), 62, 126);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	public void getTomorrowWeather( ) {
		DateCalculate date = new DateCalculate();
		date.calculateDate();
		
		TomorrowWeatherRequest tomorrowWeatherRequest = new TomorrowWeatherRequest(getApplicationContext());
		try {
			tomorrowWeatherRequest.getTomorrowWeather(getTomorrowState, date.getToday(), 62, 126);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void getFutureWeather( ) {
		DateCalculate date = new DateCalculate();
		date.calculateDate();
		
		FutureWeatherRequest futureWeatherRequest = new FutureWeatherRequest(getApplicationContext());
		
		try {
			futureWeatherRequest.getFutureWeather(getFutureState, "11B10101", date.getToday() + "0600");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	// 내일의 최저기온 / 최고기온을 가지고오는 통신을 한다.
	HttpRequester.NetworkResponseListener getTomorrowState = new HttpRequester.NetworkResponseListener() {
		
		@Override
		public void onSuccess(JSONObject jsonObject) {
			
			DateCalculate date = new DateCalculate();
			date.calculateDate();
			
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
					
					if (checkObject.getString("fcstDate").equals(date.getDayAfter())) {
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
			
			logoText.setText("내일 최저기온 : " + fsctValueTMN + ", " + "내일 최고기온 : " + fsctValueTMX);
			
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
		
			text2.setText("최고기온 : " + futureWeather.getTaMax3() + ", "
					 + futureWeather.getTaMax4() + ", "
					 + futureWeather.getTaMax5() + ", "
					 + futureWeather.getTaMax6() + ", "
					 + futureWeather.getTaMax7() + ", "
					 + futureWeather.getTaMax8());
			
			text3.setText("최저기온 : " + futureWeather.getTaMin3() + ", "
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
    
	HttpRequester.NetworkResponseListener getCurrentState = new HttpRequester.NetworkResponseListener() {
		
		@Override
		public void onSuccess(JSONObject jsonObject) {
		
			
		}
		
		@Override
		public void onFail() {
			
			
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
