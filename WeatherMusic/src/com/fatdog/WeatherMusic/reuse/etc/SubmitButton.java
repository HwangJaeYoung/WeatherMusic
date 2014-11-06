package com.fatdog.WeatherMusic.reuse.etc;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class SubmitButton extends Button {
	   private String textToRestore;
	    private ProgressBar progressBar;
	    private ArrayList<View> viewsToHold;

	    public SubmitButton(Context context) {
	        super(context);
	    }
	    
	    public SubmitButton(Context context, AttributeSet atts) {
	        super(context, atts);
	    }

	    public void init(ProgressBar aProgressBar) {
	        this.progressBar = aProgressBar;
	        aProgressBar.setVisibility(View.INVISIBLE);
	        this.textToRestore = this.getText().toString();
	        this.viewsToHold = new ArrayList<View>();
	    }

	    public void addViewToHold(View aView) {
	        this.viewsToHold.add(aView);
	    }

	    @Override
	    public boolean onTouchEvent(MotionEvent event) {

	        if(event.getAction() == MotionEvent.ACTION_UP && isEnabled()) {
	            lock();
	            this.setEnabled(false);
	            this.performClick();
	        }
	        return super.onTouchEvent(event);
	    }

	    private void lock() {
	        this.setText("");
	        progressBar.setVisibility(View.VISIBLE);
	        for (View aView : this.viewsToHold) {
	            aView.setEnabled(false);
	        }
	    }

	    public void release() {
	        this.setText(this.textToRestore);
	        this.setEnabled(true);
	        progressBar.setVisibility(INVISIBLE);
	        for (View aView : this.viewsToHold) {
	            aView.setEnabled(true);
	        }
	    }
}
