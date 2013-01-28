package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.BulletinType;

/**
 * @author Thingtrack S.L.
 *
 */
public interface BulletinTypeService {
	public List<BulletinType> getAll() throws Exception;
	public BulletinType get( Integer bulletinTypeId ) throws Exception;
	public BulletinType getByCode( String code ) throws Exception;
	public BulletinType save(BulletinType bulletinType) throws Exception;
	public void delete(BulletinType bulletinType) throws Exception;
}
