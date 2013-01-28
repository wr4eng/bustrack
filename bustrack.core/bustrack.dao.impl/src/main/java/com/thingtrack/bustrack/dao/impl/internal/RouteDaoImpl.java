package com.thingtrack.bustrack.dao.impl.internal;

import org.springframework.stereotype.Repository;

import com.thingtrack.konekti.dao.template.JpaDao;
import com.thingtrack.bustrack.dao.api.RouteDao;
import com.thingtrack.bustrack.domain.Route;

/**
 * @author Thingtrack S.L.
 *
 */
@Repository
public class RouteDaoImpl extends JpaDao<Route, Integer> implements RouteDao {
	/* (non-Javadoc)
	 * @see com.thingtrack.bustrack.dao.api.RouteDao#getByCode(java.lang.String)
	 */
	@Override
	public Route getByCode(String code) throws Exception {
		Route route = (Route)getEntityManager()
		.createQuery("SELECT p FROM " + getEntityName() + " p WHERE p.code = :code")
		.setParameter("code", code).getSingleResult();

		return route;

	}

}
