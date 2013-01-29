package com.thingtrack.bustrack.view.module.servicetemplate.addon.parser;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

import com.thingtrack.konekti.domain.Service;
import com.thingtrack.konekti.domain.ServiceStatus;
import com.thingtrack.konekti.domain.ServiceType;
import com.thingtrack.bustrack.domain.Route;
import com.thingtrack.bustrack.domain.RouteStatus;
import com.thingtrack.bustrack.domain.ServiceTemplate;
import com.thingtrack.bustrack.domain.Stop;
import com.thingtrack.konekti.domain.Address;
import com.thingtrack.konekti.service.api.ServiceService;
import com.thingtrack.konekti.service.api.ServiceStatusService;
import com.thingtrack.konekti.service.api.ServiceTypeService;
import com.thingtrack.konekti.view.kernel.IWorkbenchContext;

import com.thingtrack.bustrack.service.api.RouteStatusService;
import com.thingtrack.bustrack.view.module.servicetemplate.addon.parser.exception.NoServiceException;
import com.thingtrack.bustrack.view.module.servicetemplate.addon.parser.exception.ServiceParsingException;
import com.thingtrack.bustrack.view.module.servicetemplate.addon.parser.geolocation.GeocodedLocation;
import com.thingtrack.bustrack.view.module.servicetemplate.addon.parser.geolocation.GeocodingException;
import com.thingtrack.bustrack.view.module.servicetemplate.addon.parser.geolocation.GoogleGeocoder;
import com.thingtrack.bustrack.view.module.servicetemplate.addon.parser.geolocation.NominatinGeocoder;
import com.thingtrack.bustrack.view.module.servicetemplate.internal.ServiceTemplateViewContainer;

public class ServiceTemplateParser {
	private static final String SERVICE_SHEET_SINGLE_CAPTION = "IDA";
	private static final String SERVICE_SHEET_RETURN_CAPTION = "VUELTA";
	private static final int SERVICE_SHEET_STOP_NAME_ROW = 0;
	private static final int SERVICE_SHEET_STOP_LATITUDE_ROW = 1;
	private static final int SERVICE_SHEET_STOP_LONGITUDE_ROW = 2;
	private static final int SERVICE_SHEET_STOP_TIMETABLE_ROW = 3;
	private static final String SOURCE_BASE_URL = "http://icons.mqcdn.com/icons/stop.png?text=";
	
	private GoogleGeocoder googleGeocoder = GoogleGeocoder.getInstance();
	private NominatinGeocoder nominatinGeocoder = NominatinGeocoder.getIsntance();
		
	private IWorkbenchContext context;
	
	private static final String TEMPLATE_EMPLTY_SRTEET_NAME = "- Empty -";
	private static final String TEMPLATE_SERVICE_TYPE_CODE = "TEMPLATE";
	private static final String TEMPLATE_SERVICE_STATUS_CODE = "DESIGNED";
	private static final String TEMPLATE_ROUTE_STATUS_CODE = "PENDING";
	
	private ServiceService serviceService;
	private ServiceTypeService serviceTypeService;
	private ServiceStatusService serviceStatusService;
	private RouteStatusService routeStatusService;
	
	private ServiceType templateServiceType;
	private ServiceStatus templateServiceStatus;
	private RouteStatus templateRouteStatus;
	
	private List<Service> services = new ArrayList<Service>();
	
	public ServiceTemplateParser() {
		this.context = ServiceTemplateViewContainer.getContext();
		
		this.serviceService = ServiceTemplateViewContainer.getServiceService();
		this.serviceTypeService = ServiceTemplateViewContainer.getServiceTypeService();
		this.serviceStatusService = ServiceTemplateViewContainer.getServiceStatusService();
		this.routeStatusService = ServiceTemplateViewContainer.getServiceRouteStatusService();
	}
	
	public List<Service> parse(ServiceTemplate serviceTemplate) 
			throws FileNotFoundException, IOException, ServiceParsingException, NoServiceException {
		services.clear();
		
		// get default entities for template services
		try {
			templateServiceType = serviceTypeService.getByCode(TEMPLATE_SERVICE_TYPE_CODE);
			templateServiceStatus = serviceStatusService.getByCode(TEMPLATE_SERVICE_STATUS_CODE);
			templateRouteStatus = routeStatusService.getByCode(TEMPLATE_ROUTE_STATUS_CODE);
			
		} catch (Exception e) {
			throw new RuntimeException("¡No se puede parsear el fichero!", e);
		}

		
		InputStream file = new ByteArrayInputStream(serviceTemplate.getFile());
		
		// Load XLS file
		POIFSFileSystem fs = new POIFSFileSystem(file);
		HSSFWorkbook workbook = new HSSFWorkbook(fs);

		for (int x = 0; x < workbook.getNumberOfSheets(); x++) {
			HSSFSheet sheet = workbook.getSheetAt(x);

			if (!(SERVICE_SHEET_SINGLE_CAPTION.equals(sheet.getSheetName()) || SERVICE_SHEET_RETURN_CAPTION.equals(sheet.getSheetName())))
				throw new ServiceParsingException(ServiceParsingException.INVALID_FORMAT);

			// Parsing the service's stop's names
			List<String> stopNames = parseStops(sheet);

			// Parsing the service's stop's latitude
			List<Double> stopLatitudes = parseLatitudes(sheet);

			// Parsing the service's stop's latitude
			List<Double> stopLongitudes = parseLongitudes(sheet);
			
			// Exact cardinality
			if(stopNames.size() - stopLatitudes.size() != 0 || stopLatitudes.size() - stopLongitudes.size() != 0)
				throw new ServiceParsingException(ServiceParsingException.INVALID_FORMAT);
			
			// Geocoding the stops 
			List<GeocodedLocation> geocodedStops = geocodeStops(stopNames,stopLatitudes, stopLongitudes);
			
			for(int rowIndex = SERVICE_SHEET_STOP_TIMETABLE_ROW; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++){
				
				HSSFRow row = sheet.getRow(rowIndex);
				
				if(row == null)
					break;
				
				// Scheduling the stops
				List<Stop> scheduledStops = scheduleStops(row, geocodedStops);
				
				// Create a service from the scheduled stops
				Service service = getService(scheduledStops);
				
				// Assigning a unique code				
				//service.setCode(sheet.getSheetName() + " " + Math.random());

				// asign template resources
				service.setTemplate(true);
				service.setServiceTemplate(serviceTemplate);
				
				// Insert the new service
				services.add(service);
			}

		}
				
		return services;
	}
	
	private List<String> parseStops(HSSFSheet sheet) throws ServiceParsingException {
		
		List<String> stopNames = new ArrayList<String>();
		HSSFRow stopNamesRow = (HSSFRow) sheet
				.getRow(SERVICE_SHEET_STOP_NAME_ROW);

		if (stopNamesRow == null)
			throw new ServiceParsingException(ServiceParsingException.INVALID_FORMAT);

		Iterator<Cell> cellIterator = stopNamesRow.cellIterator();

		while (cellIterator.hasNext()) {

			Cell cell = cellIterator.next();
			try {
				stopNames.add(cell.getStringCellValue());
			} catch (Exception e) {
				throw new ServiceParsingException(
						ServiceParsingException.UNEXPECTED_VALUE + cell.getRowIndex());
			}
		}
		return stopNames;
	}
	
	private List<Double> parseLatitudes(HSSFSheet sheet) throws ServiceParsingException {

		List<Double> stopLatitudes = new ArrayList<Double>();

		HSSFRow stopLatitudesRow = (HSSFRow) sheet
				.getRow(SERVICE_SHEET_STOP_LATITUDE_ROW);

		if (stopLatitudesRow == null)
			throw new ServiceParsingException(ServiceParsingException.INVALID_FORMAT);

		Iterator<Cell> cellIterator = stopLatitudesRow.cellIterator();

		while (cellIterator.hasNext()) {

			Cell cell = cellIterator.next();
			try {
				stopLatitudes.add(cell.getNumericCellValue());
				//stopLatitudes.add(Double.parseDouble(cell.getStringCellValue()));
			} catch (Exception e) {
				throw new ServiceParsingException(
						ServiceParsingException.UNEXPECTED_VALUE + cell.getRowIndex());
			}
		}
		
		return stopLatitudes;
	}
	
	private List<Double> parseLongitudes(HSSFSheet sheet) throws ServiceParsingException {
		
		List<Double> stopLongitudes = new ArrayList<Double>();

		HSSFRow stopLongitudesRow = (HSSFRow) sheet
				.getRow(SERVICE_SHEET_STOP_LONGITUDE_ROW);

		if (stopLongitudesRow == null)
			throw new ServiceParsingException(ServiceParsingException.INVALID_FORMAT);

		Iterator<Cell> cellIterator = stopLongitudesRow.cellIterator();

		while (cellIterator.hasNext()) {

			Cell cell = cellIterator.next();
			try {
				stopLongitudes.add(cell.getNumericCellValue());
			} catch (Exception e) {
				throw new ServiceParsingException(
						ServiceParsingException.UNEXPECTED_VALUE + cell.getRowIndex());
			}
		}
		
		return stopLongitudes;
	}
	
	private List<GeocodedLocation> geocodeStops(List<String> stopNames,
			List<Double> stopLatitudes, List<Double> stopLongitudes)
			throws NoServiceException, ServiceParsingException {
		List<GeocodedLocation> geocodedStops = new ArrayList<GeocodedLocation>();
		
		for(int i = 0; i < stopNames.size(); i++){
			
			double latitude = stopLatitudes.get(i);
			double longitude = stopLongitudes.get(i);
			
			try {
				
//				List<GeocodedLocation> locations = new ArrayList<GeocodedLocation>(googleGeocoder.geocode(latitude, longitude));
				List<GeocodedLocation> locations = new ArrayList<GeocodedLocation>(nominatinGeocoder.geocode(latitude, longitude));
				
				if(locations.size() == 0)
					throw new NoServiceException();
				
				//The most insight fetch is the first one
				GeocodedLocation geocodedStop = locations.get(0);
				geocodedStop.setOriginalAddress(stopNames.get(i));
				
				geocodedStops.add(geocodedStop);

			} catch (GeocodingException e) {
				throw new ServiceParsingException(e); 
			}
		}
		return geocodedStops;
	}

	private List<Stop> scheduleStops(HSSFRow row, List<GeocodedLocation> geocodedStops) throws NoServiceException {
		
		List<Stop> scheduledStops = new ArrayList<Stop>();
	
		Iterator<Cell> cellIterator = row.cellIterator();
		
		while(cellIterator.hasNext()){
			
			Cell cell = cellIterator.next();
			Stop scheduledStop = getStop(geocodedStops.get(cell.getColumnIndex()), cell.getDateCellValue());
			scheduledStops.add(scheduledStop);
		}
	
		return scheduledStops;
	}
	
	private Stop getStop(GeocodedLocation location, Date scheduled) throws NoServiceException {

		if(scheduled == null)
			return null;
		
		Stop stop = new Stop();
		stop.setName(location.getOriginalAddress());
		
		//Geographical stop
		Address stopAddress = new Address();
		stopAddress.setCity(location.getLocality());
		stopAddress.setCounty(location.getAdministrativeAreaLevel2());
		stopAddress.setCountry(location.getCountry());
		stopAddress.setNumber(location.getStreetNumber());				
		stopAddress.setLatitude(location.getLat());
		stopAddress.setLongitude(location.getLon());
		stopAddress.setProvince(location.getAdministrativeAreaLevel1());
		if (location.getRoute() == null)
			stopAddress.setStreet(TEMPLATE_EMPLTY_SRTEET_NAME);
		else
			stopAddress.setStreet(location.getRoute());
		stopAddress.setZipCode(location.getPostalCode());
		
		stop.setStopAddress(stopAddress);
		
		//Scheduled stop's dates
		stop.setStopArrivalDate(scheduled);
		stop.setStopCheckoutDate(scheduled);
		
		return stop;

	}
	
	private Service getService(List<Stop> stops){		
		Service service;
		try {
			service = serviceService.createNewService(context.getOrganization());
		} catch (Exception e) {
			throw new RuntimeException("¡No se pudo crear el nuevo servicio!", e);
		}
		
		//Service service = new Service();
		service.setServiceType(templateServiceType);
		service.setServiceStatus(templateServiceStatus);
			
		for(int index=0; index < stops.size() -1 ; index++){
			
			try {
				Stop orig  = stops.get(index);
				Stop dest = stops.get(index+1);
				Route route = getRoute(orig, dest);
				route.setCode(Double.toString(Math.random()));
				
				service.addRoute(route);
				
			} catch (Exception e) {
				//  Do nothing
			}
		}
		
		return service;
	}
	
	private Route getRoute(Stop origin, Stop destination){
		
		Route route = new Route();
		route.setRouteStatus(templateRouteStatus);
		
		try {
			route.addStop(origin);
			route.addStop(destination);
		} catch (Exception e) {
			// Do nothing	
		}
		
		return route;
	} 
}
