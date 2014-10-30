package com.fatdog.WeatherMusic.reuse.etc;

import java.util.Calendar;
import java.util.Date;

public class DateCalculation {
	
	private int curHour;
	
	public String getYesterdayDate( ) { // 현재날짜기준의 전날자를 가지고 온다.
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
	
	public String getTomorrowDayDate( ) { // 현재 기준의 내일 날짜를 가지고 온다. 결국은 내일 모레의 날짜가 된다.
		int tomorrowMonth = 0, tomowworDay = 0, tomorrowYear = 0;
		
		Date today = new Date ();		
		Date tomorrow = new Date ( );
		tomorrow.setTime ( today.getTime ( ) + ((long) 1000 * 60 * 60 * 24 ));
		
		Calendar calendar = Calendar.getInstance ( );
		calendar.setTime ( tomorrow );
		
		tomorrowMonth = calendar.get(Calendar.MONTH) + 1; // 내일 월
		tomowworDay = calendar.get(Calendar.DATE); // 내일 일

		tomorrowYear = calendar.get(Calendar.YEAR);
		
		return tomorrowYear + checkFormat(tomorrowMonth, tomowworDay);
	}
	
	public String getTodayDate( ) {
		int todayMonth = 0, todayDay = 0, todayYear = 0;
		
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		// 오늘 날짜 구하기
		todayDay = calendar.get(Calendar.DATE); // 오늘 일
		todayMonth = calendar.get(Calendar.MONTH) + 1; // 오늘 월
		
		todayYear = calendar.get(Calendar.YEAR);
		
		return todayYear + checkFormat(todayMonth, todayDay);
	}
	
	public String getHour( ) {
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		String time = null;
		
		// 정확하게 가져오기 위해 한 시간을 뺀 전시간을 가지고 온다.
		curHour = calendar.get(Calendar.HOUR_OF_DAY);
		
		if(curHour == 2 | curHour == 3 | curHour == 4)
			curHour = 2;
		if(curHour == 5 | curHour == 6 | curHour == 7)
			curHour = 5;
		if(curHour == 8 | curHour == 9 | curHour == 10)
			curHour = 8;
		if(curHour == 11 | curHour == 12 | curHour == 13)
			curHour = 11;
		if(curHour == 14 | curHour == 15 | curHour == 16)
			curHour = 14;
		if(curHour == 17 | curHour == 18 | curHour == 19)
			curHour = 17;
		if(curHour == 20 | curHour == 21 | curHour == 22)
			curHour = 20;
		if(curHour == 23 | curHour == 0 | curHour == 1)
			curHour = 23;
			
		if(curHour < 10)
			time = "0" + String.valueOf(curHour);
		else
			time = String.valueOf(curHour);
		
		return time + "00";
	}
	
	public String realTime( ) {
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		String time = null;
		curHour = calendar.get(Calendar.HOUR_OF_DAY);
		
		if(curHour < 10)
			time = "0" + String.valueOf(curHour);
		else
			time = String.valueOf(curHour);
		
		return time + "00";
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
