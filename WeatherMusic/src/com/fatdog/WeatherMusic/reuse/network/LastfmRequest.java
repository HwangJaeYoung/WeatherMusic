package com.fatdog.WeatherMusic.reuse.network;

import org.json.JSONException;

import android.content.Context;

import com.loopj.android.http.RequestParams;

public class LastfmRequest {
	private Context context;

    public LastfmRequest(Context aContext) {
		this.context = aContext;
	}
    
    // RTSP를 가져오는 수행을 한다.
    public void getLastFmUrl(String aTag, final HttpRequesterForLastFm.NetworkResponseListener aNetworkListener) throws JSONException  {
    	RequestParams requestParams = new RequestParams( );		
    	HttpRequesterForLastFm.get(aTag, requestParams, new JsonResponseHandlerForLastFm(aNetworkListener), context);
    }
}
