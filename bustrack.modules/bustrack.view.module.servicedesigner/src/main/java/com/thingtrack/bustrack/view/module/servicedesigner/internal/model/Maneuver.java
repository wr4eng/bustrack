package com.thingtrack.bustrack.view.module.servicedesigner.internal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Maneuver implements Serializable {

	public enum Direction{
		
		NONE,
		NORTH,
		NORTHWEST,
		NORTHEAST,
		SOUTH,
		SOUTHEAST,
		SOUTHWEST,
		WEST,
		EAST;
	}
	
	public enum TurnType{
		
		STRAIGHT,
		SLIGHT_RIGHT,
		RIGHT,
		SHARP_RIGHT,
		REVERSE,
		SHARP_LEFT,
		LEFT,
		SLIGHT_LEFT,
		RIGHT_U_TURN,
		LEFT_U_TURN,
		RIGHT_MERGE,
		LEFT_MERGE,
		RIGHT_ON_RAMP,
		LEFT_ON_RAMP,
		RIGHT_OFF_RAMP,
		LEFT_OFF_RAMP,
		RIGHT_FORK,
		LEFT_FORM,
		STRAIGHT_FORK;
	}
	
	public enum TransportMode{
		
		AUTO;
		//TODO: Define the rest of modes
	}
	
	private int index;
	private int attributes;
	private Direction direcction;
	private double distance;
	private String formattedTime;
	private String iconURl;
	private TurnType turnType;
	private MapCoordinates startPoint;
	private List<String> streets = new ArrayList<String>();
	private int time;
	//TODO: linkIds
	//TODO: maneuverNotes
	private String mapURl;
	private String narrative;
	private TransportMode transportMode;
	private List<Sign> signs = new ArrayList<Sign>();
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getAttributes() {
		return attributes;
	}
	public void setAttributes(int attributes) {
		this.attributes = attributes;
	}
	public Direction getDirection() {
		return direcction;
	}
	public void setDirection(Direction direcction) {
		this.direcction = direcction;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public String getFormattedTime() {
		return formattedTime;
	}
	public void setFormattedTime(String formattedTime) {
		this.formattedTime = formattedTime;
	}
	public String getIconUrl() {
		return iconURl;
	}
	public void setIconUrl(String iconURl) {
		this.iconURl = iconURl;
	}
	public TurnType getTurnType() {
		return turnType;
	}
	public void setTurnType(TurnType turnType) {
		this.turnType = turnType;
	}
	public MapCoordinates getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(MapCoordinates startPoint) {
		this.startPoint = startPoint;
	}
	public List<String> getStreets() {
		return streets;
	}
	public void setStreets(List<String> streets) {
		this.streets = streets;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public String getMapUrl() {
		return mapURl;
	}
	public void setMapUrl(String mapURl) {
		this.mapURl = mapURl;
	}
	public String getNarrative() {
		return narrative;
	}
	public void setNarrative(String narrative) {
		this.narrative = narrative;
	}
	public TransportMode getTransportMode() {
		return transportMode;
	}
	public void setTransportMode(TransportMode transportMode) {
		this.transportMode = transportMode;
	}
	public List<Sign> getSigns() {
		return signs;
	}
	public void setSigns(List<Sign> signs) {
		this.signs = signs;
	}

}
