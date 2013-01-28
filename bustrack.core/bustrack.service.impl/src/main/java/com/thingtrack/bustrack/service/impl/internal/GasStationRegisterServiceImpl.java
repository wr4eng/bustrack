package com.thingtrack.bustrack.service.impl.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.dao.api.GasStationRegisterDao;
import com.thingtrack.bustrack.domain.GasStationRegister;
import com.thingtrack.bustrack.service.api.GasStationRegisterService;

/**
 * @author Thingtrack S.L.
 *
 */
public class GasStationRegisterServiceImpl implements GasStationRegisterService {
	@Autowired
	private GasStationRegisterDao gasStationRegisterDao;

	@Override
	public List<GasStationRegister> getAll() throws Exception {
		return this.gasStationRegisterDao.getAll();
		
	}

	@Override
	public GasStationRegister get(Integer gasStationRegisterId) throws Exception {
		return this.gasStationRegisterDao.get(gasStationRegisterId);
	}

	@Override
	public GasStationRegister save(GasStationRegister gasStationRegister) throws Exception {
		return this.gasStationRegisterDao.save(gasStationRegister);
	}

	@Override
	public void delete(GasStationRegister gasStationRegister) throws Exception {
		this.gasStationRegisterDao.delete(gasStationRegister);
		
	}

}
