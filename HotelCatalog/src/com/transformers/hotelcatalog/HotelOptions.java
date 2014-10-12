package com.transformers.hotelcatalog;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

public class HotelOptions extends Activity {

	String passedVar = null;
	private TextView passedView = null;
	RatingBar rb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_options);
	}

	@Override
	protected void onResume() {
		super.onResume();
		String data = getIntent().getStringExtra("Name");
		String ratingString = getIntent().getStringExtra("Rating");

		passedView = (TextView) findViewById(R.id.hotel_title);
		passedView.setText("Hotel " + data);

		int rating = Integer.parseInt(ratingString);
		rb = (RatingBar) findViewById(R.id.ratingBar);
		rb.setRating(rating);
	}

}
