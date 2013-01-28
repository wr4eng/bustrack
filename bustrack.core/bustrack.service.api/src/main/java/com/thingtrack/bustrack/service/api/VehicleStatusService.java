package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.VehicleStatus;

/**
 * @author Thingtrack S.L.
 *
 */
public interface VehicleStatusService {
	public List<VehicleStatus> getAll() throws Exception;
	public VehicleStatus get( Integer vehicleStatusId ) throws Exception;
	public VehicleStatus getByCode( String code ) throws Exception;
	public VehicleStatus save(VehicleStatus vehicleStatus) throws Exception;
	public void delete(VehicleStatus vehicleStatus) throws Exception;
}
