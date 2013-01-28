package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.GasType;

/**
 * @author Thingtrack S.L.
 *
 */
public interface GasTypeService {
	public List<GasType> getAll() throws Exception;
	public GasType get( Integer gasTypeId ) throws Exception;
	public GasType getByCode( String code ) throws Exception;
	public GasType save(GasType gasType) throws Exception;
	public void delete(GasType gasType) throws Exception;
}
