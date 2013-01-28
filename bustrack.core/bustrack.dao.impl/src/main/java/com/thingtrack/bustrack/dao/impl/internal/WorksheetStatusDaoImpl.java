package com.thingtrack.bustrack.dao.impl.internal;

import org.springframework.stereotype.Repository;

import com.thingtrack.bustrack.dao.api.WorksheetStatusDao;
import com.thingtrack.bustrack.domain.WorksheetStatus;
import com.thingtrack.konekti.dao.template.JpaDao;

/**
 * @author Thingtrack S.L.
 *
 */
@Repository
public class WorksheetStatusDaoImpl extends JpaDao<WorksheetStatus, Integer> implements WorksheetStatusDao {
	/* (non-Javadoc)
	 * @see com.thingtrack.bustrack.dao.api.WorksheetStatusDao#getByCode(java.lang.String)
	 */
	@Override
	public WorksheetStatus getByCode(String code) throws Exception {
		WorksheetStatus worksheetStatus = (WorksheetStatus)getEntityManager()
				.createQuery("SELECT p FROM " + getEntityName() + " p WHERE p.code = :code")
				.setParameter("code", code).getSingleResult();

		return worksheetStatus;
		
	}
	
}
