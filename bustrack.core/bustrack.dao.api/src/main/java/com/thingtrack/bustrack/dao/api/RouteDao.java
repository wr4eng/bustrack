package com.thingtrack.bustrack.dao.api;

import com.thingtrack.bustrack.domain.Route;
import com.thingtrack.konekti.dao.template.Dao;

/**
 * @author Thingtrack S.L.
 *
 */
public interface RouteDao extends Dao<Route, Integer> {
	public Route getByCode( String code ) throws Exception;
	
}
