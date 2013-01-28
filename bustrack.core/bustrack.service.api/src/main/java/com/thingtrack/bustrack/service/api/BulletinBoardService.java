package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.BulletinBoard;

/**
 * @author Thingtrack S.L.
 *
 */
public interface BulletinBoardService {
	public List<BulletinBoard> getAll() throws Exception;
	public BulletinBoard get( Integer bulletinBoardId ) throws Exception;
	public BulletinBoard getByName( String name ) throws Exception;
	public BulletinBoard save(BulletinBoard bulletinBoard) throws Exception;
	public void delete(BulletinBoard bulletinBoard) throws Exception;
}
