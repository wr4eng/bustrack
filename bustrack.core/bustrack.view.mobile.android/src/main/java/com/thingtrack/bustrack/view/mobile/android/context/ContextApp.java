package com.thingtrack.bustrack.view.mobile.android.context;

import com.thingtrack.konekti.domain.mobile.config.LocationConfigure;
import com.thingtrack.konekti.domain.mobile.config.MessageConfigure;
import com.thingtrack.konekti.domain.mobile.config.TelemetryConfigure;

public class ContextApp {
	private static LocationConfigure locationConfigure;
	private static TelemetryConfigure telemetryConfigure;
	private static MessageConfigure messageConfigure;
	/**
	 * @param locationConfigure the locationConfigure to set
	 */
	public static void setLocationConfigure(LocationConfigure locationConfigure) {
		ContextApp.locationConfigure = locationConfigure;
	}
	/**
	 * @return the locationConfigure
	 */
	public static LocationConfigure getLocationConfigure() {
		return locationConfigure;
	}
	/**
	 * @param telemetryConfigure the telemetryConfigure to set
	 */
	public static void setTelemetryConfigure(TelemetryConfigure telemetryConfigure) {
		ContextApp.telemetryConfigure = telemetryConfigure;
	}
	/**
	 * @return the telemetryConfigure
	 */
	public static TelemetryConfigure getTelemetryConfigure() {
		return telemetryConfigure;
	}
	/**
	 * @param messageConfigure the messageConfigure to set
	 */
	public static void setMessageConfigure(MessageConfigure messageConfigure) {
		ContextApp.messageConfigure = messageConfigure;
	}
	/**
	 * @return the messageConfigure
	 */
	public static MessageConfigure getMessageConfigure() {
		return messageConfigure;
	}
}
