package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.Turn;

/**
 * @author Thingtrack S.L.
 *
 */
public interface TurnService {
	public List<Turn> getAll() throws Exception;
	public Turn get( Integer turnId ) throws Exception;
	public Turn getByCode( String code ) throws Exception;
	public Turn save(Turn turn) throws Exception;
	public void delete(Turn turn) throws Exception;
}
