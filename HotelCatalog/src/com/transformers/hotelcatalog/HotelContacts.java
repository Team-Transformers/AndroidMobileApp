package com.transformers.hotelcatalog;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.os.Bundle;

public class HotelContacts extends Activity {

	private final LatLng TELERIK_LOCATION = new LatLng(42.65084700000001, 23.379431000000068);
	private GoogleMap map;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_contacts);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.addMarker(new MarkerOptions().position(TELERIK_LOCATION).title("Telerik Academy"));
		map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(TELERIK_LOCATION, 17);
		map.animateCamera(update);
	}
}