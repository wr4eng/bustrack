package com.thingtrack.bustrack.service.impl.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.dao.api.BulletinBoardDao;
import com.thingtrack.bustrack.domain.BulletinBoard;
import com.thingtrack.bustrack.service.api.BulletinBoardService;

/**
 * @author Thingtrack S.L.
 *
 */
public class BulletinBoardServiceImpl implements BulletinBoardService {
	@Autowired
	private BulletinBoardDao bulletinBoardDao;

	@Override
	public List<BulletinBoard> getAll() throws Exception {
		return this.bulletinBoardDao.getAll();
		
	}

	@Override
	public BulletinBoard get(Integer bulletinBoardId) throws Exception {
		return this.bulletinBoardDao.get(bulletinBoardId);
		
	}

	@Override
	public BulletinBoard getByName(String name) throws Exception {
		return this.bulletinBoardDao.getByName(name);
		
	}

	@Override
	public BulletinBoard save(BulletinBoard bulletinBoard) throws Exception {
		return this.bulletinBoardDao.save(bulletinBoard);
		
	}

	@Override
	public void delete(BulletinBoard bulletinBoard) throws Exception {
		this.bulletinBoardDao.delete(bulletinBoard);
		
	}

}
