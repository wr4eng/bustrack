package com.thingtrack.bustrack.view.module.scheduler.internal.form;

import com.thingtrack.konekti.domain.Service;
import com.vaadin.data.Validator.InvalidValueException;

public class ServiceCodeInvalidException extends InvalidValueException {

	private Service service;
	
	public Service getService() {
		return service;
	}

	public ServiceCodeInvalidException(String errorMessage, Service service){
		
		super(errorMessage);
		this.service = service;
	}

	public ServiceCodeInvalidException(String message,
			InvalidValueException[] causes) {
		super(message, causes);
	}

}
