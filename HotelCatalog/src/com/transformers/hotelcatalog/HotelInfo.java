package com.transformers.hotelcatalog;

import java.util.UUID;

import com.telerik.everlive.sdk.core.result.RequestResult;
import com.telerik.everlive.sdk.core.result.RequestResultCallbackAction;
import com.transformers.hotelcatalog.backend.DbRemote;
import com.transformers.hotelcatalog.backend.HotelDataItem;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HotelInfo extends Activity {

	private String hotelId;
	private TextView tvTitle;
	private TextView etHotelInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_info);
		tvTitle = (TextView) this.findViewById(R.id.tvHotelInfo);
		etHotelInfo = (TextView) this.findViewById(R.id.etHotelInfo);
	}

	@Override
	protected void onResume() {
		super.onResume();
		hotelId = getIntent().getStringExtra("id");
		generateDataFromBackend();
	}

	private void generateDataFromBackend() {
		DbRemote.GetInstance().getHotelByIdWithProjection(UUID.fromString(hotelId), new String[] {"HotelName", "HotelInfo"},
				new RequestResultCallbackAction<HotelDataItem>() {
					@Override
					public void invoke(final RequestResult<HotelDataItem> requestResult) {
						if (requestResult.getSuccess()) {
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									HotelDataItem hotel = requestResult.getValue();
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
