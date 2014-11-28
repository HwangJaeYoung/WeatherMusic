package com.fatdog.WeatherMusic.ui.favor_genrelist_page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.fatdog.WeatherMusic.R;
import com.fatdog.WeatherMusic.reuse.mvc.activity.AbstractViewForActivity;

public class ViewForFavorListActivity extends AbstractViewForActivity {

	public ViewForFavorListActivity(Context context) {
		super(context);
		
	}

	@Override
	protected View inflate() {
		return LayoutInflater.from(getContext()).inflate(R.layout.activity_webview, null);
	}

	@Override
	protected void initViews() {
		
	
	}

	@Override
	protected void setEvent() {
	
		
	}
}