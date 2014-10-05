package com.fatdog.WeatherMusic.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class FutureRainWeather {
	private static final String PARM_WF3AM = "wf3Am";
	private static final String PARM_WF4AM = "wf4Am";
	private static final String PARM_WF5AM = "wf5Am";
	private static final String PARM_WF6AM = "wf6Am";
	private static final String PARM_WF7AM = "wf7Am";

	private static final String PARM_WF3PM = "wf3Pm";
	private static final String PARM_WF4PM = "wf4Pm";
	private static final String PARM_WF5PM = "wf5Pm";
	private static final String PARM_WF6PM = "wf6Pm";
	private static final String PARM_WF7PM = "wf7Pm";
	
	private static final String PARM_WF8 = "wf8";
	
	// 오전 날씨 예보
	private String wf3Am;
	private String wf4Am;
	private String wf5Am;
	private String wf6Am;
	private String wf7Am;
	
	// 오후 날씨 예보
	private String wf3Pm;
	private String wf4Pm;
	private String wf5Pm;
	private String wf6Pm;
	private String wf7Pm;
	
	// 마지막날 날씨 예보 마지막은 하나 밖에 없음
	private String wf8;
	
	public FutureRainWeather(JSONObject aJSONObject) throws JSONException {
		wf3Am = aJSONObject.getString(PARM_WF3AM);
		wf4Am = aJSONObject.getString(PARM_WF4AM);
		wf5Am = aJSONObject.getString(PARM_WF5AM);
		wf6Am = aJSONObject.getString(PARM_WF6AM);
		wf7Am = aJSONObject.getString(PARM_WF7AM);
		
		wf3Pm = aJSONObject.getString(PARM_WF3PM);
		wf4Pm = aJSONObject.getString(PARM_WF4PM);
		wf5Pm = aJSONObject.getString(PARM_WF5PM);
		wf6Pm = aJSONObject.getString(PARM_WF6PM);
		wf7Pm = aJSONObject.getString(PARM_WF7PM);
		
		wf8 = aJSONObject.getString(PARM_WF8);
	}

	public String getWf3Am() {
		return wf3Am;
	}

	public String getWf4Am() {
		return wf4Am;
	}

	public String getWf5Am() {
		return wf5Am;
	}

	public String getWf6Am() {
		return wf6Am;
	}

	public String getWf7Am() {
		return wf7Am;
	}

	public String getWf3Pm() {
		return wf3Pm;
	}

	public String getWf4Pm() {
		return wf4Pm;
	}

	public String getWf5Pm() {
		return wf5Pm;
	}

	public String getWf6Pm() {
		return wf6Pm;
	}

	public String getWf7Pm() {
		return wf7Pm;
	}

	public String getWf8() {
		return wf8;
	}
}
