package com.transformers.hotelcatalog;

import com.telerik.everlive.sdk.core.model.base.DataItem;
import com.telerik.everlive.sdk.core.serialization.ServerType;

@ServerType("Hotel")
public class Hotel extends DataItem {
	private String name;
	private String location;
	private String info;

	private String address;
	private int IconID;

	public Hotel(String name, String location, int iconID) {
		super();
		this.name = name;
		this.location = location;
		this.IconID = iconID;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public int getIconID() {
		return IconID;
	}

	public String getInfo() {
		return info;
	}

	public String getAddress() {
		return address;
	}
}
