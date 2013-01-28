package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.JobOfferType;

/**
 * @author Thingtrack S.L
 *
 */
public interface JobOfferTypeService {
	public List<JobOfferType> getAll() throws Exception;
	public JobOfferType get( Integer jobOfferTypeId ) throws Exception;
	public JobOfferType getByCode( String code ) throws Exception;
	public JobOfferType save(JobOfferType jobOfferType) throws Exception;
	public void delete(JobOfferType jobOfferType) throws Exception;
}
