package com.fatdog.WeatherMusic.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class TrackList {
	public static final String PARM_VIDEO_KEY = "vID";
	public static final String PARM_TITLE = "title";
	public static final String PARM_ARTIST = "creator";
	
	private String videoKey;
	private String title;
	private String artist;
	
	public TrackList(JSONObject aJSONObject) throws JSONException {
		videoKey = aJSONObject.getString(PARM_VIDEO_KEY);
		title = aJSONObject.getString(PARM_TITLE);
		artist = aJSONObject.getString(PARM_ARTIST);
	}

	public String getVideoKey() {
		return videoKey;
	}

	public String getTitle() {
		return title;
	}

	public String getArtist() {
		return artist;
	}
}