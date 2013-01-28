package com.thingtrack.bustrack.service.impl.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.dao.api.JobOfferTypeDao;
import com.thingtrack.bustrack.domain.JobOfferType;
import com.thingtrack.bustrack.service.api.JobOfferTypeService;

/**
 * @author Thingtrack S.L.
 *
 */
public class JobOfferTypeServiceImpl implements JobOfferTypeService {
	@Autowired
	private JobOfferTypeDao jobOfferTypeDao;

	@Override
	public List<JobOfferType> getAll() throws Exception {
		return this.jobOfferTypeDao.getAll();
		
	}

	@Override
	public JobOfferType get(Integer jobOfferTypeId) throws Exception {
		return this.jobOfferTypeDao.get(jobOfferTypeId);
		
	}

	@Override
	public JobOfferType getByCode(String code) throws Exception {
		return this.jobOfferTypeDao.getByCode(code);
		
	}

	@Override
	public JobOfferType save(JobOfferType jobOfferType) throws Exception {
		return this.jobOfferTypeDao.save(jobOfferType);
		
	}

	@Override
	public void delete(JobOfferType jobOfferType) throws Exception {
		this.jobOfferTypeDao.delete(jobOfferType);
		
	}

}
