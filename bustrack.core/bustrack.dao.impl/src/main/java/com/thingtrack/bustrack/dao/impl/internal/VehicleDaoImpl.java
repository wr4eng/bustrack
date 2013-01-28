package com.thingtrack.bustrack.dao.impl.internal;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.thingtrack.bustrack.dao.api.VehicleDao;
import com.thingtrack.bustrack.domain.Route;
import com.thingtrack.bustrack.domain.Vehicle;
import com.thingtrack.konekti.dao.template.JpaDao;
import com.thingtrack.konekti.domain.Organization;
import com.thingtrack.konekti.domain.sensor.Sensor;
import com.thingtrack.konekti.domain.sensor.SensorTelemetry;

/**
 * @author Thingtrack S.L.
 * 
 */
@Repository
public class VehicleDaoImpl extends JpaDao<Vehicle, Integer> implements
		VehicleDao {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.thingtrack.bustrack.dao.api.VehicleDao#getByNumber(java.lang.String)
	 */
	@Override
	public Vehicle getByNumber(String number) throws Exception {
		Vehicle vehicle = (Vehicle) getEntityManager()
				.createQuery(
						"SELECT p FROM " + getEntityName()
								+ " p WHERE p.vehicleNumber = :number")
				.setParameter("number", number).getSingleResult();

		return vehicle;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Vehicle> getFreeVehicles(Organization organization)
			throws Exception {
		String queryString = "SELECT vh";
		queryString += " FROM Vehicle vh";
		queryString += " JOIN vh.vehicleStatus vs";
		queryString += " WHERE :organization MEMBER OF vh.organizations";
		queryString += " AND vs.code = :code";

		Query query = (Query) getEntityManager().createQuery(queryString)
				.setParameter("organization", organization)
				.setParameter("code", Sensor.STATUS.ACTIVE.name());

		return query.getResultList();

	}

	@Override
	public SensorTelemetry getTelemeterByNumber(String number) throws Exception {
		String queryString = "SELECT st";
		queryString += " FROM Vehicle vh";
		queryString += " JOIN vh.sensorTelemetry st";
		queryString += " JOIN st.sensorStatus stt";
		queryString += " WHERE vh.vehicleNumber = :number";
		queryString += " AND stt.code = :code";

		Query query = (Query) getEntityManager().createQuery(queryString)
				.setParameter("number", number)
				.setParameter("code", Sensor.STATUS.ACTIVE.name());

		return (SensorTelemetry) query.getSingleResult();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Vehicle> getAvailableVehicles(Organization organization,
			Route routeToCommit) throws Exception{

		String queryString = "SELECT DISTINCT vh";
		queryString += " FROM Vehicle vh";
		queryString += " JOIN vh.vehicleStatus vs";
		queryString += " LEFT JOIN vh.routes rs";
		queryString += " LEFT JOIN rs.stops ss";
		queryString += " WHERE :organization MEMBER OF vh.organizations";
		queryString += " AND vs.code = :code";
		queryString += " AND NOT EXISTS (SELECT stp FROM Stop stp WHERE ss = stp AND stp.stopCheckoutDate >= :checkoutDate AND stp.stopArrivalDate <= :arrivalDate)";
		

		Query query = (Query) getEntityManager().createQuery(queryString)
				.setParameter("organization", organization)
				.setParameter("code", Sensor.STATUS.ACTIVE.name())
				.setParameter("checkoutDate", routeToCommit.getStops().get(0).getStopCheckoutDate())
				.setParameter("arrivalDate", routeToCommit.getStops().get(1).getStopArrivalDate());

		return query.getResultList();
	}
	
}
