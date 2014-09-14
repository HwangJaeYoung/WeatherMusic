package com.fatdog.WeatherMusic.reuse.etc;

import java.util.Calendar;
import java.util.Date;

public class DateCalculate {
	private int curYear; // 오늘
	private int curMonth; 
	private int curDay;
	
	/* if문에서 저러한 계산을 하는 이유는 
	 * 28, 30, 31일 이후에 달이 바뀌게 되는데
	 * 그때 바뀌는 달을 계산 하기 위해서 만들었다. */
	
	public String getPastDate( ) {
		String month = null;
		String day = null;
		
		Calendar c = null;
		c = Calendar.getInstance();
		c.setTime(new Date());

		// 오늘 날짜 구하기
		curYear = c.get(Calendar.YEAR); // 해당연도
		curMonth = c.get(Calendar.MONTH) + 1; // 오늘 월	
		curDay = c.get(Calendar.DATE) - 1; // 오늘 일
		
		if(curMonth < 10) {
			month = "0" + curMonth;
		} else {
			month = String.valueOf(curMonth);
		}
		
		if(curDay < 10) {
			day = "0" + curDay;
		} else {
			day = String.valueOf(curDay);
		}
		
		return curYear + month + day;
	}
}
