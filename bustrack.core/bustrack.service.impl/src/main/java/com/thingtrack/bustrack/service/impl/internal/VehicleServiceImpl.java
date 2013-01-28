package com.thingtrack.bustrack.service.impl.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.dao.api.VehicleDao;
import com.thingtrack.bustrack.domain.Route;
import com.thingtrack.bustrack.domain.Vehicle;
import com.thingtrack.bustrack.service.api.VehicleService;
import com.thingtrack.konekti.domain.Organization;
import com.thingtrack.konekti.domain.sensor.SensorTelemetry;


/**
 * @author Thingtrack S.L.
 *
 */
public class VehicleServiceImpl implements VehicleService {
	@Autowired
	private VehicleDao vehicleDao;

	@Override
	public List<Vehicle> getAll() throws Exception {
		return this.vehicleDao.getAll();
		
	}

	@Override
	public Vehicle get(Integer vehicleId) throws Exception {
		return this.vehicleDao.get(vehicleId);
		
	}

	@Override
	public Vehicle getByNumber(String number) throws Exception {
		return this.vehicleDao.getByNumber(number);
		
	}

	@Override
	public Vehicle save(Vehicle vehicle) throws Exception {
		return this.vehicleDao.save(vehicle);
		
	}

	@Override
	public void delete(Vehicle vehicle) throws Exception {
		this.vehicleDao.delete(vehicle);
		
	}

	@Override
	public List<Vehicle> getFreeVehicles(Organization organization) throws Exception {
		return this.vehicleDao.getFreeVehicles(organization);
		
	}

	@Override
	public SensorTelemetry getTelemeterByNumber(String number) throws Exception {
		return this.vehicleDao.getTelemeterByNumber(number);
		
	}

	@Override
	public List<Vehicle> getAvailableVehicles(Organization organization,
			Route routeToCommit) throws Exception {
		return vehicleDao.getAvailableVehicles(organization, routeToCommit);
	}

	

}
