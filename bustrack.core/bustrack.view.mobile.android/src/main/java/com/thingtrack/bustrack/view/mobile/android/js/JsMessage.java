package com.thingtrack.bustrack.view.mobile.android.js;

import com.thingtrack.bustrack.view.mobile.android.context.ContextApp;
import com.thingtrack.konekti.domain.mobile.config.MessageConfigure;

import android.content.Context;
import android.content.Intent;

public class JsMessage extends JsInterface implements IJSInterface {
	private final String name = "MESSAGE";
	public static final String PREFIX_TOPIC = "com/thingtrack/konekti/sensor/message/mobile/";
	
	private MessageConfigure messageConfigure;
    @SuppressWarnings("unused")
	private Intent messageTrackingIntent;
    
	public JsMessage(Context context) {
		super(context);	
		
	}

	@Override
	public String getInterfaceName() {
		return this.name;
		
	}
	
	public MessageConfigure getMessageConfigure() {
		return messageConfigure;
		
	}
	
	public void init(boolean active, String deviceName, String mac, String host, int port, int keepAlive,  int qualityOfService, String topic) {
		messageConfigure = new MessageConfigure(active, deviceName, mac, host, port, keepAlive, qualityOfService, topic);
		
		ContextApp.setMessageConfigure(messageConfigure);
	}
	
	public void receiveMessage(String message) {
		
	}
	
	public void sendMessage(String message) {
		
	}
	
}
