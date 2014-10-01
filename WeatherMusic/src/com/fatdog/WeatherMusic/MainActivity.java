package com.fatdog.WeatherMusic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.WindowManager;

import com.fatdog.WeatherMusic.reuse.etc.BackPressCloseHandler;
import com.fatdog.WeatherMusic.ui.genre_page_one.BallardFragment;
import com.fatdog.WeatherMusic.ui.genre_page_three.HipHopFragment;
import com.fatdog.WeatherMusic.ui.genre_page_two.RNBFragment;
import com.fatdog.WeatherMusic.ui.navigation_drawer_menu.NavigationDrawerFragment;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
	private NavigationDrawerFragment mNavigationDrawerFragment;
	private BackPressCloseHandler backPressCloseHandler;
	private int currentMenuIndex = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		backPressCloseHandler = new BackPressCloseHandler(this);
		setContentView(R.layout.activity_main);
		// 정적으로 정의된 프래그먼트이므로 여기서는 라이프사이클을 수행한 프래그먼트가 넘어오게 된다.
		mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager( ).findFragmentById(R.id.navigation_drawer);
	    mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout)findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
        int previousMenuIndex = currentMenuIndex;
        currentMenuIndex = position; // 현재 사용자가 클릭한 메뉴의 번호, 처음에는 0이 넘어온다.
        if(currentMenuIndex != previousMenuIndex) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (position) {
            	// add는 기존의 것을 그대로 놔두며 추가하고, replace는 기존의 것을 제거하고 추가한다.
            	// 동적으로 프래그먼트를 정의하며, 레이아웃에 추가 될 때 라이프사이클을 돈다.
                case 0: // FindingJob Menu(Home)
                    transaction.replace(R.id.container, new BallardFragment()).commit();
                    break;
                default: // etc...
                    break;
            }
        }
	}

	@Override
	public void onBackPressed() {
		backPressCloseHandler.onBackPressed();
	}

}
