package com.transformers.hotelcatalog;

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
	
}
