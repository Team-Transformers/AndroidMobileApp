package com.transformers.hotelcatalog;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class HotelContacts extends Activity {

	private final LatLng TELERIK_LOCATION = new LatLng(42.65084700000001,	23.379431000000068);
	private GoogleMap map;
	private TextView distance;
	private TextView address;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_contacts);
		
		address = (TextView) findViewById(R.id.contact_text);
		address.setText(getIntent().getStringExtra("Address"));
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		map.addMarker(new MarkerOptions().position(TELERIK_LOCATION).title(
				"Telerik Academy"));
		map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(
				TELERIK_LOCATION, 17);
		map.animateCamera(update);

		LocationManager lmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener listener = new HotelLocationListener();
		lmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				listener);
	}
	
	class HotelLocationListener implements LocationListener {


		@Override
		public void onLocationChanged(Location location) {
			if (location != null) {
				double pLat = location.getLatitude();
				double pLong = location.getLongitude();

				distance = (TextView) findViewById(R.id.distance_text);
				
				float result = getDistance((float)pLat, (float)pLong, (float)TELERIK_LOCATION.latitude, (float)TELERIK_LOCATION.longitude);

				distance.setText("Distance: " + result + " meters");
			}
		}
		
		public int getDistance(float lat1, float lng1, float lat2, float lng2) {
			double earthRadius = 6371; // kilometers
			double dLat = Math.toRadians(lat2 - lat1);
			double dLng = Math.toRadians(lng2 - lng1);
			double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
					+ Math.cos(Math.toRadians(lat1))
					* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2)
					* Math.sin(dLng / 2);
			double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
			float dist = (float) (earthRadius * c);

			return (int) dist;
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

	}

}

