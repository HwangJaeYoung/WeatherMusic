package com.fatdog.WeatherMusic.ui.login_page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.fatdog.WeatherMusic.R;
import com.fatdog.WeatherMusic.reuse.mvc.activity.AbstractViewForActivity;

public class ViewForLoginActivity extends AbstractViewForActivity{

	private Controller controller;
	private Button bt_loginPageLogin;
	
	public ViewForLoginActivity(Context context, Controller aController) {
		super(context);
		controller = aController;
	}

	@Override
	protected View inflate() {
		return LayoutInflater.from(getContext()).inflate(R.layout.activity_login, null);
	}

	@Override
	protected void initViews() {
		bt_loginPageLogin = (Button)findViewById(R.id.bt_login_page_login);
		
	}

	@Override
	protected void setEvent() {
		bt_loginPageLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.onLogin();
			}
		});
	}
	
	public static interface Controller { 
		public void onLogin( );
	}
}
