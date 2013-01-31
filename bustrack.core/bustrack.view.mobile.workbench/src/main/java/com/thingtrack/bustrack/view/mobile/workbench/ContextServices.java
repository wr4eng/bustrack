package com.thingtrack.bustrack.view.mobile.workbench;

import com.thingtrack.bustrack.service.api.RouteService;
import com.thingtrack.bustrack.service.api.StopService;
import com.thingtrack.bustrack.service.api.VehicleService;
import com.thingtrack.bustrack.service.api.WorksheetService;
import com.thingtrack.konekti.service.api.EmployeeAgentService;
import com.thingtrack.konekti.service.sensor.api.SensorLocationService;
import com.thingtrack.konekti.service.sensor.api.SensorMessageService;
import com.thingtrack.konekti.service.sensor.api.SensorTelemetryService;
import com.thingtrack.konekti.service.api.UserService;
import com.thingtrack.konekti.service.security.SecurityService;

public class ContextServices {
	private static SensorLocationService sensorLocationService;
	private static SensorTelemetryService sensorTelemetryService;
	private static SensorMessageService sensorMessageService;
	private static SecurityService securityService;
	private static UserService userService;
	private static EmployeeAgentService employeeAgentService;
	private static WorksheetService worksheetService;
	private static VehicleService vehicleService;
	private static RouteService routeService;
	private static StopService stopService;

	public static void setSensorLocationService(SensorLocationService sensorLocationService) {
		ContextServices.sensorLocationService = sensorLocationService;
		
	}	
	
	public static SensorLocationService getSensorLocationService() {
		return ContextServices.sensorLocationService;
		
	}
	
	public static void setSensorTelemetryService(SensorTelemetryService sensorTelemetryService) {
		ContextServices.sensorTelemetryService = sensorTelemetryService;
		
	}	
	
	public static SensorTelemetryService getSensorTelemetryService() {
		return ContextServices.sensorTelemetryService;
		
	}
	
	public static void setSensorMessageService(SensorMessageService sensorMessageService) {
		ContextServices.sensorMessageService = sensorMessageService;
		
	}	
	
	public static SensorMessageService getSensorMessageService() {
		return ContextServices.sensorMessageService;
		
	}
	
	public static void setSecurityService(SecurityService securityService) {
		ContextServices.securityService = securityService;
		
	}	
	
	public static SecurityService getSecurityService() {
		return ContextServices.securityService;
		
	}
	
	public static void setUserService(UserService userService) {
		ContextServices.userService = userService;
		
	}	
	
	public static UserService getUserService() {
		return ContextServices.userService;
		
	}
	
	public static void setEmployeeAgentService(EmployeeAgentService employeeAgentService) {
		ContextServices.employeeAgentService = employeeAgentService;
		
	}	
	
	public static EmployeeAgentService getEmployeeAgentService() {
		return ContextServices.employeeAgentService;
		
	}
	
	public static void setWorksheetService(WorksheetService worksheetService) {
		ContextServices.worksheetService = worksheetService;
		
	}	
	
	public static WorksheetService getWorksheetService() {
		return ContextServices.worksheetService;
		
	}
	
	public static void setVehicleService(VehicleService vehicleService) {
		ContextServices.vehicleService = vehicleService;
		
	}	
	
	public static VehicleService getVehicleService() {
		return ContextServices.vehicleService;
		
	}
	
	public static void setRouteService(RouteService routeService) {
		ContextServices.routeService = routeService;
		
	}	
	
	public static RouteService getRouteService() {
		return ContextServices.routeService;
		
	}
	
	public static void setStopService(StopService stopService) {
		ContextServices.stopService = stopService;
		
	}	
	
	public static StopService getStopService() {
		return ContextServices.stopService;
		
	}
}
