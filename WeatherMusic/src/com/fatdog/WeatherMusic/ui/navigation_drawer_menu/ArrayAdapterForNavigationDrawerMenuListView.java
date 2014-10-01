package com.fatdog.WeatherMusic.ui.navigation_drawer_menu;

import android.content.Context;

import com.fatdog.WeatherMusic.reuse.listview.mvc.AbstractArrayAdapter;
import com.fatdog.WeatherMusic.reuse.listview.mvc.AbstractViewForListViewItem;

public class ArrayAdapterForNavigationDrawerMenuListView extends AbstractArrayAdapter<ViewForNavigationDrawerMenuListViewItem.DrawerMenuItem> {
	public ArrayAdapterForNavigationDrawerMenuListView(Context context, int resource) {
		super(context, resource);
	}

	// ViewForNavigationDrawerMenuListViewItem라는 하나의 뷰를 리턴하여 준다.
	@Override
	public AbstractViewForListViewItem getInstance() {
		return new ViewForNavigationDrawerMenuListViewItem(getContext());
	}
}