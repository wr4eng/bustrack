package com.thingtrack.bustrack.view.web.form.validator;

import java.util.Collection;
import java.util.List;

import com.thingtrack.bustrack.domain.Route;
import com.thingtrack.bustrack.domain.WorksheetLine;
import com.thingtrack.konekti.domain.Service;
import com.vaadin.data.Validator;

public class AssignedVehicleValidator implements Validator {

	private String errorMessage;

	@Override
	public void validate(Object value) throws InvalidValueException {

		if (!isValid(value))
			throw new InvalidValueException(errorMessage);
	}

	@Override
	public boolean isValid(Object value) {

		if (!(value instanceof Collection))
			return false;

		List<WorksheetLine> worksheetLines = (List<WorksheetLine>) value;

		Route previousRoute = null;

		for (WorksheetLine worksheetLine : worksheetLines) {

			Service service = worksheetLine.getService();

			for (Route route : service.getRoutes()) {
				if (route.getDriver() != null && route.getVehicle() == null) {
					errorMessage = "En el servicio " + service.getCode()
							+ ", la ruta " + route.getCode()
							+ " no tiene vehiculo asignado";
					return false;
				}
			}
		}

		return false;
	}

}
