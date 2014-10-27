package com.fatdog.WeatherMusic.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class CoverImage {
	public static final String PARM_COVER_URL = "#text";
	private String coverURL;

	public CoverImage(JSONObject aJSONObject) throws JSONException {
		coverURL = aJSONObject.getString(PARM_COVER_URL);
	}

	public String getCoverURL() {
		return coverURL;
	}
}
