package com.thingtrack.bustrack.service.impl.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.dao.api.WorksheetLineDao;
import com.thingtrack.bustrack.domain.WorksheetLine;
import com.thingtrack.bustrack.service.api.WorksheetLineService;

/**
 * @author Thingtrack S.L.
*
*/
public class WorksheetLineServiceImpl implements WorksheetLineService {
	@Autowired
	private WorksheetLineDao worksheetLineDao;

	@Override
	public List<WorksheetLine> getAll() throws Exception {
		return this.worksheetLineDao.getAll();
		
	}

	@Override
	public WorksheetLine get(Integer worksheetLineId) throws Exception {
		return this.worksheetLineDao.get(worksheetLineId);
		
	}

	@Override
	public WorksheetLine getByNumber(Integer number) throws Exception {
		return this.worksheetLineDao.getByNumber(number);
		
	}

	@Override
	public WorksheetLine save(WorksheetLine worksheetLine) throws Exception {
		return this.worksheetLineDao.save(worksheetLine);
		
	}

	@Override
	public void delete(WorksheetLine worksheetLine) throws Exception {
		this.worksheetLineDao.delete(worksheetLine);
		
	}

}
