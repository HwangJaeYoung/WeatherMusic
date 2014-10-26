package com.fatdog.WeatherMusic.ui.genre_page_one;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fatdog.WeatherMusic.R;
import com.fatdog.WeatherMusic.reuse.mvc.fragement.AbstractViewForFragment;

public class ViewForBalladFragment extends AbstractViewForFragment{
	
	private Button btPlayPause;
	private Button bt_next;
	private TextView tvTrack;
	private TextView tvArtist;
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
		tvTrack = (TextView)findViewById(R.id.tv_track);
		tvArtist = (TextView)findViewById(R.id.tv_artist);
	}

	@Override
	protected void setEvents() {
		btPlayPause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.startPauseMusic();
			}
		});
		
		bt_next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.nextMusicStart();
			}
		});
	}
	
	public void startButtonClicked( ) {
		btPlayPause.setBackgroundResource(R.drawable.play_btn);
	}
	
	public void pauseButtonClicked( ) {
		btPlayPause.setBackgroundResource(R.drawable.pause_btn);
	}
	
	public void setMusicTitle(String aTitle) {
		tvTrack.setText(aTitle);
	}
	
	public void setMusicArtist(String anArtist) {
		tvArtist.setText(anArtist);
	}
	
	public static interface Controller {
		public void startPauseMusic( );
		public void nextMusicStart( );
	}
}
