package com.fatdog.WeatherMusic.ui.genre_rnb;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fatdog.WeatherMusic.MainActivity;
import com.fatdog.WeatherMusic.R;
import com.fatdog.WeatherMusic.domain.CoverImage;
import com.fatdog.WeatherMusic.domain.TrackList;
import com.fatdog.WeatherMusic.domain.WeatherInfo;
import com.fatdog.WeatherMusic.reuse.etc.WeatherMusicApplication;
import com.fatdog.WeatherMusic.reuse.network.HttpRequesterForLastFm;
import com.fatdog.WeatherMusic.reuse.network.HttpRequesterForLastFmCover;
import com.fatdog.WeatherMusic.reuse.network.HttpRequesterForRTSP;
import com.fatdog.WeatherMusic.reuse.network.LastfmCoverRequest;
import com.fatdog.WeatherMusic.reuse.network.LastfmRequest;
import com.fatdog.WeatherMusic.reuse.network.RTSPurlRequest;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class RNBFragment extends Fragment implements ViewForRNBFragment.Controller {
	private ViewForRNBFragment view;
	private int length;
	private int musicPlayCount = 0;
	private boolean firstPlaying = false;
	private boolean reloadingFlag = false;
	private MediaPlayer mMediaPlayer = null;
	private ArrayList<TrackList> trackInfo;
	private double startTime = 0;
	private Handler musicHandler;
	private Handler mHandler = new Handler( );
	private HandlerThread mHandlerThread;
	
	private String weatherString = null; // 맑음, 흐림 같은 날씨 정보를 들고 있다.
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WeatherMusicApplication wma = (WeatherMusicApplication)getActivity( ).getApplicationContext();
		mMediaPlayer = wma.getMediaPlayer();
		trackInfo = new ArrayList<TrackList>( );	
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// this는 Controller를 위해서 넣어주는 것이다.
        view = new ViewForRNBFragment(getActivity( ), inflater, container, this); // 뷰를 생성해 낸다.
		
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
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mHandlerThread = new HandlerThread("SearchThread4"); // 위치추적 통신이 MainActivity에서 끝났는지 확인하기 위한 스레드
		mHandlerThread.start();
		musicHandler = new Handler(mHandlerThread.getLooper());
		musicHandler.post(new Runnable( ) {
			@Override
			public void run() {
				for ( ; ; ) {
					if (MainActivity.LOCATION_SEARCH_END == 1) { // 위치추적 통신이 끝났으면
						MainActivity getMainAct = (MainActivity)getActivity( );
						final WeatherInfo weatherInfo = getMainAct.getWeatherInfo();
						
						if(weatherInfo != null) {
							
							searchLastFmVidieKey("alternative_folk_rock"); // 노래를 오현 서버에 가서 가지고 온다.
							
							mHandler.post(new Runnable() { // 메인 스레드 에서는 기본적인 이미지와 멘트를 추가한다.
								public void run() {
									weatherString = weatherInfo.weatherInformation();
									view.setFirstAlbumCover(weatherString);
									view.setFirstWeatherInfo(weatherString);
								}
							});
							break;
						}
					}
				}
			}
		});
	}
	
	@Override
	public void onDetach( ) {
		super.onDetach();
		if(mMediaPlayer.isPlaying())
			mMediaPlayer.stop(); // 노래 재생을 아예 중지한다.
	}
	
	public void serchRTSPurlFromYouTubeServer( ) { // rtsp프로토콜을 구글에서 가지고 온다.
		
		if(musicPlayCount == 10) { // 노래 목록을 모두 사용했을 경우
			musicPlayCount = 0; // 노래 재생횟수를 0으로 초기화
			searchLastFmVidieKey("alternative_folk_rock");
			view.setTextViewInvisible();
			view.setSeekBarMax(0);
			view.setSeekBarPlayTime(0);
			view.progressOn( ); // 사용자가 키를 못눌리게 한다.
			trackInfo.clear(); // 리스트를 초기화 시켜준다.
			reloadingFlag = true; // 다시 로드 했다는 것을 알려준다.
		}
		
		else { // 일반적인 통신
			TrackList tr = trackInfo.get(musicPlayCount);
			searchLastFmCover(tr.getArtist(), tr.getTitle()); // 앨범 커버를 가지고 온다.

			musicPlayCount++; // 한곡을 들었다.

			view.setMusicTitle(tr.getTitle());
			view.setMusicArtist(tr.getArtist());

			RTSPurlRequest RTSPurlRequest = new RTSPurlRequest(getActivity());
			try {
				RTSPurlRequest.getRTSTurlAbuoutYouTubeUrl(tr.getVideoKey(),
						getRTSPListener);
			} catch (JSONException e) {
				e.printStackTrace();
			}
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
	public void seekFromUser(int aProgress) { // 마음대로 아무 위치에서나 재생하기 위한 것.
		  mMediaPlayer.seekTo(aProgress);
	}
	
	// 앨범 커버를 lastfm에가서 가지고 온다.
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
			view.setAlbumCover(image); // 가지고 온 앨범 커버 이미지를 교체한다.
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
			
			// 통신이 끝났으므로 프로그레스바를 없애고 재생을 위해 버튼을 보여준다.
			mHandler.post(new Runnable() {
				public void run() {
					view.musicLoadingEnd();
				}
			});	
			
			if(reloadingFlag == true) { // 다시 노래 목록을 가지고 온거라면
				view.musicLoadingEnd( ); // 다시 프로그레스 바를 돌린다 가져올 때 까지
				view.nextButtonClicked( ); // 다음재생 버튼을 활성화 시킨다.
				view.setTextViewVisible();
				reloadingFlag = false; // 플레그를 초기화 시켜주고
				serchRTSPurlFromYouTubeServer( ); // 다시 통신을 시작한다. 시작시키기 위해서 
			}
		}

		@Override
		public void onFail() { }
	};
	
	// 구글 데이터에서 rtsp를 가지고온 다음에 노래를 재생한다.
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
					
					if(resultRTSP.equals("rtsp")) { // rtsp catch
						MUSIC_URL = rtspString;						
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}						
			
			if(MUSIC_URL != null) {
				mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mMediaPlayer.reset(); // MediaPlayer 생명주기에 따른 것
			
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
			
			mMediaPlayer.start(); // 스레드의 시작
			mHandler.postDelayed(UpdateSongTime, 1000); // 1초마다 업데이트 한다.
			view.setSeekBarMax(mMediaPlayer.getDuration()); // 최대 시간을 가지고 온다.
			view.setSeekBarPlayTime(startTime);
			view.nextButtonPregressBarOff();
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
			view.setSeekBarPlayTime(startTime); // 이동시간 변경
	        view.setPregressAboutSeekBar((int)startTime); // 프로그레스바의 이동
	        mHandler.postDelayed(this, 1000); // 1초 마다 실행한다.
		}
	};
	
	@Override
	public void startPauseMusic() {
		if (firstPlaying == false) {
			serchRTSPurlFromYouTubeServer(); // 처음에는 노래를 가지고 온다.
			view.startButtonClicked();
			firstPlaying = true; // 들었으므로 플래그 변환
		} else {
			if (mMediaPlayer.isPlaying()) { // 노래를 듣고 있다. 즉 노래를 멈추기 위해
				length = mMediaPlayer.getCurrentPosition(); // 듣고 있었떤 위치의 저장
				mMediaPlayer.pause(); // 노래 일시정지
				view.pauseButtonClicked(); 
			} else { // 노래 재생

				mMediaPlayer.seekTo(length); // 듣고 있었던 위치를 재생한다.
				mMediaPlayer.start(); // 노래를 시작한다.
				view.startButtonClicked();
			}
		}
	}

	@Override
	public void nextMusicStart() { // 다음 노래 버튼을 클릭하였을 때
		view.nextButtonPregressBarOn();
		view.startButtonClicked();
		mMediaPlayer.stop();
		serchRTSPurlFromYouTubeServer( ); // 노래 재생	
	}

	@Override
	public void clickLike() {
		Toast.makeText(getActivity(), R.string.ready_toast, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void clickList() {
		Toast.makeText(getActivity(), R.string.ready_toast, Toast.LENGTH_SHORT).show();
	}
}
