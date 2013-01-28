package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.Stop;

/**
 * @author Thingtrack S.L.
 *
 */
public interface StopService {
	public List<Stop> getAll() throws Exception;
	public Stop get( Integer stopId ) throws Exception;
	public Stop getByCode( String code ) throws Exception;
	public Stop save(Stop stop) throws Exception;
	public void delete(Stop stop) throws Exception;
}
