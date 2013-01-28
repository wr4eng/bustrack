package com.thingtrack.bustrack.service.impl.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.dao.api.TireStatusDao;
import com.thingtrack.bustrack.domain.TireStatus;
import com.thingtrack.bustrack.service.api.TireStatusService;

/**
 * @author Thingtrack S.L.
 *
 */
public class TireStatusServiceImpl implements TireStatusService {
	@Autowired
	private TireStatusDao tireStatusDao;

	@Override
	public List<TireStatus> getAll() throws Exception {
		return this.tireStatusDao.getAll();
		
	}

	@Override
	public TireStatus get(Integer tireStatusId) throws Exception {
		return this.tireStatusDao.get(tireStatusId);
		
	}

	@Override
	public TireStatus getByCode(String code) throws Exception {
		return this.tireStatusDao.getByCode(code);
		
	}

	@Override
	public TireStatus save(TireStatus tireStatus) throws Exception {
		return this.tireStatusDao.save(tireStatus);
		
	}

	@Override
	public void delete(TireStatus tireStatus) throws Exception {
		this.tireStatusDao.delete(tireStatus);
		
	}

}
