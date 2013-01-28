package com.thingtrack.bustrack.service.impl.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.dao.api.GasStationTypeDao;
import com.thingtrack.bustrack.domain.GasStationType;
import com.thingtrack.bustrack.service.api.GasStationTypeService;

/**
 * @author Thingtrack S.L.
 *
 */
public class GasStationTypeServiceImpl implements GasStationTypeService {
	@Autowired
	private GasStationTypeDao gasStationTypeDao;

	@Override
	public List<GasStationType> getAll() throws Exception {
		return this.gasStationTypeDao.getAll();
		
	}

	@Override
	public GasStationType get(Integer gasStationTypeId) throws Exception {
		return this.gasStationTypeDao.get(gasStationTypeId);
		
	}

	@Override
	public GasStationType getByCode(String code) throws Exception {
		return this.gasStationTypeDao.getByCode(code);
		
	}

	@Override
	public GasStationType save(GasStationType gasStationType) throws Exception {
		return this.gasStationTypeDao.save(gasStationType);
		
	}

	@Override
	public void delete(GasStationType gasStationType) throws Exception {
		this.gasStationTypeDao.delete(gasStationType);
		
	}
	
}
