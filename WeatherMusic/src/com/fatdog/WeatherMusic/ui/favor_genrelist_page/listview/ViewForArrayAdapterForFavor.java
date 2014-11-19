package com.fatdog.WeatherMusic.ui.favor_genrelist_page.listview;

import android.content.Context;
import android.view.View;

import com.fatdog.WeatherMusic.reuse.listview.mvc.AbstractViewForListViewItem;
import com.fatdog.WeatherMusic.reuse.listview.mvc.IListViewItem;

public class ViewForArrayAdapterForFavor extends AbstractViewForListViewItem {

	public ViewForArrayAdapterForFavor(Context context) {
		super(context);
	
	}

	@Override
	protected View inflate() {
		
		return null;
	}

	@Override
	protected void initViews() {
	
	}

	@Override
	protected void setEvents() {

		
	}

	@Override
	protected void setData(IListViewItem aIListViewItem) {
		
		
	}
	
	public static interface IFavorList extends IListViewItem {
		public String getViedoKey( );
		public String getArtist( );
		public String getTitle( );
		public String getCoverURL( );
	}
}