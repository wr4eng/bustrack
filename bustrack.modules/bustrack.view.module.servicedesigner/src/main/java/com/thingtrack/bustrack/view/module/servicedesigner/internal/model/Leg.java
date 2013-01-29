package com.thingtrack.bustrack.view.module.servicedesigner.internal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Leg implements Serializable {
	
	public enum RoadGradeStrategy{
		
		DEFAULT_STRATEGY,
		AVOID_UP_HILL,
		AVOID_DOWN_HILL,
		AVOID_ALL_HILLS,
		FAVOR_UP_HILL,
		FAVOR_DOWN_HILL,
		FAVOR_ALL_HILLS;
	}
	
	private int origIndex;
	private String origNarrative;
	private int destIndex;
	private String destNarrative;
	private boolean hasTollRoad;
	private boolean hasFerry;
	private boolean hasHighway;
	private boolean hasSeasonalClosure;
	private boolean hasUnpaved;
	private boolean hasCountryCross;
	private int index;
	private int time;	
	private String formattedTime;
	private double distance;
	private List<Maneuver> maneuvers = new ArrayList<Maneuver>();
	private List<RoadGradeStrategy> roadGradeStrategy = new ArrayList<Leg.RoadGradeStrategy>();
	
	public int getOrigIndex() {
		return origIndex;
	}
	public void setOrigIndex(int origIndex) {
		this.origIndex = origIndex;
	}
	public String getOrigNarrative() {
		return origNarrative;
	}
	public void setOrigNarrative(String origNarrative) {
		this.origNarrative = origNarrative;
	}
	public int getDestIndex() {
		return destIndex;
	}
	public void setDestIndex(int destIndex) {
		this.destIndex = destIndex;
	}
	public String getDestNarrative() {
		return destNarrative;
	}
	public void setDestNarrative(String destNarrative) {
		this.destNarrative = destNarrative;
	}
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
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
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
	public List<Maneuver> getManeuvers() {
		return maneuvers;
	}
	public void setManeuvers(List<Maneuver> maneuvers) {
		this.maneuvers = maneuvers;
	}
	public List<RoadGradeStrategy> getRoadGradeStrategy() {
		return roadGradeStrategy;
	}
	public void setRoadGradeStrategy(List<RoadGradeStrategy> roadGradeStrategy) {
		this.roadGradeStrategy = roadGradeStrategy;
	}
	
	

}
