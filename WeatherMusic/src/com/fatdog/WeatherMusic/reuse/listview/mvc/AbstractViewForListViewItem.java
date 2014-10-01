package com.fatdog.WeatherMusic.reuse.listview.mvc;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

public abstract class AbstractViewForListViewItem extends LinearLayout {
    private View root;

    public AbstractViewForListViewItem(Context context) {
        super(context);
        root = inflate(); // 실질적인 뷰를 생성해내는 부분
        initViews();
        setEvents();
    }

    protected abstract View inflate(); // inflate
    protected abstract void initViews(); // findByViewId
    protected abstract void setEvents(); // click event etc...
    protected abstract void setData(IListViewItem aIListViewItem); // insert View info

    protected View findViewById_(int aResourceId)
    {
        return root.findViewById(aResourceId);
    }
}