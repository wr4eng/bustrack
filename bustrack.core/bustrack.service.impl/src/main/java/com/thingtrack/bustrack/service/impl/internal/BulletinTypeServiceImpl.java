package com.thingtrack.bustrack.service.impl.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.dao.api.BulletinTypeDao;
import com.thingtrack.bustrack.domain.BulletinType;
import com.thingtrack.bustrack.service.api.BulletinTypeService;

/**
 * @author Thingtrack S.:L.
 *
 */
public class BulletinTypeServiceImpl implements BulletinTypeService {
	@Autowired
	private BulletinTypeDao bulletinTypeDao;

	@Override
	public List<BulletinType> getAll() throws Exception {
		return this.bulletinTypeDao.getAll();
		
	}

	@Override
	public BulletinType get(Integer bulletinTypeId) throws Exception {
		return this.bulletinTypeDao.get(bulletinTypeId);
		
	}

	@Override
	public BulletinType getByCode(String code) throws Exception {
		return this.bulletinTypeDao.getByCode(code);
		
	}

	@Override
	public BulletinType save(BulletinType bulletinType) throws Exception {
		return this.bulletinTypeDao.save(bulletinType);
		
	}

	@Override
	public void delete(BulletinType bulletinType) throws Exception {
		this.bulletinTypeDao.delete(bulletinType);
		
	}

}
