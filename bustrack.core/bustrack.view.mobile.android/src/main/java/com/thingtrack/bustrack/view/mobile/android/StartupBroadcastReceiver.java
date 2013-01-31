package com.thingtrack.bustrack.view.mobile.android;

import com.thingtrack.bustrack.view.mobile.android.service.MessagingService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartupBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		Log.d(StartupBroadcastReceiver.class.getSimpleName(), "Starting Messaging Services");
		
		if(!Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))
				return;
		
		Intent messagingServiceIntent = new Intent(context, MessagingService.class);
		
		context.startService(messagingServiceIntent);
		
		Log.d(StartupBroadcastReceiver.class.getSimpleName(), "Started Messaging Services");

	}

}