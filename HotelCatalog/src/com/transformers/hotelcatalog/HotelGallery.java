package com.transformers.hotelcatalog;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class HotelGallery extends Activity {

	private GridView gallery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_gallery);
		gallery = (GridView) findViewById(R.id.grid_gallery);
		gallery.setAdapter(new ImageAdapter(HotelGallery.this));
	}
}
