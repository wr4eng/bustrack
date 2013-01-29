package com.thingtrack.bustrack.view.module.servicedesigner.internal.model;

import java.io.Serializable;

public class MapCoordinates implements Serializable {
	
	private double lng;
	private double lat;

	public MapCoordinates(double latitude, double longitude) {
		
		this.lat = latitude;
		this.lng = longitude;
	}
	
	public double getLng() {
		return lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLng(double longitude) {
		this.lng = longitude;
	}

	public void setLat(double latitude) {
		this.lat = latitude;
	}

}
