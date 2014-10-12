package com.transformers.hotelcatalog;

import java.util.ArrayList;
import java.util.List;

import com.telerik.everlive.sdk.core.EverliveApp;
import com.telerik.everlive.sdk.core.result.RequestResult;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnItemClickListener {

	 private List<Hotel> hotels = new ArrayList<Hotel>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		EverliveApp app = new EverliveApp("0LOLF0K5aFI9RsSE");
		RequestResult allItems = app.workWith().data(Hotel.class).getAll()
				.executeSync();

		if (allItems.getSuccess()) {
			// returns all hotels (not in use yet)
			ArrayList hotels = (ArrayList) allItems.getValue();
		}

		populateHotels();
		populateListView();
	}

	private void populateHotels() {
		hotels.add(new Hotel("Sheraton", "Sofia", R.drawable.hilton));
		hotels.add(new Hotel("Hilton", "Sofia", R.drawable.hilton));
		hotels.add(new Hotel("Kempinski", "Sofia", R.drawable.hilton));
		hotels.add(new Hotel("Bulgaria", "Burgas", R.drawable.hilton));
		hotels.add(new Hotel("Sankt Peterburg", "Plovdiv", R.drawable.hilton));
		hotels.add(new Hotel("Pliska", "Sofia", R.drawable.hilton));
	}

	private void populateListView() {
		ArrayAdapter<Hotel> adapter = new HotelsListAdapter();
		ListView list = (ListView) findViewById(R.id.hotelsListView);
		list.setAdapter(adapter);
	}

	public class HotelsListAdapter extends ArrayAdapter<Hotel> {
		public HotelsListAdapter() {
			super(MainActivity.this, R.layout.hotel_view, hotels);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView = convertView;
			if (itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.hotel_view,
						parent, false);
			}

			Hotel currentHotel = hotels.get(position);

			ImageView imageIcon = (ImageView) itemView
					.findViewById(R.id.item_imageIcon);
			imageIcon.setImageResource(currentHotel.getIconID());

			TextView textName = (TextView) itemView
					.findViewById(R.id.item_hotelName);
			textName.setText(currentHotel.getName());

			TextView textLocation = (TextView) itemView
					.findViewById(R.id.item_hotelLocation);
			textLocation.setText(currentHotel.getLocation());

			return itemView;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Attach onclick to every list item
		// TextView text = (TextView) view;
		// Toast.makeText(this, text.getText(), Toast.LENGTH_SHORT).show();
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // Handle action bar item clicks here. The action bar will
	// // automatically handle clicks on the Home/Up button, so long
	// // as you specify a parent activity in AndroidManifest.xml.
	// int id = item.getItemId();
	// if (id == R.id.action_settings) {
	// return true;
	// }
	// return super.onOptionsItemSelected(item);
	// }

}
