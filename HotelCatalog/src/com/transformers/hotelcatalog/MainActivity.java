package com.transformers.hotelcatalog;

import java.util.ArrayList;
import java.util.List;
import com.telerik.everlive.sdk.core.result.RequestResult;
import com.telerik.everlive.sdk.core.result.RequestResultCallbackAction;
import com.transformers.hotelcatalog.backend.DbRemote;
import com.transformers.hotelcatalog.backend.HotelDataItem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class MainActivity extends Activity {

	public final static String EVERLIVE_API_KEY = "0LOLF0K5aFI9RsSE";
	public final static String ID_EXTRA = "com.transformers.hotelcatalog._ID";
	private static final String TABLE_NAME = "hotel";
	private static final String ABOUT_HOTEL_INFO_MESSAGE = "Put some decent \"About\" information here!";
	private static final int NOTIFICATION_ID = 0;
	private List<HotelDataItem> hotels = new ArrayList<HotelDataItem>();
	private NotificationManager mNM;
	Context context = this;
	SQLiteDatabase db;
	ListView list;
	ArrayAdapter<HotelDataItem> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ConnectivityManager conManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nInfo = conManager.getActiveNetworkInfo();
		
		if(nInfo==null){
			
			 mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			 Notification n = new Notification();
			 n.icon = R.drawable.no_connection;
			 n.tickerText = "No internet connection...";
			 n.when = System.currentTimeMillis();
			 
			 Intent notificationIntent = new Intent(this, Notifications.class);
			 PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
			 
			 CharSequence contentText = "Please check your internet connection...";
			 CharSequence contentTitle = "No Internet";
			 n.setLatestEventInfo(this, contentTitle, contentText, contentIntent);
			 mNM.notify(NOTIFICATION_ID, n);
		}
		
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		list = (ListView) findViewById(R.id.hotelsListView);

		adapter = new HotelsListAdapter();
		list.setAdapter(adapter);

		DbRemote.GetInstance().setEverlive(EVERLIVE_API_KEY);
		DbRemote.GetInstance().getAllHotels(
				new RequestResultCallbackAction<ArrayList<HotelDataItem>>() {
					@Override
					public void invoke(
							RequestResult<ArrayList<HotelDataItem>> requestResult) {
						if (requestResult.getSuccess()) {
							// CreateResultItem resultItem = (CreateResultItem)
							// requestResult
							// .getValue();
							// for (Hotel hotel : requestResult.getValue()) {
							// Log.d("d1", String.valueOf(hotel.getServerId()));
							// }

							hotels = requestResult.getValue();

							list.post(new Runnable() {
								@Override
								public void run() {
									for (HotelDataItem hotel : hotels) {
										adapter.add(hotel);
									}
									adapter.notifyDataSetChanged();
								}
							});
						} else {
							Log.d("d1", "Sled malko");
						}
					}
				});

		registerClickCallback();
		InitializeAboutApp();
	}

//	private void populateHotels() {
//		hotels.add(new Hotel("Hemus", "Sofia", R.drawable.hilton, 5));
//		hotels.add(new Hotel("Sheraton", "Sofia", R.drawable.hilton, 5));
//		hotels.add(new Hotel("Hilton", "Sofia", R.drawable.hilton, 5));
//		hotels.add(new Hotel("Kempinski", "Sofia", R.drawable.hilton, 4));
//		hotels.add(new Hotel("Bulgaria", "Burgas", R.drawable.hilton, 3));
//		hotels.add(new Hotel("Sankt Peterburg", "Plovdiv", R.drawable.hilton, 4));
//		hotels.add(new Hotel("Pliska", "Sofia", R.drawable.hilton, 3));
//	}

	private void registerClickCallback() {
		ListView list = (ListView) findViewById(R.id.hotelsListView);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
				HotelDataItem clickedHotel = hotels.get(position);
				Intent i = new Intent(MainActivity.this, HotelOptions.class);
				i.putExtra("Name", String.valueOf(clickedHotel.getName()));
				i.putExtra("Rating", String.valueOf(clickedHotel.getRating()));
				i.putExtra("Address", String.valueOf(clickedHotel.getAddress()));
				i.putExtra("id", String.valueOf(clickedHotel.getServerId()));

				startActivity(i);
			}
		});
	}

	public class HotelsListAdapter extends ArrayAdapter<HotelDataItem> {
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

			HotelDataItem currentHotel = hotels.get(position);

			ImageView imageIcon = (ImageView) itemView
					.findViewById(R.id.item_imageIcon);
			imageIcon.setImageResource(currentHotel.getIconID());

			TextView textName = (TextView) itemView
					.findViewById(R.id.item_hotelName);
			textName.setText(currentHotel.getName());

			TextView textLocation = (TextView) itemView
					.findViewById(R.id.item_hotelLocation);
			String address = currentHotel.getAddress();
			textLocation.setText(address.substring(0, address.indexOf(",")));

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
		//db.delete(TABLE_NAME, null, null);

		ContentValues values = new ContentValues();
		values.put("comment", ABOUT_HOTEL_INFO_MESSAGE);
		db.insert(TABLE_NAME, null, values);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//TODO: db = drop;
	}
}
