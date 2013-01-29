package com.thingtrack.bustrack.view.module.scheduler.internal.calendar;

import java.util.Date;
import java.util.List;

import com.thingtrack.bustrack.domain.Route;
import com.thingtrack.bustrack.domain.Stop;
import com.thingtrack.konekti.domain.Service;
import com.thingtrack.konekti.domain.ServiceStatus;
import com.vaadin.addon.calendar.event.BasicEventProvider;
import com.vaadin.addon.calendar.event.CalendarEvent;
import com.vaadin.addon.calendar.event.CalendarEvent.EventChange;

/**
 * <p>
 * Service Event Provider implementation extmnds of
 * {@link com.vaadin.addon.calendar.event.BasicEventProvider BasicEventProvider}.
 * 
 * @author Thingtrack S.L.
 */
@SuppressWarnings("serial")
public class ServiceEventProvider extends BasicEventProvider {

	public ServiceEventProvider(List<Service> services) {

		for (Service service : services) {

			ServiceEvent event = new ServiceEvent(service, getMinStartDate(service), getMaxStartDate(service));

			addEvent(event);
		}
	}

	@Override
	public void addEvent(CalendarEvent event) {

		if (!(event instanceof ServiceEvent))
			return;

		ServiceEvent serviceEvent = (ServiceEvent) event;
		
		//Apply the service event style
		if(ServiceStatus.STATUS.ASSIGNED.name().equals(serviceEvent.getServiceStatus().getDescription()))
			serviceEvent.setStyleName(ServiceEvent.ASSIGNED_STYLE);
		
		if(ServiceStatus.STATUS.CANCELLED.name().equals(serviceEvent.getServiceStatus().getDescription()))
			serviceEvent.setStyleName(ServiceEvent.CANCELLED_STYLE);
		
		if(ServiceStatus.STATUS.COMPLETED.name().equals(serviceEvent.getServiceStatus().getDescription()))
			serviceEvent.setStyleName(ServiceEvent.COMPLETED_STYLE);
		
		if(ServiceStatus.STATUS.EXECUTING.name().equals(serviceEvent.getServiceStatus().getDescription()))
			serviceEvent.setStyleName(ServiceEvent.EXECUTING_STYLE);
		
		if(ServiceStatus.STATUS.OPENED.name().equals(serviceEvent.getServiceStatus().getDescription()))
			serviceEvent.setStyleName(ServiceEvent.OPENED_STYLE);
		
		if(ServiceStatus.STATUS.STOPPED.name().equals(serviceEvent.getServiceStatus().getDescription()))
			serviceEvent.setStyleName(ServiceEvent.STOPPED_STYLE);
		
		
		super.addEvent(event);
	}

	@Override
	public void removeEvent(CalendarEvent event) {

		if (!(event instanceof ServiceEvent))
			return;

		super.removeEvent(event);

	}

	public static Date getMinStartDate(Service service) {

		Date startDate = new Date(Long.MAX_VALUE);

		for (Route route : service.getRoutes()) {
			for (Stop stop : route.getStops()) {

				if (stop.getStopArrivalDate() != null
						&& stop.getStopArrivalDate().before(startDate)) {
					startDate = stop.getStopArrivalDate();
				}
				if (stop.getStopCheckoutDate() != null
						&& stop.getStopCheckoutDate().before(startDate)) {
					startDate = stop.getStopCheckoutDate();
				}
			}
		}

		return startDate;
	}

	public static Date getMaxStartDate(Service service) {

		Date endDate = new Date(Long.MIN_VALUE);

		for (Route route : service.getRoutes()) {
			for (Stop stop : route.getStops()) {

				if (stop.getStopArrivalDate() != null
						&& stop.getStopArrivalDate().after(endDate)) {
					endDate = stop.getStopArrivalDate();
				}
				if (stop.getStopCheckoutDate() != null
						&& stop.getStopCheckoutDate().after(endDate)) {
					endDate = stop.getStopCheckoutDate();
				}
			}
		}

		return endDate;

	}

}
