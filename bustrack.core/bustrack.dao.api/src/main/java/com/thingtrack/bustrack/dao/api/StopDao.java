package com.thingtrack.bustrack.dao.api;

import com.thingtrack.bustrack.domain.Stop;
import com.thingtrack.konekti.dao.template.Dao;

/**
 * @author Thingtrack S.L.
 *
 */
public interface StopDao extends Dao<Stop, Integer> {
	public Stop getByCode( String code ) throws Exception;
}
