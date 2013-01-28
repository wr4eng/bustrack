package com.thingtrack.bustrack.dao.api;

import com.thingtrack.bustrack.domain.WorksheetStatus;
import com.thingtrack.konekti.dao.template.Dao;

/**
 * @author Thingtrack S.L. 
 *
 */
public interface WorksheetStatusDao extends Dao<WorksheetStatus, Integer> {
	public WorksheetStatus getByCode( String code ) throws Exception;

}
