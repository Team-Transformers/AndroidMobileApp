package com.transformers.hotelcatalog;

import com.telerik.everlive.sdk.core.model.base.DataItem;
import com.telerik.everlive.sdk.core.serialization.ServerType;

@ServerType("Hotel")
public class Hotel extends DataItem {
	private String name;
	private String location;
	private String info;
	private String address;
}
