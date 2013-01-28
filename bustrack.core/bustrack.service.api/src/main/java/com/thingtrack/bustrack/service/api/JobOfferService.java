package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.JobOffer;

/**
 * @author Thingtrack S.L
 *
 */
public interface JobOfferService {
	public List<JobOffer> getAll() throws Exception;
	public JobOffer get( Integer jobOfferId ) throws Exception;
	public JobOffer getByName( String name ) throws Exception;
	public JobOffer save(JobOffer jobOffer) throws Exception;
	public void delete(JobOffer jobOffer) throws Exception;
}
