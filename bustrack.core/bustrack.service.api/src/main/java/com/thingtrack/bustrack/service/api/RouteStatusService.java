package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.RouteStatus;

/**
 * @author Thingtrack S.L.
 *
 */
public interface RouteStatusService {
	public List<RouteStatus> getAll() throws Exception;
	public RouteStatus get( Integer routeId ) throws Exception;
	public RouteStatus getByCode( String code ) throws Exception;
	public RouteStatus save(RouteStatus routeStatus) throws Exception;
	public void delete(RouteStatus routeStatus) throws Exception;
}
