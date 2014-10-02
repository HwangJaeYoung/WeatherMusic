package com.fatdog.WeatherMusic.ui.main_page;

import com.fatdog.WeatherMusic.ui.genre_page_one.ViewForBalladFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainPageFragment extends Fragment{
	private ViewForMainPageFragment view;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// this는 Controller를 위해서 넣어주는 것이다.
        view = new ViewForMainPageFragment(getActivity( ), inflater, container); // 뷰를 생성해 낸다.
        return view.getRoot();
    }
}
