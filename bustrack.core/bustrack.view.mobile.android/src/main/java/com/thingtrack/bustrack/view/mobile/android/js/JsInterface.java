package com.thingtrack.bustrack.view.mobile.android.js;

import android.content.Context;

public abstract class JsInterface {
	private Context context;
	
	private boolean active=true;
	
	public JsInterface (Context context) {
        this.context = context;
    }

	/**
	 * @param context the context to set
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * @return the context
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

}
