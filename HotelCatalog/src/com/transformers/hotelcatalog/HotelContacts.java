package com.transformers.hotelcatalog;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class HotelContacts extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_contacts);

		ContactsFragment frag = new ContactsFragment();
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(R.id.contact_frag, frag, "Contacts Fragment");
		transaction.commit();
	}
}
