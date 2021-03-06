package com.thingtrack.bustrack.view.mobile.workbench.gwt.client;

import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;

public class VJsCallback extends Widget implements Paintable {

    /** Set the CSS class name to allow styling. */
    public static final String CLASSNAME = "v-jscallback";

    /** The client side widget identifier */
    protected String paintableId;

    /** Reference to the server connection object. */
    ApplicationConnection client;

    /**
     * The constructor should first call super() to initialize the component and
     * then handle any initialization relevant to Vaadin.
     */
    public VJsCallback() {      
        // Change to proper element or remove if extending another widget
        setElement(Document.get().createDivElement());
        
        // This method call of the Paintable interface sets the component
        // style name in DOM tree
        setStyleName(CLASSNAME);
       
    }

    /**
     * Called whenever an update is received from the server 
     */
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        // This call should be made first. 
        // It handles sizes, captions, tooltips, etc. automatically.
        if (client.updateComponent(this, uidl, true)) {
            // If client.updateComponent returns true there has been no changes and we
            // do not need to update anything.
            return;
        }

        // Save reference to server connection object to be able to send
        // user interaction later
        this.client = client;

        // Save the client side identifier (paintable id) for the widget
        paintableId = uidl.getId();
        
        exportGetLocationMac(this);
        exportGetTelemetryMac(this);
    }

    // Expose the following method into JavaScript.
    private void getTelemetryMac(String telemetryMac) {
    	this.client.updateVariable(paintableId, "telemetryMac", telemetryMac, true);
    	
    }
    
    // It is only used to create a reference in the browser to the final method
    private native void exportGetTelemetryMac(VJsCallback instance) /*-{
      $wnd.getMacTelemetry = function(telemetryMac) {
         instance.@com.thingtrack.bustrack.view.mobile.workbench.gwt.client.VJsCallback::getTelemetryMac(Ljava/lang/String;) (telemetryMac);
      };
    }-*/;
    
    // Expose the following method into JavaScript.
    private void getLocationMac(String locationMac) {
    	this.client.updateVariable(paintableId, "locationMac", locationMac, true);
    	
    }
    
    // It is only used to create a reference in the browser to the final method
    private native void exportGetLocationMac(VJsCallback instance) /*-{
      $wnd.getMacLocation = function(locationMac) {
         instance.@com.thingtrack.bustrack.view.mobile.workbench.gwt.client.VJsCallback::getLocationMac(Ljava/lang/String;) (locationMac);
      };
    }-*/;

}
