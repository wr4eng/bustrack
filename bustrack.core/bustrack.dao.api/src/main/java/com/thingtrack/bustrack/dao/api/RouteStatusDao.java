package com.thingtrack.bustrack.dao.api;

import com.thingtrack.bustrack.domain.RouteStatus;
import com.thingtrack.konekti.dao.template.Dao;

public interface RouteStatusDao extends Dao<RouteStatus, Integer> {
	public RouteStatus getByCode( String code ) throws Exception;
}
