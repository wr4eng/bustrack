package com.thingtrack.bustrack.dao.api;

import java.util.Date;
import java.util.List;

import com.thingtrack.bustrack.domain.Vehicle;
import com.thingtrack.bustrack.domain.Worksheet;
import com.thingtrack.konekti.dao.template.Dao;
import com.thingtrack.konekti.domain.EmployeeAgent;
import com.thingtrack.konekti.domain.Organization;
import com.thingtrack.konekti.domain.Service;

/**
 * @author Thingtrack S.L.
 *
 */
public interface WorksheetDao extends Dao<Worksheet, Integer> {
	public List<Service> getOpenServicesFromUserToday(EmployeeAgent employeeAgent, Organization organization, Vehicle vehicle);
	public Worksheet getOpenWorksheetFromUserToday(EmployeeAgent employeeAgent, Organization organization, Vehicle vehicle);
	
	public Worksheet getWorsheetAssigned(EmployeeAgent employeeAgent, Organization organization, Date startDate);
}
