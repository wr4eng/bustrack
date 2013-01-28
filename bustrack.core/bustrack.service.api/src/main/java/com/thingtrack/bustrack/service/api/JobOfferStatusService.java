package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.JobOfferStatus;

/**
 * @author Thingtrack S.L
 *
 */
public interface JobOfferStatusService {
	public List<JobOfferStatus> getAll() throws Exception;
	public JobOfferStatus get( Integer jobOfferStatusId ) throws Exception;
	public JobOfferStatus getByCode( String code ) throws Exception;
	public JobOfferStatus save(JobOfferStatus jobOfferStatus) throws Exception;
	public void delete(JobOfferStatus jobOfferStatus) throws Exception;
}
