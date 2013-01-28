package com.thingtrack.bustrack.service.impl.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.dao.api.TireDao;
import com.thingtrack.bustrack.domain.Tire;
import com.thingtrack.bustrack.service.api.TireService;

/**
 * @author Thingtrack S.L.
 *
 */
public class TireServiceImpl implements TireService {
	@Autowired
	private TireDao tireDao;

	@Override
	public List<Tire> getAll() throws Exception {
		return this.tireDao.getAll();
		
	}

	@Override
	public Tire get(Integer gasTypeId) throws Exception {
		return this.tireDao.get(gasTypeId);
		
	}

	@Override
	public Tire getBySerialNumber(String serialNumber) throws Exception {
		return this.tireDao.getBySerialNumber(serialNumber);
		
	}

	@Override
	public Tire save(Tire tire) throws Exception {
		return this.tireDao.save(tire);
		
	}

	@Override
	public void delete(Tire tire) throws Exception {
		this.tireDao.delete(tire);
		
	}
	
}
