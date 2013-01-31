package com.thingtrack.bustrack.view.mobile.workbench;

import java.io.Serializable;
import java.util.Map;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.ClientWidget;

import com.thingtrack.bustrack.view.mobile.workbench.gwt.client.VJsCallback;
/**
 * Server side component for the VMyComponent widget.
 */
@SuppressWarnings("serial")
@ClientWidget(VJsCallback.class)
public class JsCallback extends AbstractComponent {
	private String locationMac = null;
	private String telemetryMac = null;
	
	private JsCallbackLocationListener listenerJsCallbackLocation;
	private JsCallbackTelemetryListener listenerJsCallbackTelemetry;
	
	@Override
    public void paintContent(PaintTarget target) throws PaintException {
        super.paintContent(target);

    }


    /** Deserialize changes received from client. */
    @SuppressWarnings("rawtypes")
	@Override
    public void changeVariables(Object source, Map variables) {
    	
        // Sets the currently selected color
        if (variables.containsKey("locationMac")) { 
        	locationMac = (String) variables.get("locationMac");
        	
        	if (this.listenerJsCallbackLocation != null)
        		this.listenerJsCallbackLocation.JsCallback(new CallbackLocationEvent(locationMac));
        }
        
        if (variables.containsKey("telemetryMac")) { 
        	telemetryMac = (String) variables.get("telemetryMac");
        	
        	if (this.listenerJsCallbackTelemetry != null)
        		this.listenerJsCallbackTelemetry.JsCallback(new CallbackTelemetryEvent(telemetryMac));
        }                
    	
    }
    
	public void addJsCallbackLocationListener(JsCallbackLocationListener listener) {
		this.listenerJsCallbackLocation = listener;
		
	}
	
	public void addJsCallbackTelemetryListener(JsCallbackTelemetryListener listener) {
		this.listenerJsCallbackTelemetry = listener;
		
	}
	
	public interface JsCallbackLocationListener extends Serializable {
        public void JsCallback(CallbackLocationEvent event);

    }
	
	public interface JsCallbackTelemetryListener extends Serializable {
        public void JsCallback(CallbackTelemetryEvent event);

    }
	
    public static class CallbackLocationEvent {
		private String locationMac;
		
		public CallbackLocationEvent(String locationMac) {			
			this.locationMac = locationMac;
			
		}
		
		/**
		 * @param locationMac the locationMac to set
		 */
		public void setLocationMac(String locationMac) {
			this.locationMac = locationMac;
		}

		/**
		 * @return the locationMac
		 */
		public String getLocationMac() {
			return locationMac;
		}

    }
    
    public static class CallbackTelemetryEvent {
		private String telemetryMac;
		
		public CallbackTelemetryEvent(String telemetryMac) {			
			this.setTelemetryMac(telemetryMac);
			
		}

		/**
		 * @param telemetryMac the telemetryMac to set
		 */
		public void setTelemetryMac(String telemetryMac) {
			this.telemetryMac = telemetryMac;
		}

		/**
		 * @return the telemetryMac
		 */
		public String getTelemetryMac() {
			return telemetryMac;
		}

    }    
}
