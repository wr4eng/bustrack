package com.thingtrack.bustrack.view.mobile.android.activity;


import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;

import com.thingtrack.bustrack.view.mobile.android.R;
import com.thingtrack.bustrack.view.mobile.android.addon.ActionBar;
import com.thingtrack.bustrack.view.mobile.android.addon.ActionBar.IntentAction;

public class SettingsActivity extends PreferenceActivity {
	private ActionBar actionBar;
	
	 @Override
	  protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 
		 Log.i(getClass().getSimpleName(), "onCreate");
		 
		 addPreferencesFromResource(R.xml.settings);
		 setContentView(R.layout.settings);
         		 
	     // create native action bar
	     loadActionBar();
	     
	 }
	 	 
	 private void loadActionBar() {
	     actionBar = (ActionBar) findViewById(R.id.actionbar);
		     	     
	     actionBar.setHomeAction(new IntentAction(this, MainActivity.createIntent(this), R.drawable.ic_title_home_default));
         actionBar.setDisplayHomeAsUpEnabled(true);
         
	 }
	 
}
