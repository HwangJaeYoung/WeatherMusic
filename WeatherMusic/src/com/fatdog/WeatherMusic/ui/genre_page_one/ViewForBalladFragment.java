package com.fatdog.WeatherMusic.ui.genre_page_one;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fatdog.WeatherMusic.R;
import com.fatdog.WeatherMusic.reuse.mvc.fragement.AbstractViewForFragment;

public class ViewForBalladFragment extends AbstractViewForFragment{
	
	private Button btPlayPause;
	private Button bt_next;
	private Controller controller;
	
	public ViewForBalladFragment(Context context,LayoutInflater layoutInflater, ViewGroup container, Controller aController) {
		super(context, layoutInflater, container);
		controller = aController;
	}

	@Override
	protected View inflate(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.fragment_ballard, container, false);
	}

	@Override
	protected void initViews() {
		btPlayPause = (Button)findViewById(R.id.bt_play_pause);
		bt_next = (Button)findViewById(R.id.bt_next);
	}

	@Override
	protected void setEvents() {
		btPlayPause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.startPauseMusic();
			}
		});
	}
	
	public static interface Controller {
		void startPauseMusic( );
	}
}
