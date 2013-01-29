package com.thingtrack.bustrack.view.module.servicetemplate.addon.parser.exception;

public class ServiceParsingException extends Exception {
	
	public static final String INVALID_FORMAT = "Service sheet with no valid format";
	public static final String UNEXPECTED_VALUE = "Unexpected value in the row "; 
	
	public ServiceParsingException(String message){
		super(message);
	}
	
	public ServiceParsingException(Exception e){
		super(e);
	}

}
