package com.fatdog.WeatherMusic.ui.genre_page_one;

import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.fatdog.WeatherMusic.R;
import com.fatdog.WeatherMusic.domain.CoverImage;
import com.fatdog.WeatherMusic.reuse.etc.CircularImageView;
import com.fatdog.WeatherMusic.reuse.mvc.fragement.AbstractViewForFragment;

public class ViewForBalladFragment extends AbstractViewForFragment{
	
	private Button btPlayPause;
	private Button bt_next;
	private TextView tvTrack;
	private TextView tvArtist;
	private TextView tvPlayingTime;
	private TextView tvMusicTime;
	private SeekBar sbMusicSeekbar;
	private Controller controller;
	private CircularImageView ivAlbumCover;
	
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
		sbMusicSeekbar = (SeekBar)findViewById(R.id.sb_music_seekbar);
		ivAlbumCover = (CircularImageView)findViewById(R.id.iv_album_cover);
		tvPlayingTime = (TextView)findViewById(R.id.tv_playing_time);
		tvMusicTime = (TextView)findViewById(R.id.tv_music_time);
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
		
		sbMusicSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) { }
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) { }
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if(fromUser) {
					controller.seekFromUser(progress);
				}
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
	
	public void setAlbumCover(CoverImage anImage) {
		ivAlbumCover.setImageUrl(anImage.getCoverURL());
	}
	
	public void setSeekBarMax(int aMaxPlayTime) {
		sbMusicSeekbar.setMax(aMaxPlayTime);
		tvMusicTime.setText(String.format("%d:%d", 
	            TimeUnit.MILLISECONDS.toMinutes((long) aMaxPlayTime),
	            TimeUnit.MILLISECONDS.toSeconds((long) aMaxPlayTime) - 
	            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
	            toMinutes((long) aMaxPlayTime))));
	}
	
	public void setSeekBarPlayTime(double aStartTime) {
		tvPlayingTime.setText(String.format("%d:%d", 
	            TimeUnit.MILLISECONDS.toMinutes((long) aStartTime),
	            TimeUnit.MILLISECONDS.toSeconds((long) aStartTime) - 
	            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
	            toMinutes((long) aStartTime))));
	}
	
	
	public void setPregressAboutSeekBar(int aCurrentPosition)  {
		sbMusicSeekbar.setProgress(aCurrentPosition);
	}
	
	public static interface Controller {
		public void startPauseMusic( );
		public void nextMusicStart( );
		public void seekFromUser(int aProgress);
	}
}
