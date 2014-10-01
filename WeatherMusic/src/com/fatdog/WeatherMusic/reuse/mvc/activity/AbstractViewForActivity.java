package com.fatdog.WeatherMusic.reuse.mvc.activity;

import android.content.Context;
import android.view.View;

public abstract class AbstractViewForActivity {
	private View root;
	private Context context;

	public AbstractViewForActivity(Context context) {
		this.context = context;
		this.root = inflate(); // 실질적인 뷰를 생성해내는 부분
		initViews();
		setEvent();
	}

	public View findViewById(int aResourceId) {
		return getRoot().findViewById(aResourceId);
	}

	abstract protected View inflate(); // inflate

	abstract protected void initViews(); // findByViewId

	abstract protected void setEvent(); // click event etc...

	public View getRoot() { // inflate된 뷰를 가지고 온다.
		return this.root;
	}

	public Context getContext() { // context가 필요할 때 사용
		return this.context;
	}
}
