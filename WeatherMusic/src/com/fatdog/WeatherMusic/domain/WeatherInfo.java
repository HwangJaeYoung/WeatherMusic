package com.fatdog.WeatherMusic.domain;

public class WeatherInfo {
	private int skyInfo;
	private int ptyInfo;
	private int curHour;

	public WeatherInfo( ) { }
	
	public WeatherInfo(String aSky, String aPty, String aHour) {
		skyInfo = Integer.parseInt(aSky);
		ptyInfo = Integer.parseInt(aPty);
		curHour = Integer.parseInt(aHour);
	}

	public String weatherInformation( ) {
		String weatherText = null;
		
		if(ptyInfo == 0) { // 강수형태가 0, 즉 비, 눈이 안내릴 때 
			if(curHour < 1900) { // 저녁 7시 이전이면
				if(skyInfo == 1)  // 맑음
					weatherText = "맑음";
				else if(skyInfo == 2 | skyInfo == 3 | skyInfo == 4) // 흐림
					weatherText = "흐림";
			}
			else
				weatherText = "흐림";
		}
		
		else if(ptyInfo == 1 | ptyInfo == 2 | ptyInfo == 3) { // 비가 내릴 떄
			weatherText = "비";
		}
		
		return weatherText;
	}
}