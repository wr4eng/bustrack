package com.thingtrack.bustrack.view.mobile.android.service;

import java.io.StringWriter;
import java.util.Date;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import com.thingtrack.bustrack.view.mobile.android.context.ContextApp;
import com.thingtrack.bustrack.view.mobile.android.js.JsLocation;
import com.thingtrack.konekti.domain.mobile.device.LocationCaptureDevice;

public class LocationTrackingService extends Service implements LocationListener {

	private String topic;
	private Serializer serializer;
	
	private LocationManager locationManager;
	
	private int identifier;
	
	@Override
	public void onCreate() {
		super.onCreate();	
		
		Log.d(LocationTrackingService.class.getSimpleName(), "on Create Location Service");

		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		
		if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
			Log.w(LocationTrackingService.class.getSimpleName(), "The GPS provider is unabled");
			
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);		
		}
		
//		try {
//			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, ContextApp.getLocationConfigure().getMinTime(), ContextApp.getLocationConfigure().getMinDistance(), this);
//		}
//		catch (Exception ex) {
//			ex.getMessage();
//		}
		
		//Get topic to send for each location capture
		topic = ContextApp.getLocationConfigure().getTopic();
		
		//Initialize the Serializer to serialize the domain capture to xml
		serializer = new Persister();
		
		Log.d(LocationTrackingService.class.getSimpleName(), "Location Service created");
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
		
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		
		// revover the identification location service
		this.identifier = intent.getExtras().getInt(JsLocation.IDENTIFICATION_KEY);
		
		// start location service
		try {
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, ContextApp.getLocationConfigure().getMinTime(), ContextApp.getLocationConfigure().getMinDistance(), this);
		}
		catch (Exception ex) {
			ex.getMessage();
		}
		
	}
	
	@Override
	public void onDestroy() {
		// stop location service
		locationManager.removeUpdates(this);
		
	}
	
	@Override
	public void onLocationChanged(Location location) {
		// persist and send location value throw MQtt Protocol
		LocationCaptureDevice capture = new LocationCaptureDevice();
		
		capture.setIdentifier(identifier);
		capture.setTime(new Date(location.getTime()));
		capture.setAccuracy(location.getAccuracy());
		capture.setAltitude(location.getAltitude());
		capture.setBearing(location.getBearing());
		capture.setLatitude(location.getLatitude());
		capture.setLongitude(location.getLongitude());
		capture.setProvider(location.getProvider());
		capture.setSpeed(location.getSpeed());
		
		StringWriter writer = new StringWriter(); 
		
		try {
			serializer.write(capture, writer);
		} catch (Exception e) {
		
			Log.e(LocationTrackingService.class.getSimpleName(), e.getMessage());
			e.printStackTrace();
			return;
		}
		
		byte[] xmlCapture = writer.toString().getBytes();
		
		Intent sendToMessagingService = new Intent(this, MessagingService.class);
		sendToMessagingService.putExtra(MessagingService.TOPIC_KEY, topic);
		sendToMessagingService.putExtra(MessagingService.MESSAGE_KEY, xmlCapture);
		
		startService(sendToMessagingService);
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.w(LocationTrackingService.class.getSimpleName(), provider + " on status change event");
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		Log.w(LocationTrackingService.class.getSimpleName(), provider + " on provider enabled event");
	
	}

	@Override
	public void onProviderDisabled(String provider) {
		Log.w(LocationTrackingService.class.getSimpleName(), provider + " on provider disable event");
		
	}

}
