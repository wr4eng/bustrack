package com.thingtrack.bustrack.view.mobile.workbench;

import com.thingtrack.bustrack.view.mobile.workbench.JsCallback.CallbackLocationEvent;
import com.thingtrack.bustrack.view.mobile.workbench.JsCallback.JsCallbackLocationListener;
import com.vaadin.addon.touchkit.ui.TouchKitApplication;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class MyVaadinApplication extends TouchKitApplication
{
	private static final String MAC = "00-B0-D0-86-BB-F7";
	
	private JsCallback jsCallback;
	private Button button;
	
	@Override
    public void onBrowserDetailsReady() {
		// add callback js component
    	jsCallback = new JsCallback();
    	jsCallback.addJsCallbackLocationListener(new JsCallbackLocationListener() {
			
			public void JsCallback(CallbackLocationEvent event) {
				getMainWindow().showNotification(event.getLocationMac());
				
			}
		});
    	
    	getMainWindow().addComponent(jsCallback);
    	
    	// add execute js script button
        button = new Button("Click Me");
        button.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                getMainWindow().executeJavaScript("getMac(\""  + MAC + "\");");
                
            }
        });
        
        getMainWindow().addComponent(button);
    }

    
}
