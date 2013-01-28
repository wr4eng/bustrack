package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.TireStatus;

/**
 * @author Thingtrack S.L.
 *
 */
public interface TireStatusService {
	public List<TireStatus> getAll() throws Exception;
	public TireStatus get( Integer tireStatusId ) throws Exception;
	public TireStatus getByCode( String code ) throws Exception;
	public TireStatus save(TireStatus tireStatus) throws Exception;
	public void delete(TireStatus tireStatus) throws Exception;
}
