package com.fatdog.WeatherMusic.reuse.etc;

import java.util.Calendar;
import java.util.Date;

public class DateCalculate {
	private int curYear; // 올해 연도
	private int curMonth; // 오늘
	private int curDay;
	private int tomMonth; // 내일
	private int tomDay;
	private int afterMonth; // 내일 모레
	private int afterDay;
	
	private int curHour;
	
	/* if문에서 저러한 계산을 하는 이유는 
	 * 28, 30, 31일 이후에 달이 바뀌게 되는데
	 * 그때 바뀌는 달을 계산 하기 위해서 만들었다. */
	
	public void calculateDate() {
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		// 올해 연도 구하기
		curYear = calendar.get(Calendar.YEAR);
		
		// 오늘 날짜 구하기
		curDay = calendar.get(Calendar.DATE) - 1; // 오늘 일
		curMonth = calendar.get(Calendar.MONTH) + 1; // 오늘 월

		// 내일 날짜 구하기
		calendar.add(Calendar.DATE, 1);
		tomDay = calendar.get(Calendar.DATE) - 1;
		tomMonth = curMonth;

		if (curDay == 28 && tomDay == 1)
			tomMonth = curMonth + 1;
		else if (curDay == 30 && tomDay == 1)
			tomMonth = curMonth + 1;
		else if (curDay == 31 && tomDay == 1)
			tomMonth = curMonth + 1;
		
		// 내일 모레 날짜 구하기
		calendar.add(Calendar.DATE, 1);
		afterDay = calendar.get(Calendar.DATE) - 1;
		afterMonth = tomMonth;

		if (tomDay == 28 && afterDay == 1)
			afterMonth = tomMonth + 1;
		else if (tomDay == 30 && afterDay == 1)
			afterMonth = tomMonth + 1;
		else if (tomDay == 31 && afterDay == 1)
			afterMonth = tomMonth + 1;
		
		curHour = calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	public String getYear( ) {
		return String.valueOf(curYear);
	}
	
	public String getToday( ) {
		return curYear + checkFormat(curMonth, curDay);
	}
	
	public String getTomorrow( ) {
		return curYear + checkFormat(tomMonth, tomDay);
	}
	
	public String getDayAfter( ) {
		return curYear + checkFormat(afterMonth, afterDay);
	}
	
	public String getHour( ) {
		return String.valueOf(curHour - 1) + "00";
	}
	
	public String checkFormat(int aMonth, int aDay) {
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
