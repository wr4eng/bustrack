package com.thingtrack.bustrack.service.impl.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.dao.api.WorksheetStatusDao;
import com.thingtrack.bustrack.domain.WorksheetStatus;
import com.thingtrack.bustrack.service.api.WorksheetStatusService;

/**
 * @author Thingtrack S.L.
*
*/
public class WorksheetStatusServiceImpl implements WorksheetStatusService {
	@Autowired
	private WorksheetStatusDao worksheetStatusDao;

	@Override
	public List<WorksheetStatus> getAll() throws Exception {
		return this.worksheetStatusDao.getAll();
		
	}

	@Override
	public WorksheetStatus get(Integer worksheetStatusId) throws Exception {
		return this.worksheetStatusDao.get(worksheetStatusId);
		
	}

	@Override
	public WorksheetStatus getByCode(String code) throws Exception {
		return this.worksheetStatusDao.getByCode(code);
		
	}

	@Override
	public WorksheetStatus save(WorksheetStatus worksheetStatus) throws Exception {
		return this.worksheetStatusDao.save(worksheetStatus);
	}

	@Override
	public void delete(WorksheetStatus worksheetStatus) throws Exception {
		this.worksheetStatusDao.delete(worksheetStatus);
		
	}

}
