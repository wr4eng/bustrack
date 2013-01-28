package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.Route;
import com.thingtrack.bustrack.domain.Vehicle;
import com.thingtrack.konekti.domain.Organization;
import com.thingtrack.konekti.domain.sensor.SensorTelemetry;

/**
 * @author Thingtrack S.L.
 *
 */
public interface VehicleService {
	public List<Vehicle> getAll() throws Exception;
	public Vehicle get( Integer vehicleId ) throws Exception;
	public Vehicle getByNumber( String number ) throws Exception;
	public Vehicle save(Vehicle vehicle) throws Exception;
	public void delete(Vehicle vehicle) throws Exception;
	
	public List<Vehicle> getFreeVehicles(Organization organization) throws Exception;
	public SensorTelemetry getTelemeterByNumber(String number) throws Exception;
	
	public List<Vehicle> getAvailableVehicles(Organization organization, Route routeToCommit) throws Exception;
}
