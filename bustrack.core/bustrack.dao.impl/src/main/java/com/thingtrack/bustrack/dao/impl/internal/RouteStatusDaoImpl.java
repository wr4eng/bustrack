package com.thingtrack.bustrack.dao.impl.internal;

import org.springframework.stereotype.Repository;

import com.thingtrack.bustrack.dao.api.RouteStatusDao;
import com.thingtrack.bustrack.domain.RouteStatus;
import com.thingtrack.konekti.dao.template.JpaDao;

@Repository
public class RouteStatusDaoImpl extends JpaDao<RouteStatus, Integer> implements RouteStatusDao {
	@Override
	public RouteStatus getByCode(String code) throws Exception {
		RouteStatus routeStatus = (RouteStatus)getEntityManager()
		.createQuery("SELECT p FROM " + getEntityName() + " p WHERE p.code = :code")
		.setParameter("code", code).getSingleResult();

		return routeStatus;
	}

}
