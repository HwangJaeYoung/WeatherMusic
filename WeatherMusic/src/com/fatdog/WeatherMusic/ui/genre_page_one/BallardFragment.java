package com.fatdog.WeatherMusic.ui.genre_page_one;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BallardFragment extends Fragment{
	private ViewForBalladFragment view;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// this는 Controller를 위해서 넣어주는 것이다.
        view = new ViewForBalladFragment(getActivity( ), inflater, container); // 뷰를 생성해 낸다.
        return view.getRoot();
    }
}
