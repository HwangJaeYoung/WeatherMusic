package com.fatdog.WeatherMusic.reuse.etc;

public class LocationPosition {
	private int nx, ny; // 각 위치 지점.
	private String location; // 위치추적 해온 것
	
	public LocationPosition(String aLocation) {
		location = aLocation;
		setNxNyPosition( );
	}
	
	private void setNxNyPosition( ) {
		if(location.equals("서울특별시 광진구")) {
			nx = 62; ny = 126;			
		}
		
		else if(location.equals("서울특별시 성동구")) {
			nx = 62; ny = 126;			
		}
		
		else if(location.equals("서울특별시 용산구")) {
			nx = 62; ny = 126;			
		}
		
		else if(location.equals("서울특별시 종로구")) {
			nx = 62; ny = 126;			
		}
		
		else if(location.equals("서울특별시 강서구")) {
			nx = 62; ny = 126;			
		}
		
		else if(location.equals("서울특별시 양천구")) {
			nx = 62; ny = 126;			
		}
		
		else if(location.equals("서울특별시 구로구")) {
			nx = 62; ny = 126;			
		}
		
		else if(location.equals("서울특별시 동대문구")) {
			nx = 62; ny = 126;			
		}
		
		else if(location.equals("서울특별시 영등포구")) {
			nx = 62; ny = 126;			
		}
		
		else if(location.equals("서울특별시 금천구")) {
			nx = 62; ny = 126;			
		}
		
		else if(location.equals("서울특별시 서초구")) {
			nx = 62; ny = 126;			
		}
		
		else if(location.equals("서울특별시 강남구")) {
			nx = 62; ny = 126;			
		}
		
		else if(location.equals("서울특별시 송파구")) {
			nx = 62; ny = 126;			
		}
		
		else if(location.equals("서울특별시 강동구")) {
			nx = 62; ny = 126;			
		}
		
		else if(location.equals("서울특별시 마포구")) {
			nx = 62; ny = 126;			
		}
		
		else if(location.equals("서울특별시 서대문구")) {
			nx = 62; ny = 126;			
		}
		
		else if(location.equals("서울특별시 은평구")) {
			nx = 62; ny = 126;			
		}
		
		else if(location.equals("서울특별시 중구")) {
			nx = 62; ny = 126;			
		}
		
		else if(location.equals("서울특별시 성북구")) {
			nx = 62; ny = 126;			
		}
		
		else if(location.equals("서울특별시 강북구")) {
			nx = 62; ny = 126;			
		}
		
		else if(location.equals("서울특별시 도봉구")) {
			nx = 62; ny = 126;			
		}
		
		else if(location.equals("서울특별시 노원구")) {
			nx = 62; ny = 126;			
		}
		
		else if(location.equals("서울특별시 중랑구")) {
			nx = 62; ny = 126;			
		}
		
		else if(location.equals("서울특별시 동작구")) {
			nx = 62; ny = 126;			
		}
		
		else if(location.equals("서울특별시 관악구")) {
			nx = 62; ny = 126;			
		}
	}
	
	public int getNX( ) {
		return nx;
	}
	
	public int getNY( ) {
		return ny;
	}
}
