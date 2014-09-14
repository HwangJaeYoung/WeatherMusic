package com.fatdog.WeatherMusic.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class FutureWeather {
	public static final String PARM_TAMAX3 = "taMax3";
	public static final String PARM_TAMAX4 = "taMax4";
	public static final String PARM_TAMAX5 = "taMax5";
	public static final String PARM_TAMAX6 = "taMax6";
	public static final String PARM_TAMAX7 = "taMax7";
	public static final String PARM_TAMAX8 = "taMax8";
	
	public static final String PARM_TAMIN3 = "taMin3";
	public static final String PARM_TAMIN4 = "taMin4";
	public static final String PARM_TAMIN5 = "taMin5";
	public static final String PARM_TAMIN6 = "taMin6";
	public static final String PARM_TAMIN7 = "taMin7";
	public static final String PARM_TAMIN8 = "taMin8";
	
	// 오늘로 부터 3일 후 부터 시작되는 최대기온
	private String taMax3;
	private String taMax4;
	private String taMax5;
	private String taMax6;
	private String taMax7;
	private String taMax8;

	// 오늘로 부터 3일 후 부터 시작되는 최저기온
	private String taMin3;
	private String taMin4;
	private String taMin5;
	private String taMin6;
	private String taMin7;
	private String taMin8;

	public FutureWeather(JSONObject aJSONObject) throws JSONException {
		taMax3 = aJSONObject.getString(PARM_TAMAX3);
		taMax4 = aJSONObject.getString(PARM_TAMAX4);
		taMax5 = aJSONObject.getString(PARM_TAMAX5);
		taMax6 = aJSONObject.getString(PARM_TAMAX6);
		taMax7 = aJSONObject.getString(PARM_TAMAX7);
		taMax8 = aJSONObject.getString(PARM_TAMAX8);
		
		taMin3 = aJSONObject.getString(PARM_TAMIN3);
		taMin4 = aJSONObject.getString(PARM_TAMIN4);
		taMin5 = aJSONObject.getString(PARM_TAMIN5);
		taMin6 = aJSONObject.getString(PARM_TAMIN6);
		taMin7 = aJSONObject.getString(PARM_TAMIN7);
		taMin8 = aJSONObject.getString(PARM_TAMIN8);
	}

	public String getTaMax3() {
		return taMax3;
	}

	public String getTaMax4() {
		return taMax4;
	}

	public String getTaMax5() {
		return taMax5;
	}

	public String getTaMax6() {
		return taMax6;
	}

	public String getTaMax7() {
		return taMax7;
	}

	public String getTaMax8() {
		return taMax8;
	}

	public String getTaMin3() {
		return taMin3;
	}

	public String getTaMin4() {
		return taMin4;
	}

	public String getTaMin5() {
		return taMin5;
	}

	public String getTaMin6() {
		return taMin6;
	}

	public String getTaMin7() {
		return taMin7;
	}

	public String getTaMin8() {
		return taMin8;
	}
}
