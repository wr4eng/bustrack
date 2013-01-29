package com.thingtrack.bustrack.view.module.servicetemplate.addon.parser.geolocation;

public class GeocodingException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1962942758806315431L;

	public GeocodingException(String message) {
        super(message);
    }

    public GeocodingException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeocodingException(Throwable cause) {
        super(cause);
    }

}
