package com.thingtrack.bustrack.dao.api;

import com.thingtrack.bustrack.domain.Turn;
import com.thingtrack.konekti.dao.template.Dao;

/**
 * @author Thingtrack S.L.
 *
 */
public interface TurnDao extends Dao<Turn, Integer> {
	public Turn getByCode( String code ) throws Exception;
}
