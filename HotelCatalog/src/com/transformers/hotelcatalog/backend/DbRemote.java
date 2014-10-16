package com.transformers.hotelcatalog.backend;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

import android.util.Log;

import com.telerik.everlive.sdk.core.EverliveApp;
import com.telerik.everlive.sdk.core.query.definition.FieldsDefinition;
import com.telerik.everlive.sdk.core.query.definition.FileField;
import com.telerik.everlive.sdk.core.query.definition.filtering.simple.ValueCondition;
import com.telerik.everlive.sdk.core.query.definition.filtering.simple.ValueConditionOperator;
import com.telerik.everlive.sdk.core.query.definition.sorting.SortDirection;
import com.telerik.everlive.sdk.core.query.definition.sorting.SortingDefinition;
import com.telerik.everlive.sdk.core.result.RequestResult;
import com.telerik.everlive.sdk.core.result.RequestResultCallbackAction;
import com.transformers.hotelcatalog.Hotel;

public class DbRemote {

	public EverliveApp app;
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
		// Sorting
		SortingDefinition sortDef = new SortingDefinition("HotelRating", SortDirection.Descending);
		
		this.app.workWith().data(Hotel.class).getAll().sort(sortDef).executeAsync(callbackAction);
	}

	public void getHotelByIdWithProjection(UUID id, String[] columns,
			RequestResultCallbackAction<Hotel> callbackAction) {
		FieldsDefinition includedFieldsDefinition = new FieldsDefinition();
		includedFieldsDefinition.addIncludedFields(columns);

		this.app.workWith().data(Hotel.class).getById(id)
				.select(includedFieldsDefinition).executeAsync(callbackAction);
	}

	public void getAllPicturesByHotelId(UUID hotelId,
			RequestResultCallbackAction<ArrayList<PictureDataItem>> callbackAction) {
		
		ValueCondition valueCond = new ValueCondition("HotelId", hotelId, ValueConditionOperator.EqualTo);
		this.app.workWith().data(PictureDataItem.class).get()
		        .where(valueCond).executeAsync(callbackAction);
	}
	
	public String getDownloadLink(UUID fileId)
	{
	    return this.app.workWith().files().download(fileId).getDownloadPath();
	}

	public void UploadFileWithHotelId(String fileName, String contentType, InputStream inputStream,
			RequestResultCallbackAction callbackAction){
		FileField fileField = new FileField(fileName, contentType, inputStream);
		this.app.workWith().files().upload(fileField).executeAsync(callbackAction);
	}
}
