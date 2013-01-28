package com.thingtrack.bustrack.dao.impl.internal;

import org.springframework.stereotype.Repository;

import com.thingtrack.konekti.dao.template.JpaDao;
import com.thingtrack.bustrack.dao.api.TireDao;
import com.thingtrack.bustrack.domain.Tire;

/**
 * @author Thingtrack S.L.
 *
 */
@Repository
public class TireDaoImpl extends JpaDao<Tire, Integer> implements TireDao {
	/* (non-Javadoc)
	 * @see com.thingtrack.bustrack.dao.api.TireDao#getBySerialNumber(java.lang.String)
	 */
	@Override
	public Tire getBySerialNumber(String serialNumber) throws Exception {
		Tire tire = (Tire)getEntityManager()
		.createQuery("SELECT p FROM " + getEntityName() + " p WHERE p.serialNumber = :serialNumber")
		.setParameter("serialNumber", serialNumber).getSingleResult();

		return tire;
		
	}

}
