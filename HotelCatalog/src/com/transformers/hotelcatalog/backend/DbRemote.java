package com.transformers.hotelcatalog.backend;

import java.util.ArrayList;
import java.util.UUID;

import android.util.Log;

import com.telerik.everlive.sdk.core.EverliveApp;
import com.telerik.everlive.sdk.core.query.definition.FieldsDefinition;
import com.telerik.everlive.sdk.core.result.RequestResult;
import com.telerik.everlive.sdk.core.result.RequestResultCallbackAction;
import com.transformers.hotelcatalog.Hotel;

public class DbRemote {

	private EverliveApp app;
	private static DbRemote instance;

	public static DbRemote GetInstance() {
		if (instance == null) {
			instance = new DbRemote();
		}
		return instance;
	}

	public void setEverlive(String apiKey) {
		this.app = new EverliveApp(apiKey);
	}

	public void getAllHotels(
			RequestResultCallbackAction<ArrayList<Hotel>> callbackAction) {
		this.app.workWith().data(Hotel.class).getAll()
				.executeAsync(callbackAction);
	}

	public void getHotelsProjection(RequestResultCallbackAction<ArrayList<Hotel>> callbackAction) {
		FieldsDefinition includedFieldsDefinition = new FieldsDefinition();
	    includedFieldsDefinition.addIncludedFields("HotelName");
	    
		this.app.workWith().data(Hotel.class).get()
				.select(includedFieldsDefinition).executeAsync(callbackAction);
	}
	
	public void getHotelById(UUID id,
			RequestResultCallbackAction<Hotel> callbackAction) {
		this.app.workWith().data(Hotel.class).getById(id).executeAsync(callbackAction);
		
		/* You call like this
		 * 
		 * 
		 * 
		 * 		DbRemote.GetInstance().getHotelById(UUID.fromString("b6ef7770-52b6-11e4-91a4-4365342c4751"), new RequestResultCallbackAction<Hotel>() {
					@Override
					public void invoke(RequestResult<Hotel> requestResult) {
						if (requestResult.getSuccess()) {
							Log.d("d1", String.valueOf(((Hotel)requestResult.getValue()).getName()));
						}
						else{
							Log.d("d1", "Sled malko");
						}
					}
				});
		 * 
		 * */
	}

}
