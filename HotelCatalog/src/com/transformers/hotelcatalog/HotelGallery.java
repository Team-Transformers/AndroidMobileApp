package com.transformers.hotelcatalog;

import java.util.ArrayList;
import java.util.UUID;

import com.telerik.everlive.sdk.core.result.RequestResult;
import com.telerik.everlive.sdk.core.result.RequestResultCallbackAction;
import com.transformers.hotelcatalog.backend.DbRemote;
import com.transformers.hotelcatalog.backend.PictureDataItem;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

public class HotelGallery extends Activity {

	private String hotelId;
	private GridView gallery;
	private ImageAdapter imageAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_gallery);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		gallery = (GridView) findViewById(R.id.grid_gallery);
		imageAdapter = new ImageAdapter(HotelGallery.this);
		gallery.setAdapter(imageAdapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		hotelId = getIntent().getStringExtra("id");
		generateDataFromBackend();
	}

	private void generateDataFromBackend() {
		DbRemote.GetInstance().getAllPicturesByHotelId(
				UUID.fromString(hotelId),
				new RequestResultCallbackAction<ArrayList<PictureDataItem>>() {
					@Override
					public void invoke(
							final RequestResult<ArrayList<PictureDataItem>> requestResult) {
						if (requestResult.getSuccess()) {
							Log.d("d1", "Success!");
							for (PictureDataItem i : requestResult.getValue()) {
								Log.d("d1", i.getPicture().toString());
							}
						} else {
							Log.d("d1", "Sled malko!");
						}
					}
				});
	}
}
