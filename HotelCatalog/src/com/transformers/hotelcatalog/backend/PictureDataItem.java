package com.transformers.hotelcatalog.backend;

import java.util.UUID;

import com.telerik.everlive.sdk.core.model.base.DataItem;
import com.telerik.everlive.sdk.core.serialization.ServerProperty;
import com.telerik.everlive.sdk.core.serialization.ServerType;

@ServerType("Picture")
public class PictureDataItem extends DataItem {

	@ServerProperty("HotelId")
	private UUID hotelId;

	@ServerProperty("HotelPicture")
	private UUID picture;

	public UUID getHotelId() {
		return hotelId;
	}

	public void setHotelId(UUID hotelId) {
		this.hotelId = hotelId;
	}

	public UUID getPicture() {
		return picture;
	}

	public void setPicture(UUID picture) {
		this.picture = picture;
	}
}
