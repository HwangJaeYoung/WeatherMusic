package com.fatdog.WeatherMusic;

import java.util.ArrayList;

import com.fatdog.WeatherMusic.domain.TrackList;
import com.fatdog.WeatherMusic.domain.WeatherInfo;
import com.fatdog.WeatherMusic.reuse.etc.WeatherMusicApplication;
import com.fatdog.WeatherMusic.ui.genre_alternative.ViewForAlternativeFragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TempFragment extends Fragment {
	
	private ViewForTempFragment view;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view =  new ViewForTempFragment(getActivity( ), inflater, container); // 뷰를 생성해 낸다.
        return view.getRoot();
    }
}
