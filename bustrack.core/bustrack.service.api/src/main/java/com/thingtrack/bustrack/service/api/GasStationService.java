package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.GasStation;
import com.thingtrack.konekti.domain.Workshop;

/**
 * @author Thingtrack S.L.
 *
 */
public interface GasStationService {
	public List<GasStation> getAll() throws Exception;
	public GasStation get( Integer gasStation ) throws Exception;
	public GasStation getByCode( String code ) throws Exception;
	public GasStation save(GasStation gasStation) throws Exception;
	public void delete(GasStation gasStation) throws Exception;
	public GasStation createEntity(Workshop workshop) throws Exception;
}
