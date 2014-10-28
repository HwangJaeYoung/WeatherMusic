package com.fatdog.WeatherMusic.ui.genre_page_three;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fatdog.WeatherMusic.reuse.etc.WeatherMusicApplication;
import com.fatdog.WeatherMusic.reuse.network.HttpRequesterForRTSP;
import com.fatdog.WeatherMusic.reuse.network.RTSPurlRequest;
import com.fatdog.WeatherMusic.ui.genre_page_one.ViewForBalladFragment;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class HipHopFragment extends Fragment{
	private ViewForHipHopFragment view;
	private String videoId = "sr3JaQ3h7YA"; // 나중에 서버랑 통신해서 받음
	
	MediaPlayer mMediaPlayer = null;
	String MUSIC_URL = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WeatherMusicApplication wma = (WeatherMusicApplication)getActivity( ).getApplicationContext();
		mMediaPlayer = wma.getMediaPlayer();
	}
	
	@Override
	public void onDetach( ) {
		super.onDetach();
		mMediaPlayer.stop();
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// this는 Controller를 위해서 넣어주는 것이다.
        view = new ViewForHipHopFragment(getActivity( ), inflater, container); // 뷰를 생성해 낸다.
        serchRTSPurlFromYouTubeServer("4GW7xwZkDsY");
        
		mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mMediaPlayer.stop();
				serchRTSPurlFromYouTubeServer("_kr3bOs5s8U");					
			}
		});
		
        return view.getRoot();
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
			Log.i("js", "in");
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
			

		}	
		
		@Override
		public void onFail() { }
	};
}
