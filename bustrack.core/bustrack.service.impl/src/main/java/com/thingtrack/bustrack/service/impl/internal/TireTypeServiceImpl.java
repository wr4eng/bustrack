package com.thingtrack.bustrack.service.impl.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.dao.api.TireTypeDao;
import com.thingtrack.bustrack.domain.TireType;
import com.thingtrack.bustrack.service.api.TireTypeService;


/**
 * @author Thingtrack S.L.
 *
 */
public class TireTypeServiceImpl implements TireTypeService {
	@Autowired
	private TireTypeDao tireTypeDao;

	@Override
	public List<TireType> getAll() throws Exception {
		return this.tireTypeDao.getAll();
		
	}

	@Override
	public TireType get(Integer tireTypeId) throws Exception {
		return this.tireTypeDao.get(tireTypeId);
		
	}

	@Override
	public TireType getByCode(String code) throws Exception {
		return this.tireTypeDao.getByCode(code);
		
	}

	@Override
	public TireType save(TireType tireType) throws Exception {
		return this.tireTypeDao.save(tireType);
		
	}

	@Override
	public void delete(TireType tireType) throws Exception {
		this.tireTypeDao.delete(tireType);
		
	}

}
