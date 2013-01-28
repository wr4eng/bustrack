package com.thingtrack.bustrack.service.impl.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.dao.api.TurnDao;
import com.thingtrack.bustrack.domain.Turn;
import com.thingtrack.bustrack.service.api.TurnService;

/**
 * @author Thingtrack S.L.
 *
 */
public class TurnServiceImpl implements TurnService {
	@Autowired
	private TurnDao turnDao;

	@Override
	public List<Turn> getAll() throws Exception {
		return this.turnDao.getAll();
		
	}

	@Override
	public Turn get(Integer turnId) throws Exception {
		return this.turnDao.get(turnId);
		
	}

	@Override
	public Turn getByCode(String code) throws Exception {
		return this.turnDao.getByCode(code);
		
	}

	@Override
	public Turn save(Turn turn) throws Exception {
		return this.turnDao.save(turn);
		
	}

	@Override
	public void delete(Turn turn) throws Exception {
		this.turnDao.delete(turn);
		
	}

}
