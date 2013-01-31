package com.thingtrack.bustrack.view.mobile.android.js;

import com.thingtrack.konekti.domain.mobile.device.Context;

public class JsContext implements IJSInterface {
	private final String name = "CONTEXT";

	private int organizationId;
	private int userId;
	private int vehicleId;
	private int routeId;		
	private Context context;
		
	@Override
	public String getInterfaceName() {
		return name;
		
	}
	
	/**
	 * @return the organizationId
	 */
	public int getOrganizationId() {
		return organizationId;
	}

	/**
	 * @param organizationId the organizationId to set
	 */
	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the vehicleId
	 */
	public int getVehicleId() {
		return vehicleId;
	}

	/**
	 * @param vehicleId the vehicleId to set
	 */
	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}
		
	/**
	 * @return the context
	 */
	public Context getContext() {
		return context;
	}
	
    /** configure JsContext */
	public void init() {
		context = new Context();
		
	}
	
	public void init(int organizationId, int userId, int vehicleId, int routeId) {
		context = new Context(organizationId, userId, vehicleId, routeId);
		
		this.organizationId = organizationId;
		this.userId = userId;
		this.vehicleId = vehicleId;
		this.routeId = routeId;
		
	}

	/**
	 * @param routeId the routeId to set
	 */
	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

	/**
	 * @return the routeId
	 */
	public int getRouteId() {
		return routeId;
	}
	
}
