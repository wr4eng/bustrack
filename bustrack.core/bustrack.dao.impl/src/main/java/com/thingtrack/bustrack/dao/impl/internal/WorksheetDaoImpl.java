package com.thingtrack.bustrack.dao.impl.internal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;

import com.thingtrack.konekti.dao.template.JpaDao;
import com.thingtrack.konekti.domain.EmployeeAgent;
import com.thingtrack.konekti.domain.Organization;
import com.thingtrack.konekti.domain.Service;
import com.thingtrack.bustrack.dao.api.WorksheetDao;
import com.thingtrack.bustrack.domain.Vehicle;
import com.thingtrack.bustrack.domain.Worksheet;
import com.thingtrack.bustrack.domain.WorksheetLine;

/**
 * @author Thingtrack S.L.
 *
 */
@Repository
public class WorksheetDaoImpl extends JpaDao<Worksheet, Integer> implements WorksheetDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Service> getOpenServicesFromUserToday(EmployeeAgent employeeAgent, Organization organization, Vehicle vehicle) {		
	String queryString = "SELECT DISTINCT wh";
		queryString += " FROM Worksheet wh";
		queryString += " JOIN wh.worksheetLines wl";
		queryString += " JOIN wl.service sv";
		queryString += " JOIN sv.routes rt";
		queryString += " JOIN rt.stops st";
		queryString += " WHERE wh.driver = :driver";
		queryString += " AND wh.worksheetDate = :worksheetDate";
		queryString += " AND sv.organization = :organization";
		queryString += " AND wh.worksheetstatus.code = :worksheetStatus_code";
		queryString += " AND rt.driver = :driver";
		
		if (vehicle != null)
			queryString += " AND rt.vehicle = :vehicle";
						
		Query query = (Query) getEntityManager()
			.createQuery(queryString)
			.setParameter("organization", organization)
			.setParameter("driver", employeeAgent)
			.setParameter("worksheetDate", getNowDate())
			.setParameter("worksheetStatus_code", Worksheet.STATUS.OPEN.name());

		if (vehicle != null)
			query.setParameter("vehicle", vehicle);
		
		// create the list result
		List<Service> services = new ArrayList<Service>();
		
		for (Worksheet worksheet : (List<Worksheet>)query.getResultList()) {
			for (WorksheetLine worksheetLine : worksheet.getWorksheetLines())
				services.add(worksheetLine.getService());
		}
		
		return services;
		
	}

	@Override
	public Worksheet getOpenWorksheetFromUserToday(EmployeeAgent employeeAgent, Organization organization, Vehicle vehicle) {
		String queryString;
		
		// Retrieve existing code
		queryString = "SELECT DISTINCT wh";
		queryString += " FROM Worksheet wh";
		queryString += " JOIN wh.worksheetLines wl";
		queryString += " JOIN wl.service sv";
		queryString += " JOIN sv.routes rt";
		queryString += " JOIN rt.stops st";
		queryString += " WHERE wh.driver = :driver";
		queryString += " AND wh.worksheetDate = :worksheetDate";
		queryString += " AND sv.organization = :organization";
		queryString += " AND wh.worksheetstatus.code = :worksheetStatus_code";
		queryString += " AND rt.driver = :driver";
		
		if (vehicle != null)
			queryString += " AND rt.vehicle = :vehicle";
				
		Query query = (Query) getEntityManager()
			.createQuery(queryString)
			.setParameter("organization", organization)
			.setParameter("driver", employeeAgent)
			.setParameter("worksheetDate", getNowDate())
			.setParameter("worksheetStatus_code", Worksheet.STATUS.OPEN.name());

		if (vehicle != null)
			query.setParameter("vehicle", vehicle);
				
		return (Worksheet)query.getSingleResult();
		
	}
	
	private Date getNowDate() {
		Date date = new Date();  
		  
		// Get Calendar object set to the date and time of the given Date object  
		Calendar cal = Calendar.getInstance();  
		cal.setTime(date);  
		  
		// Set time fields to zero  
		cal.set(Calendar.HOUR_OF_DAY, 0);  
		cal.set(Calendar.MINUTE, 0);  
		cal.set(Calendar.SECOND, 0);  
		cal.set(Calendar.MILLISECOND, 0);  
		  
		// Put it back in the Date object  
		return cal.getTime(); 
	}

	@Override
	public Worksheet getWorsheetAssigned(EmployeeAgent employeeAgent,
			Organization organization, Date startDate) {

		String queryString;
		
		// Retrieve existing code
		queryString = "SELECT DISTINCT wh";
		queryString += " FROM Worksheet wh";
		queryString += " JOIN wh.worksheetLines wl";
		queryString += " JOIN wl.service sv";
		queryString += " JOIN sv.routes rt";
		queryString += " JOIN rt.stops st";
		queryString += " WHERE wh.driver = :driver";
		queryString += " AND wh.worksheetDate = :worksheetDate";
		queryString += " AND sv.organization = :organization";
		queryString += " AND rt.driver = :driver";
		
		Query query = (Query) getEntityManager()
			.createQuery(queryString)
			.setParameter("organization", organization)
			.setParameter("driver", employeeAgent)
			.setParameter("worksheetDate", startDate, TemporalType.DATE);
			
		return (Worksheet)query.getSingleResult();
	}
}
