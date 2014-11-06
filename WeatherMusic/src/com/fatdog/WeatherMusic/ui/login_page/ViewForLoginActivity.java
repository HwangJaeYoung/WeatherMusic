package com.fatdog.WeatherMusic.ui.login_page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.fatdog.WeatherMusic.R;
import com.fatdog.WeatherMusic.reuse.etc.SubmitButton;
import com.fatdog.WeatherMusic.reuse.mvc.activity.AbstractViewForActivity;

public class ViewForLoginActivity extends AbstractViewForActivity{

	private Controller controller;
	private SubmitButton bt_loginPageLogin;
	
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
		bt_loginPageLogin = (SubmitButton)findViewById(R.id.bt_login_page_login);
		
		bt_loginPageLogin.init((ProgressBar)findViewById(R.id.pg_sign_up));
		bt_loginPageLogin.addViewToHold(bt_loginPageLogin);
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
	
	
	public void releateButton ( ) {
		bt_loginPageLogin.setEnabled(true);
	}
	
	public static interface Controller { 
		public void onLogin( );
	}
}
