package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.Route;

/**
 * @author Thingtrack S.L.
 *
 */
public interface RouteService {
	public List<Route> getAll() throws Exception;
	public Route get( Integer routeId ) throws Exception;
	public Route getByCode( String code ) throws Exception;
	public Route save(Route route) throws Exception;
	public void delete(Route route) throws Exception;
	
	public Route setRunningStatus(Route route) throws Exception; 
	public Route setStopStatus(Route route) throws Exception;
	public Route setFinalizeStatus(Route route) throws Exception;
	public Route createNewRoute() throws Exception;
}
