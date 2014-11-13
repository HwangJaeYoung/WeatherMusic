package com.fatdog.WeatherMusic.ui.navigation_drawer_menu;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.widget.ProfilePictureView;
import com.fatdog.WeatherMusic.R;
import com.fatdog.WeatherMusic.reuse.etc.WeatherMusicApplication;

public class NavigationDrawerFragment extends Fragment {
	private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
	private NavigationDrawerCallbacks mCallbacks;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerListView;
	private View mFragmentContainerView;
	private int mCurrentSelectedPosition = 0;
	private View root;

	private TextView tvNavigationDrawerMenuUser;	
	private ProfilePictureView profilePicture;
	
	private Boolean firstChoice = true;
	private SharedPreferences prefs; // 첫 화면 로딩
	
	private ImageView ivNavigationDrawerMenuSetting;

	
	public NavigationDrawerFragment() { }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState != null) // 저장되어 있던 선택된 항목의 번호를 줌. 
			mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);

		  prefs = PreferenceManager.getDefaultSharedPreferences(getActivity()); // 프레퍼런스 초기화
		  
          if(firstChoice == true)
        	  mCurrentSelectedPosition = prefs.getInt("favorGenreNumber", 5); // false이면 처음에 설치한 사람이다.
          
		// 첫번째 또는 마지막에 선택된 항목을 보여준다.
		selectItem(mCurrentSelectedPosition);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true); // 메뉴를 사용한다는 것을 알려준다.
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// 해당 프레그먼트의 뷰를 inflate한다.(드로워 메뉴)
		root = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
		mDrawerListView = (ListView) root.findViewById(R.id.lv_navigation_drawer_menu);
	
		mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selectItem(position);
			}
		});
		
		ArrayAdapterForNavigationDrawerMenuListView arrayAdapter = new ArrayAdapterForNavigationDrawerMenuListView(getActivity(), 0);

		arrayAdapter.add(new ViewForNavigationDrawerMenuListViewItem.DrawerMenuItem(R.drawable.alternative));
		arrayAdapter.add(new ViewForNavigationDrawerMenuListViewItem.DrawerMenuItem(R.drawable.hiphop));
		arrayAdapter.add(new ViewForNavigationDrawerMenuListViewItem.DrawerMenuItem(R.drawable.acoustic));
		arrayAdapter.add(new ViewForNavigationDrawerMenuListViewItem.DrawerMenuItem(R.drawable.rnb));	
		
		mDrawerListView.setAdapter(arrayAdapter);
		
		profilePicture = (ProfilePictureView)root.findViewById(R.id.profilePicture);
		tvNavigationDrawerMenuUser = (TextView)root.findViewById(R.id.tv_navigation_drawer_menu_user);
		
		WeatherMusicApplication wma = (WeatherMusicApplication)getActivity( ).getApplicationContext();
		
		profilePicture.setProfileId(wma.getUserId());
		tvNavigationDrawerMenuUser.setText(wma.getUserName());	
		
		ivNavigationDrawerMenuSetting = (ImageView)root.findViewById(R.id.iv_navigation_drawer_menu_setting);
		
		ivNavigationDrawerMenuSetting.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCallbacks.settingClick();
			}
		});
		
		return root;
	}
	
	// 사용자가 드로워 메뉴 중 하나를 선택 하였을 때
	private void selectItem(int position) {
		mCurrentSelectedPosition = position;

		if (mDrawerLayout != null) { // 열려있는 드로워를 닫아 버린다.
			mDrawerLayout.closeDrawer(mFragmentContainerView);
		}
		// 처음에는 index(0)에 해당하는 findingjob을 켜 놓는다.
		if (mCallbacks != null) { // 클릭시 수행 할 행동
			mCallbacks.onNavigationDrawerItemSelected(position);
		}
	}

	public void setUp(int fragmentId, DrawerLayout drawerLayout) {
		// 프래그먼트의 컨테이너뷰(부모 뷰)를 찾는다고 생각하면 된다.
		mFragmentContainerView = getActivity().findViewById(fragmentId);
		mDrawerLayout = drawerLayout;

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setIcon(android.R.color.transparent);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#323a45")));
		
		// ActionBarDrawerToggle은 navigation drawer와 action bar app icon을 연결해 서로 상호 작용이 가능하게 한다.
		mDrawerToggle = new ActionBarDrawerToggle(getActivity(), // host Activity
		mDrawerLayout, // DrawerLayout 객체
		R.drawable.bt_menu, // 네비게이션 이미지 
		R.string.title_section1, // 열었을 때 설명 
		R.string.title_section1 // 닫았을 때 설명 
		) {
			// 드로워를 닫았을 때 동작하는 방식
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) 
                    return;
            }

            // 드로워를 열었을 때 동작하는 방식
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded())
                    return;
            }
        };

		mDrawerLayout.post(new Runnable() {
			@Override
			public void run() {
				mDrawerToggle.syncState();
			}
		});
		mDrawerLayout.setDrawerListener(mDrawerToggle);		
	}
	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			// NavigationDrawerCallbacks를 구현하고 있는 액티비티를 가지고 온다.
			mCallbacks = (NavigationDrawerCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}

	// 현재 어떤 메뉴를 눌렸는지에 대한 상태를 저장한다.
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
	}

	// 메뉴버튼을 생성한다.(여기에서는 "Photoco"버튼)
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	} 
	
	// 메뉴버튼이 클릭 되었을 때 할 행동정의("Photoco"버튼을 눌렸을 때)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item))
			return true;
		return super.onOptionsItemSelected(item);
	}
	
	// 변화가 일어났을 때 해줘야하는 작업에 대해 기술한다.
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

	// 현재 액티비티의 ActionBar를 알려준다.
	private ActionBar getActionBar() {
		return ((ActionBarActivity) getActivity()).getSupportActionBar();
	}

	// 이 프래그먼트를 사용하는 액티비티들은 무조건 이 인터페이스를 구현해야 한다.
	public static interface NavigationDrawerCallbacks {
		// 네비게이션 드로워의 항목이 선택되었을 때 호출 되는 메소드
		void onNavigationDrawerItemSelected(int position);
		public void settingClick( );
	}
}
