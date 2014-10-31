package com.fatdog.WeatherMusic.reuse.network;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

public class JsonResponseHandlerForCuver extends JsonHttpResponseHandler{
	private HttpRequesterForLastFmCover.NetworkResponseListener networkResponseListener;
	private static final String PARM_TRACK = "track";
	private static final String PARM_ALBUM = "album";
	
	public JsonResponseHandlerForCuver(HttpRequesterForLastFmCover.NetworkResponseListener aNetworkResponseListener) {
		this.networkResponseListener = aNetworkResponseListener;
	}

	// 여기가 콜백 메소드 부분이다.
	// Fired when a request returns successfully
	@Override
	public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
		try {
			this.networkResponseListener.onSuccess(response.getJSONObject(PARM_TRACK).getJSONObject(PARM_ALBUM));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// Returns when request failed
	@Override
	public void onFailure(int statusCode, Header[] headers,Throwable throwable, JSONObject errorResponse) {
		this.networkResponseListener.onFail();
	}
}
