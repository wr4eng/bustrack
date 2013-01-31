package com.thingtrack.bustrack.view.mobile.android.service;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.ibm.mqtt.IMqttClient;
import com.ibm.mqtt.MqttClient;
import com.ibm.mqtt.MqttException;
import com.ibm.mqtt.MqttNotConnectedException;
import com.ibm.mqtt.MqttPersistence;
import com.ibm.mqtt.MqttPersistenceException;
import com.ibm.mqtt.MqttSimpleCallback;
import com.thingtrack.bustrack.view.mobile.android.R;
import com.thingtrack.bustrack.view.mobile.android.activity.MQTTNotifierActivity;
import com.thingtrack.bustrack.view.mobile.android.activity.MainActivity;
import com.thingtrack.bustrack.view.mobile.android.service.util.ForegroundCheckTask;

public class MessagingService extends Service implements MqttPersistence,
		MqttSimpleCallback {

	/************************************************************************/
	/* CONSTANTS */
	/************************************************************************/

	// something unique to identify your app - used for stuff like accessing
	// application preferences
	public static final String APP_ID = "com.dalelane.mqtt";

	// constants used to notify the Activity UI of received messages
	public static final String MQTT_MSG_RECEIVED_INTENT = "com.thingtrack.bustrack.mobile.mqtt.MSGRECVD";
	public static final String MQTT_MSG_RECEIVED_TOPIC = "com.thingtrack.bustrack.mobile.mqtt.MSGRECVD_TOPIC";
	public static final String MQTT_MSG_RECEIVED_MSG = "com.thingtrack.bustrack.mobile.mqtt.MSGRECVD_MSGBODY";

	// constants used to tell the Activity UI the connection status
	public static final String MQTT_STATUS_INTENT = "com.thingtrack.bustrack.mobile.mqtt.STATUS";
	public static final String MQTT_STATUS_MSG = "com.thingtrack.bustrack.mobile.mqtt.STATUS_MSG";

	// constant used internally to schedule the next ping event
	public static final String MQTT_PING_ACTION = "com.thingtrack.bustrack.mobile.mqtt.PING";

	// constants used by status bar notifications
	public static final int MQTT_NOTIFICATION_ONGOING = 1;
	public static final int MQTT_NOTIFICATION_UPDATE = 2;

	// constants used to define MQTT connection status
	public enum MQTTConnectionStatus {
		INITIAL, // initial status
		CONNECTING, // attempting to connect
		CONNECTED, // connected
		NOTCONNECTED_WAITINGFORINTERNET, // can't connect because the phone
											// does not have Internet access
		NOTCONNECTED_USERDISCONNECT, // user has explicitly requested
										// disconnection
		NOTCONNECTED_DATADISABLED, // can't connect because the user
									// has disabled data access
		NOTCONNECTED_UNKNOWNREASON
		// failed to connect for some reason
	}

	// MQTT constants
	public static final int MAX_MQTT_CLIENTID_LENGTH = 22;

	/************************************************************************/
	/* VARIABLES used to maintain state */
	/************************************************************************/

	// status of MQTT client connection
	@SuppressWarnings("unused")
	private MQTTConnectionStatus connectionStatus = MQTTConnectionStatus.INITIAL;

	/************************************************************************/
	/* VARIABLES used to configure MQTT connection */
	/************************************************************************/

	public static final String TOPIC_KEY = "topic";
	public static final String MESSAGE_KEY = "message_body";

	// Message Broker connection data
	private String mqttClientId;
	private String brokerHostName;
	private int brokerHostPort;
	private boolean cleanStart;
	private int qualityOfService; // {0, 1 ,2}

	// how often should the app ping the server to keep the connection alive?
	//
	// too frequently - and you waste battery life
	// too infrequently - and you wont notice if you lose your connection
	// until the next unsuccessfull attempt to ping
	//
	// it's a trade-off between how time-sensitive the data is that your
	// app is handling, vs the acceptable impact on battery life
	//
	// it is perhaps also worth bearing in mind the network's support for
	// long running, idle connections. Ideally, to keep a connection open
	// you want to use a keep alive value that is less than the period of
	// time after which a network operator will kill an idle connection
	private short keepAlive; // In seconds, recommended 20 * 60 as the average
	private String topicName;

	// connection to the message broker
	private IMqttClient mqttClient = null;

	/************************************************************************/
	/* VARIABLES - other local variables */
	/************************************************************************/

	// Manager to obtain the device id
	TelephonyManager telephonyManager;

	// receiver that notifies the Service when the phone gets data connection
	private NetworkConnectionIntentReceiver netConnReceiver;

	// receiver that wakes the Service up when it's time to ping the server
	private PingSender pingSender;

	// receiver that notifies the Service when the user changes data use
	// preferences
	private BackgroundDataChangeIntentReceiver dataEnabledReceiver;
	// Then set up the receivers to get the messages rebroadcasted by the
	// Service.
	// There are two types of messages broadcast by the Service � MQTT messages,
	// and the current connection status.
	private StatusUpdateReceiver statusUpdateIntentReceiver;
	private MQTTMessageReceiver messageIntentReceiver;

	private LocalBinder<MessagingService> mBinder;

	@Override
	public void onCreate() {
		super.onCreate();

		// reset status variable to initial state
		connectionStatus = MQTTConnectionStatus.INITIAL;

		// create a binder that will let the Activity UI send
		// commands to the Service
		mBinder = new LocalBinder<MessagingService>(this);

		dataEnabledReceiver = new BackgroundDataChangeIntentReceiver();
		registerReceiver(dataEnabledReceiver, new IntentFilter(
				ConnectivityManager.ACTION_BACKGROUND_DATA_SETTING_CHANGED));

		statusUpdateIntentReceiver = new StatusUpdateReceiver();
		IntentFilter intentSFilter = new IntentFilter(
				MessagingService.MQTT_STATUS_INTENT);
		registerReceiver(statusUpdateIntentReceiver, intentSFilter);

		messageIntentReceiver = new MQTTMessageReceiver();
		IntentFilter intentCFilter = new IntentFilter(
				MessagingService.MQTT_MSG_RECEIVED_INTENT);
		registerReceiver(messageIntentReceiver, intentCFilter);

		// Get the Message broker configuration settings
		loadMessageBrokerConfigSettings();

		// Create the MQTT Client and connect it to the broker
		createMqttClient();

	}

	@Override
	public void onDestroy() {

		super.onDestroy();

		// disconnect immediately
		disconnectFromBroker();

		// inform the app that the app has successfully disconnected
		broadcastServiceStatus("Disconnected");

		// try not to leak the listener
		if (dataEnabledReceiver != null) {
			unregisterReceiver(dataEnabledReceiver);
			dataEnabledReceiver = null;
		}

		if (statusUpdateIntentReceiver != null) {

			unregisterReceiver(statusUpdateIntentReceiver);
			statusUpdateIntentReceiver = null;
		}

		if (messageIntentReceiver != null) {

			unregisterReceiver(messageIntentReceiver);
			messageIntentReceiver = null;
		}

		if (mBinder != null) {
			mBinder.close();
			mBinder = null;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public int onStartCommand(final Intent intent, int flags, final int startId) {

		if (!isAlreadyConnected()) {
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
					handleStart(intent, startId);
			// }
			// }, "MQTTservice").start();
		}

		if (intent.getExtras() != null) {
			Bundle extras = intent.getExtras();
			publishMessage(extras);
		}

		// return START_NOT_STICKY - we want this Service to be left
		// running
		// unless explicitly stopped, and it's process is killed, we
		// want it to
		// be restarted

		return START_STICKY;
	}

	private String generateClientId() {
		// generate a unique client id if we haven't done so before, otherwise
		// re-use the one we already have

		if (mqttClientId == null) {
			// generate a unique client ID - I'm basing this on a combination of
			// the phone device id and the current timestamp
			String timestamp = "" + (new Date()).getTime();
			String android_id = Settings.System.getString(getContentResolver(),
					Secure.ANDROID_ID);
			mqttClientId = timestamp + android_id;

			// truncate - MQTT spec doesn't allow client ids longer than 23
			// chars
			if (mqttClientId.length() > MAX_MQTT_CLIENTID_LENGTH) {
				mqttClientId = mqttClientId.substring(0,
						MAX_MQTT_CLIENTID_LENGTH);
			}
		}

		return mqttClientId;
	}
	
	private synchronized void handleStart(Intent intent, int startid) {

		// before we start - check for a couple of reasons why we should stop

		if (mqttClient == null) {
			// we were unable to define the MQTT client connection, so we stop
			// immediately - there is nothing that we can do
			stopSelf();
			return;
		}

		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		if (cm.getBackgroundDataSetting() == false) // respect the user's
													// request not to use data!
		{
			// user has disabled background data
			connectionStatus = MQTTConnectionStatus.NOTCONNECTED_DATADISABLED;

			// update the app to show that the connection has been disabled
			broadcastServiceStatus("Not connected - background data disabled");

			// we have a listener running that will notify us when this
			// preference changes, and will call handleStart again when it
			// is - letting us pick up where we leave off now
			return;
		}

		// set the status to show we're trying to connect
		connectionStatus = MQTTConnectionStatus.CONNECTING;

		// we are creating a background service that will run forever until
		// the user explicity stops it. so - in case they start needing
		// to save battery life - we should ensure that they don't forget
		// we're running, by leaving an ongoing notification in the status
		// bar while we are running
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.icon, "MQTT",
				System.currentTimeMillis());
		notification.flags |= Notification.FLAG_ONGOING_EVENT;
		notification.flags |= Notification.FLAG_NO_CLEAR;
		Intent notificationIntent = new Intent(this, MQTTNotifierActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(this, "MQTT",
				"MQTT Service is running", contentIntent);
		nm.notify(MQTT_NOTIFICATION_ONGOING, notification);

		// before we attempt to connect - we check if the phone has a
		// working data connection
		if (isOnline()) {
			// we think we have an Internet connection, so try to connect
			// to the message broker
			if (connectToBroker()) {
				// we subscribe to a topic - registering to receive push
				// notifications with a particular key
				// in a 'real' app, you might want to subscribe to multiple
				// topics - I'm just subscribing to one as an example
				// note that this topicName could include a wildcard, so
				// even just with one subscription, we could receive
				// messages for multiple topics
				subscribeToTopic(topicName);
			}
		} else {
			// we can't do anything now because we don't have a working
			// data connection
			connectionStatus = MQTTConnectionStatus.NOTCONNECTED_WAITINGFORINTERNET;

			// inform the app that we are not connected
			broadcastServiceStatus("Waiting for network connection");
		}

		// changes to the phone's network - such as bouncing between WiFi
		// and mobile data networks - can break the MQTT connection
		// the MQTT connectionLost can be a bit slow to notice, so we use
		// Android's inbuilt notification system to be informed of
		// network changes - so we can reconnect immediately, without
		// haing to wait for the MQTT timeout
		if (netConnReceiver == null) {
			netConnReceiver = new NetworkConnectionIntentReceiver();
			registerReceiver(netConnReceiver, new IntentFilter(
					ConnectivityManager.CONNECTIVITY_ACTION));

		}

		// creates the intents that are used to wake up the phone when it is
		// time to ping the server
		if (pingSender == null) {
			pingSender = new PingSender();
			registerReceiver(pingSender, new IntentFilter(MQTT_PING_ACTION));
		}

	}

	private void publishMessage(Bundle extras) {
		String topic = extras.getString(TOPIC_KEY);
		byte[] messagebody = extras.getByteArray(MESSAGE_KEY);

		if (topic == null || messagebody == null) {
			Log.w(MessagingService.class.getSimpleName(),
					"Invalid message format");
			return;
		}

		try {
			mqttClient.publish(topic, messagebody, qualityOfService, true);
		} catch (MqttNotConnectedException e) {
			Log.e(MessagingService.class.getSimpleName(), e.getMessage());
			e.printStackTrace();
		} catch (MqttPersistenceException e) {
			Log.e(MessagingService.class.getSimpleName(), e.getMessage());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			Log.e(MessagingService.class.getSimpleName(), e.getMessage());
			e.printStackTrace();
		} catch (MqttException e) {
			Log.e(MessagingService.class.getSimpleName(), e.getMessage());
			e.printStackTrace();
		}
	}

	private void loadMessageBrokerConfigSettings() {

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);

		brokerHostName = preferences.getString("broker_hostname_key",
				"192.168.1.23");
		brokerHostPort = preferences.getInt("broker_port_key", 1883);
		keepAlive = new Short(preferences.getString("broker_keepalive_key",
				"1200"));
		topicName = preferences.getString("broker_hostname_key",
		"com/thintrack/sensor/bustrack");
	}

	private void createMqttClient() {

		String mqttConnSpec = "tcp://" + brokerHostName + ":" + brokerHostPort;
		try {

			// define the connection to the broker
			mqttClient = MqttClient.createMqttClient(mqttConnSpec, this);
			// register this client app has being able to receive messages
			mqttClient.registerSimpleHandler(this);

		} catch (MqttException e) {

			Log.e(MessagingService.class.getSimpleName(),
					"Failure creating the MQTT Client");

			// something went wrong!
			mqttClient = null;
			connectionStatus = MQTTConnectionStatus.NOTCONNECTED_UNKNOWNREASON;

			//
			// inform the app that we failed to connect so that it can update
			// the UI accordingly
			broadcastServiceStatus("Invalid connection parameters");

			//
			// inform the user (for times when the Activity UI isn't running)
			// that we failed to connect
			notifyUser("Unable to connect", "MQTT", "Unable to connect");
		}
	}

	private boolean connectToBroker() {

		String clientId = generateClientId();

		// try to connect
		try {
			mqttClient.connect(clientId, cleanStart, keepAlive);

			// inform the app that the app has successfully connected
			broadcastServiceStatus("Connected");

			// we are connected
			connectionStatus = MQTTConnectionStatus.CONNECTED;

			// we need to wake up the phone's CPU frequently enough so that the
			// keep alive messages can be sent
			// we schedule the first one of these now
			scheduleNextPing();

		} catch (MqttException e) {
			// something went wrong!

			Log.e(MessagingService.class.getSimpleName(), e.getMessage());

			connectionStatus = MQTTConnectionStatus.NOTCONNECTED_UNKNOWNREASON;

			//
			// inform the app that we failed to connect so that it can update
			// the UI accordingly
			broadcastServiceStatus("Unable to connect");

			//
			// inform the user (for times when the Activity UI isn't running)
			// that we failed to connect
			notifyUser("Unable to connect", "MQTT",
					"Unable to connect - will retry later");

			// if something has failed, we wait for one keep-alive period before
			// trying again
			// in a real implementation, you would probably want to keep count
			// of how many times you attempt this, and stop trying after a
			// certain number, or length of time - rather than keep trying
			// forever.
			// a failure is often an intermittent network issue, however, so
			// some limited retry is a good idea
			scheduleNextPing();

			return false;
		}

		return true;
	}

	/*
	 * Terminates a connection to the message broker.
	 */
	private void disconnectFromBroker() {
		// if we've been waiting for an Internet connection, this can be
		// cancelled - we don't need to be told when we're connected now
		try {
			if (netConnReceiver != null) {
				unregisterReceiver(netConnReceiver);
				netConnReceiver = null;
			}

			if (pingSender != null) {
				unregisterReceiver(pingSender);
				pingSender = null;
			}
		} catch (Exception eee) {
			// probably because we hadn't registered it
			Log.e("mqtt", "unregister failed", eee);
		}

		try {
			if (mqttClient != null) {
				mqttClient.disconnect();
			}
		} catch (MqttPersistenceException e) {
			Log.e("mqtt", "disconnect failed - persistence exception", e);
		} finally {
			mqttClient = null;
		}

		// we can now remove the ongoing notification that warns users that
		// there was a long-running ongoing service running
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nm.cancelAll();
	}

	/*
	 * Send a request to the message broker to be sent messages published with
	 * the specified topic name. Wildcards are allowed.
	 */
	private void subscribeToTopic(String topicName) {
		boolean subscribed = false;

		if (isAlreadyConnected() == false) {
			// quick sanity check - don't try and subscribe if we
			// don't have a connection

			Log.e("mqtt", "Unable to subscribe as we are not connected");
		} else {
			try {
				
				//concat the client id
				topicName += mqttClientId;
				String[] topics = { topicName };
				int[] qualitiesOfService = new int[] { qualityOfService };
				mqttClient.subscribe(topics, qualitiesOfService);

				subscribed = true;
			} catch (MqttNotConnectedException e) {
				Log.e("mqtt", "subscribe failed - MQTT not connected", e);
			} catch (IllegalArgumentException e) {
				Log.e("mqtt", "subscribe failed - illegal argument", e);
			} catch (MqttException e) {
				Log.e("mqtt", "subscribe failed - MQTT exception", e);
			}
		}

		if (subscribed == false) {
			//
			// inform the app of the failure to subscribe so that the UI can
			// display an error
			broadcastServiceStatus("Unable to subscribe");

			//
			// inform the user (for times when the Activity UI isn't running)
			notifyUser("Unable to subscribe", "MQTT", "Unable to subscribe");
		}
	}

	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isAvailable()
				&& cm.getActiveNetworkInfo().isConnected()) {
			return true;
		}

		return false;
	}

	/*
	 * Checks if the MQTT client thinks it has an active connection
	 */
	private boolean isAlreadyConnected() {
		return ((mqttClient != null) && (mqttClient.isConnected() == true));
	}

	/*
	 * Schedule the next time that you want the phone to wake up and ping the
	 * message broker server
	 */
	private void scheduleNextPing() {
		// When the phone is off, the CPU may be stopped. This means that our
		// code may stop running.
		// When connecting to the message broker, we specify a 'keep alive'
		// period - a period after which, if the client has not contacted
		// the server, even if just with a ping, the connection is considered
		// broken.
		// To make sure the CPU is woken at least once during each keep alive
		// period, we schedule a wake up to manually ping the server
		// thereby keeping the long-running connection open
		// Normally when using this Java MQTT client library, this ping would be
		// handled for us.
		// Note that this may be called multiple times before the next scheduled
		// ping has fired. This is good - the previously scheduled one will be
		// cancelled in favour of this one.
		// This means if something else happens during the keep alive period,
		// (e.g. we receive an MQTT message), then we start a new keep alive
		// period, postponing the next ping.

		PendingIntent pendingIntent = PendingIntent
				.getBroadcast(this, 0, new Intent(MQTT_PING_ACTION),
						PendingIntent.FLAG_UPDATE_CURRENT);

		// in case it takes us a little while to do this, we try and do it
		// shortly before the keep alive period expires
		// it means we're pinging slightly more frequently than necessary
		Calendar wakeUpTime = Calendar.getInstance();
		wakeUpTime.add(Calendar.SECOND, keepAlive);

		AlarmManager aMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		aMgr.set(AlarmManager.RTC_WAKEUP, wakeUpTime.getTimeInMillis(),
				pendingIntent);
	}

	/************************************************************************/
	/* METHODS - broadcasts and notifications */
	/************************************************************************/

	// methods used to notify the Activity UI of something that has happened
	// so that it can be updated to reflect status and the data received
	// from the server

	private void broadcastServiceStatus(String statusDescription) {
		// inform the app (for times when the Activity UI is running /
		// active) of the current MQTT connection status so that it
		// can update the UI accordingly
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(MQTT_STATUS_INTENT);
		broadcastIntent.putExtra(MQTT_STATUS_MSG, statusDescription);
		sendBroadcast(broadcastIntent);
	}

	private void broadcastReceivedMessage(String topic, String message) {
		// pass a message received from the MQTT server on to the Activity UI
		// (for times when it is running / active) so that it can be displayed
		// in the app GUI
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(MQTT_MSG_RECEIVED_INTENT);
		broadcastIntent.putExtra(MQTT_MSG_RECEIVED_TOPIC, topic);
		broadcastIntent.putExtra(MQTT_MSG_RECEIVED_MSG, message);
		sendBroadcast(broadcastIntent);
	}

	// methods used to notify the user of what has happened for times when
	// the app Activity UI isn't running

	private void notifyUser(String alert, String title, String body) {
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.icon, alert,
				System.currentTimeMillis());
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.ledARGB = Color.MAGENTA;
		Intent notificationIntent = new Intent(this, MQTTNotifierActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(this, title, body, contentIntent);
		nm.notify(MQTT_NOTIFICATION_UPDATE, notification);
	}

	private void sendMessageNotification(String messageBody) {
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		Notification notification = new Notification(R.drawable.icon,
				"Bustrack", System.currentTimeMillis());

		notification.defaults |= Notification.DEFAULT_ALL;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.flags |= Notification.FLAG_INSISTENT;

		Intent intent = new Intent(this, MainActivity.class);
		PendingIntent activity = PendingIntent.getActivity(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		notification.setLatestEventInfo(this, "Oficina", messageBody, activity);
		notificationManager.notify(0, notification);
	}

	/***********************************************************/
	/* MQTT CALLBACK METHODS */
	/***********************************************************/

	/*
	 * callback - method called when we no longer have a connection to the
	 * message broker server
	 */
	@Override
	public void connectionLost() throws Exception {

		// we protect against the phone switching off while we're doing this
		// by requesting a wake lock - we request the minimum possible wake
		// lock - just enough to keep the CPU running until we've finished
		PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
		WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MQTT");
		wl.acquire();

		//
		// have we lost our data connection?
		//

		if (isOnline() == false) {
			connectionStatus = MQTTConnectionStatus.NOTCONNECTED_WAITINGFORINTERNET;

			// inform the app that we are not connected any more
			broadcastServiceStatus("Connection lost - no network connection");

			//
			// inform the user (for times when the Activity UI isn't running)
			// that we are no longer able to receive messages
			notifyUser("Connection lost - no network connection", "MQTT",
					"Connection lost - no network connection");

			//
			// wait until the phone has a network connection again, when we
			// the network connection receiver will fire, and attempt another
			// connection to the broker
		} else {
			//
			// we are still online
			// the most likely reason for this connectionLost is that we've
			// switched from wifi to cell, or vice versa
			// so we try to reconnect immediately
			//

			connectionStatus = MQTTConnectionStatus.NOTCONNECTED_UNKNOWNREASON;

			// inform the app that we are not connected any more, and are
			// attempting to reconnect
			broadcastServiceStatus("Connection lost - reconnecting...");

			// try to reconnect
			if (connectToBroker()) {
				subscribeToTopic(topicName);
			}
		}

		// we're finished - if the phone is switched off, it's okay for the CPU
		// to sleep now
		wl.release();

	}

	/*
	 * callback - called when we receive a message from the server
	 */
	@Override
	public void publishArrived(String topic, byte[] payloadbytes, int qos,
			boolean retained) throws Exception {

		// we protect against the phone switching off while we're doing this
		// by requesting a wake lock - we request the minimum possible wake
		// lock - just enough to keep the CPU running until we've finished
		PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
		WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MQTT");
		wl.acquire();

		//
		// I'm assuming that all messages I receive are being sent as strings
		// this is not an MQTT thing - just me making as assumption about what
		// data I will be receiving - your app doesn't have to send/receive
		// strings - anything that can be sent as bytes is valid
		String messageBody = new String(payloadbytes);

		//
		// for times when the app's Activity UI is not running, the Service
		// will need to safely store the data that it receives and notify the
		// user
		boolean isAppForegrounded = new ForegroundCheckTask().execute(this)
				.get();

		if (isAppForegrounded) {

			// this is a new message - a value we haven't seen before

			//
			// inform the app (for times when the Activity UI is running) of
			// the
			// received message so the app UI can be updated with the new
			// data
			broadcastReceivedMessage(topic, messageBody);

			//
			// inform the user (for times when the Activity UI isn't
			// running)
			// that there is new data available
			notifyUser("New data received", topic, messageBody);

		}
		// Send notification
		else {

			sendMessageNotification(messageBody);

		}

		// receiving this message will have kept the connection alive for us, so
		// we take advantage of this to postpone the next scheduled ping
		scheduleNextPing();

		// we're finished - if the phone is switched off, it's okay for the CPU
		// to sleep now
		wl.release();

	}

	/***********************************************************/
	/* MQTT PERSISTENCE METHODS */
	/***********************************************************/
	@Override
	public void addReceivedMessage(int arg0, byte[] arg1)
			throws MqttPersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addSentMessage(int arg0, byte[] arg1)
			throws MqttPersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delReceivedMessage(int arg0) throws MqttPersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delSentMessage(int arg0) throws MqttPersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public byte[][] getAllReceivedMessages() throws MqttPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[][] getAllSentMessages() throws MqttPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void open(String arg0, String arg1) throws MqttPersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() throws MqttPersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updSentMessage(int arg0, byte[] arg1)
			throws MqttPersistenceException {
		// TODO Auto-generated method stub

	}

	/***********************************************************/
	/* INNER CLASSES */
	/***********************************************************/

	/*
	 * Called in response to a change in network connection - after losing a
	 * connection to the server, this allows us to wait until we have a usable
	 * data connection again
	 */
	private class NetworkConnectionIntentReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context ctx, Intent intent) {
			// we protect against the phone switching off while we're doing this
			// by requesting a wake lock - we request the minimum possible wake
			// lock - just enough to keep the CPU running until we've finished
			PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
			WakeLock wl = powerManager.newWakeLock(
					PowerManager.PARTIAL_WAKE_LOCK, "MQTT");
			wl.acquire();

			if (isOnline()) {
				// we have an internet connection - have another try at
				// connecting
				if (connectToBroker()) {
					// we subscribe to a topic - registering to receive push
					// notifications with a particular key
					subscribeToTopic(topicName);
				}
			}

			// we're finished - if the phone is switched off, it's okay for the
			// CPU
			// to sleep now
			wl.release();
		}
	}

	/*
	 * Used to implement a keep-alive protocol at this Service level - it sends
	 * a PING message to the server, then schedules another ping after an
	 * interval defined by keepAliveSeconds
	 */
	public class PingSender extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// Note that we don't need a wake lock for this method (even though
			// it's important that the phone doesn't switch off while we're
			// doing this).
			// According to the docs, "Alarm Manager holds a CPU wake lock as
			// long as the alarm receiver's onReceive() method is executing.
			// This guarantees that the phone will not sleep until you have
			// finished handling the broadcast."
			// This is good enough for our needs.

			try {
				mqttClient.ping();
			} catch (MqttException e) {
				// if something goes wrong, it should result in connectionLost
				// being called, so we will handle it there
				Log.e("mqtt", "ping failed - MQTT exception", e);

				// assume the client connection is broken - trash it
				try {
					mqttClient.disconnect();
				} catch (MqttPersistenceException e1) {
					Log.e("mqtt", "disconnect failed - persistence exception",
							e1);
				}

				// reconnect
				if (connectToBroker()) {
					subscribeToTopic(topicName);
				}
			}

			// start the next keep alive period
			scheduleNextPing();
		}
	}

	public class BackgroundDataChangeIntentReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context ctx, Intent intent) {
			// we protect against the phone switching off while we're doing this
			// by requesting a wake lock - we request the minimum possible wake
			// lock - just enough to keep the CPU running until we've finished
			PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
			WakeLock wl = pm
					.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MQTT");
			wl.acquire();

			ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
			if (cm.getBackgroundDataSetting()) {
				// user has allowed background data - we start again - picking
				// up where we left off in handleStart before
				connectToBroker();
				handleStart(intent, 0);
			} else {
				// user has disabled background data
				connectionStatus = MQTTConnectionStatus.NOTCONNECTED_DATADISABLED;

				// update the app to show that the connection has been disabled
				broadcastServiceStatus("Not connected - background data disabled");

				// disconnect from the broker
				disconnectFromBroker();
			}

			// we're finished - if the phone is switched off, it's okay for the
			// CPU
			// to sleep now
			wl.release();
		}
	}

	public class StatusUpdateReceiver extends BroadcastReceiver {
		@SuppressWarnings("unused")
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle notificationData = intent.getExtras();
			String newStatus = notificationData
					.getString(MessagingService.MQTT_STATUS_MSG);

		}
	}

	private class MQTTMessageReceiver extends BroadcastReceiver {
		@SuppressWarnings("unused")
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle notificationData = intent.getExtras();
			
			String newTopic = notificationData
					.getString(MessagingService.MQTT_MSG_RECEIVED_TOPIC);
			String newData = notificationData
					.getString(MessagingService.MQTT_MSG_RECEIVED_MSG);

		}
	}

	public class LocalBinder<S> extends Binder {
		private WeakReference<S> mService;

		public LocalBinder(S service) {
			mService = new WeakReference<S>(service);
		}

		public S getService() {
			return mService.get();
		}

		public void close() {
			mService = null;
		}
	}

}
