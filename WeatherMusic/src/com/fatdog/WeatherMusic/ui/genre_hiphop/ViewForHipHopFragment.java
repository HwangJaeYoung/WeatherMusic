package com.fatdog.WeatherMusic.ui.genre_hiphop;

import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.fatdog.WeatherMusic.R;
import com.fatdog.WeatherMusic.domain.CoverImage;
import com.fatdog.WeatherMusic.reuse.etc.CircularImageView;
import com.fatdog.WeatherMusic.reuse.mvc.fragement.AbstractViewForFragment;

public class ViewForHipHopFragment extends AbstractViewForFragment {

	private Button btHipHopPlayPause;
	private Button btHipHopNext;
	private TextView tvHipHopTrack;
	private TextView tvHipHopArtist;
	private TextView tvHipHopPlayingTime;
	private TextView tvHipHopMusicTime;
	private SeekBar sbHipHopMusicSeekbar;
	private Controller controller;
	private CircularImageView ivHipHopAlbumCover;
	private ProgressBar pbHipHopMusicLoading;
	
	public ViewForHipHopFragment(Context context,LayoutInflater layoutInflater, ViewGroup container, Controller aController) {
		super(context, layoutInflater, container);
		controller = aController;
	}

	@Override
	protected View inflate(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.fragment_ballard, container, false);
	}

	@Override
	protected void initViews() {
		btHipHopPlayPause = (Button)findViewById(R.id.bt_hiphop_play_pause);
		btHipHopNext = (Button)findViewById(R.id.bt_hiphop_next);
		tvHipHopTrack = (TextView)findViewById(R.id.tv_hiphop_track);
		tvHipHopArtist = (TextView)findViewById(R.id.tv_hiphop_artist);
		sbHipHopMusicSeekbar = (SeekBar)findViewById(R.id.sb_hiphop_music_seekbar);
		ivHipHopAlbumCover = (CircularImageView)findViewById(R.id.iv_hiphop_album_cover);
		tvHipHopPlayingTime = (TextView)findViewById(R.id.tv_hiphop_playing_time);
		tvHipHopMusicTime = (TextView)findViewById(R.id.tv_hiphop_music_time);
		pbHipHopMusicLoading = (ProgressBar)findViewById(R.id.pb_hiphop_music_loading);
	}

	@Override
	protected void setEvents() {
		btHipHopPlayPause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.startPauseMusic();
			}
		});
		
		btHipHopNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.nextMusicStart();
			}
		});
		
		sbHipHopMusicSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
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
	
	public void musicLoadingEnd( ) {
		pbHipHopMusicLoading.setVisibility(View.INVISIBLE);
		btHipHopPlayPause.setVisibility(View.VISIBLE);
		btHipHopNext.setVisibility(View.VISIBLE);
	}
	
	public void progressOn( ) {
		pbHipHopMusicLoading.setVisibility(View.VISIBLE);
		btHipHopPlayPause.setVisibility(View.INVISIBLE);
		btHipHopNext.setVisibility(View.INVISIBLE);
	}
	
	public void startButtonClicked( ) {
		btHipHopPlayPause.setBackgroundResource(R.drawable.play_btn);
	}
	
	public void pauseButtonClicked( ) {
		btHipHopPlayPause.setBackgroundResource(R.drawable.pause_btn);
	}
	
	public void setMusicTitle(String aTitle) {
		tvHipHopTrack.setText(aTitle);
	}
	
	public void setMusicArtist(String anArtist) {
		tvHipHopArtist.setText(anArtist);
	}
	
	public void setAlbumCover(CoverImage anImage) {
		ivHipHopAlbumCover.setImageUrl(anImage.getCoverURL());
	}
	
	public void setFirstAlbumCover(String aWeatherInfo) {
		int resourceId = 0;
		
		if(aWeatherInfo.equals("맑음"))
			resourceId = R.drawable.sunny;
		else if(aWeatherInfo.equals("흐림")) 
			resourceId = R.drawable.cloudy;
		else if(aWeatherInfo.equals("비"))
			resourceId = R.drawable.rain;
		else if(aWeatherInfo.equals("저녁"))
			resourceId = R.drawable.night;
		
		ivHipHopAlbumCover.setImageResource(resourceId);
	}
	
	public void setFirstWeatherInfo(String aWeatherInfo) {
		int textId = 0;
		
		if(aWeatherInfo.equals("맑음"))
			textId = R.string.sunny_text;
		else if(aWeatherInfo.equals("흐림")) 
			textId = R.string.cloudy_text;
		else if(aWeatherInfo.equals("비"))
			textId = R.string.rain_text;
		else if(aWeatherInfo.equals("저녁"))
			textId = R.string.night_text;
		
		tvHipHopArtist.setText(textId);
	}
	
	public void setSeekBarMax(int aMaxPlayTime) {
		sbHipHopMusicSeekbar.setMax(aMaxPlayTime);
		
		int check = (int) (TimeUnit.MILLISECONDS.toSeconds((long) aMaxPlayTime) - 
	            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
	            toMinutes((long) aMaxPlayTime)));
		
		if(check < 10)
			tvHipHopMusicTime.setText(String.format("%d:0%d", TimeUnit.MILLISECONDS.toMinutes((long) aMaxPlayTime), check));
		else
			tvHipHopMusicTime.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes((long) aMaxPlayTime), check));
	}
	
	public void setSeekBarPlayTime(double aStartTime) {
		int check = (int) (TimeUnit.MILLISECONDS.toSeconds((long) aStartTime) - 
	            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
	            toMinutes((long) aStartTime)));
		
		if(check < 10)
			tvHipHopPlayingTime.setText(String.format("%d:0%d", TimeUnit.MILLISECONDS.toMinutes((long) aStartTime), check));
		else
			tvHipHopPlayingTime.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes((long) aStartTime), check));			
	}
	
	
	public void setPregressAboutSeekBar(int aCurrentPosition)  {
		sbHipHopMusicSeekbar.setProgress(aCurrentPosition);
	}
	
	public static interface Controller {
		public void startPauseMusic( );
		public void nextMusicStart( );
		public void seekFromUser(int aProgress);
	}
}