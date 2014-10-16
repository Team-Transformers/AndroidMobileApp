package com.transformers.hotelcatalog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.telerik.everlive.sdk.core.model.system.File;
import com.telerik.everlive.sdk.core.result.RequestResult;
import com.telerik.everlive.sdk.core.result.RequestResultCallbackAction;
import com.transformers.hotelcatalog.backend.DbRemote;
import com.transformers.hotelcatalog.backend.PictureDataItem;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;

public class HotelGallery extends Activity {

	private String hotelId;
	private GridView gallery;
	private ImageAdapter imageAdapter;
	private List<String> picturesUUID = new ArrayList<String>();
	private Button btnTakePic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_gallery);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		gallery = (GridView) findViewById(R.id.grid_gallery);
		btnTakePic = (Button) findViewById(R.id.btnTakePic);
		btnTakePic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivityForResult(intent, 0);
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0 && resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			Bitmap imageBitmap = (Bitmap) extras.get("data");
			// ivImageView.setImageBitmap(imageBitmap);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			boolean isCompressed = imageBitmap.compress(CompressFormat.PNG,
					0 /* ignored for PNG */, bos);

			byte[] bitmapdata = bos.toByteArray();
			ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);

			DbRemote.GetInstance().UploadFileWithHotelId("pesho", "image/jpeg",
					bs, new RequestResultCallbackAction() {

						@Override
						public void invoke(RequestResult result) {
							if (result.getSuccess()) {
								Log.d("d1", "SUCCES! Kartinkata");

								ArrayList<File> files = (ArrayList<File>) result.getValue();
								File file = files.get(0);
								UUID id = UUID.fromString(file.getId()
										.toString());
								Log.d("d1", file.getId().toString());
								Log.d("d1", "6teeeeeeeeeeeeee");

								PictureDataItem pic = new PictureDataItem();
								pic.setHotelId(UUID
										.fromString(hotelId));
								pic.setPicture(file.getId());
								DbRemote.GetInstance().app.workWith().data(PictureDataItem.class)
										.create(pic).executeAsync();
							} else {
								Log.d("d1", "Fail! Kartinkata");
							}
						}
					});
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		picturesUUID = new ArrayList<String>();
		hotelId = getIntent().getStringExtra("id");
		DbRemote.GetInstance().getAllPicturesByHotelId(
				UUID.fromString(hotelId),
				new RequestResultCallbackAction<ArrayList<PictureDataItem>>() {
					@Override
					public void invoke(
							final RequestResult<ArrayList<PictureDataItem>> requestResult) {
						if (requestResult.getSuccess()) {
							Log.d("d1", "HotelGallery - Success!");
							for (PictureDataItem picUuId : requestResult
									.getValue()) {
								Log.d("d1", picUuId.getPicture().toString());
								// String url = "http://api.everlive.com/v1/"
								// + MainActivity.EVERLIVE_API_KEY + "/Files/" +
								// i.getPicture().toString();
								String picUuidAsString = picUuId.getPicture()
										.toString();
								Log.d("d1", picUuidAsString);
								picturesUUID.add(picUuidAsString);
							}
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									requestResult.getValue();

									imageAdapter = new ImageAdapter(
											HotelGallery.this, picturesUUID);
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
}
