package com.thingtrack.bustrack.view.mobile.android.js;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.webkit.WebView;

public class JsConfigure implements IJSInterface {
	private static String TAG = "JsConfigure";
	
	private final String name = "CONFIGURE";
	
	private Context context;
	private Activity activity;
	private WebView webView;
	
	private String locationMac;
	
	private static final String DEFAULT_MAC = "00:00:00:00:00:00";
	
	private static final int REQUEST_ENABLE_BT = 1;
	private BroadcastReceiver mReceiver;
	
	@Override
	public String getInterfaceName() {
		return name;
		
	}

	public JsConfigure (Context context, Activity activity, WebView webView) {
        this.context = context;
        this.activity = activity;
        this.webView = webView;
        
    }
			
	/**
	 * @param webView the webView to set
	 */
	public void setWebView(WebView webView) {
		this.webView = webView;
		
	}
		
	public void getLocationMac() {
		// Android Wifi Manager
		WifiManager wifiManager =(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		
		if (wifiManager == null)
			return;
		
		if (!wifiManager.isWifiEnabled())
			wifiManager.setWifiEnabled(true);
		
		locationMac = wifiManager.getConnectionInfo().getMacAddress();
	
		// from emulator we get default mac
		if (locationMac == null)
			locationMac = DEFAULT_MAC;
				
		// send data to web layer
		webView.loadUrl("javascript:getMacLocation(\"" + locationMac + "\")");
		
		//wifiManager.setWifiEnabled(false);
		
	}
	
	public void getTelemetryMac() {
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    	
		if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        	   
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        
	    // If there are paired devices
	    if (pairedDevices.size() > 0) {
	         // Loop through paired devices
	         for (BluetoothDevice device : pairedDevices) {
	             // Add the name and address to an array adapter to show in a ListView
	        	 if (device.getName().equals("CANgineBT-FMS"))
	        		 webView.loadUrl("javascript:getMacTelemetry(\"" + device.getAddress() + "\")");
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
	                	Log.i(TAG, device.getAddress());
	                	webView.loadUrl("javascript:getMacTelemetry(\"" + device.getAddress() + "\")");
	                }
	                	
	            }
	        }
	    };
	    	    
	    // Register the BroadcastReceiver
	    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
	    activity.registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
	    
        // Starting the device discovery
        mBluetoothAdapter.startDiscovery();
        
        //webView.loadUrl("javascript:getMacTelemetry(\"" + "XX:XX:XX:XX:XX:XX" + "\")");
	}

}
