package com.thingtrack.bustrack.view.module.scheduler.internal.form;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.thingtrack.bustrack.domain.Route;
import com.thingtrack.bustrack.domain.Stop;
import com.vaadin.data.Validator;

@SuppressWarnings("serial")
public class RoutesValidator implements Validator {

	private String errorMessage;
	
	@Override
	public void validate(Object value) throws InvalidValueException {

		if(!isValid(value))
			throw new InvalidValueException(errorMessage);
	}

	@Override
	public boolean isValid(Object value) {
		
		if(!(value instanceof Collection))
			return false; 
		
		@SuppressWarnings("unchecked")
		List<Route> routes =  (List<Route>) value;
		
		
		Date currentDate = new Date(Long.MIN_VALUE);    
		
		for(Route route : routes){
			
			//Stops
			Stop origin =  route.getStops().get(0);
			Stop destination =  route.getStops().get(1);
			
			//Validate if the checkout of the origin is less than the arrival of the destination
			if(currentDate.after(origin.getStopCheckoutDate())){
				errorMessage = "La fecha de salida de la ruta " + route.getCode() + " es inferior a la de llegada de la ruta anterior o de inicio de servicio";
				return false;
			}
			
			currentDate = origin.getStopCheckoutDate();
			
			
			if(currentDate.after(destination.getStopArrivalDate())){
				errorMessage = "La fecha de llegada de la ruta " + route.getCode() + " es inferior a la fecha de salida";
				return false;
			}
			
			currentDate = destination.getStopArrivalDate();
		}
		return true;
	}

}
