package com.thingtrack.bustrack.view.module.servicedesigner.internal.model;

import java.io.Serializable;

public class Sign implements Serializable {

	private Maneuver.Direction direcction;
	private String extraText;
	private String url;
	//It might be a enum structure
	private String type;
	
	public Maneuver.Direction getDirecction() {
		return direcction;
	}
	public void setDirecction(Maneuver.Direction direcction) {
		this.direcction = direcction;
	}
	public String getExtraText() {
		return extraText;
	}
	public void setExtraText(String extraText) {
		this.extraText = extraText;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
