package com.thingtrack.bustrack.dao.impl.internal;

import org.springframework.stereotype.Repository;

import com.thingtrack.bustrack.domain.JobOfferStatus;
import com.thingtrack.bustrack.dao.api.JobOfferStatusDao;
import com.thingtrack.konekti.dao.template.JpaDao;

@Repository
public class JobOfferStatusDaoImpl extends JpaDao<JobOfferStatus, Integer> implements JobOfferStatusDao {	
	/* (non-Javadoc)
	 * @see com.thingtrack.bustrack.dao.api.JobOfferStatusDao#getByCode(java.lang.String)
	 */
	@Override
	public JobOfferStatus getByCode(String code) throws Exception {
		JobOfferStatus jobOfferStatus = (JobOfferStatus)getEntityManager()
		.createQuery("SELECT p FROM " + getEntityName() + " p WHERE p.code = :code")
		.setParameter("code", code).getSingleResult();

		return jobOfferStatus;
		
	}

}
