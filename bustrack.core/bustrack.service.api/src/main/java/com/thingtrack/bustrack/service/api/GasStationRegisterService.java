package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.GasStationRegister;

/**
 * @author Thingtrack S.L.
 *
 */
public interface GasStationRegisterService {
	public List<GasStationRegister> getAll() throws Exception;
	public GasStationRegister get( Integer gasStationRegisterId ) throws Exception;
	public GasStationRegister save(GasStationRegister gasStationRegister) throws Exception;
	public void delete(GasStationRegister gasStationRegister) throws Exception;
}
