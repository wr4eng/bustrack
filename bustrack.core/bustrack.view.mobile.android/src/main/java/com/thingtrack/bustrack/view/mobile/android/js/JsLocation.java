package com.thingtrack.bustrack.view.mobile.android.js;

import com.thingtrack.bustrack.view.mobile.android.context.ContextApp;
import com.thingtrack.bustrack.view.mobile.android.service.LocationTrackingService;
import com.thingtrack.konekti.domain.mobile.config.LocationConfigure;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class JsLocation extends JsInterface implements IJSInterface {
	private final String name = "LOCATION";	
	
	public static final String IDENTIFICATION_KEY = "IDENTIFICATION";
	public static final String PREFIX_TOPIC = "com/thingtrack/konekti/sensor/location/mobile/";
	
	private LocationConfigure locationConfigure;
	private Intent locationTrackingIntent;
	
	public JsLocation(Context context) {
		super(context);		
		
		// create location tracker
		locationTrackingIntent = new Intent(getContext(), LocationTrackingService.class);
	}
	
	@Override
	public String getInterfaceName() {
		return this.name;
		
	}
	
	public LocationConfigure getLocationConfigure() {
		return locationConfigure;
		
	}
	
	public void init(boolean active, String deviceName, long minTime, float minDistance, String mac, String host, int port, int keepAlive, int qualityOfService) {
		locationConfigure = new LocationConfigure(active, deviceName, minTime, minDistance, mac, host, port, keepAlive, qualityOfService, PREFIX_TOPIC + deviceName);
	
		ContextApp.setLocationConfigure(locationConfigure);

	}
	
    public void start(int identifier) {
    	Log.d(getClass().getSimpleName(), "start location service");  	      
    	
    	if (!locationConfigure.isActive())
    		return;
    	
    	// set the identification value
    	locationTrackingIntent.putExtra(IDENTIFICATION_KEY, identifier);
    	getContext().getApplicationContext().startService(locationTrackingIntent);
    	
    }
    
    public void stop(int identification) {
    	Log.d(getClass().getSimpleName(), "stop location service");
    	
    	getContext().getApplicationContext().stopService(locationTrackingIntent);
    	
    }
    
}
