package com.thingtrack.bustrack.dao.api;

import com.thingtrack.bustrack.domain.WorksheetLine;
import com.thingtrack.konekti.dao.template.Dao;

/**
 * @author Thingtrack S.L.
 *
 */
public interface WorksheetLineDao extends Dao<WorksheetLine, Integer> {
	public WorksheetLine getByNumber( Integer number ) throws Exception;
	
}
