package com.thingtrack.bustrack.service.impl.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.dao.api.JobOfferDao;
import com.thingtrack.bustrack.domain.JobOffer;
import com.thingtrack.bustrack.service.api.JobOfferService;

/**
 * @author Thingtrack S.L.
 *
 */
public class JobOfferServiceImpl implements JobOfferService {
	@Autowired
	private JobOfferDao jobOfferDao;

	@Override
	public List<JobOffer> getAll() throws Exception {
		return this.jobOfferDao.getAll();
	}

	@Override
	public JobOffer get(Integer jobOfferId) throws Exception {
		return this.jobOfferDao.get(jobOfferId);
		
	}
	
	@Override
	public JobOffer getByName(String name) throws Exception {
		return this.jobOfferDao.getByName(name);
		
	}
	
	@Override
	public JobOffer save(JobOffer jobOffer) throws Exception {
		return this.jobOfferDao.save(jobOffer);
		
	}

	@Override
	public void delete(JobOffer jobOffer) throws Exception {
		this.jobOfferDao.delete(jobOffer);
		
	}

}
