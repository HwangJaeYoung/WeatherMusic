package com.fatdog.WeatherMusic.reuse.etc;

import java.util.Calendar;
import java.util.Date;

public class DateCalculation {
	
	private int curHour;
	
	public String getYesterdayDate( ) {
		int yesterdayMonth = 0, yesterdayDay = 0, yesterdayYear = 0;
		
		Date today = new Date (); 
		Date yesterday = new Date ( );
		yesterday.setTime ( today.getTime ( ) - ((long) 1000 * 60 * 60 * 24 ));
		
		Calendar calendar = Calendar.getInstance ( );
		calendar.setTime ( yesterday );
		
		yesterdayMonth = calendar.get(Calendar.MONTH) + 1; // 내일 월
		yesterdayDay = calendar.get(Calendar.DATE); // 내일 일

		yesterdayYear = calendar.get(Calendar.YEAR);
		
		return yesterdayYear + checkFormat(yesterdayMonth, yesterdayDay);
	}
	
	public String getHour( ) {
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		curHour = calendar.get(Calendar.HOUR_OF_DAY);
		return String.valueOf(curHour - 1) + "00";
	}	
	
	private String checkFormat(int aMonth, int aDay) {

		String month, day = null;
		
		if(aMonth < 10)
			month = "0" + String.valueOf(aMonth);
		else
			month = String.valueOf(aMonth);
		
		if(aDay < 10)
			day = "0" + String.valueOf(aDay);
		else
			day = String.valueOf(aDay);
		
		return month + day;
	}
}
