/*
 * Copyright 2009 IT Mill Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.thingtrack.bustrack.view.mobile.workbench;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import com.github.wolfie.sessionguard.SessionGuard;
import com.thingtrack.bustrack.service.api.RouteService;
import com.thingtrack.bustrack.service.api.StopService;
import com.thingtrack.bustrack.service.api.VehicleService;
import com.thingtrack.bustrack.service.api.WorksheetService;
import com.thingtrack.bustrack.view.mobile.workbench.ui.BustrackHierarchyManager;
import com.thingtrack.bustrack.view.mobile.workbench.ui.LogonWindow;
import com.thingtrack.konekti.service.api.EmployeeAgentService;
import com.thingtrack.konekti.service.sensor.api.SensorLocationService;
import com.thingtrack.konekti.service.sensor.api.SensorMessageService;
import com.thingtrack.konekti.service.sensor.api.SensorTelemetryService;
import com.thingtrack.konekti.service.api.UserService;
import com.thingtrack.konekti.service.security.SecurityService;
import com.vaadin.terminal.gwt.server.WebBrowser;

import com.thingtrack.com.vaadin.addons.springstuff.mobile.TouchKitSpringContextApplication;

/**
 * The Application's "main" class
 */
@Configurable(autowire = Autowire.BY_TYPE)
@Scope("session")
@SuppressWarnings("serial")
public class Main extends TouchKitSpringContextApplication {	
	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private SensorLocationService sensorLocationService;
	
	@Autowired
	private SensorTelemetryService sensorTelemetryService;

	@Autowired
	private SensorMessageService sensorMessageService;
	
	@Autowired
	private EmployeeAgentService employeeAgentService;
	
	@Autowired
	private WorksheetService worksheetService;

	@Autowired
	private VehicleService vehicleService;

	@Autowired
	private RouteService routeService;

	@Autowired
	private StopService stopService;
	
    static CustomizedSystemMessages customizedSystemMessages = new CustomizedSystemMessages();

    private static final int WARNING_PERIOD_MINS = 2;
    private boolean keepalive = true;
    
    private SessionGuard sessionGuard;
    
    /**
     * Make application reload itself when the session has expired. Our demo app
     * gains nothing for showing session expired message.
     * 
     * @see TouchKitApplication#getSystemMessages()
     */
    static {
        // reload on session expired
        customizedSystemMessages.setSessionExpiredCaption(null);
        customizedSystemMessages.setSessionExpiredMessage(null);
    	customizedSystemMessages.setSessionExpiredURL(null);
    	customizedSystemMessages.setSessionExpiredNotificationEnabled(false);
    }

    public static SystemMessages getSystemMessages() {
        return customizedSystemMessages;
    }
    
    @Override
    public void initSpringApplication(ConfigurableWebApplicationContext context) {
        setMainWindow(new LogonWindow());
                        
		// Using mobile mail theme
        setTheme("konekti");      
		
        // session guard for unlimmited session
        //sessionGuard = new SessionGuard();
        //sessionGuard.setTimeoutWarningPeriod(WARNING_PERIOD_MINS);
        //sessionGuard.setKeepalive(keepalive);
        
        //getMainWindow().addComponent(sessionGuard);
        //getMainWindow().setPersistentSessionCookie(true);
        
        // get telemetry data
        //getMainWindow().executeJavaScript("CONFIGURE.getTelemetryMac();");
		
		// get location data
        //getMainWindow().executeJavaScript("CONFIGURE.getLocationMac();");
        
        // set Services context
        setServices();
        
    }
        
	@Override
	public void onBrowserDetailsReady(ConfigurableWebApplicationContext arg0) {
	       WebBrowser browser = getBrowser();
	        if (!browser.isTouchDevice())        	
	            getMainWindow().showNotification("You appear to be running on a desktop software or other non touch device. We'll show you the tablet (or smartphone view if small screen size) for debug purposess.");        

	        if (isSmallScreenDevice()) {        	
	            getMainWindow().setContent(new BustrackHierarchyManager());
	        }
	        else {
	            //getMainWindow().setContent(new TabletMainView());	        	        
	        	getMainWindow().setContent(new BustrackHierarchyManager());
	        }
		
	}

    public boolean isSmallScreenDevice() {
        float viewPortWidth = getMainWindow().getWidth();
        return viewPortWidth < 600;
    }
    
    private void setServices() {
    	// Sensor Context
    	ContextServices.setSensorLocationService(sensorLocationService);
    	ContextServices.setSensorTelemetryService(sensorTelemetryService);
    	ContextServices.setSensorMessageService(sensorMessageService);

    	// Security Context
    	ContextServices.setSecurityService(securityService);
    	ContextServices.setUserService(userService);
    	
    	// Worksheet Context
    	ContextServices.setEmployeeAgentService(employeeAgentService);
    	ContextServices.setWorksheetService(worksheetService);
    	ContextServices.setVehicleService(vehicleService);
    	ContextServices.setRouteService(routeService);
    	ContextServices.setStopService(stopService);
    	
    }
    
}
