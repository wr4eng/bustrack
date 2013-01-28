package com.thingtrack.bustrack.dao.impl.internal;
import org.springframework.stereotype.Repository;

import com.thingtrack.konekti.dao.template.JpaDao;
import com.thingtrack.bustrack.dao.api.WorksheetLineDao;
import com.thingtrack.bustrack.domain.WorksheetLine;

/**
 * @author Thingtrack S.L.
 *
 */
@Repository
public class WorksheetLineDaoImpl extends JpaDao<WorksheetLine, Integer> implements WorksheetLineDao {	
	/* (non-Javadoc)
	 * @see com.thingtrack.bustrack.dao.api.WorksheetLineDao#getByNumber(java.lang.Integer)
	 */
	@Override
	public WorksheetLine getByNumber(Integer number) throws Exception {
		WorksheetLine worksheetLine = (WorksheetLine)getEntityManager()
				.createQuery("SELECT p FROM " + getEntityName() + " p where p.number = :number")
				.setParameter("number", number).getSingleResult();

		return worksheetLine;
		
	}

}
