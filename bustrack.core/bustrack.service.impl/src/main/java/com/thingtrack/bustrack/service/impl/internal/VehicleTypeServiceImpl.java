package com.thingtrack.bustrack.service.impl.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.dao.api.VehicleTypeDao;
import com.thingtrack.bustrack.domain.VehicleType;
import com.thingtrack.bustrack.service.api.VehicleTypeService;

public class VehicleTypeServiceImpl implements VehicleTypeService {
	@Autowired
	private VehicleTypeDao vehicleTypeDao;

	@Override
	public List<VehicleType> getAll() throws Exception {
		return this.vehicleTypeDao.getAll();
		
	}

	@Override
	public VehicleType get(Integer vehicleTypeId) throws Exception {
		return this.vehicleTypeDao.get(vehicleTypeId);
		
	}

	@Override
	public VehicleType getByCode(String code) throws Exception {
		return this.vehicleTypeDao.getByCode(code);
		
	}

	@Override
	public VehicleType save(VehicleType vehicleType) throws Exception {
		return this.vehicleTypeDao.save(vehicleType);
		
	}

	@Override
	public void delete(VehicleType vehicleType) throws Exception {
		this.vehicleTypeDao.delete(vehicleType);
		
	}

}
