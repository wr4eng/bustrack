package com.thingtrack.bustrack.dao.api;

import com.thingtrack.bustrack.domain.BulletinType;
import com.thingtrack.konekti.dao.template.Dao;

/**
 * @author Thingtrack S.L.
 *
 */
public interface BulletinTypeDao extends Dao<BulletinType, Integer> {
	public BulletinType getByCode( String code ) throws Exception;
	
}
