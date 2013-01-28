package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.Tire;

/**
 * @author Thingtrack S.L.
 *
 */
public interface TireService {
	public List<Tire> getAll() throws Exception;
	public Tire get( Integer gasTypeId ) throws Exception;
	public Tire getBySerialNumber( String serialNumber ) throws Exception;
	public Tire save(Tire tire) throws Exception;
	public void delete(Tire tire) throws Exception;
}
