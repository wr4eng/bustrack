package com.thingtrack.bustrack.view.module.servicedesigner.internal.model;

import java.io.Serializable;

public class Location implements Serializable {

	public enum AdminAreaType {
		
		UNKNOWN("Unknown"),
		COUNTRY("Country"),
		STATE("State"),
		COUNTY("County"),
		CITY("City");
		
		private String value;
		
		public String getValue() {
			return value;
		}

		AdminAreaType(String value){
			this.value = value;
		}
	}
	
	private String adminArea1; 
	private AdminAreaType adminArea1Type;
	private String adminArea3;
	private AdminAreaType adminArea3Type;
	private String adminArea4;
	private AdminAreaType adminArea4Type;
	private String adminArea5;
	private AdminAreaType adminArea5Type;
	private MapCoordinates displayLatLng;
	private boolean dragPoint;
	private String geocodeQuality;
	private String geocodeQualityCode;
	private MapCoordinates latLng;
	private int linkId;
	private String postalCode;
	//FIXME: It might be a enumeration
	private String sideOFStreet;
	private String street;
	//FIXME: It might be a enumeration
	private String type;
	
	public String getAdminArea1() {
		return adminArea1;
	}
	public void setAdminArea1(String adminArea1) {
		this.adminArea1 = adminArea1;
	}
	public AdminAreaType getAdminArea1Type() {
		return adminArea1Type;
	}
	public void setAdminArea1Type(AdminAreaType adminArea1Type) {
		this.adminArea1Type = adminArea1Type;
	}
	public String getAdminArea3() {
		return adminArea3;
	}
	public void setAdminArea3(String adminArea3) {
		this.adminArea3 = adminArea3;
	}
	public AdminAreaType getAdminArea3Type() {
		return adminArea3Type;
	}
	public void setAdminArea3Type(AdminAreaType adminArea3Type) {
		this.adminArea3Type = adminArea3Type;
	}
	public String getAdminArea4() {
		return adminArea4;
	}
	public void setAdminArea4(String adminArea4) {
		this.adminArea4 = adminArea4;
	}
	public AdminAreaType getAdminArea4Type() {
		return adminArea4Type;
	}
	public void setAdminArea4Type(AdminAreaType adminArea4Type) {
		this.adminArea4Type = adminArea4Type;
	}
	public String getAdminArea5() {
		return adminArea5;
	}
	public void setAdminArea5(String adminArea5) {
		this.adminArea5 = adminArea5;
	}
	public AdminAreaType getAdminArea5Type() {
		return adminArea5Type;
	}
	public void setAdminArea5Type(AdminAreaType adminArea5Type) {
		this.adminArea5Type = adminArea5Type;
	}
	public MapCoordinates getDisplayLatLng() {
		return displayLatLng;
	}
	public void setDisplayLatLng(MapCoordinates displayLatLng) {
		this.displayLatLng = displayLatLng;
	}
	public boolean isDragPoint() {
		return dragPoint;
	}
	public void setDragPoint(boolean dragPoint) {
		this.dragPoint = dragPoint;
	}
	public String getGeocodeQuality() {
		return geocodeQuality;
	}
	public void setGeocodeQuality(String geocodeQuality) {
		this.geocodeQuality = geocodeQuality;
	}
	public String getGeocodeQualityCode() {
		return geocodeQualityCode;
	}
	public void setGeocodeQualityCode(String geocodeQualityCode) {
		this.geocodeQualityCode = geocodeQualityCode;
	}
	public MapCoordinates getLatLng() {
		return latLng;
	}
	public void setLatLng(MapCoordinates latLng) {
		this.latLng = latLng;
	}
	public int getLinkId() {
		return linkId;
	}
	public void setLinkId(int linkId) {
		this.linkId = linkId;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getSideOFStreet() {
		return sideOFStreet;
	}
	public void setSideOFStreet(String sideOFStreet) {
		this.sideOFStreet = sideOFStreet;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
