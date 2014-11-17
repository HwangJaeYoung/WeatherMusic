package com.fatdog.WeatherMusic.ui.genre_alternative;

import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.fatdog.WeatherMusic.R;
import com.fatdog.WeatherMusic.domain.CoverImage;
import com.fatdog.WeatherMusic.reuse.etc.CircularImageView;
import com.fatdog.WeatherMusic.reuse.mvc.fragement.AbstractViewForFragment;

public class ViewForAlternativeFragment extends AbstractViewForFragment {
	
	private Button bt_next;
	private Button btLike;
	private Button btList;
	private TextView tvTrack;
	private TextView tvArtist;
	private TextView tvPlayingTime;
	private TextView tvMusicTime;
	private SeekBar sbMusicSeekbar;
	private Controller controller;
	private CircularImageView ivAlbumCover;
	private CircularImageView ivAlbumCoverListener;
	private ProgressBar pbMusicLoading;
	
	public ViewForAlternativeFragment(Context context,LayoutInflater layoutInflater, ViewGroup container, Controller aController) {
		super(context, layoutInflater, container);
		controller = aController;
	}

	@Override
	protected View inflate(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.fragment_alternative, container, false);
	}

	@Override
	protected void initViews() {
		bt_next = (Button)findViewById(R.id.bt_next);
		tvTrack = (TextView)findViewById(R.id.tv_track);
		tvArtist = (TextView)findViewById(R.id.tv_artist);
		sbMusicSeekbar = (SeekBar)findViewById(R.id.sb_music_seekbar);
		ivAlbumCover = (CircularImageView)findViewById(R.id.iv_album_cover);
		tvPlayingTime = (TextView)findViewById(R.id.tv_playing_time);
		tvMusicTime = (TextView)findViewById(R.id.tv_music_time);
		pbMusicLoading = (ProgressBar)findViewById(R.id.pb_music_loading);
		btLike= (Button)findViewById(R.id.bt_like);
		btList = (Button)findViewById(R.id.bt_list);
		ivAlbumCoverListener = (CircularImageView)findViewById(R.id.iv_album_cover_listener);
	}

	@Override
	protected void setEvents() {
		ivAlbumCover.setOnClickListener(new View.OnClickListener() {
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
		
		btLike.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				controller.clickLike();			
			}
		});
		
		btList.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.clickList();				
			}
		});
	}
	
	public void musicLoadingEnd( ) {
		pbMusicLoading.setVisibility(View.INVISIBLE);
		sbMusicSeekbar.setVisibility(View.VISIBLE);
	}
	
	public void progressOn( ) {
		pbMusicLoading.setVisibility(View.VISIBLE);
		sbMusicSeekbar.setVisibility(View.INVISIBLE);
		bt_next.setVisibility(View.INVISIBLE);
	}
	
	public void startButtonClicked( ) {
		bt_next.setVisibility(View.VISIBLE); 
		ivAlbumCoverListener.setVisibility(View.INVISIBLE);
	}
	
	public void pauseButtonClicked( ) {
		ivAlbumCoverListener.setVisibility(View.VISIBLE);
	}
	
	public void nextButtonClicked( ) { // 노래 목록을 불러온 다음에 버튼을 보여주기 위해서 설정하였다.
		bt_next.setVisibility(View.VISIBLE);
	}
	
	public void setMusicTitle(String aTitle) {
		tvTrack.setText(aTitle);
		tvTrack.setSelected(true); // 말퀴 먹일려고 사용함
	}
	
	public void setMusicArtist(String anArtist) {
		tvArtist.setText(anArtist);
	}
	
	public void setAlbumCover(CoverImage anImage) {
		ivAlbumCover.setImageUrl(anImage.getCoverURL());
		
	}
	
	public void setTextViewInvisible( ) { // 노래 불러올 때 나머지 텍스트를 보이지 않게한다.
		tvArtist.setVisibility(View.INVISIBLE);
		tvTrack.setVisibility(View.INVISIBLE);
	}
	
	public void setTextViewVisible() { // 노래 불러올 때 나머지 텍스트를 보이게 한다.
		tvArtist.setVisibility(View.VISIBLE);
		tvTrack.setVisibility(View.VISIBLE);
	}
	
	public void nextButtonPregressBarOn( ) {
		pbMusicLoading.setVisibility(View.VISIBLE);
	}
	
	public void nextButtonPregressBarOff( ) {
		pbMusicLoading.setVisibility(View.INVISIBLE);
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
		
		ivAlbumCover.setImageResource(resourceId);
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
		
		tvArtist.setText(textId);
	}
	
	public void setSeekBarMax(int aMaxPlayTime) {
		sbMusicSeekbar.setMax(aMaxPlayTime);
		
		int check = (int) (TimeUnit.MILLISECONDS.toSeconds((long) aMaxPlayTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) aMaxPlayTime)));
		
		if(check < 10)
			tvMusicTime.setText(String.format("%d:0%d", TimeUnit.MILLISECONDS.toMinutes((long) aMaxPlayTime), check));
		else
			tvMusicTime.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes((long) aMaxPlayTime), check));
	}
	
	public void setSeekBarPlayTime(double aStartTime) {
		int check = (int) (TimeUnit.MILLISECONDS.toSeconds((long) aStartTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) aStartTime)));
		
		if(check < 10)
			tvPlayingTime.setText(String.format("%d:0%d", TimeUnit.MILLISECONDS.toMinutes((long) aStartTime), check));
		else
			tvPlayingTime.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes((long) aStartTime), check));			
	}
	
	public void setPregressAboutSeekBar(int aCurrentPosition)  {
		sbMusicSeekbar.setProgress(aCurrentPosition);
	}
	
	public static interface Controller {
		public void startPauseMusic( );
		public void nextMusicStart( );
		public void clickLike( );
		public void clickList( );
		public void seekFromUser(int aProgress);
	}
}
