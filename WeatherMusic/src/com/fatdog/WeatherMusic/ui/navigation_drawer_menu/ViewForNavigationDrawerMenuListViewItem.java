package com.fatdog.WeatherMusic.ui.navigation_drawer_menu;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fatdog.WeatherMusic.R;
import com.fatdog.WeatherMusic.reuse.listview.mvc.AbstractViewForListViewItem;
import com.fatdog.WeatherMusic.reuse.listview.mvc.IListViewItem;

public class ViewForNavigationDrawerMenuListViewItem extends AbstractViewForListViewItem {
	TextView tv_contents;

	public ViewForNavigationDrawerMenuListViewItem(Context context) {
		super(context);
	}

	@Override
	protected View inflate() {
		return inflate(getContext(), R.layout.item_navigation_drawer_menu, this);
	}

	@Override
	protected void initViews() {
		tv_contents = (TextView)findViewById_(R.id.tv_list_item);
	}

	@Override
	protected void setEvents() { }

	@Override
	protected void setData(IListViewItem aIListViewItem) {
		DrawerMenuItem drawerMenuItem = (DrawerMenuItem) aIListViewItem;
		tv_contents.setText(drawerMenuItem.getText());
	}

	// 여기서의 item은 다른 것들과 다르게 데이터가 변하지 않는다.
	public static class DrawerMenuItem implements IListViewItem {
		private String text;

		public DrawerMenuItem(String aText) {
			text = aText;
		}

		public String getText() {
			return text;
		}
	}
}
