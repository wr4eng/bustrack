package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.GasStationType;

/**
 * @author Thingtrack S.L.
 *
 */
public interface GasStationTypeService {
	public List<GasStationType> getAll() throws Exception;
	public GasStationType get( Integer gasStationTypeId ) throws Exception;
	public GasStationType getByCode( String code ) throws Exception;
	public GasStationType save(GasStationType gasStationType) throws Exception;
	public void delete(GasStationType gasStationType) throws Exception;
}
