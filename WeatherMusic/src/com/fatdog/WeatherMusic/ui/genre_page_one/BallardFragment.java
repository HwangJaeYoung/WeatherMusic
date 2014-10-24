package com.fatdog.WeatherMusic.ui.genre_page_one;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fatdog.WeatherMusic.reuse.etc.WeatherMusicApplication;
import com.fatdog.WeatherMusic.reuse.network.HttpRequesterForRTSP;
import com.fatdog.WeatherMusic.reuse.network.RTSPurlRequest;

public class BallardFragment extends Fragment implements ViewForBalladFragment.Controller {
	private ViewForBalladFragment view;
	private String videoId = "sr3JaQ3h7YA"; // 나중에 서버랑 통신해서 받음
	private int length;
	MediaPlayer mMediaPlayer = null;
	String MUSIC_URL = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WeatherMusicApplication wma = (WeatherMusicApplication)getActivity( ).getApplicationContext();
		mMediaPlayer = wma.getMediaPlayer();
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// this는 Controller를 위해서 넣어주는 것이다.
        view = new ViewForBalladFragment(getActivity( ), inflater, container, this); // 뷰를 생성해 낸다.
        serchRTSPurlFromYouTubeServer("sr3JaQ3h7YA");
        
		mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mMediaPlayer.stop();
				mMediaPlayer.reset();
				serchRTSPurlFromYouTubeServer("_kr3bOs5s8U");					
			}
		});
		
        return view.getRoot();
    }
	
	@Override
	public void onDetach( ) {
		super.onDetach();
		mMediaPlayer.stop();
		mMediaPlayer.reset();
	}
	
	public void serchRTSPurlFromYouTubeServer(String aVideoId) {
		RTSPurlRequest RTSPurlRequest = new RTSPurlRequest(getActivity());
		try {
			RTSPurlRequest.getRTSTurlAbuoutYouTubeUrl(aVideoId, getRTSPListener);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	HttpRequesterForRTSP.NetworkResponseListener getRTSPListener = new HttpRequesterForRTSP.NetworkResponseListener() {
		@Override
		public void onSuccess(JSONObject jsonObject) {
			JSONArray tempJsonArray = null;
			JSONObject titleObject = null;
			String MUSIC_URL = null;
			String musicTitle = null;
			
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
			
			try {
				titleObject = jsonObject.getJSONObject("media$group").getJSONObject("media$title");
				musicTitle = titleObject.getString("$t");
				view.setMusicTitle(musicTitle);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

			try {
				mMediaPlayer.setDataSource(MUSIC_URL);
			} catch (IllegalArgumentException e) {
				Toast.makeText(getActivity(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
			} catch (SecurityException e) {
				Toast.makeText(getActivity(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
			} catch (IllegalStateException e) {
				Toast.makeText(getActivity(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				mMediaPlayer.prepare();
			} catch (IllegalStateException e) {
				Toast.makeText(getActivity(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				Toast.makeText(getActivity(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
			}
			mMediaPlayer.start();
		}	
		
		@Override
		public void onFail() { }
	};

	@Override
	public void startPauseMusic() {
		if(mMediaPlayer.isPlaying()) {
			length = mMediaPlayer.getCurrentPosition();
			mMediaPlayer.pause();
			view.startButtonClicked();
		}
		else {
			mMediaPlayer.seekTo(length);
			mMediaPlayer.start();		
			view.pauseButtonClicked();
		}
	}

	@Override
	public void nextMusicStart() {
		mMediaPlayer.stop();
		mMediaPlayer.reset();
		serchRTSPurlFromYouTubeServer("_kr3bOs5s8U");	
	}
}