package com.fatdog.WeatherMusic.ui.genre_rnb;

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

public class ViewForRNBFragment  extends AbstractViewForFragment {
	private Button btRNBPlayPause;
	private Button btRNBNext;
	private Button btRNBLike;
	private Button btRNBList;
	private TextView tvRNBTrack;
	private TextView tvRNBArtist;
	private TextView tvRNBPlayingTime;
	private TextView tvRNBMusicTime;
	private SeekBar sbRNBMusicSeekbar;
	private Controller controller;
	private CircularImageView ivRNBAlbumCover;
	private ProgressBar pbRNBMusicLoading;
	
	public ViewForRNBFragment(Context context,LayoutInflater layoutInflater, ViewGroup container, Controller aController) {
		super(context, layoutInflater, container);
		controller = aController;
	}

	@Override
	protected View inflate(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.fragment_rnb, container, false);
	}

	@Override
	protected void initViews() {
		btRNBPlayPause = (Button)findViewById(R.id.bt_rnb_play_pause);
		btRNBNext = (Button)findViewById(R.id.bt_rnb_next);
		tvRNBTrack = (TextView)findViewById(R.id.tv_rnb_track);
		tvRNBArtist = (TextView)findViewById(R.id.tv_rnb_artist);
		sbRNBMusicSeekbar = (SeekBar)findViewById(R.id.sb_rnb_music_seekbar);
		ivRNBAlbumCover = (CircularImageView)findViewById(R.id.iv_rnb_album_cover);
		tvRNBPlayingTime = (TextView)findViewById(R.id.tv_rnb_playing_time);
		tvRNBMusicTime = (TextView)findViewById(R.id.tv_rnb_music_time);
		pbRNBMusicLoading = (ProgressBar)findViewById(R.id.pb_rnb_music_loading);
		btRNBLike= (Button)findViewById(R.id.bt_rnb_like);
		btRNBList = (Button)findViewById(R.id.bt_rnb_list);
	}

	@Override
	protected void setEvents() {
		btRNBPlayPause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.startPauseMusic();
			}
		});
		
		btRNBNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.nextMusicStart();
			}
		});
		
		sbRNBMusicSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
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
		
		btRNBLike.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				controller.clickLike();			
			}
		});
		
		btRNBList.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.clickList();				
			}
		});
	}
	
	public void musicLoadingEnd( ) {
		pbRNBMusicLoading.setVisibility(View.INVISIBLE);
		btRNBPlayPause.setVisibility(View.VISIBLE);
		btRNBNext.setVisibility(View.VISIBLE);
		tvRNBPlayingTime.setVisibility(View.VISIBLE);
		tvRNBMusicTime.setVisibility(View.VISIBLE);
		sbRNBMusicSeekbar.setVisibility(View.VISIBLE);
	}
	
	public void progressOn( ) {
		pbRNBMusicLoading.setVisibility(View.VISIBLE);
		btRNBPlayPause.setVisibility(View.INVISIBLE);
		btRNBNext.setVisibility(View.INVISIBLE);
	}
	
	public void startButtonClicked( ) {
		btRNBPlayPause.setBackgroundResource(R.drawable.bt_play);
	}
	
	public void pauseButtonClicked( ) {
		btRNBPlayPause.setBackgroundResource(R.drawable.pause_btn);
	}
	
	public void setMusicTitle(String aTitle) {
		tvRNBTrack.setText(aTitle);
	}
	
	public void setMusicArtist(String anArtist) {
		tvRNBArtist.setText(anArtist);
	}
	
	public void setAlbumCover(CoverImage anImage) {
		ivRNBAlbumCover.setImageUrl(anImage.getCoverURL());
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
		
		ivRNBAlbumCover.setImageResource(resourceId);
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
		
		tvRNBArtist.setText(textId);
	}
	
	public void setSeekBarMax(int aMaxPlayTime) {
		sbRNBMusicSeekbar.setMax(aMaxPlayTime);
		
		int check = (int) (TimeUnit.MILLISECONDS.toSeconds((long) aMaxPlayTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) aMaxPlayTime)));
		
		if(check < 10)
			tvRNBMusicTime.setText(String.format("%d:0%d", TimeUnit.MILLISECONDS.toMinutes((long) aMaxPlayTime), check));
		else
			tvRNBMusicTime.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes((long) aMaxPlayTime), check));
	}
	
	public void setSeekBarPlayTime(double aStartTime) {
		int check = (int) (TimeUnit.MILLISECONDS.toSeconds((long) aStartTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) aStartTime)));
		
		if(check < 10)
			tvRNBPlayingTime.setText(String.format("%d:0%d", TimeUnit.MILLISECONDS.toMinutes((long) aStartTime), check));
		else
			tvRNBPlayingTime.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes((long) aStartTime), check));			
	}
	
	public void setPregressAboutSeekBar(int aCurrentPosition)  {
		sbRNBMusicSeekbar.setProgress(aCurrentPosition);
	}
	
	public static interface Controller {
		public void startPauseMusic( );
		public void nextMusicStart( );
		public void clickLike( );
		public void clickList( );
		public void seekFromUser(int aProgress);
	}
}
