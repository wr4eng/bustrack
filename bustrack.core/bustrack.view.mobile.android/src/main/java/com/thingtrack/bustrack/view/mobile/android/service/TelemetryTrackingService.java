package com.thingtrack.bustrack.view.mobile.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Date;
import java.util.UUID;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.thingtrack.bustrack.view.mobile.android.context.ContextApp;
import com.thingtrack.bustrack.view.mobile.android.js.JsTelemetry;
import com.thingtrack.konekti.domain.mobile.device.TelemetryCaptureDevice;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class TelemetryTrackingService extends Service {	
	private String topic;
	private Serializer serializer;
	
    private static String TAG = "TelemetryTrackingService";
    
    private static int TELEMETRY_VALUES = 106;
    
    @SuppressWarnings("unused")
	private boolean isEnabled = false;
    
    public static final  boolean DEBUG_MODE = true;
    
	private ConnectThread mConnecteThread;
	private ConnectedThread mConnectedThread;
    
	private int identifier;
	private String deviceName;
	
	private static final UUID DEFAULT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothSocket mBluetoothSocket;
	
    @Override
	public void onCreate() {
    	super.onCreate();
    	
		Log.d(TelemetryTrackingService.class.getSimpleName(), "on Create Telemetry Tracker");
		
		//Get the Device Id to build the message's topic to send for each location capture
		topic =  ContextApp.getTelemetryConfigure().getTopic();
		
		//Initialize the Serializer to serialize the domain capture to xml
		serializer = new Persister();
		
	}
		
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		
		Log.d(TelemetryTrackingService.class.getSimpleName(), "on Start Telemetry Tracker");
		
		// recover the identification location and hub sensor name
		this.identifier = intent.getExtras().getInt(JsTelemetry.IDENTIFICATION_KEY);
		this.deviceName = intent.getExtras().getString(JsTelemetry.DEVICE_NAME_KEY);
		
		// start telemetry service
        try {        							
        	mConnecteThread = new ConnectThread(JsTelemetry.mBluetoothDevice); // how pass this parameter to the service????
            mConnecteThread.start();	           
        } catch (Exception e) {
	        Log.e(TAG, e.getMessage());
	        
		} 
        
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		Log.d(TelemetryTrackingService.class.getSimpleName(), "on Destroy Telemetry Tracker");
		
        try {
        	mBluetoothSocket.close();
        } catch (IOException e) { 
        	Log.e(TAG, e.getMessage());
        }

	}
	
	private class ConnectThread extends Thread {
		public ConnectThread(BluetoothDevice btdevice) throws Exception {	
			try {
				mBluetoothSocket = btdevice.createRfcommSocketToServiceRecord(DEFAULT_UUID);
			}
			catch( IOException ex) {
				Log.i("ConnectThread", ex.getMessage());
				ex.printStackTrace();
			}
			
		}
		
		public void run() {
			Log.i("TelemetryTrackingService", "Run Tracker Connect Thread!");
			
			// Cancel discovery because it will slow down the connection
			if (mBluetoothAdapter != null)
				mBluetoothAdapter.cancelDiscovery();
				
			try {
	            // Connect the device through the socket. This will block
	            // until it succeeds or throws an exception
				mBluetoothSocket.connect();			
	     	} catch (IOException connectException) {
	    	    Log.e("ConnectThread", connectException.getMessage());
	    	    connectException.printStackTrace();
				
	            // Unable to connect; close the socket and get out
	            try {
	            	mBluetoothSocket.close();
	            } catch (IOException closeException) { 
	            	Log.i("ConnectThread", closeException.getMessage());
	            }
	            
	            return;
	       }
	     	
        	// thread to connected device
            try {        	
               	mConnectedThread = new ConnectedThread();
               	mConnectedThread.start();	           
            } catch (Exception e) {
    	        Log.e("ConnectThread", e.getMessage());
    	        e.printStackTrace();
    		} 
	     	 
       }	
		
	}
	
	private class ConnectedThread extends Thread {
	    private final InputStream mmInStream;
	 
	    public ConnectedThread() {
	        InputStream tmpIn = null;
	 
	        // Get the input and output streams, using temp objects because
	        // member streams are final
	        try {
	            tmpIn = mBluetoothSocket.getInputStream();
	        } catch (IOException e) { }
	 
	        mmInStream = tmpIn;
	    }
	 
	    public void run() {
	    	Log.i("TelemetryTrackingService", "Run Tracker Connected Thread!");
	    	
	    	boolean exist = false;
	    	
			try {
				byte[] buffer = new byte[4096];
				int read1 = mmInStream.read(buffer);
				
				while (read1 != -1) {
					String str= new String(buffer);  
	                
	                String[] arraystr = null;
	                String[] lines = str.split("\r\n");
	                
	                 // get the first line with 106 parameters to send throw MQTT
	                for (String line : lines) {
		                 arraystr=line.split(";");
		                
		                 if (!line.contains("CANgineBT") && arraystr.length == TELEMETRY_VALUES) {
		                	 exist = true;
		                	 
		                	 break;
		                 }
		                 
	                }
	                
	                // si tras el split arraystr s�lo tiene un campo --> mensaje inicial --> descartar (no asignar nada al objeto capture)
	                //if (arraystr.length > 2) {
	                if (exist) {
	                	Log.i(TAG, "Getting data... Inputstream...");
	                	Log.i(TAG, "Getting data... str...");
	                              
	                	TelemetryCaptureDevice capture = new TelemetryCaptureDevice();
	                	
	                	// task id
	                	capture.setTime(new Date());
	                	capture.setIdentifier(identifier);
	                	capture.setIdentifieId(deviceName);
	                	capture.setTime1(arraystr[0]);
	                	capture.setEngSpeed(arraystr[1]);
	                	capture.setAccel(arraystr[2]);
	                	capture.setTCO_Speed(arraystr[3]);	                    
	                	capture.setTCO_MD(arraystr[4]);
	                	capture.setTCO_OS(arraystr[5]);
	                	capture.setTCO_DI(arraystr[6]);
	                	capture.setTCO_DP(arraystr[7]);
	                	capture.setTCO_HI(arraystr[8]);
	                	capture.setTCO_EV(arraystr[9]);	                    
	                	capture.setTCO_D1_PR(arraystr[10]);
	                	capture.setTCO_D1_WS(arraystr[11]);
	                	capture.setTCO_D1_TS(arraystr[12]);
	                	capture.setTCO_D2_PR(arraystr[13]);
	                	capture.setTCO_D2_WS(arraystr[14]);
	                	capture.setTCO_D2_TS(arraystr[15]);	                    
		                capture.setVehSpeed(arraystr[16]);
		                capture.setCC(arraystr[17]);
		                capture.setBR(arraystr[18]);
		                capture.setCS(arraystr[19]);	                    
		                capture.setPB(arraystr[20]);	                    
		                capture.setDistance(arraystr[21]);
		                capture.setEngHours(arraystr[22]);
		                capture.setFuelC(arraystr[23]);
		                capture.setEngTemp(arraystr[24]);
		                capture.setFuelLev(arraystr[25]);
		                capture.setVehID(arraystr[26]);	                    
		                capture.setFMS_Versd(arraystr[27]);
		                capture.setFMS_Diag(arraystr[28]);
		                capture.setFMS_Requ(arraystr[29]);
		                capture.setGear_S(arraystr[30]);                                     
		                capture.setGear_C(arraystr[31]);
		                capture.setDC1_P(arraystr[32]);
		                capture.setDC1_R(arraystr[33]);
		                capture.setDC1_S(arraystr[34]);
		                capture.setDC2_1(arraystr[35]);
		                capture.setDC2_2(arraystr[36]);	                    
		                capture.setDC2_3(arraystr[37]);
		                capture.setDC2_4(arraystr[38]);	              
		                capture.setDC2_5(arraystr[39]);
		                capture.setDC2_6(arraystr[40]);
		                capture.setDC2_7(arraystr[41]);
		                capture.setDC2_8(arraystr[42]);
		                capture.setDC2_9(arraystr[43]);
		                capture.setDC2_10(arraystr[44]);	                   	                    
		                capture.setBellowPr_FAL(arraystr[45]);
		                capture.setBellowPr_FAR(arraystr[46]);                    
		                capture.setBellowPr_RAL(arraystr[47]);
		                capture.setBellowPr_RAR(arraystr[48]);	                    
		                capture.setBrakePR_1(arraystr[49]);
		                capture.setBrakePr_2(arraystr[50]);   	                    
		                capture.setAltern_1(arraystr[51]);
		                capture.setAltern_2(arraystr[52]);          
		                capture.setAltern_3(arraystr[53]);
		                capture.setAltern_4(arraystr[54]);
		                capture.setDateTime(arraystr[55]);
		                capture.setAmbTemp(arraystr[56]);          
		                capture.setCard1_ID(arraystr[57]);
		                capture.setCard1_Type(arraystr[58]);	                    
		                capture.setCard1_Country(arraystr[59]);
		                capture.setCard2_ID(arraystr[60]);          
		                capture.setCard2_Type(arraystr[61]);
		                capture.setCard2_Country(arraystr[62]);
		                capture.setFuelEco_l_h(arraystr[63]);
		                capture.setFuelEco_km_l(arraystr[64]);                	
		            	capture.setTS_1(arraystr[65]);
		                capture.setTS_2(arraystr[66]);          
		                capture.setTS_3(arraystr[67]);
		                capture.setTS_4(arraystr[68]);
		                capture.setTS_5(arraystr[69]);
		                capture.setTS_6(arraystr[70]);          
		                capture.setTS_7(arraystr[71]);
		                capture.setTS_8(arraystr[72]);
		                capture.setTS_9(arraystr[73]);
		                capture.setTS_10(arraystr[74]);
		                capture.setTS_11(arraystr[75]);          
		                capture.setTS_12(arraystr[76]);
		                capture.setTS_13(arraystr[77]);
		                capture.setTS_14(arraystr[78]);
		                capture.setTS_15(arraystr[79]);
		                capture.setTS_16(arraystr[80]);          
		                capture.setTS_17(arraystr[81]);
		                capture.setTS_18(arraystr[82]);
		                capture.setTS_19(arraystr[83]);
		                capture.setTS_20(arraystr[84]);
		                capture.setTS_21(arraystr[85]);          
		                capture.setTS_22(arraystr[86]);
		                capture.setTS_23(arraystr[87]);
		                capture.setTS_24(arraystr[88]);
		                capture.setTS_25(arraystr[89]);
		                capture.setTS_26(arraystr[90]);          
		                capture.setTS_27(arraystr[91]);
		                capture.setTS_28(arraystr[92]);
		                capture.setTS_29(arraystr[93]);
		                capture.setTS_30(arraystr[94]);
		                capture.setTS_31(arraystr[95]);          
		                capture.setTS_32(arraystr[96]);
		                capture.setTS_33(arraystr[97]);
		                capture.setTS_34(arraystr[98]);
		                capture.setTS_35(arraystr[99]);
		                capture.setTS_36(arraystr[100]);          
		                capture.setTS_37(arraystr[101]);
		                capture.setTS_38(arraystr[102]);
		                capture.setTS_39(arraystr[103]);
		                capture.setTS_40(arraystr[104]);
		                capture.setTS_41(arraystr[105]);                 
		      
		                // serialize             
		                StringWriter writer = new StringWriter(); 
		        		
		        		try {
		        			Log.i("TelemetryTrackingService", "Capture Serialized!");
		        			serializer.write(capture, writer);
		        		} catch (Exception e) {		        		
		        			Log.e(TelemetryTrackingService.class.getSimpleName(), e.getMessage());
		        			e.printStackTrace();
		        			
		        			return;
		        		}
		        		
		        		byte[] xmlCapture = writer.toString().getBytes();
		        		
		        		Intent sendToMessagingService = new Intent(getApplicationContext(), MessagingService.class);	/// topic
		        		sendToMessagingService.putExtra(MessagingService.TOPIC_KEY, topic);
		        		sendToMessagingService.putExtra(MessagingService.MESSAGE_KEY, xmlCapture);
		        		
		        		startService(sendToMessagingService);
		        		Log.i("TelemetryTrackingService", "Capture send!");
		        		
//		        		try {
//							Thread.sleep(5000);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
	                }
	                
	                // next buffer read
	                read1 = mmInStream.read(buffer);
	                
	        		try {
	        			Thread.sleep(5000);
	        		} catch (InterruptedException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	                
	                exist = false;
	            }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
	 
	    /* Call this from the main activity to shutdown the connection */
	    @SuppressWarnings("unused")
		public void cancel() {
	        try {
	        	mBluetoothSocket.close();
	        } catch (IOException e) { }
	    }
	}
} 
