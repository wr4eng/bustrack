package com.thingtrack.bustrack.dao.api;

import com.thingtrack.bustrack.domain.JobOffer;
import com.thingtrack.konekti.dao.template.Dao;

public interface JobOfferDao extends Dao<JobOffer, Integer> {
	public JobOffer getByName( String name ) throws Exception;
	
}
