package com.fatdog.WeatherMusic.reuse.network;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

public class HttpRequesterForLastFmCover {
    private static final String BASE_URL = "http://ws.audioscrobbler.com/2.0/?method=track.getInfo&api_key=1866f574dc385e541fe4cf5c4538459c";
    private static AsyncHttpClient client = new AsyncHttpClient();
    
    public static void post(String url, RequestParams params, JsonResponseHandlerForCuver responseHandler, Context aContext) {
    	request(url, params, responseHandler, aContext, true);
    }

    public static void get(String url, RequestParams params, JsonResponseHandlerForCuver responseHandler, Context aContext) {
    	request(url, params, responseHandler, aContext, false);
    }
    
    public static void request(String url, RequestParams params, JsonResponseHandlerForCuver responseHandler, Context aContext, boolean anIsPost) {
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
    
    // 처리를 위해 공통적인 규약을 준것이다.
    public static interface NetworkResponseListener {
        public void onSuccess(JSONObject jsonObject);
        public void onFail( );
    }
}
