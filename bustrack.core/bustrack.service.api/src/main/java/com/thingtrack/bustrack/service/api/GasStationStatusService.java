package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.GasStationStatus;

/**
 * @author Thingtrack S.L.
 *
 */
public interface GasStationStatusService {
	public List<GasStationStatus> getAll() throws Exception;
	public GasStationStatus get( Integer gasStationStatusId ) throws Exception;
	public GasStationStatus getByCode( String code ) throws Exception;
	public GasStationStatus save(GasStationStatus gasStationStatus) throws Exception;
	public void delete(GasStationStatus gasStationStatus) throws Exception;
}
