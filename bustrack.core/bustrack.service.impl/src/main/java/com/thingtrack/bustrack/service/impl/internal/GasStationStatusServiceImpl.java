package com.thingtrack.bustrack.service.impl.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.dao.api.GasStationStatusDao;
import com.thingtrack.bustrack.domain.GasStationStatus;
import com.thingtrack.bustrack.service.api.GasStationStatusService;

/**
 * @author Thingtrack S.L.
 *
 */
public class GasStationStatusServiceImpl implements GasStationStatusService {
	@Autowired
	private GasStationStatusDao gasStationStatusDao;

	@Override
	public List<GasStationStatus> getAll() throws Exception {
		return this.gasStationStatusDao.getAll();
		
	}

	@Override
	public GasStationStatus get(Integer gasStationStatusId) throws Exception {
		return this.gasStationStatusDao.get(gasStationStatusId);
		
	}

	@Override
	public GasStationStatus getByCode(String code) throws Exception {
		return this.gasStationStatusDao.getByCode(code);
		
	}

	@Override
	public GasStationStatus save(GasStationStatus gasStationStatus) throws Exception {
		return this.gasStationStatusDao.save(gasStationStatus);
		
	}

	@Override
	public void delete(GasStationStatus gasStationStatus) throws Exception {
		this.gasStationStatusDao.delete(gasStationStatus);
		
	}
	
}
