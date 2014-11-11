package com.fatdog.WeatherMusic.ui.genre_accoustic;

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
import com.fatdog.WeatherMusic.ui.genre_alternative.ViewForAlternativeFragment.Controller;

public class ViewForAccousticFragement extends AbstractViewForFragment {
	private Button btAccouPlayPause;
	private Button btAccouNext;
	private Button btAccouLike;
	private Button btAccouList;
	private TextView tvAccouTrack;
	private TextView tvAccouArtist;
	private TextView tvAccouPlayingTime;
	private TextView tvAccouMusicTime;
	private SeekBar sbAccouMusicSeekbar;
	private Controller controller;
	private CircularImageView ivAccouAlbumCover;
	private ProgressBar pbAccouMusicLoading;
	
	public ViewForAccousticFragement(Context context,LayoutInflater layoutInflater, ViewGroup container, Controller aController) {
		super(context, layoutInflater, container);
		controller = aController;
	}

	@Override
	protected View inflate(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.fragment_accoustic, container, false);
	}

	@Override
	protected void initViews() {
		btAccouPlayPause = (Button)findViewById(R.id.bt_accu_play_pause);
		btAccouNext = (Button)findViewById(R.id.bt_accu_next);
		tvAccouTrack = (TextView)findViewById(R.id.tv_accu_track);
		tvAccouArtist = (TextView)findViewById(R.id.tv_accu_artist);
		sbAccouMusicSeekbar = (SeekBar)findViewById(R.id.sb_accu_music_seekbar);
		ivAccouAlbumCover = (CircularImageView)findViewById(R.id.iv_accu_album_cover);
		tvAccouPlayingTime = (TextView)findViewById(R.id.tv_accu_playing_time);
		tvAccouMusicTime = (TextView)findViewById(R.id.tv_accu_music_time);
		pbAccouMusicLoading = (ProgressBar)findViewById(R.id.pb_accu_music_loading);
		btAccouLike= (Button)findViewById(R.id.bt_accu_like);
		btAccouList = (Button)findViewById(R.id.bt_accu_list);
	}

	@Override
	protected void setEvents() {
		btAccouPlayPause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.startPauseMusic();
			}
		});
		
		btAccouNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.nextMusicStart();
			}
		});
		
		sbAccouMusicSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
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
		
		btAccouLike.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				controller.clickLike();			
			}
		});
		
		btAccouList.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.clickList();				
			}
		});
	}
	
	public void musicLoadingEnd( ) {
		pbAccouMusicLoading.setVisibility(View.INVISIBLE);
		btAccouPlayPause.setVisibility(View.VISIBLE);
		btAccouNext.setVisibility(View.VISIBLE);
		tvAccouPlayingTime.setVisibility(View.VISIBLE);
		tvAccouMusicTime.setVisibility(View.VISIBLE);
		sbAccouMusicSeekbar.setVisibility(View.VISIBLE);
	}
	
	public void progressOn( ) {
		pbAccouMusicLoading.setVisibility(View.VISIBLE);
		btAccouPlayPause.setVisibility(View.INVISIBLE);
		btAccouNext.setVisibility(View.INVISIBLE);
	}
	
	public void startButtonClicked( ) {
		btAccouPlayPause.setBackgroundResource(R.drawable.bt_play);
	}
	
	public void pauseButtonClicked( ) {
		btAccouPlayPause.setBackgroundResource(R.drawable.pause_btn);
	}
	
	public void setMusicTitle(String aTitle) {
		tvAccouTrack.setText(aTitle);
	}
	
	public void setMusicArtist(String anArtist) {
		tvAccouArtist.setText(anArtist);
	}
	
	public void setAlbumCover(CoverImage anImage) {
		ivAccouAlbumCover.setImageUrl(anImage.getCoverURL());
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
		
		ivAccouAlbumCover.setImageResource(resourceId);
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
		
		tvAccouArtist.setText(textId);
	}
	
	public void setSeekBarMax(int aMaxPlayTime) {
		sbAccouMusicSeekbar.setMax(aMaxPlayTime);
		
		int check = (int) (TimeUnit.MILLISECONDS.toSeconds((long) aMaxPlayTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) aMaxPlayTime)));
		
		if(check < 10)
			tvAccouMusicTime.setText(String.format("%d:0%d", TimeUnit.MILLISECONDS.toMinutes((long) aMaxPlayTime), check));
		else
			tvAccouMusicTime.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes((long) aMaxPlayTime), check));
	}
	
	public void setSeekBarPlayTime(double aStartTime) {
		int check = (int) (TimeUnit.MILLISECONDS.toSeconds((long) aStartTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) aStartTime)));
		
		if(check < 10)
			tvAccouPlayingTime.setText(String.format("%d:0%d", TimeUnit.MILLISECONDS.toMinutes((long) aStartTime), check));
		else
			tvAccouPlayingTime.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes((long) aStartTime), check));			
	}
	
	public void setPregressAboutSeekBar(int aCurrentPosition)  {
		sbAccouMusicSeekbar.setProgress(aCurrentPosition);
	}
	
	public static interface Controller {
		public void startPauseMusic( );
		public void nextMusicStart( );
		public void clickLike( );
		public void clickList( );
		public void seekFromUser(int aProgress);
	}
}
