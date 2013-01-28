package com.thingtrack.bustrack.service.impl.internal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.domain.OfferRequestLineRegularService;
import com.thingtrack.bustrack.domain.Route;
import com.thingtrack.bustrack.domain.RouteStatus;
import com.thingtrack.bustrack.domain.Stop;
import com.thingtrack.bustrack.dao.api.OfferRequestLineRegularServiceDao;
import com.thingtrack.bustrack.dao.api.RouteStatusDao;
import com.thingtrack.bustrack.service.api.OfferRequestLineRegularServiceService;
import com.thingtrack.konekti.dao.api.AddressDao;
import com.thingtrack.konekti.dao.api.OfferLineStatusDao;
import com.thingtrack.konekti.dao.api.OfferRequestLineStatusDao;
import com.thingtrack.konekti.dao.api.SequenceDao;
import com.thingtrack.konekti.dao.api.ServiceStatusDao;
import com.thingtrack.konekti.dao.api.ServiceTypeDao;
import com.thingtrack.konekti.domain.Address;
import com.thingtrack.konekti.domain.Offer;
import com.thingtrack.konekti.domain.OfferLine;
import com.thingtrack.konekti.domain.OfferLineStatus;
import com.thingtrack.konekti.domain.OfferRequest;
import com.thingtrack.konekti.domain.OfferRequestLine;
import com.thingtrack.konekti.domain.OfferRequestLineStatus;
import com.thingtrack.konekti.domain.Organization;
import com.thingtrack.konekti.domain.Sequence;
import com.thingtrack.konekti.domain.Service;
import com.thingtrack.konekti.domain.ServiceStatus;
import com.thingtrack.konekti.domain.ServiceType;

/**
 * @author Thingtrack S.L.
 *
 */
public class OfferRequestLineRegularServiceServiceImpl implements OfferRequestLineRegularServiceService {
	@Autowired
	private OfferRequestLineRegularServiceDao offerRequestLineRegularServiceDao;

	@Autowired
	private OfferRequestLineStatusDao offerRequestLineStatusDao;
	
	@Autowired
	private ServiceStatusDao serviceStatusDao;

	@Autowired
	private ServiceTypeDao serviceTypeDao;
	
	@Autowired
	private RouteStatusDao routeStatusDao;
	
	@Autowired
	private AddressDao addressDao;

	@Autowired
	private OfferLineStatusDao offerLineStatusDao;
	
	@Autowired
	private SequenceDao sequenceDao;
	
	@Override
	public List<OfferRequestLineRegularService> getAll() throws Exception {
		return this.offerRequestLineRegularServiceDao.getAll();
		
	}

	@Override
	public OfferRequestLineRegularService get(Integer offerRequestLineRegularServiceId) throws Exception {
		return this.offerRequestLineRegularServiceDao.get(offerRequestLineRegularServiceId);
		
	}

	@Override
	public OfferRequestLineRegularService getByNumber(Integer number) throws Exception {
		return this.offerRequestLineRegularServiceDao.getByNumber(number);
		
	}

	@Override
	public OfferRequestLineRegularService save(OfferRequestLineRegularService offerRequestLineRegularService) throws Exception {
		return this.offerRequestLineRegularServiceDao.save(offerRequestLineRegularService);
		
	}

	@Override
	public void delete(OfferRequestLineRegularService offerRequestLineRegularService) throws Exception {
		this.offerRequestLineRegularServiceDao.delete(offerRequestLineRegularService);
		
	}

	@Override
	public List<OfferRequestLineRegularService> getByOpenedStatus(OfferRequest offerRequest) throws Exception {
		OfferRequestLineStatus offerRequestLineStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.OPENED.name());
		
		return this.offerRequestLineRegularServiceDao.getByStatus(offerRequest, offerRequestLineStatus);
	}

	@Override
	public List<OfferRequestLineRegularService> getByPendingStatus(OfferRequest offerRequest) throws Exception {
		OfferRequestLineStatus offerRequestLineStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.PENDING.name());
		
		return this.offerRequestLineRegularServiceDao.getByStatus(offerRequest, offerRequestLineStatus);
	}

	@Override
	public List<OfferRequestLineRegularService> getByTransferedStatus(OfferRequest offerRequest) throws Exception {
		OfferRequestLineStatus offerRequestLineStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.TRANSFERRED.name());
		
		return this.offerRequestLineRegularServiceDao.getByStatus(offerRequest, offerRequestLineStatus);
	}

	@Override
	public List<OfferRequestLineRegularService> getByOpenedOrPendingStatus(OfferRequest offerRequest) throws Exception {
		OfferRequestLineStatus offerRequestLineOpenedStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.OPENED.name());
		OfferRequestLineStatus offerRequestLinePendingStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.PENDING.name());
		
		List<OfferRequestLineStatus> status = new ArrayList<OfferRequestLineStatus>();
		status.add(offerRequestLineOpenedStatus);
		status.add(offerRequestLinePendingStatus);
		
		return this.offerRequestLineRegularServiceDao.getByStatusCollection(offerRequest, status);
	}
	
	@Override
	public List<OfferRequestLineRegularService> getByRejetedStatus(OfferRequest offerRequest) throws Exception {
		OfferRequestLineStatus offerRequestLineStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.REJECTED.name());
		
		return this.offerRequestLineRegularServiceDao.getByStatus(offerRequest, offerRequestLineStatus);
	}

	@Override
	public List<OfferRequestLineRegularService> getByClosedStatus(OfferRequest offerRequest) throws Exception {
		OfferRequestLineStatus offerRequestLineStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.CLOSED.name());
		
		return this.offerRequestLineRegularServiceDao.getByStatus(offerRequest, offerRequestLineStatus);
	}

	@Override
	public void setOpenedStatus(OfferRequestLineRegularService offerRequestLineRegularService) throws Exception {
		OfferRequestLineStatus offerRequestLineStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.OPENED.name());
		
		offerRequestLineRegularService.setOfferRequestLineStatus(offerRequestLineStatus);
		offerRequestLineRegularServiceDao.save(offerRequestLineRegularService);
		
	}

	@Override
	public void setPendingStatus(OfferRequestLineRegularService offerRequestLineRegularService) throws Exception {
		OfferRequestLineStatus offerRequestLineStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.PENDING.name());
		
		offerRequestLineRegularService.setOfferRequestLineStatus(offerRequestLineStatus);
		offerRequestLineRegularServiceDao.save(offerRequestLineRegularService);
		
	}

	@Override
	public void setTransferredStatus(OfferRequestLineRegularService offerRequestLineRegularService) throws Exception {
		OfferRequestLineStatus offerRequestLineStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.TRANSFERRED.name());
		
		offerRequestLineRegularService.setOfferRequestLineStatus(offerRequestLineStatus);
		offerRequestLineRegularServiceDao.save(offerRequestLineRegularService);
		
	}

	@Override
	public void setRejectStatus(OfferRequestLineRegularService offerRequestLineRegularService) throws Exception {
		OfferRequestLineStatus offerRequestLineStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.REJECTED.name());
		
		offerRequestLineRegularService.setOfferRequestLineStatus(offerRequestLineStatus);
		offerRequestLineRegularServiceDao.save(offerRequestLineRegularService);
		
	}

	@Override
	public void setCloseStatus(OfferRequestLineRegularService offerRequestLineRegularService) throws Exception {
		OfferRequestLineStatus offerRequestLineStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.CLOSED.name());
		
		offerRequestLineRegularService.setOfferRequestLineStatus(offerRequestLineStatus);
		offerRequestLineRegularServiceDao.save(offerRequestLineRegularService);
		
	}

	@Override
	public Offer createOfferLine(Organization organization, Offer offer, OfferRequestLineRegularService offerRequestLineRegularService, int number) throws Exception {
		// get default offerrequest status and save
		/*OfferRequestLineStatus offerRequestLineStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.TRANSFERRED.name());
		
		offerRequestLineRegularService.setOfferRequestLineStatus(offerRequestLineStatus);
		offerRequestLineRegularServiceDao.save(offerRequestLineRegularService);
		
		// get default service status
		ServiceStatus serviceStatus = serviceStatusDao.getByCode(Service.STATUS.OPENED.name());
				
		// get service type	from Offer Request or default is not exist
		ServiceType serviceType = null;
		try {
			serviceType = serviceTypeDao.getByCode(offerRequestLineRegularService.getOfferRequest().getOfferRequestType().getCode());
		}
		catch (Exception e) {
			serviceType = serviceTypeDao.getByCode(Service.TYPE.OTHER.name());
		}
		
		// create offerline
		// get default offer Status
		OfferLineStatus offerLineStatus = offerLineStatusDao.getByCode(OfferLine.STATUS.OPENED.name());	
		
		OfferLine offerLine = new OfferLine();
		offerLine.setNumber(number);
		offerLine.setOffer(offer);
		offerLine.setOfferLineStatus(offerLineStatus);
		offerLine.setOfferLineDate(new Date());
		
		// loop under date interval to create services
		Calendar startInterval = Calendar.getInstance();
		startInterval.setTime(offerRequestLineRegularService.getValidateStartDate());
		Calendar endInterval = Calendar.getInstance();
		endInterval.setTime(offerRequestLineRegularService.getValidateEndDate());
		
		// get Days of Week
		boolean isMonday = false;
		boolean isTuesday = false;
		boolean isWednesday = false;
		boolean isThursday = false;
		boolean isFriday = false;
		boolean isSaturday = false;
		boolean isSunday = false;
		
		char[] stringArray = null;
		String dayOfWeekCollection = offerRequestLineRegularService.getWeekDay();
		stringArray = dayOfWeekCollection.toCharArray();
		
		for(int index=0; index < stringArray.length; index++) {
			if (stringArray[index] == 'L')
				isMonday = true;
			    	
			if (stringArray[index] == 'M')
				isTuesday = true;
			    	
			if (stringArray[index] == 'X')
				isWednesday = true;
			    	
			if (stringArray[index] == 'J')
				isThursday = true;
			    	
			if (stringArray[index] == 'V')
				isFriday = true;
		   	
			if (stringArray[index] == 'S')
				isSaturday = true;
			    	
			if (stringArray[index] == 'D')
				isSunday = true;
		}
		
		// loop between interval and create services
		for(; !startInterval.after(endInterval); startInterval.add(Calendar.DATE, 1)) {
			Date current = null;
			
			if (isMonday && startInterval.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
				current = startInterval.getTime();
			else if(isTuesday && startInterval.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY)
				current = startInterval.getTime();
			else if(isWednesday && startInterval.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY)
				current = startInterval.getTime();
			else if(isThursday && startInterval.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)
				current = startInterval.getTime();
			else if(isFriday && startInterval.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
				current = startInterval.getTime();		
			else if(isSaturday && startInterval.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				current = startInterval.getTime();
			else if(isSunday && startInterval.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				current = startInterval.getTime();

			// get default service status
			RouteStatus routeStatus = routeStatusDao.getByCode(Route.STATUS.PENDING.name());
			
			if (current != null) {
				// create new Service Entity
				Service service = new Service();
				service.setOrganization(organization);
				service.setCode(sequenceDao.setNextSequence(Sequence.CODE.SERVICE.name()));	
				service.setVehicleType(offerRequestLineRegularService.getVehicleType());
				service.setServiceType(serviceType);
				service.setServiceStatus(serviceStatus);
				service.setClient(offer.getOfferClient());
				//service.setPassengers(offerRequestLineFixService.getPassengers());
				service.setReservationDate(new Date());

				// create new Route Entity				
				Route route = new Route();
				route.setCode(sequenceDao.setNextSequence(Sequence.CODE.ROUTE.name()));
				route.setActive(true);
				route.setRouteStatus(routeStatus);

				// create Start Stop Entity
				Stop startStop = new Stop();
				Address startAddress = new Address();
				startAddress.setStreet(offerRequestLineRegularService.getStartStop());
				startAddress = addressDao.save(startAddress);
				
				Calendar dCalStart = Calendar.getInstance();
				dCalStart.setTime(current);
				Calendar tCalStart = Calendar.getInstance();
				tCalStart.setTime(offerRequestLineRegularService.getStartTime());
				
				dCalStart.set(Calendar.HOUR_OF_DAY, tCalStart.get(Calendar.HOUR_OF_DAY));
				dCalStart.set(Calendar.MINUTE, tCalStart.get(Calendar.MINUTE));
								
				startStop.setStopAddress(startAddress);
				startStop.setStopCheckoutDate(dCalStart.getTime());
				startStop.setActive(true);

				// create End Stop Entity
				Stop stopEnd = new Stop();
				Address stopAddress = new Address();
				stopAddress.setStreet(offerRequestLineRegularService.getEndStop());
				stopAddress = addressDao.save(startAddress);
				
				Calendar dCalEnd = Calendar.getInstance();
				dCalEnd.setTime(current);
				Calendar tCalEnd = Calendar.getInstance();
				tCalEnd.setTime(offerRequestLineRegularService.getEndTime());
				
				dCalEnd.set(Calendar.HOUR_OF_DAY, tCalEnd.get(Calendar.HOUR_OF_DAY));
				dCalEnd.set(Calendar.MINUTE, tCalEnd.get(Calendar.MINUTE));
				
				stopEnd.setStopAddress(stopAddress);
				stopEnd.setStopArrivalDate(dCalEnd.getTime());
				stopEnd.setActive(true);

				// add stops to route
				route.addStop(startStop);
				route.addStop(stopEnd);

				// add route to service
				service.addRoute(route);
				
				// add service to offer line
				offerLine.addService(service);
			}
			
			current = null;						
		}
		
		// add OfferLine to Offer
		offer.addOfferLine(offerLine);
		
		return offer;*/
		return null;
	}
	
	@Override
	public OfferRequestLineRegularService createNewOfferRequestLineFixService(OfferRequest offerRequest) throws Exception {
		OfferRequestLineRegularService offerRequestLineRegularService = new OfferRequestLineRegularService();
				
		offerRequestLineRegularService.setOfferRequest(offerRequest);
		offerRequestLineRegularService.setNumber(offerRequest.getOfferRequestLineFixServices().size() + 1);
		offerRequestLineRegularService.setOfferRequestLineDate(new Date());
		offerRequestLineRegularService.setOfferRequestLineStatus(offerRequestLineStatusDao.getByCode(OfferLine.STATUS.OPENED.name()));
		
		return offerRequestLineRegularService;
	}
}
