package com.fatdog.WeatherMusic.domain;

import android.util.Log;

public class WeatherInfo {
	private int skyInfo;
	private int ptyInfo;
	private int curHour;
	
	private String weatherText = null;

	public WeatherInfo( ) { }
	
	public WeatherInfo(String aSky, String aPty, String aHour) {
		Log.i("timetime", aSky + ","+ aPty + ","+ aHour);
		skyInfo = Integer.parseInt(aSky);
		ptyInfo = Integer.parseInt(aPty);
		curHour = Integer.parseInt(aHour);
	}

	public String weatherInformation( ) {
		
		if(ptyInfo == 0) { // 강수형태가 0, 즉 비, 눈이 안내릴 때 
			if(curHour > 600 && curHour < 1700) { // 저녁 7시 이전이면, 그리고 새벽...
				if(skyInfo == 1)  // 맑음
					weatherText = "맑음";
				else if(skyInfo == 2 | skyInfo == 3 | skyInfo == 4) // 흐림
					weatherText = "흐림";
			}
			else
				weatherText = "저녁";
		}
		
		else if(ptyInfo == 1 | ptyInfo == 2 | ptyInfo == 3) { // 비가 내릴 떄
			weatherText = "비";
		}
		
		return weatherText;
	}
	
	public String getWeatherTag() {
		if (weatherText.equals("비") || weatherText.equals("흐림") || weatherText.equals("저녁")) {
			return "cloudy";
		} else {
			return "sunny";
		}
	}
}