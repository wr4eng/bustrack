package com.thingtrack.bustrack.service.impl.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.dao.api.StopDao;
import com.thingtrack.bustrack.domain.Stop;
import com.thingtrack.bustrack.service.api.StopService;

/**
 * @author Thingtrack S.L.
 *
 */
public class StopServiceImpl implements StopService {
	@Autowired
	private StopDao stopDao;

	@Override
	public List<Stop> getAll() throws Exception {
		return this.stopDao.getAll();
		
	}

	@Override
	public Stop get(Integer stopId) throws Exception {
		return this.stopDao.get(stopId);
		
	}

	@Override
	public Stop getByCode(String code) throws Exception {
		return this.stopDao.getByCode(code);
		
	}
	
	@Override
	public Stop save(Stop stop) throws Exception {
		return this.stopDao.save(stop);
		
	}

	@Override
	public void delete(Stop stop) throws Exception {
		this.stopDao.delete(stop);
		
	}

}
