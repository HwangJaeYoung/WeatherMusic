package com.fatdog.WeatherMusic.ui.genre_page_one;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fatdog.WeatherMusic.R;
import com.fatdog.WeatherMusic.reuse.mvc.fragement.AbstractViewForFragment;

public class ViewForBalladFragment extends AbstractViewForFragment{

	public ViewForBalladFragment(Context context,LayoutInflater layoutInflater, ViewGroup container) {
		super(context, layoutInflater, container);
	}

	@Override
	protected View inflate(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.fragment_ballard, container, false);
	}

	@Override
	protected void initViews() {

		
	}

	@Override
	protected void setEvents() {
	
	}

}
