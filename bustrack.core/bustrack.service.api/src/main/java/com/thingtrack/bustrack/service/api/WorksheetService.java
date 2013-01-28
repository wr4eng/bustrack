package com.thingtrack.bustrack.service.api;

import java.util.Date;
import java.util.List;

import com.thingtrack.bustrack.domain.Vehicle;
import com.thingtrack.bustrack.domain.Worksheet;
import com.thingtrack.konekti.domain.EmployeeAgent;
import com.thingtrack.konekti.domain.Organization;
import com.thingtrack.konekti.domain.Service;

/**
 * @author Thingtrack S.L.
 *
 */
public interface WorksheetService {
	public List<Worksheet> getAll() throws Exception;
	public Worksheet get( Integer worksheetId ) throws Exception;
	public Worksheet save(Worksheet worksheet) throws Exception;
	public void delete(Worksheet worksheet) throws Exception;
	
	public List<Service> getOpenServicesFromUserToday(EmployeeAgent employeeAgent, Organization organization, Vehicle vehicle);
	public Worksheet getOpenWorksheetFromUserToday(EmployeeAgent employeeAgent, Organization organization, Vehicle vehicle);
	public Worksheet setOpenWorksheet(Worksheet worksheet) throws Exception;
	public Worksheet setCloseWorksheet(Worksheet worksheet) throws Exception;
	public Worksheet addDefaultService(Organization organization, Worksheet worksheet, Vehicle vehicle, EmployeeAgent driver, String description) throws Exception;
	
	public Worksheet getWorsheetAssigned(EmployeeAgent employeeAgent, Organization organization, Date startDate);
}
