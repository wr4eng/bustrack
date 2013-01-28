package com.thingtrack.bustrack.service.impl.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.dao.api.RouteStatusDao;
import com.thingtrack.bustrack.domain.RouteStatus;
import com.thingtrack.bustrack.service.api.RouteStatusService;

/**
 * @author Thingtrack S.L.
 *
 */
public class RouteStatusServiceImpl implements RouteStatusService {
	@Autowired
	private RouteStatusDao routeStatusDao;
	
	@Override
	public List<RouteStatus> getAll() throws Exception {
		return this.routeStatusDao.getAll();
		
	}

	@Override
	public RouteStatus get(Integer routeId) throws Exception {
		return this.routeStatusDao.get(routeId);
		
	}

	@Override
	public RouteStatus getByCode(String code) throws Exception {
		return this.routeStatusDao.getByCode(code);
		
	}

	@Override
	public RouteStatus save(RouteStatus routeStatus) throws Exception {
		return this.routeStatusDao.save(routeStatus);
		
	}

	@Override
	public void delete(RouteStatus routeStatus) throws Exception {
		this.routeStatusDao.delete(routeStatus);
		
	}

}
