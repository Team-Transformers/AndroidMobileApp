package com.transformers.hotelcatalog;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HotelOptions extends Activity {

	String passedVar = null;
	private TextView passedView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.hotel_options);
	}

	@Override
	protected void onResume() {
		super.onResume();
		String data = getIntent().getStringExtra("Name");
		passedView = (TextView) findViewById(R.id.hotel_title);
		passedView.setText("Hotel "+ data);
	}

}
