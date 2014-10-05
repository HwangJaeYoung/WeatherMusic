package com.fatdog.WeatherMusic.reuse.network;

import org.json.JSONException;

import android.content.Context;

import com.loopj.android.http.RequestParams;

public class RTSPurlRequest{
	private Context context;

    public RTSPurlRequest(Context aContext) {
		this.context = aContext;
	}
    
    // RTSP를 가져오는 수행을 한다.
    public void getRTSTurlAbuoutYouTubeUrl(String aVideoId, final HttpRequesterForRTSP.NetworkResponseListener aNetworkListener) throws JSONException  {
    	RequestParams requestParams = new RequestParams( );		
    	HttpRequesterForRTSP.get(aVideoId + "?alt=json", requestParams, new JsonResponseHandlerForRTSP(aNetworkListener), context);
    }
}