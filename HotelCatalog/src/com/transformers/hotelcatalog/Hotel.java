package com.transformers.hotelcatalog;

import com.telerik.everlive.sdk.core.model.base.DataItem;
import com.telerik.everlive.sdk.core.serialization.ServerType;

@ServerType("Hotel")
public class Hotel extends DataItem {
	
	private String name;
	private String location;
	private String info;
	private String address;
	private int iconID;
	private int rating;

	public Hotel(String name, String location, int iconID, int rating) {
		super();
		this.name = name;
		this.location = location;
		this.iconID = iconID;
		this.rating = rating;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
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
