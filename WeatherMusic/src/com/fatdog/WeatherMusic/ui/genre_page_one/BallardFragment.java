package com.fatdog.WeatherMusic.ui.genre_page_one;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fatdog.WeatherMusic.MainActivity;
import com.fatdog.WeatherMusic.domain.CoverImage;
import com.fatdog.WeatherMusic.domain.TrackList;
import com.fatdog.WeatherMusic.reuse.etc.WeatherMusicApplication;
import com.fatdog.WeatherMusic.reuse.network.HttpRequesterForLastFm;
import com.fatdog.WeatherMusic.reuse.network.HttpRequesterForLastFmCover;
import com.fatdog.WeatherMusic.reuse.network.HttpRequesterForRTSP;
import com.fatdog.WeatherMusic.reuse.network.LastfmCoverRequest;
import com.fatdog.WeatherMusic.reuse.network.LastfmRequest;
import com.fatdog.WeatherMusic.reuse.network.RTSPurlRequest;

public class BallardFragment extends Fragment implements ViewForBalladFragment.Controller {
	private ViewForBalladFragment view;
	private int length;
	private int musicPlayCount = 0;
	private boolean firstPlaying = false;
	private MediaPlayer mMediaPlayer = null;
	private ArrayList<TrackList> trackInfo;
	private double startTime = 0;
	private Handler musicHandler;
	private Handler mHandler = new Handler( );
	private HandlerThread mHandlerThread;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WeatherMusicApplication wma = (WeatherMusicApplication)getActivity( ).getApplicationContext();
		mMediaPlayer = wma.getMediaPlayer();
		trackInfo = new ArrayList<TrackList>( );	
		
		mHandlerThread = new HandlerThread("SearchThread");
		mHandlerThread.start();
		musicHandler = new Handler(mHandlerThread.getLooper());
		musicHandler.post(new Runnable( ) {
			@Override
			public void run() {
				for ( ; ; ) {
					if (MainActivity.LOCATION_SEARCH_END == 1) {
						MainActivity.LOCATION_SEARCH_END = 0;
						searchLastFmVidieKey("alternative_folk_rock");
						
						mHandler.post(new Runnable() {
							public void run() {
								view.setFirstAlbumCover();
							}
						});	
						break;
					}
				}
			}
		});
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// this는 Controller를 위해서 넣어주는 것이다.
        view = new ViewForBalladFragment(getActivity( ), inflater, container, this); // 뷰를 생성해 낸다.
		mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // 노래를 다 들었을 경우에
			@Override
			public void onCompletion(MediaPlayer mp) {
				mMediaPlayer.stop();
				view.setSeekBarMax(0);
				view.setSeekBarPlayTime(0);
				serchRTSPurlFromYouTubeServer( ); // 노래를 가지고 온다. 즉 재생한다.	 				
			}
		});
        return view.getRoot();
    }
	
	@Override
	public void onDetach( ) {
		super.onDetach();
		mMediaPlayer.stop(); // 노래 재생을 아예 중지한다.
	}
	
	public void serchRTSPurlFromYouTubeServer( ) { // rtsp프로토콜을 구글에서 가지고 온다.
		TrackList tr = trackInfo.get(musicPlayCount);
		searchLastFmCover(tr.getArtist(), tr.getTitle()); // 앨범 커버를 가지고 온다.

		musicPlayCount++; // 한곡을 들었다.
		
		view.setMusicTitle(tr.getTitle());
		view.setMusicArtist(tr.getArtist());
		
		RTSPurlRequest RTSPurlRequest = new RTSPurlRequest(getActivity());
		try {
			RTSPurlRequest.getRTSTurlAbuoutYouTubeUrl(tr.getVideoKey(), getRTSPListener);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void searchLastFmVidieKey(String aTag) { // php서버에 접속하여 비디오키 10개를 가지고 온다.
		
		LastfmRequest lfr = new LastfmRequest(getActivity());
		try {
			lfr.getLastFmUrl(aTag, getLastFmListener);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void searchLastFmCover(String anArtist, String aTitle) { // 앨범 커버의 검색
		LastfmCoverRequest lfcr = new LastfmCoverRequest(getActivity());
		try {
			lfcr.getLastFmCoverUrl(anArtist, aTitle, getLastfmCoverListener);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void seekFromUser(int aProgress) {
		  mMediaPlayer.seekTo(aProgress);
	}
	
	HttpRequesterForLastFmCover.NetworkResponseListener getLastfmCoverListener = new HttpRequesterForLastFmCover.NetworkResponseListener() {	
		@Override
		public void onSuccess(JSONObject jsonObject) {
			JSONArray tempJSONArray = new JSONArray( );
			try {
				tempJSONArray = jsonObject.getJSONArray("image");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			CoverImage image = null;
			
			for(int i = 0; i < tempJSONArray.length() ; i++) {
				try {
					image = new CoverImage(tempJSONArray.getJSONObject(i));

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			view.setAlbumCover(image);
		}
		
		@Override
		public void onFail() { }
	};
	
	HttpRequesterForLastFm.NetworkResponseListener getLastFmListener = new HttpRequesterForLastFm.NetworkResponseListener( )  {
		@Override
		public void onSuccess(JSONObject jsonObject) {
			JSONArray tempJSONArray = new JSONArray( );
			try {
				tempJSONArray = jsonObject.getJSONArray("track_list");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			for(int i = 0; i < 10; i++) { // 노래를 10곡 가지고 온다.
				try {
					TrackList tr = new TrackList(tempJSONArray.getJSONObject(i));
					trackInfo.add(tr);	
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			mHandler.post(new Runnable() {
				public void run() {
					view.musicLoadingEnd();
				}
			});	
			
			Log.i("lastfm", "" + trackInfo.size());
		}

		@Override
		public void onFail() { }
	};
	
	HttpRequesterForRTSP.NetworkResponseListener getRTSPListener = new HttpRequesterForRTSP.NetworkResponseListener() {
		@Override
		public void onSuccess(JSONObject jsonObject) {
			JSONArray tempJsonArray = null;
			String MUSIC_URL = null;
			
			try {
				tempJsonArray = jsonObject.getJSONObject("media$group").getJSONArray("media$content");
				
				
				for(int i = 0; i < tempJsonArray.length(); i++) {
					JSONObject urlObject = tempJsonArray.getJSONObject(i);
					String rtspString = urlObject.getString("url");
					String resultRTSP = rtspString.split(":")[0];
					
					if(resultRTSP.equals("rtsp")) {
						MUSIC_URL = rtspString;						
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}						
			
			if(MUSIC_URL != null) {
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mMediaPlayer.reset();
			
			try {
				mMediaPlayer.setDataSource(MUSIC_URL);
			} catch (IllegalArgumentException e) {
				Toast.makeText(getActivity(), "Illegal", Toast.LENGTH_LONG).show();
			} catch (SecurityException e) {
				Toast.makeText(getActivity(), "Security", Toast.LENGTH_LONG).show();
			} catch (IllegalStateException e) {
				Toast.makeText(getActivity(), "IllegalState", Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				mMediaPlayer.prepare();
			} catch (IllegalStateException e) {
				Toast.makeText(getActivity(), "prepare IllegalState", Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				Toast.makeText(getActivity(), "prepare IOException", Toast.LENGTH_LONG).show();
			}
			
			mMediaPlayer.start();
			mHandler.postDelayed(UpdateSongTime, 1000);
			view.setSeekBarMax(mMediaPlayer.getDuration());
			view.setSeekBarPlayTime(startTime);
			
			}
			
			else
				serchRTSPurlFromYouTubeServer( ); // rtsp가 없어서 못 가지고 왔을 경우에
		}	
		
		@Override
		public void onFail() { }
	};

	// 노래 재생 스레드
	private Runnable UpdateSongTime = new Runnable() {
		public void run() {
			startTime = mMediaPlayer.getCurrentPosition();
			view.setSeekBarPlayTime(startTime);
	        view.setPregressAboutSeekBar((int)startTime);
	        mHandler.postDelayed(this, 1000);
		}
	};
	
	@Override
	public void startPauseMusic() {
		if (firstPlaying == false) {
			serchRTSPurlFromYouTubeServer(); // 처음에는 노래를 가지고 온다.
			view.pauseButtonClicked();
			firstPlaying = true;
		} else {
			if (mMediaPlayer.isPlaying()) {
				length = mMediaPlayer.getCurrentPosition();
				mMediaPlayer.pause();
				view.startButtonClicked();
			} else {

				mMediaPlayer.seekTo(length);
				mMediaPlayer.start();
				view.pauseButtonClicked();
			}
		}
	}

	@Override
	public void nextMusicStart() {
		view.pauseButtonClicked();
		mMediaPlayer.stop();
		mMediaPlayer.reset();
		serchRTSPurlFromYouTubeServer( );	
	}

}