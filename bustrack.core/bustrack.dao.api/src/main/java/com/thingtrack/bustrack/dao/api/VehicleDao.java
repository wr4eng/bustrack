package com.thingtrack.bustrack.dao.api;

import java.util.List;

import com.thingtrack.bustrack.domain.Route;
import com.thingtrack.bustrack.domain.Vehicle;
import com.thingtrack.konekti.dao.template.Dao;
import com.thingtrack.konekti.domain.Organization;
import com.thingtrack.konekti.domain.sensor.SensorTelemetry;

/**
 * @author Thingtrack S.L.
 *
 */
public interface VehicleDao extends Dao<Vehicle, Integer> {
	public Vehicle getByNumber( String number ) throws Exception;
	public List<Vehicle> getFreeVehicles(Organization organization) throws Exception;
	public SensorTelemetry getTelemeterByNumber(String number) throws Exception;
	public List<Vehicle> getAvailableVehicles(Organization organization, Route routeToCommit) throws Exception;
}
