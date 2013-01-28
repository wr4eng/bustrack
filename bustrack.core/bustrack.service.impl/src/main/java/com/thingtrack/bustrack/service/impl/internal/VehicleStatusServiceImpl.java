package com.thingtrack.bustrack.service.impl.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.dao.api.VehicleStatusDao;
import com.thingtrack.bustrack.domain.VehicleStatus;
import com.thingtrack.bustrack.service.api.VehicleStatusService;

/**
 * @author Thingtrack S.L.
 *
 */
public class VehicleStatusServiceImpl implements VehicleStatusService {
	@Autowired
	private VehicleStatusDao vehicleStatusDao;

	@Override
	public List<VehicleStatus> getAll() throws Exception {
		return this.vehicleStatusDao.getAll();
		
	}

	@Override
	public VehicleStatus get(Integer vehicleStatusId) throws Exception {
		return this.vehicleStatusDao.get(vehicleStatusId);
		
	}

	@Override
	public VehicleStatus getByCode(String code) throws Exception {
		return this.vehicleStatusDao.getByCode(code);
		
	}

	@Override
	public VehicleStatus save(VehicleStatus vehicleStatus) throws Exception {
		return this.vehicleStatusDao.save(vehicleStatus);
		
	}

	@Override
	public void delete(VehicleStatus vehicleStatus) throws Exception {
		this.vehicleStatusDao.delete(vehicleStatus);
		
	}
	
}
