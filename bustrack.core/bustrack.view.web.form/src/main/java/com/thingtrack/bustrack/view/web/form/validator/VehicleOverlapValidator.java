package com.thingtrack.bustrack.view.web.form.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.thingtrack.bustrack.domain.Route;
import com.thingtrack.bustrack.domain.WorksheetLine;
import com.vaadin.data.Validator;

public class VehicleOverlapValidator implements Validator {

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

		List<Route> allRoutes = new ArrayList<Route>();

		for (WorksheetLine worksheetLine : worksheetLines)
			allRoutes.addAll(worksheetLine.getService().getRoutes());

		// Order by checkout date
		Collections.sort(allRoutes, new RouteComparator());

		Route previousRoute = null;
		for (Route route : allRoutes) {

			if (previousRoute != null) {

				if (previousRoute.getStops().get(1).getStopArrivalDate()
						.after(route.getStops().get(1).getStopArrivalDate())) {

					if (previousRoute.getVehicle().equals(route.getVehicle())) {
						errorMessage = "La ruta " + previousRoute.getCode()
								+ " tiene un solapamiento con la ruta "
								+ route.getCode() + "asignando el veh’culo "
								+ route.getVehicle().getVehicleNumber();
						return false;
					}
				}

			}

			previousRoute = route;
		}

		return true;

	}

	private class RouteComparator implements Comparator<Route> {

		@Override
		public int compare(Route arg0, Route arg1) {

			return arg0.getStops().get(0).getStopCheckoutDate()
					.compareTo(arg1.getStops().get(0).getStopCheckoutDate());
		}
	}

}
