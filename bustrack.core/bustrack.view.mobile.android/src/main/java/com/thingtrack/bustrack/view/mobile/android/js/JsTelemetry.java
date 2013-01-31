package com.thingtrack.bustrack.view.mobile.android.js;

import java.util.Set;

import com.thingtrack.bustrack.view.mobile.android.context.ContextApp;
import com.thingtrack.bustrack.view.mobile.android.service.TelemetryTrackingService;
import com.thingtrack.konekti.domain.mobile.config.TelemetryConfigure;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class JsTelemetry extends JsInterface implements IJSInterface {
	private final String name = "TELEMETRY"; 
	
    public static final String IDENTIFICATION_KEY = "IDENTIFICATION";
    public static final String DEVICE_NAME_KEY = "DEVICE_NAME";
	public static final String PREFIX_TOPIC = "com/thingtrack/konekti/sensor/telemetry/mobile/";
	
	private TelemetryConfigure telemetryConfigure;
    private Intent telemetryTrackingIntent;
    
	private Activity mainActivity;
	
	private static final int REQUEST_ENABLE_BT = 1;
	private BluetoothAdapter mBluetoothAdapter;
	public static BluetoothDevice mBluetoothDevice;
	private BroadcastReceiver mReceiver;
	
	public JsTelemetry(Context context, Activity activity) {
		super(context);
		
		this.mainActivity = activity;
		
		// create telemetry traker
		telemetryTrackingIntent = new Intent(getContext(), TelemetryTrackingService.class);
		
	}

	@Override
	public String getInterfaceName() {
		return this.name;
		
	}
	
	public TelemetryConfigure getTelemetryConfigure() {
		return telemetryConfigure;
		
	}
	
	public void init(boolean active, String deviceName, String mac, String host, int port, int keepAlive, int qualityOfService) {
		// create telemetry configuration
		telemetryConfigure = new TelemetryConfigure(active, deviceName, mac, host, port, keepAlive, qualityOfService, PREFIX_TOPIC + deviceName);
		
		// set telemetry configuration native context
		ContextApp.setTelemetryConfigure(telemetryConfigure);
		
		// discover bluetooth telemeter (must be paired!!)
		discoverBlueToothSensor();
	
	}
		
    public void start(int identifier) {
    	Log.d(getClass().getSimpleName(), "start");
    	
    	// if any paired telemetry sensor exist
    	if (mBluetoothDevice == null)
    		return;
    	
    	if (!telemetryConfigure.isActive())
    		return;
    	
    	telemetryTrackingIntent.putExtra(IDENTIFICATION_KEY, identifier);
    	telemetryTrackingIntent.putExtra(DEVICE_NAME_KEY, telemetryConfigure.getDeviceName());
    	getContext().startService(telemetryTrackingIntent);
    }
    
    public void stop(int identifier) {
    	Log.d(getClass().getSimpleName(), "stop");
    	
    	getContext().stopService(telemetryTrackingIntent);
    	
    }
    
    private void discoverBlueToothSensor() {
    	mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    	
        // Check for Bluetooth support in the first place 
        // Emulator doesn't support Bluetooth and will return null
        if(mBluetoothAdapter == null) { 
        	Log.e("MyDiscoverBluetoothActivity", "Bluetooth NOT supported. Aborting.");
          
        	return;
        }
        
        // Check if is enable and request to active if is necesary
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            mainActivity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            
        }
        
        // Listing paired devices if exist one
        Log.e("MyDiscoverBluetooth", "Devices Pared:");
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        
        // If there are paired devices
	    if (pairedDevices.size() > 0) {
	         // Loop through paired devices
	         for (BluetoothDevice device : pairedDevices) {
	        	 if (device.getName().equals("CANgineBT-FMS")) {
	        		//00:80:25:1B:9B:D7
	        		 Log.e("MyDiscoverBluetoothActivity", "Paired Device " + device.getName() + "bonded with address: " + device.getAddress());
	        		 
	        		 // recover bluetooth device
	                 mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(device.getAddress());	    
	        		
	                 // set telemetry configuration
	                 //telemetryConfigure.setMac(mBluetoothDevice.getAddress());	                 	        		 
	        		
	                 // finalize the discovery process
	                 mBluetoothAdapter.cancelDiscovery();
	                	
	        		 return;
	        	 }
	         }
	    }
     
	    // Create a BroadcastReceiver for ACTION_FOUND
	    mReceiver = new BroadcastReceiver() {
	        public void onReceive(Context context, Intent intent) {
	            String action = intent.getAction();
	            // When discovery finds a device
	            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
	                // Get the BluetoothDevice object from the Intent
	                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	                
	                if (device.getName().equals("CANgineBT-FMS")) {
	                	Log.e("MyDiscoverBluetoothActivity", "Founded Device " + device.getName() + "bonded with address: " + device.getAddress());
	                	
	                	// recover bluetooth device
	                	mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(device.getAddress());
	                	
	                	// set telemetry configuration
		                //telemetryConfigure.setMac(mBluetoothDevice.getAddress());
		        		//ContextApp.setTelemetryConfigure(telemetryConfigure);
		        		 
	                	// finalize the discovery process
	                	mBluetoothAdapter.cancelDiscovery();
	                }
	                	
	            }
	        }
	    };
	    
	    // Register the BroadcastReceiver
	    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
	    getContext().registerReceiver(mReceiver, filter);
	    
        // Starting the device discovery
        mBluetoothAdapter.startDiscovery();
    }
    
    public void destroyReceiver() {
		if (mReceiver != null)
			getContext().unregisterReceiver(mReceiver);
    	
    }
}
