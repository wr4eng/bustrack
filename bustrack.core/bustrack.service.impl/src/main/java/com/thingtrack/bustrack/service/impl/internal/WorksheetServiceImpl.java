package com.thingtrack.bustrack.service.impl.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.dao.api.RouteStatusDao;
import com.thingtrack.bustrack.dao.api.WorksheetDao;
import com.thingtrack.bustrack.dao.api.WorksheetStatusDao;
import com.thingtrack.bustrack.domain.Route;
import com.thingtrack.bustrack.domain.RouteStatus;
import com.thingtrack.bustrack.domain.Stop;
import com.thingtrack.bustrack.domain.Vehicle;
import com.thingtrack.bustrack.domain.Worksheet;
import com.thingtrack.bustrack.domain.WorksheetLine;
import com.thingtrack.bustrack.domain.WorksheetStatus;
import com.thingtrack.bustrack.service.api.WorksheetService;
import com.thingtrack.konekti.dao.api.ServiceStatusDao;
import com.thingtrack.konekti.dao.api.ServiceTypeDao;
import com.thingtrack.konekti.domain.EmployeeAgent;
import com.thingtrack.konekti.domain.Organization;
import com.thingtrack.konekti.domain.Service;
import com.thingtrack.konekti.domain.ServiceStatus;
import com.thingtrack.konekti.domain.ServiceType;

/**
 * @author Thingtrack S.L.
*
*/
public class WorksheetServiceImpl implements WorksheetService {
	@Autowired
	private WorksheetDao worksheetDao;

	@Autowired
	private ServiceTypeDao serviceTypeDao;

	@Autowired
	private ServiceStatusDao serviceStatusDao;
	
	@Autowired
	private WorksheetStatusDao worksheetStatusDao;

	@Autowired
	private RouteStatusDao routeStatusDao;
	
	@Override
	public List<Worksheet> getAll() throws Exception {
		return this.worksheetDao.getAll();
		
	}

	@Override
	public Worksheet get(Integer worksheetId) throws Exception {
		return this.worksheetDao.get(worksheetId);
		
	}

	@Override
	public Worksheet save(Worksheet worksheet) throws Exception {
		return this.worksheetDao.save(worksheet);
		
	}

	@Override
	public void delete(Worksheet worksheet) throws Exception {
		this.worksheetDao.delete(worksheet);
		
	}

	@Override
	public List<Service> getOpenServicesFromUserToday(EmployeeAgent employeeAgent, Organization organization, Vehicle vehicle) {
		return this.worksheetDao.getOpenServicesFromUserToday(employeeAgent, organization, vehicle);
		
	}

	@Override
	public Worksheet getOpenWorksheetFromUserToday(EmployeeAgent employeeAgent, Organization organization, Vehicle vehicle) {
		return this.worksheetDao.getOpenWorksheetFromUserToday(employeeAgent, organization, vehicle);
		
	}
	
	@Override
	public Worksheet setOpenWorksheet(Worksheet worksheet) throws Exception {
		worksheet.setWorksheetStartDate(new Date());
		
		return this.worksheetDao.save(worksheet);
		
	}
	
	@Override
	public Worksheet setCloseWorksheet(Worksheet worksheet) throws Exception {
		WorksheetStatus worksheetStatus = worksheetStatusDao.getByCode("CLOSE");
		worksheet.setWorksheetEndDate(new Date());
		worksheet.setWorksheetstatus(worksheetStatus);
		
		return this.worksheetDao.save(worksheet);
		
	}
	
	@Override
	public Worksheet addDefaultService(Organization organization, Worksheet worksheet, Vehicle vehicle, EmployeeAgent driver, String description) throws Exception {
		RouteStatus routeStatus = routeStatusDao.getByCode(RouteStatus.STATUS.RUNNING.name());
		ServiceType serviceType = serviceTypeDao.getByCode(ServiceType.TYPE.UNPLANNED.name());
		ServiceStatus serviceStatus = serviceStatusDao.getByCode(ServiceStatus.STATUS.OPENED.name());
		
		Date now = new Date();
		
		int number = worksheet.getWorksheetLines().size() + 1;
		
		// create two stops
		List<Stop> stops = new ArrayList<Stop>();
		 
		Stop stopFrom = new Stop();
		stopFrom.setActive(true);
		stopFrom.setComment(description);
		//FIXME : We have to pass an Address Object now
//		stopFrom.setStopAddress(ServiceType.TYPE.UNPLANNED.name());
		stopFrom.setStopCheckoutDate(now);
		
		Stop stopTo = new Stop();
		stopTo.setActive(true);
		stopTo.setComment(description);
		//FIXME : We have to pass an Address Object now
//		stopTo.setStopAddress(ServiceType.TYPE.UNPLANNED.name());
		
		stops.add(stopFrom);
		stops.add(stopTo);
		
		// create a new unplanned route
		Route route = new Route();
		
		route.setActive(true);
		route.setDescription(description);
		route.setVehicle(vehicle);
		route.setDriver(driver);
		route.setKilometer("0");
		route.setCode(ServiceType.TYPE.UNPLANNED.name() + "-" + number);
		route.setStartDate(now);
		route.setRouteStatus(routeStatus);
		
//		route.asetStops(stops);
		
		List<Route> routes = new ArrayList<Route>();
		routes.add(route);
		 
		// create new unplanned service		
		Service service = new Service();
		
		service.setCode(ServiceType.TYPE.UNPLANNED.name() + "-" + number);
		service.setDescription(description);
		service.setReservationDate(now);
		service.setStartService(now);
		service.setOrganization(organization);
		service.setServiceStatus(serviceStatus);
		service.setServiceType(serviceType);
			
//		service.setRoutes(routes);
		
		WorksheetLine worksheetLine = new WorksheetLine();
		
		// create new unplanned worksheet line and save worksheet
		worksheetLine.setWorksheet(worksheet);
		worksheetLine.setWorksheetDate(now);
		worksheetLine.setNumber(number);
		worksheetLine.setService(service);
		
		worksheet.getWorksheetLines().add(worksheetLine);
		
		return this.save(worksheet);
	}

	@Override
	public Worksheet getWorsheetAssigned(EmployeeAgent employeeAgent,
			Organization organization, Date startDate) {
		return this.worksheetDao.getWorsheetAssigned(employeeAgent, organization, startDate);
	}
}
