package com.fatdog.WeatherMusic.reuse.network;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

// BASE_URL등 기본적인 것들이 다르기 떄문에 날씨와 다르게 새로 정의하였다.
public class HttpRequesterForRTSP {
	private static final String BASE_URL = "http://gdata.youtube.com/feeds/api/videos/";
	private static AsyncHttpClient client = new AsyncHttpClient();

	public static void post(String url, RequestParams params, JsonResponseHandlerForRTSP responseHandler, Context aContext) {
		request(url, params, responseHandler, aContext, true);
	}

	public static void get(String url, RequestParams params, JsonResponseHandlerForRTSP responseHandler, Context aContext) {
		request(url, params, responseHandler, aContext, false);
	}

	public static void request(String url, RequestParams params, JsonResponseHandlerForRTSP responseHandler, Context aContext, boolean anIsPost) {
		Log.i("request", "Url: " + url);
		Log.i("request", "Parms: " + params.toString());

		if (anIsPost)
			client.post(getAbsoluteUrl(url), params, responseHandler);
		else
			client.get(getAbsoluteUrl(url), params, responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}

	// 처리를 위해 공통적인 규약을 준것이다.
	public static interface NetworkResponseListener {
		public void onSuccess(JSONObject jsonObject);
		public void onFail();
	}
}
