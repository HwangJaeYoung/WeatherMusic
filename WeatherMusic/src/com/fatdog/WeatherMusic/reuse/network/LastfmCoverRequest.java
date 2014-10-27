package com.fatdog.WeatherMusic.reuse.network;

import org.json.JSONException;

import android.content.Context;

import com.loopj.android.http.RequestParams;

public class LastfmCoverRequest {
	private static final String PARM_ARTIST = "artist";
	private static final String PARM_TITLE = "track";
	private static final String PARM_FORMAT = "format";
	private Context context;

    public LastfmCoverRequest(Context aContext) {
		this.context = aContext;
	}
    
    // RTSP를 가져오는 수행을 한다.
    public void getLastFmCoverUrl(String anArtist, String aTitle, final HttpRequesterForLastFmCover.NetworkResponseListener aNetworkListener) throws JSONException {
    	RequestParams requestParams = new RequestParams( );
    	requestParams.put(PARM_ARTIST, anArtist);
    	requestParams.put(PARM_TITLE, aTitle);
    	requestParams.put(PARM_FORMAT, "json");
    	
    	HttpRequesterForLastFmCover.get("", requestParams, new JsonResponseHandlerForCuver(aNetworkListener), context);
    }
}