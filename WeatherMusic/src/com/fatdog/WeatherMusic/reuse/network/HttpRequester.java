package com.fatdog.WeatherMusic.reuse.network;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpRequester {
    private static final String BASE_URL = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService/";
    private static final String BASE_URL_SECOND = "http://newsky2.kma.go.kr/service/MiddleFrcstInfoService/";
    
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void post(String url, RequestParams params, JsonHttpResponseHandler responseHandler, Context aContext) {
    	request(url, params, responseHandler, aContext, true);
    }

    public static void get(String url, RequestParams params, JsonHttpResponseHandler responseHandler, Context aContext) {
    	request(url, params, responseHandler, aContext, false);
    }
    
    public static void request(String url, RequestParams params, JsonHttpResponseHandler responseHandler, Context aContext, boolean anIsPost) {
    	Log.i("request", "Url: "+url);
        Log.i("request", "Parms: " + params.toString());
       
        if(anIsPost)
        	client.post(getAbsoluteUrl(url), params, responseHandler);
        else
        	client.get(getAbsoluteUrl(url), params, responseHandler);
    }
    
    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
    
    public static void postWeek(String url, RequestParams params, JsonHttpResponseHandler responseHandler, Context aContext) {
    	requestWeek(url, params, responseHandler, aContext, true);
    }

    public static void getWeek(String url, RequestParams params, JsonHttpResponseHandler responseHandler, Context aContext) {
    	requestWeek(url, params, responseHandler, aContext, false);
    }
    
    public static void requestWeek(String url, RequestParams params, JsonHttpResponseHandler responseHandler, Context aContext, boolean anIsPost) {
    	Log.i("request", "Url: "+url);
        Log.i("request", "Parms: " + params.toString());
       
        if(anIsPost)
        	client.post(getAbsoluteWeekUrl(url), params, responseHandler);
        else
        	client.get(getAbsoluteWeekUrl(url), params, responseHandler);
    }
    
    private static String getAbsoluteWeekUrl(String relativeUrl) {
        return BASE_URL_SECOND + relativeUrl;
    }
    
    // 처리를 위해 공통적인 규약을 준것이다.
    public static interface NetworkResponseListener {
        public void onSuccess(JSONObject jsonObject);
        public void onFail( );
    }
}
