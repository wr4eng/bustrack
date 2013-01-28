package com.thingtrack.bustrack.dao.api;

import com.thingtrack.bustrack.domain.JobOfferType;
import com.thingtrack.konekti.dao.template.Dao;

public interface JobOfferTypeDao extends Dao<JobOfferType, Integer> {
	public JobOfferType getByCode( String code ) throws Exception;
	
}
