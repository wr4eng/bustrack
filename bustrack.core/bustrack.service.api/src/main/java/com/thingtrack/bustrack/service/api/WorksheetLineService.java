package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.WorksheetLine;

/**
 * @author Thingtrack S.L.
 *
 */
public interface WorksheetLineService {
	public List<WorksheetLine> getAll() throws Exception;
	public WorksheetLine get( Integer worksheetLineId ) throws Exception;
	public WorksheetLine getByNumber( Integer number ) throws Exception;
	public WorksheetLine save(WorksheetLine worksheetLine) throws Exception;
	public void delete(WorksheetLine worksheetLine) throws Exception;
}
