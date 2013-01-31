package com.thingtrack.bustrack.view.mobile.android.activity;

import com.thingtrack.bustrack.view.mobile.android.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.thingtrack.bustrack.view.mobile.android.activity.SettingsActivity;
import com.thingtrack.bustrack.view.mobile.android.addon.ActionBar;
import com.thingtrack.bustrack.view.mobile.android.addon.CrossView;
import com.thingtrack.bustrack.view.mobile.android.addon.MultiDirectionSlidingDrawer;
import com.thingtrack.bustrack.view.mobile.android.addon.ActionBar.Action;
import com.thingtrack.bustrack.view.mobile.android.addon.ActionBar.IntentAction;
import com.thingtrack.bustrack.view.mobile.android.js.JsConfigure;
import com.thingtrack.bustrack.view.mobile.android.js.JsContext;
import com.thingtrack.bustrack.view.mobile.android.js.JsLocation;
import com.thingtrack.bustrack.view.mobile.android.js.JsMessage;
import com.thingtrack.bustrack.view.mobile.android.js.JsTelemetry;


public class MainActivity extends Activity {
	private static String TAG = "bustrack.mobile";
	
	// native components
	private CrossView crossView;
	private ActionBar actionBar;
	private MultiDirectionSlidingDrawer notificationBar;
	private Button mCloseButton;
	private Button mOpenButton;
	private WebView webView;	
	
	//TEST URI: http://93.156.59.136:8080/konekti.mobile
	private static final String DEFAULT_URI = "http://127.0.0.1:8080/konekti.mobile?restartApplication=true";
	
	// preferences
	private SharedPreferences preferences;
	private String uriKonekti;
	
	// native itents
	private Intent settingsIntent;
	private Action settingsAction;
	
	private Intent emailIntent;
	private Action emailAction;
	
	private Intent messageIntent;
	private Action messageAction;
	
	// sensor configurations
	private JsConfigure jsConfigure;
	private JsMessage jsMessage;
	private JsLocation jsLocation;
	private JsTelemetry jsTelemetry;
	private JsContext jsContext;
	
	/**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		Log.i(getClass().getSimpleName(), "onCreate");
        setContentView(R.layout.main);

    	crossView = (CrossView) findViewById(R.id.crossview);
    	
    	// load preferences
    	loadPreferences();
    	
        // create native action bar and load configuration
        loadActionBar();
        
        // configure cross view
        loadCrossView();
        
        // create webclient web engine
        loadWebClient();
        
    }
    
    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        
        return i;
    }
        
    private void loadPreferences() {
    	preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
    	
    }
    
    private void loadActionBar() {
    	actionBar = (ActionBar) findViewById(R.id.actionbar);
        actionBar.setTitle("Bustrack Mobile v1.0");
        
        // define long action click event for notification bar
        actionBar.setOnLongClickListener(onLongClickMultiDirectionSliderListener); 
        
        // add share ativity action: email, etc
        emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Servicio email");
        emailAction = new IntentAction(this, Intent.createChooser(emailIntent, "Share"), R.drawable.ic_title_share_default);
        
        actionBar.addAction(emailAction);
        
        // add message ativity action
        /*messageIntent = new Intent(this, MessageActivity.class);
        messageAction = new IntentAction(this, messageIntent, R.drawable.ic_title_share_default);
        
        actionBar.addAction(messageAction);*/
        
        // add settings activity action
        settingsIntent = new Intent(this, SettingsActivity.class);
        settingsAction = new IntentAction(this, settingsIntent, R.drawable.ic_title_export_default);
        
        actionBar.addAction(settingsAction);
        
    }
    
    private void loadCrossView() {
        // define notification bar
//      mCloseButton = (Button) findViewById( R.id.button_close );
//    	mOpenButton = (Button) findViewById( R.id.button_open );
    	notificationBar = (MultiDirectionSlidingDrawer) findViewById( R.id.drawer );
    	    	
    	// set drawer widgwet to crossView
    	crossView.setActionBar(actionBar);
    	crossView.setSlidingDrawer(notificationBar);  
    	
//        mCloseButton.setOnClickListener( new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				mDrawer.animateClose();
//				
//			}
//		});
//        
//        mOpenButton.setOnClickListener( new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if( !mDrawer.isOpened() )
//					mDrawer.animateOpen();
//				
//			}
//		});
    	
    }
    
	private void loadWebClient() {
		// create the WebView client for Android
        webView = (WebView) findViewById(R.id.webview);
        
        webView.getSettings().setJavaScriptEnabled(true);        
        webView.setWebViewClient(new MainWebViewClient());
             
        // Publish Interface Configuration to comunicate native layer <---> web layer
        jsConfigure = new JsConfigure(getApplicationContext(), this, webView); 
        webView.addJavascriptInterface(jsConfigure, jsConfigure.getInterfaceName());
        
        // Publish Interface Configuration for Message Sensor
        jsMessage = new JsMessage(getApplicationContext());
        webView.addJavascriptInterface(jsMessage, jsMessage.getInterfaceName());
        
        // Publish Interface Configuration for Location Sensor
        try {
	        jsLocation = new JsLocation(getApplicationContext());
	        jsLocation.init(preferences.getBoolean("key_sensor_location_status", false), // location sensor active 
	        				preferences.getString("key_device_name", null), // hub device name
	        				Long.parseLong(preferences.getString("key_sensor_location_min_time", "60")), // min distance
	        				Float.parseFloat(preferences.getString("key_sensor_location_min_distance", "1000")), //min time
	        		        null, // wifi mac address
	        		        preferences.getString("key_koneki_message_protocol_service_host", "127.0.0.1"), // MQTT host
	        		        Integer.parseInt(preferences.getString("key_koneki_message_protocol_service_port", "1883")), // MQTT port 
	        		        Integer.parseInt(preferences.getString("key_koneki_message_protocol_service_keepalive", "1200")), // MQTT keepalive
	        		        Integer.parseInt(preferences.getString("key_koneki_message_protocol_service_qos", "1"))); // MQTT quality of services   
	        
	        webView.addJavascriptInterface(jsLocation, jsLocation.getInterfaceName());
        }
        catch(Exception ex) {
        	Log.i(TAG, ex.getMessage()); 
        }
        
        // Publish Interface Configuration for Telemetry Sensor
        try {
	        jsTelemetry = new JsTelemetry(getApplicationContext(), this);
	        jsTelemetry.init(preferences.getBoolean("key_sensor_telemetry_status", false), // telemetry sensor active
	        				 preferences.getString("key_device_name", null), // hub device Name
	        		         null, // bluetooth mac address
	        		         preferences.getString("key_koneki_message_protocol_service_host", "127.0.0.1"), // MQTT host 
	        		         Integer.parseInt(preferences.getString("key_koneki_message_protocol_service_port", "1883")), // MQTT port 
	        		         Integer.parseInt(preferences.getString("key_koneki_message_protocol_service_keepalive", "1200")), // MQTT keepalive
	        		         Integer.parseInt(preferences.getString("key_koneki_message_protocol_service_qos", "1"))); // MQTT quality of services
	        
	        webView.addJavascriptInterface(jsTelemetry, jsTelemetry.getInterfaceName());
        }
        catch(Exception ex) {
        	Log.i(TAG, ex.getMessage()); 
        }
        
        // Publish Interface Configuration for Context native layer
        jsContext = new JsContext();
        webView.addJavascriptInterface(jsContext, jsContext.getInterfaceName());
        
        // Load konekti web client. Force restart
        uriKonekti = preferences.getString("key_uri_konekti", DEFAULT_URI);    
        webView.loadUrl(uriKonekti + "?restartApplication=true");
          
	}
	
    private class MainWebViewClient extends WebViewClient {                       
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
        	Log.i(TAG, "Processing webview url click...");
            view.loadUrl(url);
            
            return true;
        }
        
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.e(TAG, "Error: " + description);
            
            actionBar.setProgressBarVisibility(View.GONE);
            settingsAction.performAction(null);
        }
        
        @Override
        public  void onPageStarted(WebView view, String url, Bitmap favicon) {
        	actionBar.setProgressBarVisibility(View.VISIBLE);
        	
        }
        
        @Override
        public void onPageFinished(WebView view, String url) {
        	actionBar.setProgressBarVisibility(View.GONE);
        	
        }
    }
    
    @Override
    protected void onNewIntent (Intent intent) {
    	Log.e(TAG, "Intent Package: " + intent.getPackage());
    	
    	if (uriKonekti != preferences.getString("key_uri_konekti", DEFAULT_URI)) {
    		uriKonekti =  preferences.getString("key_uri_konekti", DEFAULT_URI);
    		   		
    		webView.loadUrl(uriKonekti);
    		
    		actionBar.setProgressBarVisibility(View.VISIBLE);
    	}
    }
    
    private OnLongClickListener onLongClickMultiDirectionSliderListener = new OnLongClickListener() {		
		@Override
		public boolean onLongClick(View v) {
			notificationBar.setVisibility(0);
			
			return true;
		}
	};
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		jsTelemetry.destroyReceiver();
		
	}
}

