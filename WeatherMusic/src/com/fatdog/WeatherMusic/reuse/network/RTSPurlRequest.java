package com.fatdog.WeatherMusic.reuse.network;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.RequestParams;

public class RTSPurlRequest{
	private Context context;

    public RTSPurlRequest(Context aContext) {
		this.context = aContext;
	}
    
    public void getRTSTurlAbuoutYouTubeUrl(String aVideoId, final HttpRequesterForRTSP.NetworkResponseListener aNetworkListener) throws JSONException  {
    	RequestParams requestParams = new RequestParams( );		
    	HttpRequesterForRTSP.get(aVideoId + "?alt=json", requestParams, new JsonResponseHandlerForRTSP(aNetworkListener), context);
    	Log.i("net", "request in");
    }
}