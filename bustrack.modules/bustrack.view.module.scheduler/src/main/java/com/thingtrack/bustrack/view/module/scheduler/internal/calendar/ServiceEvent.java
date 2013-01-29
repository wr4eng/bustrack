package com.thingtrack.bustrack.view.module.scheduler.internal.calendar;

import java.util.Date;
import java.util.List;

import com.thingtrack.bustrack.domain.Route;
import com.thingtrack.konekti.domain.Client;
import com.thingtrack.konekti.domain.InvoiceLine;
import com.thingtrack.konekti.domain.OfferLine;
import com.thingtrack.konekti.domain.Service;
import com.thingtrack.konekti.domain.ServiceStatus;
import com.thingtrack.konekti.domain.ServiceType;
import com.vaadin.addon.calendar.event.BasicEvent;

/**
 * Service event is a {@link com.thingtrack.konekti.domain.Service Service}
 * wrapper and it extends of {@link com.vaadin.addon.calendar.event.BasicEvent
 * BasicEvent}. Has setters for all required fields and fires events when this
 * event is changed.
 * */
@SuppressWarnings("serial")
public class ServiceEvent extends BasicEvent {

	public static final String OPENED_STYLE = "opened";
	public static final String EXECUTING_STYLE = "executing";
	public static final String COMPLETED_STYLE = "completed";
	public static final String ASSIGNED_STYLE = "assigned";
	public static final String CANCELLED_STYLE = "cancelled";
	public static final String STOPPED_STYLE = "stopped";

	private Service service;

	public ServiceEvent() {

		super();

		// Service domain object
		service = new Service();
	}

	public ServiceEvent(Service service, Date startDate, Date endDate) {

		super(service.getCode(), service.getDescription(), startDate, endDate);
		this.service = service;
	}

	public Service getService() {
		return service;
	}

	public String getCode() {
		return service.getCode();
	}
	
	public String getDescription(){
		return service.getDescription();
	}

	public ServiceType getServiceType() {

		return service.getServiceType();
	}

	public Client getClient() {

		return service.getClient();
	}

	public InvoiceLine getInvoiceLine() {

		return service.getInvoiceLine();
	}

	public OfferLine getOfferline() {

		return service.getOfferLine();
	}

	public ServiceStatus getServiceStatus() {

		return service.getServiceStatus();
	}

	public List<Route> getRoutes() {
		return service.getRoutes();
	}

	public Date getReservationDate() {

		return service.getReservationDate();
	}

	public Date getScheduleDate() {

		return service.getScheduleDate();
	}

	public void setCode(String code) {

		service.setCode(code);
		this.setCaption(service.getCode());

		fireEventChange();
	}
	
	public void setDescription(String description){
		
		service.setDescription(description);
		super.setDescription(service.getDescription());
		
		fireEventChange();		
	}

	public void setServiceType(ServiceType serviceType) {

		service.setServiceType(serviceType);
		fireEventChange();
	}

	public void setClient(Client client) {

		service.setClient(client);
		fireEventChange();
	}

	public void setInvoiceLine(InvoiceLine invoiceLine) {

		service.setInvoiceLine(invoiceLine);
		fireEventChange();
	}

	public void setOfferline(OfferLine offerLine) {

		service.setOfferLine(offerLine);
		fireEventChange();
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {

		service.setServiceStatus(serviceStatus);
		fireEventChange();
	}

	public void setReservationDate(Date reservationDate) {

		service.setReservationDate(reservationDate);
		fireEventChange();
	}

	public void setScheduleDate(Date scheduleDate) {

		service.setScheduleDate(scheduleDate);
		fireEventChange();
	}

	public void setRoutes(List<Route> routes) {

		//Clean all list
		for(Route route : service.getRoutes())
			service.removeRoute(route);
		
		for (Route route : routes) {
			service.addRoute(route);
		}
		
		//Update the event date range
		setStart(ServiceEventProvider.getMinStartDate(service));
		setEnd(ServiceEventProvider.getMaxStartDate(service));

		fireEventChange();
	}

	private String generateDetail() {

		StringBuffer stringBuffer = new StringBuffer();
		return stringBuffer.toString();
	}

}
