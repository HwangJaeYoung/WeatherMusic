package com.fatdog.WeatherMusic.reuse.network;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

public class JsonResponseHandlerForLastFm extends JsonHttpResponseHandler {
	private HttpRequesterForLastFm.NetworkResponseListener networkResponseListener;

	public JsonResponseHandlerForLastFm(HttpRequesterForLastFm.NetworkResponseListener aNetworkResponseListener) {
		this.networkResponseListener = aNetworkResponseListener;
	}

	// 여기가 콜백 메소드 부분이다.
	// Fired when a request returns successfully
	@Override
	public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
		this.networkResponseListener.onSuccess(response);
	}

	// Returns when request failed
	@Override
	public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
		this.networkResponseListener.onFail();
	}
}