package com.transformers.hotelcatalog;

<<<<<<< HEAD
public class Hotel {
	private String name;
	private String location;
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
	
=======
import com.telerik.everlive.sdk.core.model.base.DataItem;
import com.telerik.everlive.sdk.core.serialization.ServerType;

@ServerType("Hotel")
public class Hotel extends DataItem {
	private String name;
	private String location;
	private String info;
	private String address;
>>>>>>> 67a65d46375fbc9f61ce813a393b5a5a4f3fdab7
}
