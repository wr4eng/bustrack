package com.thingtrack.bustrack.service.impl.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.dao.api.GasStationDao;
import com.thingtrack.bustrack.dao.api.GasStationStatusDao;
import com.thingtrack.bustrack.domain.GasStation;
import com.thingtrack.bustrack.domain.GasStationStatus;
import com.thingtrack.bustrack.service.api.GasStationService;
import com.thingtrack.konekti.domain.Sequence;
import com.thingtrack.konekti.domain.Workshop;
import com.thingtrack.konekti.service.api.SequenceService;

/**
 * @author Thingtrack S.L.
 *
 */
public class GasStationServiceImpl implements GasStationService {
	@Autowired
	private GasStationDao gasStationDao;

	@Autowired
	private GasStationStatusDao gasStationStatusDao;
	
	@Autowired
	private SequenceService sequenceService;
	
	
	@Override
	public List<GasStation> getAll() throws Exception {
		return this.gasStationDao.getAll();
		
	}

	@Override
	public GasStation get(Integer gasStation) throws Exception {
		return this.gasStationDao.get(gasStation);
		
	}

	@Override
	public GasStation getByCode(String code) throws Exception {
		return this.gasStationDao.getByCode(code);
		
	}

	@Override
	public GasStation save(GasStation gasStation) throws Exception {
		return this.gasStationDao.save(gasStation);
		
	}

	@Override
	public void delete(GasStation gasStation) throws Exception {
		this.gasStationDao.delete(gasStation);
		
	}


	@Override
	public GasStation createEntity(Workshop workshop) throws Exception {
		GasStation gasStation = new GasStation();
		
		GasStationStatus gasStationStatus = gasStationStatusDao.getByCode(GasStation.STATUS.OPENED.name());
		
		gasStation.setCode(sequenceService.setNextSequence(Sequence.CODE.GAS_STATION.name()));
		gasStation.setWorkshop(workshop);
		gasStation.setGasStationStatus(gasStationStatus);
		
		return gasStation;
	}
}
