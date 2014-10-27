package com.fatdog.WeatherMusic.reuse.network;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

public class JsonResponseHandlerForCuver extends JsonHttpResponseHandler{
	private HttpRequesterForLastFmCover.NetworkResponseListener networkResponseListener;

	private static final String PARM_RESPONSE = "response";
	private static final String PARM_BODY = "body";
	private static final String PARM_ITEMS = "items";

	// &artist=cher&track=believe&format=json
	public JsonResponseHandlerForCuver(HttpRequesterForLastFmCover.NetworkResponseListener aNetworkResponseListener) {
		this.networkResponseListener = aNetworkResponseListener;
	}

	// 여기가 콜백 메소드 부분이다.
	// Fired when a request returns successfully
	@Override
	public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
		Log.i("json", response.toString());
		
	}

	// Returns when request failed
	@Override
	public void onFailure(int statusCode, Header[] headers,Throwable throwable, JSONObject errorResponse) {
		this.networkResponseListener.onFail();
	}
}
