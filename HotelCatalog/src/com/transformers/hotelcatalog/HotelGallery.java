package com.transformers.hotelcatalog;

import java.util.ArrayList;
import java.util.List;
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
	private List<String> picturesUrl = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_gallery);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		gallery = (GridView) findViewById(R.id.grid_gallery);

		hotelId = getIntent().getStringExtra("id");
		DbRemote.GetInstance().getAllPicturesByHotelId(
				UUID.fromString(hotelId),
				new RequestResultCallbackAction<ArrayList<PictureDataItem>>() {
					@Override
					public void invoke(
							final RequestResult<ArrayList<PictureDataItem>> requestResult) {
						if (requestResult.getSuccess()) {
							Log.d("d1", "HotelGallery - Success!");
							for (PictureDataItem picUuId : requestResult.getValue()) {
								Log.d("d1", picUuId.getPicture().toString());
//								String url = "http://api.everlive.com/v1/"
//										+ MainActivity.EVERLIVE_API_KEY + "/Files/" +
//										i.getPicture().toString();
								String picUuidAsString = picUuId.getPicture().toString();
								Log.d("d1", picUuidAsString);
								picturesUrl.add(picUuidAsString);
							}
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									requestResult.getValue();

									imageAdapter = new ImageAdapter(HotelGallery.this, picturesUrl);
									gallery.setAdapter(imageAdapter);
								}
							});
						} else {
							Log.d("d1", "Sled malko!");
						}
					}
				});

		ArrayList<String> imgUrls = new ArrayList<String>();
		imageAdapter = new ImageAdapter(HotelGallery.this, imgUrls);
		gallery.setAdapter(imageAdapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		hotelId = getIntent().getStringExtra("id");
		//generateDataFromBackend();
	}

	// not in use yet
//	private void generateDataFromBackend() {
//		DbRemote.GetInstance().getAllPicturesByHotelId(
//				UUID.fromString(hotelId),
//				new RequestResultCallbackAction<ArrayList<PictureDataItem>>() {
//					@Override
//					public void invoke(
//							final RequestResult<ArrayList<PictureDataItem>> requestResult) {
//						if (requestResult.getSuccess()) {
//							Log.d("d1", "Success!");
//							for (PictureDataItem i : requestResult.getValue()) {
//								Log.d("d1", i.getPicture().toString());
//							}
//						} else {
//							Log.d("d1", "Sled malko!");
//						}
//					}
//				});
//	}
}
