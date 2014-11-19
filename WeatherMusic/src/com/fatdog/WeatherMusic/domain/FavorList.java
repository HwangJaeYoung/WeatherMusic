package com.fatdog.WeatherMusic.domain;

import org.json.JSONObject;

import com.fatdog.WeatherMusic.ui.favor_genrelist_page.listview.ViewForArrayAdapterForFavor.IFavorList;

public class FavorList implements IFavorList{

	private String videoKey;
	private String coverURL;
	private String artist;
	private String title;
	
	public FavorList(JSONObject aJSONObject) {
		
	}

	@Override
	public String getViedoKey() {
	
		return null;
	}

	@Override
	public String getArtist() {
		
		return null;
	}

	@Override
	public String getTitle() {
		
		return null;
	}

	@Override
	public String getCoverURL() {
		
		return null;
	}
}