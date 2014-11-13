package com.fatdog.WeatherMusic;

import android.os.Bundle;
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
