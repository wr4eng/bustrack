package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.WorksheetStatus;

/**
 * @author Thingtrack S.L.
 *
 */
public interface WorksheetStatusService {
	public List<WorksheetStatus> getAll() throws Exception;
	public WorksheetStatus get( Integer worksheetStatusId ) throws Exception;
	public WorksheetStatus getByCode( String code ) throws Exception;
	public WorksheetStatus save(WorksheetStatus worksheetStatus) throws Exception;
	public void delete(WorksheetStatus worksheetStatus) throws Exception;
}
