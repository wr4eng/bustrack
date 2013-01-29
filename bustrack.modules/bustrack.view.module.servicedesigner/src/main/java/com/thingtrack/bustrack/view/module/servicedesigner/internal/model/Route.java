package com.thingtrack.bustrack.view.module.servicedesigner.internal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Route implements Serializable {
	
	private boolean hasTollRoad;
	private boolean hasFerry;
	private boolean hasHighway;
	private boolean hasSeasonalClosure;
	private boolean hasUnpaved;
	private boolean hasCountryCross;
	private List<MapCoordinates> boundingBox; 
	private long time;
	private String formattedTime;
	private double distance;
	private double fuelUsed;
	private List<Leg> legs = new ArrayList<Leg>();
	private List<Integer> locationSequence = new ArrayList<Integer>();
	private List<Location> locations = new ArrayList<Location>();
	private List<String> options = new ArrayList<String>();
	private int realTime;
	private String sessionId;
	private Shape shape;
	
	public boolean isHasTollRoad() {
		return hasTollRoad;
	}
	public void setHasTollRoad(boolean hasTollRoad) {
		this.hasTollRoad = hasTollRoad;
	}
	public boolean isHasFerry() {
		return hasFerry;
	}
	public void setHasFerry(boolean hasFerry) {
		this.hasFerry = hasFerry;
	}
	public boolean isHasHighway() {
		return hasHighway;
	}
	public void setHasHighway(boolean hasHighway) {
		this.hasHighway = hasHighway;
	}
	public boolean isHasSeasonalClosure() {
		return hasSeasonalClosure;
	}
	public void setHasSeasonalClosure(boolean hasSeasonalClosure) {
		this.hasSeasonalClosure = hasSeasonalClosure;
	}
	public boolean isHasUnpaved() {
		return hasUnpaved;
	}
	public void setHasUnpaved(boolean hasUnpaved) {
		this.hasUnpaved = hasUnpaved;
	}
	public boolean isHasCountryCross() {
		return hasCountryCross;
	}
	public void setHasCountryCross(boolean hasCountryCross) {
		this.hasCountryCross = hasCountryCross;
	}
	public List<MapCoordinates> getBoundingBox() {
		return boundingBox;
	}
	public void setBoundingBox(List<MapCoordinates> boundingBox) {
		this.boundingBox = boundingBox;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getFormattedTime() {
		return formattedTime;
	}
	public void setFormattedTime(String formattedTime) {
		this.formattedTime = formattedTime;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getFuelUsed() {
		return fuelUsed;
	}
	public void setFuelUsed(double fuelUsed) {
		this.fuelUsed = fuelUsed;
	}
	public List<Leg> getLegs() {
		return legs;
	}
	public void setLegs(List<Leg> legs) {
		this.legs = legs;
	}
	public List<Integer> getLocationSequence() {
		return locationSequence;
	}
	public void setLocationSequence(List<Integer> locationSequence) {
		this.locationSequence = locationSequence;
	}
	public List<Location> getLocations() {
		return locations;
	}
	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}
	public List<String> getOptions() {
		return options;
	}
	public void setOptions(List<String> options) {
		this.options = options;
	}
	public int getRealTime() {
		return realTime;
	}
	public void setRealTime(int realTime) {
		this.realTime = realTime;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public Shape getShape() {
		return shape;
	}
	public void setShape(Shape shape) {
		this.shape = shape;
	}
}
