package com.thingtrack.bustrack.dao.api;

import com.thingtrack.bustrack.domain.JobOfferStatus;
import com.thingtrack.konekti.dao.template.Dao;

public interface JobOfferStatusDao extends Dao<JobOfferStatus, Integer> {
	public JobOfferStatus getByCode( String code ) throws Exception;
	
}
