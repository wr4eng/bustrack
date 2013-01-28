package com.thingtrack.bustrack.service.impl.internal;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.dao.api.RouteDao;
import com.thingtrack.bustrack.dao.api.RouteStatusDao;
import com.thingtrack.bustrack.domain.Route;
import com.thingtrack.bustrack.domain.RouteStatus;
import com.thingtrack.bustrack.service.api.RouteService;
import com.thingtrack.konekti.domain.Sequence;
import com.thingtrack.konekti.service.api.SequenceService;

/**
 * @author Thingtrack S.L.
 *
 */
public class RouteServiceImpl implements RouteService {
	@Autowired
	private RouteDao routeDao;

	@Autowired
	private RouteStatusDao routeStatusDao;
	
	@Autowired
	private SequenceService sequenceService;
	
	@Override
	public List<Route> getAll() throws Exception {
		return this.routeDao.getAll();
		
	}

	@Override
	public Route get(Integer routeId) throws Exception {
		return this.routeDao.get(routeId);
		
	}

	@Override
	public Route getByCode(String code) throws Exception {
		return this.routeDao.getByCode(code);
		
	}

	@Override
	public Route save(Route route) throws Exception {
		return this.routeDao.save(route);
		
	}

	@Override
	public void delete(Route route) throws Exception {
		this.routeDao.delete(route);
		
	}

	@Override
	public Route setRunningStatus(Route route) throws Exception {
		RouteStatus routeStatus = routeStatusDao.getByCode("RUNNING");
		route.setStartDate(new Date());
		route.setRouteStatus(routeStatus);
		
		return this.routeDao.save(route);
		
	}
	
	@Override
	public Route setStopStatus(Route route) throws Exception {
		RouteStatus routeStatus = routeStatusDao.getByCode("PENDING");
		route.setStopDate(new Date());
		route.setRouteStatus(routeStatus);
		
		return this.routeDao.save(route);
		
	}
	
	@Override
	public Route setFinalizeStatus(Route route) throws Exception {
		RouteStatus routeStatus = routeStatusDao.getByCode("FINISH");
		route.setStopDate(new Date());
		route.setRouteStatus(routeStatus);
		
		return this.routeDao.save(route);
		
	}
	
	@Override
	public Route createNewRoute() throws Exception {
		Route route = new Route();
		
		route.setCode(sequenceService.setNextSequence(Sequence.CODE.ROUTE.name()));
		route.setRouteStatus(routeStatusDao.getByCode(Route.STATUS.PENDING.name()));
		
		return route;
	}
}
