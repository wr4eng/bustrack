package com.thingtrack.bustrack.view.mobile.workbench;

import com.thingtrack.bustrack.domain.Vehicle;
import com.thingtrack.bustrack.domain.Worksheet;
import com.thingtrack.konekti.domain.EmployeeAgent;
import com.thingtrack.konekti.domain.Organization;
import com.thingtrack.konekti.domain.sensor.SensorLocation;
import com.thingtrack.konekti.domain.sensor.SensorMessage;
import com.thingtrack.konekti.domain.sensor.SensorTelemetry;
import com.thingtrack.konekti.domain.User;

public class ContextApp {
	private static User user;
	private static EmployeeAgent employeeAgent;
	private static Organization organization;
	private static SensorLocation sensorLocation;
	private static SensorTelemetry sensorTelemetry;
	private static SensorMessage sensorMessage;
	private static Worksheet worksheet;
	private static Vehicle vehicle;
	
	// User bean
	public static void setUser(User user) {
		ContextApp.user = user;
		
	}	
	public static User getUser() {
		return ContextApp.user;
	}

	// Employee Agent bean
	public static void setEmployeeAgent(EmployeeAgent employeeAgent) {
		ContextApp.employeeAgent = employeeAgent;
		
	}	
	public static EmployeeAgent getEmployeeAgent() {
		return ContextApp.employeeAgent;
	}

	// Organization Bean
	public static void setOrganization(Organization organization) {
		ContextApp.organization = organization;
		
	}	
	public static Organization getOrganization() {
		return ContextApp.organization;
	}
	
	// Sensor Location Bean
	public static void setSensorLocation(SensorLocation sensorLocation) {
		ContextApp.sensorLocation = sensorLocation;
		
	}	
	public static SensorLocation getSensorLocation() {
		return ContextApp.sensorLocation;
	}
	
	// Sensor Telemetry Bean
	public static void setSensorTelemetry(SensorTelemetry sensorTelemetry) {
		ContextApp.sensorTelemetry = sensorTelemetry;
		
	}	
	public static SensorTelemetry getSensorTelemetry() {
		return ContextApp.sensorTelemetry;
	}
	
	// Sensor Message Bean
	public static void setSensorMessage(SensorMessage sensorMessage) {
		ContextApp.sensorMessage = sensorMessage;
		
	}	
	public static SensorMessage getSensorMessage() {
		return ContextApp.sensorMessage;
	}
	
	// Driver Worksheet Bean
	public static void setWorksheet(Worksheet worksheet) {
		ContextApp.worksheet = worksheet;
		
	}	
	public static Worksheet getWorksheet() {
		return ContextApp.worksheet;
		
	}
	
	// Active Vehicle
	public static void setVehicle(Vehicle vehicle) {
		ContextApp.vehicle = vehicle;
		
	}	
	public static Vehicle getVehicle() {
		return ContextApp.vehicle;
		
	}
}
