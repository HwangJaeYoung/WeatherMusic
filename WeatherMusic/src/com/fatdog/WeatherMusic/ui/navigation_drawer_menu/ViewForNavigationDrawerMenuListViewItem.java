package com.fatdog.WeatherMusic.ui.navigation_drawer_menu;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fatdog.WeatherMusic.R;
import com.fatdog.WeatherMusic.reuse.listview.mvc.AbstractViewForListViewItem;
import com.fatdog.WeatherMusic.reuse.listview.mvc.IListViewItem;

public class ViewForNavigationDrawerMenuListViewItem extends AbstractViewForListViewItem {
	private ImageView ivListItem; 

	public ViewForNavigationDrawerMenuListViewItem(Context context) {
		super(context);
	}

	@Override
	protected View inflate() {
		return inflate(getContext(), R.layout.item_navigation_drawer_menu, this);
	}

	@Override
	protected void initViews() {
		ivListItem = (ImageView)findViewById(R.id.iv_list_item);
	}

	@Override
	protected void setEvents() { }

	@Override
	protected void setData(IListViewItem aIListViewItem) {
		DrawerMenuItem drawerMenuItem = (DrawerMenuItem) aIListViewItem;
		ivListItem.setImageResource(drawerMenuItem.getResourceId());
	}

	// 여기서의 item은 다른 것들과 다르게 데이터가 변하지 않는다.
	public static class DrawerMenuItem implements IListViewItem {
		private int resourceId;

		public DrawerMenuItem(int aResourceId) {
			resourceId = aResourceId;
		}

		public int getResourceId() {
			return resourceId;
		}
	}
}
