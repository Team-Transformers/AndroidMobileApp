package com.transformers.hotelcatalog.backend;

import java.util.UUID;

import com.telerik.everlive.sdk.core.model.base.DataItem;
import com.telerik.everlive.sdk.core.model.system.GeoPoint;
import com.telerik.everlive.sdk.core.serialization.ServerIgnore;
import com.telerik.everlive.sdk.core.serialization.ServerProperty;
import com.telerik.everlive.sdk.core.serialization.ServerType;

@ServerType("Hotel")
public class HotelDataItem extends DataItem {
	@ServerIgnore
	private UUID id;
	
	@ServerProperty("HotelName")
	private String name;
	
	@ServerProperty("HotelLocation")
	private GeoPoint location;
	
	@ServerProperty("HotelInfo")
	private String info;
	
	@ServerProperty("HotelAddress")
	private String address;
	
	@ServerIgnore
	private int iconID;
	
	@ServerProperty("HotelRating")
	private int rating;

	public HotelDataItem(){
		super();
	}
	
	public HotelDataItem(String name) {
		this();
		this.name = name;
	}
	
	public HotelDataItem(String name, String address, int iconID, int rating) {
		this(name);
		this.address = address;
		this.iconID = iconID;
		this.rating = rating;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public GeoPoint getLocation() {
		return location;
	}

	public int getIconID() {
		return iconID;
	}

	public String getInfo() {
		return info;
	}

	public String getAddress() {
		return address;
	}

	public int getRating() {
		return rating;
	}
}
