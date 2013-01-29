package com.thingtrack.bustrack.view.module.servicedesigner.internal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Shape implements Serializable {
	
	List<Integer> legIndex = new ArrayList<Integer>();
	List<Integer> maneuverIndex = new ArrayList<Integer>();
	List<MapCoordinates> shapePoints = new ArrayList<MapCoordinates>();
	
	public List<Integer> getLegIndex() {
		return legIndex;
	}
	public void setLegIndex(List<Integer> legIndex) {
		this.legIndex = legIndex;
	}
	public List<Integer> getManeuverIndex() {
		return maneuverIndex;
	}
	public void setManeuverIndex(List<Integer> maneuverIndex) {
		this.maneuverIndex = maneuverIndex;
	}
	public List<MapCoordinates> getShapePoints() {
		return shapePoints;
	}
	public void setShapePoints(List<MapCoordinates> shapePoints) {
		this.shapePoints = shapePoints;
	}
	
	

}
