package com.thingtrack.bustrack.service.impl.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.dao.api.JobOfferStatusDao;
import com.thingtrack.bustrack.domain.JobOfferStatus;
import com.thingtrack.bustrack.service.api.JobOfferStatusService;

/**
 * @author Thingtrack S.L.
 *
 */
public class JobOfferStatusServiceImpl implements JobOfferStatusService {
	@Autowired
	private JobOfferStatusDao jobOfferStatusDao;

	@Override
	public List<JobOfferStatus> getAll() throws Exception {
		return this.jobOfferStatusDao.getAll();
		
	}

	@Override
	public JobOfferStatus get(Integer jobOfferStatusId) throws Exception {
		return this.jobOfferStatusDao.get(jobOfferStatusId);
		
	}

	@Override
	public JobOfferStatus getByCode(String code) throws Exception {
		return this.jobOfferStatusDao.getByCode(code);
		
	}

	@Override
	public JobOfferStatus save(JobOfferStatus jobOfferStatus) throws Exception {
		return this.jobOfferStatusDao.save(jobOfferStatus);
		
	}

	@Override
	public void delete(JobOfferStatus jobOfferStatus) throws Exception {
		this.jobOfferStatusDao.delete(jobOfferStatus);
		
	}

}
