package com.transformers.hotelcatalog;


import com.transformers.hotelcatalog.Services.MediaPlayerService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HotelReservations extends Activity {

	private Button playButton;
	private Button stopButton;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_reservations);
		
		this.playButton = (Button) findViewById(R.id.btnStartService);
		this.playButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						MediaPlayerService.class);
				intent.putExtra(MediaPlayerService.START_PLAY, true);
				startService(intent);
			}
		});

		this.stopButton = (Button) findViewById(R.id.btnStopService);
		this.stopButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						MediaPlayerService.class);
				stopService(intent);
			}
		});
	}
}
