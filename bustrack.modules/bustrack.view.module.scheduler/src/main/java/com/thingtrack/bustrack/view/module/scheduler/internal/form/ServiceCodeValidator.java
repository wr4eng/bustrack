package com.thingtrack.bustrack.view.module.scheduler.internal.form;

import com.thingtrack.konekti.domain.Service;
import com.thingtrack.konekti.service.api.ServiceService;
import com.vaadin.data.validator.AbstractValidator;

public class ServiceCodeValidator extends  AbstractValidator{

	private String errorMessage;
	private ServiceService serviceService;
	private ServiceCodeInvalidException causeException;
	
	public ServiceCodeValidator(ServiceService serviceService) {
		
		super("Ya existe un servicio en el sistema con el título: {0}");
		this.serviceService = serviceService;
	}
	
	@Override
	public void validate(Object value) throws InvalidValueException {
		
		if (!isValid(value)) {
            
			String message = getErrorMessage().replace("{0}", String.valueOf(value));
            throw new InvalidValueException(message, new InvalidValueException[]{causeException});
        }
	}
	
	@Override
	public boolean isValid(Object value) {
		
		String code = (String) value;
		Service service = null;
		try {
			service = serviceService.getByCode(code);
		} catch (Exception e) {
			return true;			
		}
		
		causeException = new ServiceCodeInvalidException("", service);
		return false;
	}

}
