package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.VehicleType;

/**
 * @author Thingtrack S.L.
 *
 */
public interface VehicleTypeService {
	public List<VehicleType> getAll() throws Exception;
	public VehicleType get( Integer vehicleTypeId ) throws Exception;
	public VehicleType getByCode( String code ) throws Exception;
	public VehicleType save(VehicleType vehicleType) throws Exception;
	public void delete(VehicleType vehicleType) throws Exception;
}
