package com.thingtrack.bustrack.dao.impl.internal;

import org.springframework.stereotype.Repository;

import com.thingtrack.konekti.dao.template.JpaDao;
import com.thingtrack.bustrack.dao.api.JobOfferDao;
import com.thingtrack.bustrack.domain.JobOffer;

/**
 * @author Thingtrack S.L.
 *
 */
@Repository
public class JobOfferDaoImpl extends JpaDao<JobOffer, Integer> implements JobOfferDao {
	/* (non-Javadoc)
	 * @see com.thingtrack.bustrack.dao.api.JobOfferDao#getByName(java.lang.String)
	 */
	@Override
	public JobOffer getByName(String name) throws Exception {
		JobOffer jobOffer = (JobOffer)getEntityManager()
		.createQuery("SELECT p FROM " + getEntityName() + " p WHERE p.name = :name")
		.setParameter("name", name).getSingleResult();

		return jobOffer;
		
	}
	
}
