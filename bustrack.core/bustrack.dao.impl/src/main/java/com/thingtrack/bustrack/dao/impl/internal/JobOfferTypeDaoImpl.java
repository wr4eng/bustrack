package com.thingtrack.bustrack.dao.impl.internal;

import org.springframework.stereotype.Repository;

import com.thingtrack.konekti.dao.template.JpaDao;
import com.thingtrack.bustrack.dao.api.JobOfferTypeDao;
import com.thingtrack.bustrack.domain.JobOfferType;

@Repository
public class JobOfferTypeDaoImpl extends JpaDao<JobOfferType, Integer> implements JobOfferTypeDao {
	/* (non-Javadoc)
	 * @see com.thingtrack.bustrack.dao.api.JobOfferTypeDao#getByCode(java.lang.String)
	 */
	@Override
	public JobOfferType getByCode(String code) throws Exception {
		JobOfferType jobOfferType = (JobOfferType)getEntityManager()
		.createQuery("SELECT p FROM " + getEntityName() + " p WHERE p.code = :code")
		.setParameter("code", code).getSingleResult();

		return jobOfferType;
		
	}

}
