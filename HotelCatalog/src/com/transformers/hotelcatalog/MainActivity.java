package com.transformers.hotelcatalog;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.impl.client.RequestWrapper;

import com.telerik.everlive.sdk.core.EverliveApp;
import com.telerik.everlive.sdk.core.result.RequestResult;
import com.telerik.everlive.sdk.core.result.RequestResultCallbackAction;
import com.transformers.hotelcatalog.backend.DbRemote;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class MainActivity extends Activity {

	public final static String ID_EXTRA = "com.transformers.hotelcatalog._ID";
	private static final String TABLE_NAME = "hotel";
	private static final String ABOUT_HOTEL_INFO_MESSAGE = "Put some decent \"About\" information here!";
	private List<Hotel> hotels = new ArrayList<Hotel>();
	Context context = this;
	SQLiteDatabase db;
	ListView list;
	ArrayAdapter<Hotel> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		list = (ListView) findViewById(R.id.hotelsListView);

		adapter = new HotelsListAdapter();
		list.setAdapter(adapter);
		
		DbRemote.GetInstance().setEverlive("0LOLF0K5aFI9RsSE");
		DbRemote.GetInstance().getAllHotels(new RequestResultCallbackAction<ArrayList<Hotel>>() {
					@Override
					public void invoke(RequestResult<ArrayList<Hotel>> requestResult) {
						if (requestResult.getSuccess()) {
							//CreateResultItem resultItem = (CreateResultItem) requestResult
							//		.getValue();
//							for (Hotel hotel : requestResult.getValue()) {
//								Log.d("d1", String.valueOf(hotel.getServerId()));
//							}
							
							hotels = requestResult.getValue();
							
							list.post(new Runnable() {
                                @Override
                                public void run() {
                                	for (Hotel hotel : hotels) {
										adapter.add(hotel);
									}
                                	adapter.notifyDataSetChanged();
                                }
                            });
						}
						else{
							Log.d("d1", "Sled malko");
						}
						
						// TODO: Connect result with hotels ArrayList
						// TODO: Not straightforward to do because of Async operation
					}
				});

		Log.d("d1", "onCreate");
		
		//populateHotels();
		registerClickCallback();
		InitializeAboutApp();
	}

	private void populateHotels() {
		hotels.add(new Hotel("Hemus", "Sofia", R.drawable.hilton, 5));
		hotels.add(new Hotel("Sheraton", "Sofia", R.drawable.hilton, 5));
		hotels.add(new Hotel("Hilton", "Sofia", R.drawable.hilton, 5));
		hotels.add(new Hotel("Kempinski", "Sofia", R.drawable.hilton, 4));
		hotels.add(new Hotel("Bulgaria", "Burgas", R.drawable.hilton, 3));
		hotels.add(new Hotel("Sankt Peterburg", "Plovdiv", R.drawable.hilton, 4));
		hotels.add(new Hotel("Pliska", "Sofia", R.drawable.hilton, 3));
//		hotels.add(new Hotel("Sheraton", "Sofia", R.drawable.hilton, 5));
//		hotels.add(new Hotel("Hilton", "Sofia", R.drawable.hilton, 5));
//		hotels.add(new Hotel("Kempinski", "Sofia", R.drawable.hilton, 4));
//		hotels.add(new Hotel("Bulgaria", "Burgas", R.drawable.hilton, 3));
//		hotels.add(new Hotel("Sankt Peterburg", "Plovdiv", R.drawable.hilton, 4));
//		hotels.add(new Hotel("Pliska", "Sofia", R.drawable.hilton, 3));
	}

	private void registerClickCallback() {
		ListView list = (ListView) findViewById(R.id.hotelsListView);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {
				Hotel clickedHotel = hotels.get(position);
				Intent i = new Intent(MainActivity.this, HotelOptions.class);
				i.putExtra("Name", String.valueOf(clickedHotel.getName()));
				i.putExtra("Rating", String.valueOf(clickedHotel.getRating()));
				i.putExtra("Town", String.valueOf(clickedHotel.getLocation()));

				startActivity(i);
			}
		});
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
			textLocation.setText(currentHotel.getAddress());

			return itemView;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null,
					null);
			cursor.moveToFirst();

			// TODO: make it popupWindow or something else
			Toast.makeText(context, cursor.getString(0), Toast.LENGTH_LONG)
					.show();

			cursor.close();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void InitializeAboutApp() {
		db = openOrCreateDatabase("HotelAbout", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
				+ "(comment VARCHAR);");
		// TODO: fix bug where old records never deleted or double or more
		// inserted
		// db.delete(TABLE_NAME, null, null);

		ContentValues values = new ContentValues();
		values.put("comment", ABOUT_HOTEL_INFO_MESSAGE);
		db.insert(TABLE_NAME, null, values);
	}
}
