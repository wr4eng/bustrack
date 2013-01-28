package com.thingtrack.bustrack.dao.api;

import com.thingtrack.bustrack.domain.BulletinBoard;
import com.thingtrack.konekti.dao.template.Dao;

/**
 * @author Thingtrack S.L.
 *
 */
public interface BulletinBoardDao extends Dao<BulletinBoard, Integer> {
	public BulletinBoard getByName( String name ) throws Exception;
	
}
