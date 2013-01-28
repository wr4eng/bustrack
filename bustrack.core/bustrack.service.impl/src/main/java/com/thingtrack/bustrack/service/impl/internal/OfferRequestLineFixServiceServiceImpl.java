package com.thingtrack.bustrack.service.impl.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.domain.OfferRequestLineFixService;
import com.thingtrack.bustrack.domain.Route;
import com.thingtrack.bustrack.domain.RouteStatus;
import com.thingtrack.bustrack.domain.Stop;
import com.thingtrack.bustrack.dao.api.OfferRequestLineFixServiceDao;
import com.thingtrack.bustrack.dao.api.RouteStatusDao;
import com.thingtrack.bustrack.service.api.OfferRequestLineFixServiceService;
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
public class OfferRequestLineFixServiceServiceImpl implements OfferRequestLineFixServiceService {
	@Autowired
	private OfferRequestLineFixServiceDao offerRequestLineFixServiceDao;
	
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
	public List<OfferRequestLineFixService> getAll() throws Exception {
		return this.offerRequestLineFixServiceDao.getAll();
		
	}

	@Override
	public OfferRequestLineFixService get(Integer offerRequestLineFixServiceId) throws Exception {
		return this.offerRequestLineFixServiceDao.get(offerRequestLineFixServiceId);
		
	}

	@Override
	public OfferRequestLineFixService getByNumber(Integer number) throws Exception {
		return this.offerRequestLineFixServiceDao.getByNumber(number);
		
	}

	@Override
	public OfferRequestLineFixService save(OfferRequestLineFixService offerRequestLineFixService) throws Exception {
		return this.offerRequestLineFixServiceDao.save(offerRequestLineFixService);
		
	}

	@Override
	public void delete(OfferRequestLineFixService offerRequestLineFixService) throws Exception {
		this.offerRequestLineFixServiceDao.delete(offerRequestLineFixService);
		
	}

	@Override
	public List<OfferRequestLineFixService> getByOpenedStatus(OfferRequest offerRequest) throws Exception {
		OfferRequestLineStatus offerRequestLineStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.OPENED.name());
		
		return this.offerRequestLineFixServiceDao.getByStatus(offerRequest, offerRequestLineStatus);
	}

	@Override
	public List<OfferRequestLineFixService> getByPendingStatus(OfferRequest offerRequest) throws Exception {
		OfferRequestLineStatus offerRequestLineStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.PENDING.name());
		
		return this.offerRequestLineFixServiceDao.getByStatus(offerRequest, offerRequestLineStatus);
	}

	@Override
	public List<OfferRequestLineFixService> getByTransferedStatus(OfferRequest offerRequest) throws Exception {
		OfferRequestLineStatus offerRequestLineStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.TRANSFERRED.name());
		
		return this.offerRequestLineFixServiceDao.getByStatus(offerRequest, offerRequestLineStatus);
	}

	@Override
	public List<OfferRequestLineFixService> getByRejetedStatus(OfferRequest offerRequest) throws Exception {
		OfferRequestLineStatus offerRequestLineStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.REJECTED.name());
		
		return this.offerRequestLineFixServiceDao.getByStatus(offerRequest, offerRequestLineStatus);
	}

	@Override
	public List<OfferRequestLineFixService> getByClosedStatus(OfferRequest offerRequest) throws Exception {
		OfferRequestLineStatus offerRequestLineStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.CLOSED.name());
		
		return this.offerRequestLineFixServiceDao.getByStatus(offerRequest, offerRequestLineStatus);
	}

	@Override
	public List<OfferRequestLineFixService> getByOpenedOrPendingStatus(OfferRequest offerRequest) throws Exception {
		OfferRequestLineStatus offerRequestLineOpenedStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.OPENED.name());
		OfferRequestLineStatus offerRequestLinePendingStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.PENDING.name());
		
		List<OfferRequestLineStatus> status = new ArrayList<OfferRequestLineStatus>();
		status.add(offerRequestLineOpenedStatus);
		status.add(offerRequestLinePendingStatus);
		
		return this.offerRequestLineFixServiceDao.getByStatusCollection(offerRequest, status);
	}
	
	@Override
	public void setOpenedStatus(OfferRequestLineFixService offerRequestLineFixService) throws Exception {
		OfferRequestLineStatus offerRequestLineStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.OPENED.name());
		
		offerRequestLineFixService.setOfferRequestLineStatus(offerRequestLineStatus);
		offerRequestLineFixServiceDao.save(offerRequestLineFixService);
		
	}

	@Override
	public void setPendingStatus(OfferRequestLineFixService offerRequestLineFixService) throws Exception {
		OfferRequestLineStatus offerRequestLineStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.PENDING.name());
		
		offerRequestLineFixService.setOfferRequestLineStatus(offerRequestLineStatus);
		offerRequestLineFixServiceDao.save(offerRequestLineFixService);
		
	}

	@Override
	public void setTransferredStatus(OfferRequestLineFixService offerRequestLineFixService) throws Exception {
		OfferRequestLineStatus offerRequestLineStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.TRANSFERRED.name());
		
		offerRequestLineFixService.setOfferRequestLineStatus(offerRequestLineStatus);
		offerRequestLineFixServiceDao.save(offerRequestLineFixService);
		
	}

	@Override
	public void setRejectStatus(OfferRequestLineFixService offerRequestLineFixService) throws Exception {
		OfferRequestLineStatus offerRequestLineStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.REJECTED.name());
		
		offerRequestLineFixService.setOfferRequestLineStatus(offerRequestLineStatus);
		offerRequestLineFixServiceDao.save(offerRequestLineFixService);
		
	}

	@Override
	public void setCloseStatus(OfferRequestLineFixService offerRequestLineFixService) throws Exception {
		OfferRequestLineStatus offerRequestLineStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.CLOSED.name());
		
		offerRequestLineFixService.setOfferRequestLineStatus(offerRequestLineStatus);
		offerRequestLineFixServiceDao.save(offerRequestLineFixService);
		
	}

	@Override
	public Offer createOfferLine(Organization organization, Offer offer, OfferRequestLineFixService offerRequestLineFixService, int number) throws Exception {
		// get default offerrequest status and save
		OfferRequestLineStatus offerRequestLineStatus = offerRequestLineStatusDao.getByCode(OfferRequestLine.STATUS.TRANSFERRED.name());
		
		offerRequestLineFixService.setOfferRequestLineStatus(offerRequestLineStatus);
		offerRequestLineFixServiceDao.save(offerRequestLineFixService);
		
		// get default service status
		ServiceStatus serviceStatus = serviceStatusDao.getByCode(Service.STATUS.OPENED.name());
				
		// get service type	from Offer Request or default is not exist
		ServiceType serviceType = null;
		try {
			serviceType = serviceTypeDao.getByCode(offerRequestLineFixService.getOfferRequest().getOfferRequestType().getCode());
		}
		catch (Exception e) {
			serviceType = serviceTypeDao.getByCode(Service.TYPE.OTHER.name());
		}
		
		// create new Service
		Service service = new Service();
		service.setOrganization(organization);
		service.setCode(sequenceDao.setNextSequence(Sequence.CODE.SERVICE.name()));
		service.setLunch(offerRequestLineFixService.isLunch());
		service.setDinner(offerRequestLineFixService.isDinner());
		service.setAccomodation(offerRequestLineFixService.isAccomodation());
		service.setBreackfast(offerRequestLineFixService.isBreackfast());
		service.setVehicleType(offerRequestLineFixService.getVehicleType());
		service.setServiceType(serviceType);
		service.setServiceStatus(serviceStatus);
		service.setClient(offer.getOfferClient());
		service.setPassengers(offerRequestLineFixService.getPassengers());
		service.setReservationDate(new Date());

		// create new Route for Service
		// get default service status
		RouteStatus routeStatus = routeStatusDao.getByCode(Route.STATUS.PENDING.name());
		
		Route route = new Route();
		route.setCode(sequenceDao.setNextSequence(Sequence.CODE.ROUTE.name()));
		route.setActive(true);
		route.setRouteStatus(routeStatus);
		
		// create two nodes to route
		Stop startStop = new Stop();
		Stop stopEnd = new Stop();
		
		// create Address not geolocated
		Address startAddress = new Address();
		startAddress.setStreet(offerRequestLineFixService.getStartStop());
		startAddress = addressDao.save(startAddress);

		startStop.setStopAddress(startAddress);
		startStop.setStopCheckoutDate(offerRequestLineFixService.getStartDate());
		startStop.setActive(true);

		Address stopAddress = new Address();
		stopAddress.setStreet(offerRequestLineFixService.getEndStop());
		stopAddress = addressDao.save(stopAddress);

		stopEnd.setStopAddress(stopAddress);
		stopEnd.setStopArrivalDate(offerRequestLineFixService.getEndDate());
		stopEnd.setActive(true);
		
		// add route stops
		route.addStop(startStop);
		route.addStop(stopEnd);
		
		// add route to service
		service.addRoute(route);
		
		// create offerline
		// get default offer Status
		OfferLineStatus offerLineStatus = offerLineStatusDao.getByCode(OfferLine.STATUS.OPENED.name());		
		
		OfferLine offerLine = new OfferLine();
		offerLine.setNumber(number);
		offerLine.setOffer(offer);
		offerLine.addService(service);
		offerLine.setOfferLineStatus(offerLineStatus);
		offerLine.setOfferLineDate(new Date());
		
		// add OfferLine to Offer
		offer.addOfferLine(offerLine);
		
		return offer;
	}

	@Override
	public OfferRequestLineFixService createNewOfferRequestLineFixService(OfferRequest offerRequest) throws Exception {
		OfferRequestLineFixService offerRequestLineFixService = new OfferRequestLineFixService();
				
		offerRequestLineFixService.setOfferRequest(offerRequest);
		offerRequestLineFixService.setNumber(offerRequest.getOfferRequestLineFixServices().size() + 1);
		offerRequestLineFixService.setOfferRequestLineDate(new Date());
		offerRequestLineFixService.setOfferRequestLineStatus(offerRequestLineStatusDao.getByCode(OfferLine.STATUS.OPENED.name()));
		
		return offerRequestLineFixService;
	}
	
}
