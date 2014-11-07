package com.fatdog.WeatherMusic.ui.login_page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.fatdog.WeatherMusic.R;
import com.fatdog.WeatherMusic.reuse.mvc.activity.AbstractViewForActivity;

public class ViewForLoginActivity extends AbstractViewForActivity{

	private Controller controller;
	private Button bt_loginPageLogin;
	private ProgressBar pgSignUp;
	
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
		pgSignUp = (ProgressBar)findViewById(R.id.pg_sign_up);		
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
	
	public void lockButton ( ) {
		bt_loginPageLogin.setEnabled(false);
		bt_loginPageLogin.setText("");
		pgSignUp.setVisibility(View.VISIBLE);
	}
	
	public void unLockButton( ) {
		bt_loginPageLogin.setEnabled(true);
		bt_loginPageLogin.setText("Login With Facebook");
		pgSignUp.setVisibility(View.INVISIBLE);
	}
	
	public static interface Controller { 
		public void onLogin( );
	}
}
