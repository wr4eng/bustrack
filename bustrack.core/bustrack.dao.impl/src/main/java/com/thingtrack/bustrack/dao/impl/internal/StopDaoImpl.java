package com.thingtrack.bustrack.dao.impl.internal;

import org.springframework.stereotype.Repository;

import com.thingtrack.konekti.dao.template.JpaDao;
import com.thingtrack.bustrack.dao.api.StopDao;
import com.thingtrack.bustrack.domain.Stop;

/**
 * @author Thingtrack S.L.
 *
 */
@Repository
public class StopDaoImpl extends JpaDao<Stop, Integer> implements StopDao {
	@Override
	public Stop getByCode(String code) throws Exception {
		Stop stop = (Stop)getEntityManager()
		.createQuery("SELECT p FROM " + getEntityName() + " p WHERE p.code = :code")
		.setParameter("code", code).getSingleResult();

		return stop;
	}

}
