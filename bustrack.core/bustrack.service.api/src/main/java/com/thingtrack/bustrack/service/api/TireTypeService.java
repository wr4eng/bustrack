package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.TireType;

/**
 * @author Thingtrack S.L.
 *
 */
public interface TireTypeService {
	public List<TireType> getAll() throws Exception;
	public TireType get( Integer tireTypeId ) throws Exception;
	public TireType getByCode( String code ) throws Exception;
	public TireType save(TireType tireType) throws Exception;
	public void delete(TireType tireType) throws Exception;
}
