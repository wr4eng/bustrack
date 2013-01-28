package com.thingtrack.bustrack.service.impl.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.dao.api.GasTypeDao;
import com.thingtrack.bustrack.domain.GasType;
import com.thingtrack.bustrack.service.api.GasTypeService;

/**
 * @author Thingtrack S.L.
 *
 */
public class GasTypeServiceImpl implements GasTypeService {
	@Autowired
	private GasTypeDao gasTypeDao;

	@Override
	public List<GasType> getAll() throws Exception {
		return this.gasTypeDao.getAll();
		
	}

	@Override
	public GasType get(Integer gasTypeId) throws Exception {
		return this.gasTypeDao.get(gasTypeId);
		
	}

	@Override
	public GasType getByCode(String code) throws Exception {
		return this.gasTypeDao.getByCode(code);
		
	}

	@Override
	public GasType save(GasType gasType) throws Exception {
		return this.gasTypeDao.save(gasType);
		
	}

	@Override
	public void delete(GasType gasType) throws Exception {
		this.gasTypeDao.delete(gasType);
		
	}

}
