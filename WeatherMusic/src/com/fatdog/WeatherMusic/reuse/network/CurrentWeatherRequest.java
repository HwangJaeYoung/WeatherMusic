package com.fatdog.WeatherMusic.reuse.network;

import org.json.JSONException;

import android.content.Context;

import com.loopj.android.http.RequestParams;

public class CurrentWeatherRequest {
	private Context context;

    private static final String PARM_BASE_TIME = "base_time";
    private static final String PARM_BASE_DATE = "base_date";
    private static final String PARM_NX = "nx";
    private static final String PARM_NY = "ny";
    
	private static final String URL_BASE = "ForecastGrib?ServiceKey=Dt4G7hV1JCZaZ7qgQ2QXdAjRgo/W7N4R1JsEO9Kez7YX/lX0LflHSeeXcJ/Nc1u3B0csKUqDxQjs9qUZN1HJrw==";

    public CurrentWeatherRequest(Context aContext) {
		this.context = aContext;
	}
	
	public void getTodayWeather(final HttpRequester.NetworkResponseListener aNetworkListener, String aDate, String aTime, int aNX, int aNY) throws JSONException {
		RequestParams requestParams = new RequestParams( );
		requestParams.put(PARM_BASE_DATE, aDate);
		requestParams.put(PARM_BASE_TIME, aTime);
		requestParams.put(PARM_NX, aNX);
		requestParams.put(PARM_NY, aNY);
		
		HttpRequester.get(URL_BASE + "&_type=json", requestParams, new JsonResponseHandler(aNetworkListener), context);
	}
}
