package com.thingtrack.bustrack.dao.api;

import com.thingtrack.bustrack.domain.Tire;
import com.thingtrack.konekti.dao.template.Dao;

/**
 * @author Thingtrack S.L.
 *
 */
public interface TireDao extends Dao<Tire, Integer> {
	public Tire getBySerialNumber( String serialNumber ) throws Exception;

}
