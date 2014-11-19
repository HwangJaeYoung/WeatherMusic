package com.fatdog.WeatherMusic.reuse.favor_genre_page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.fatdog.WeatherMusic.R;
import com.fatdog.WeatherMusic.reuse.mvc.activity.AbstractViewForActivity;

public class ViewForFavorGenreActivity extends AbstractViewForActivity{

	private Button btFavorPageAlternative;
	private Button btFavorPageHipHop;
	private Button btFavorPageAccoustic;
	private Button btFavorPageRNB;
	
	private Button btExam;
	
	private Controller controller;
	
	public ViewForFavorGenreActivity(Context context, Controller aController) {
		super(context);
		controller = aController;	
	}

	@Override
	protected View inflate() {
		return LayoutInflater.from(getContext()).inflate(R.layout.activity_favor, null);
	}

	@Override
	protected void initViews() {
		btFavorPageAlternative = (Button)findViewById(R.id.bt_favor_page_alternative);
		btFavorPageHipHop = (Button)findViewById(R.id.bt_favor_page_hiphop);
		btFavorPageAccoustic = (Button)findViewById(R.id.bt_favor_page_accoustic);
		btFavorPageRNB = (Button)findViewById(R.id.bt_favor_page_rnb);
		
		btExam = (Button)findViewById(R.id.bt_exam);
	}

	@Override
	protected void setEvent() {
		btFavorPageAlternative.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.alternativeClick();

			}
		});

		btFavorPageHipHop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.hiphopClick();

			}
		});

		btFavorPageAccoustic.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				controller.accousticClick();
			}
		});

		btFavorPageRNB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.rnbClick();
			}
		});
		
		btExam.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				controller.goYoutube();
				
			}
		});
	}

	public static interface Controller {
		public void alternativeClick( );
		public void hiphopClick( );
		public void accousticClick( );
		public void rnbClick( );
		
		public void goYoutube( );
	}
}