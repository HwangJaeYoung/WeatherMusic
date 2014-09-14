package com.fatdog.WeatherMusic.reuse.network;

import org.json.JSONException;

import android.content.Context;

import com.loopj.android.http.RequestParams;

public class FutureWeatherRequest {
	private Context context;

    private static final String PARM_REGID = "regId";
    private static final String PARM_TMFC = "tmFc";

	private static final String URL_BASE = "getMiddleTemperature?ServiceKey=Dt4G7hV1JCZaZ7qgQ2QXdAjRgo/W7N4R1JsEO9Kez7YX/lX0LflHSeeXcJ/Nc1u3B0csKUqDxQjs9qUZN1HJrw==";

    public FutureWeatherRequest(Context aContext) {
		this.context = aContext;
	}
	
	public void getFutureWeather(final HttpRequester.NetworkResponseListener aNetworkListener, String aRedId, String aDate) throws JSONException {
		RequestParams requestParams = new RequestParams( );
		requestParams.put(PARM_REGID, aRedId);
		requestParams.put(PARM_TMFC, aDate);
		
		HttpRequester.getWeek(URL_BASE + "&_type=json", requestParams, new JsonResponseHandler(aNetworkListener), context);
	}
}
