package com.transformers.hotelcatalog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class HotelOptions extends Activity {

	private TextView hotelTitle = null;
	private TextView hotelTown = null;
	private ImageView info = null;
	private ImageView gallery = null;
	private ImageView reservations = null;
	private ImageView contacts = null;
	private RatingBar rb;
	private String hotelId;
	private String hotelAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_options);

		info = (ImageView) findViewById(R.id.icon_info);
		gallery = (ImageView) findViewById(R.id.icon_gallery);
		reservations = (ImageView) findViewById(R.id.icon_reservations);
		contacts = (ImageView) findViewById(R.id.icon_contacts);

		info.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				Intent infoIntent = new Intent(HotelOptions.this, HotelInfo.class);
				infoIntent.putExtra("id", hotelId);
				startActivity(infoIntent);
//				Toast.makeText(HotelOptions.this, hotelId, Toast.LENGTH_SHORT).show();
			}
		});

		gallery.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				Intent galleryIntent = new Intent(HotelOptions.this, HotelGallery.class);
				galleryIntent.putExtra("id", hotelId);
				startActivity(galleryIntent);
//				Toast.makeText(HotelOptions.this, "Gallery clicked", Toast.LENGTH_SHORT).show();
			}
		});

		reservations.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				Intent reservationsIntent = new Intent(HotelOptions.this, HotelReservations.class);
//				reservationsIntent.putExtra("ID", "ID_FROM_MAIN_ACTIVITY");
				startActivity(reservationsIntent);
//				Toast.makeText(HotelOptions.this, "Reservations clicked", Toast.LENGTH_SHORT).show();
			}
		});

		contacts.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				Intent contactsIntent = new Intent(HotelOptions.this, HotelContacts.class);
				contactsIntent.putExtra("Address", hotelAddress);
				startActivity(contactsIntent);
//				Toast.makeText(HotelOptions.this, "Contacts clicked", Toast.LENGTH_SHORT).show();
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		String data = getIntent().getStringExtra("Name");
		String ratingString = getIntent().getStringExtra("Rating");
		String town = getIntent().getStringExtra("Address");
		String id = getIntent().getStringExtra("id");
		this.hotelId = id;
		this.hotelAddress = town;
		//Toast.makeText(HotelOptions.this, hotelId, Toast.LENGTH_SHORT).show();

		hotelTitle = (TextView) findViewById(R.id.hotel_title);
		hotelTitle.setText("Hotel " + data);
		
		hotelTown = (TextView) findViewById(R.id.hotel_town);
		hotelTown.setText(town.substring(0, town.indexOf(",")));

		int rating = Integer.parseInt(ratingString);
		rb = (RatingBar) findViewById(R.id.ratingBar);
		rb.setRating(rating);
	}

}
