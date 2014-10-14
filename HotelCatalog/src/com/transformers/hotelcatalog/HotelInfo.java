package com.transformers.hotelcatalog;

import java.util.ArrayList;
import java.util.UUID;

import com.telerik.everlive.sdk.core.result.RequestResult;
import com.telerik.everlive.sdk.core.result.RequestResultCallbackAction;
import com.transformers.hotelcatalog.backend.DbRemote;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class HotelInfo extends Activity {

	private Context context;
	private String hotelId;
	private TextView tvTitle;
	private EditText etHotelInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_info);
		tvTitle = (TextView) this.findViewById(R.id.tvHotelInfo);
		etHotelInfo = (EditText) this.findViewById(R.id.etHotelInfo);
	}

	@Override
	protected void onResume() {
		super.onResume();
		hotelId = getIntent().getStringExtra("id");
		generateDataFromBackend();
	}

	private void generateDataFromBackend() {
		DbRemote.GetInstance().getHotelByIdWithProjection(UUID.fromString(hotelId), new String[] {"HotelName", "HotelInfo"},
				new RequestResultCallbackAction<Hotel>() {
					@Override
					public void invoke(final RequestResult<Hotel> requestResult) {
						if (requestResult.getSuccess()) {
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									Hotel hotel = requestResult.getValue();
									tvTitle.setText(hotel.getName() + " Information");
									etHotelInfo.setText(
											requestResult.getValue().getInfo());
								}
							});
						} else {
							// TODO: Can not generate data from Internet
						}
					}
				});
	}
}
