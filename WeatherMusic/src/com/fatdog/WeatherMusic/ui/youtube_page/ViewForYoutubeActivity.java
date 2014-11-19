package com.fatdog.WeatherMusic.ui.youtube_page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;

import com.fatdog.WeatherMusic.R;
import com.fatdog.WeatherMusic.reuse.mvc.activity.AbstractViewForActivity;

public class ViewForYoutubeActivity  extends AbstractViewForActivity  {
	
	private WebView webView;
	
	public ViewForYoutubeActivity(Context context) {
		super(context);
		
	}

	@Override
	protected View inflate() {
		return LayoutInflater.from(getContext()).inflate(R.layout.activity_webview, null);
	}

	@Override
	protected void initViews() {
		webView = (WebView)findViewById(R.id.webview);
		webView.loadUrl("http://www.youtube.com/watch?v=4Pax5vCQbMA");
	}

	@Override
	protected void setEvent() {

		
	}
	
}
