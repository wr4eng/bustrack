package com.thingtrack.bustrack.view.mobile.workbench.ui;

import com.thingtrack.bustrack.view.mobile.workbench.ContextApp;
import com.thingtrack.bustrack.view.mobile.workbench.ContextServices;
import com.thingtrack.bustrack.view.mobile.workbench.JsCallback;
import com.thingtrack.bustrack.view.mobile.workbench.JsCallback.CallbackLocationEvent;
import com.thingtrack.bustrack.view.mobile.workbench.JsCallback.CallbackTelemetryEvent;
import com.thingtrack.bustrack.view.mobile.workbench.JsCallback.JsCallbackLocationListener;
import com.thingtrack.bustrack.view.mobile.workbench.JsCallback.JsCallbackTelemetryListener;
import com.thingtrack.konekti.domain.EmployeeAgent;
import com.thingtrack.konekti.domain.sensor.Sensor;
import com.thingtrack.konekti.domain.sensor.SensorLocation;
import com.thingtrack.konekti.domain.sensor.SensorMessage;
import com.thingtrack.konekti.domain.sensor.SensorTelemetry;
import com.thingtrack.konekti.domain.User;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class LogonView extends NavigationView {
	private static final long serialVersionUID = 1L;
	
	private BustrackHierarchyManager nav;
	
	private TextField txtUsername;
	private TextField txtPassword;
	private Button logon;
	
	private User userAuthenticated = null;
	private User user = null;
	
	private EmployeeAgent employeeAgent = null;
	
	private VerticalComponentGroup logonGroup = new VerticalComponentGroup();
	
	@SuppressWarnings({ "serial", "deprecation" })
	public LogonView(final BustrackHierarchyManager nav) {		
        setCaption("Inicio sesión");
        setWidth("100%");
        setHeight("100%");

        this.nav = nav;
        
        // login CSS layout
        CssLayout root = new CssLayout();
        root.setWidth("100%");
        root.setMargin(true);        
        
        // add Javascript callback for mac mobile attribute
        JsCallback jsMacCallback = new JsCallback();
        jsMacCallback.addJsCallbackTelemetryListener(new JsCallbackTelemetryListener() {			
			@Override
			public void JsCallback(CallbackTelemetryEvent event) {
				boolean active = false;
				String jsScript;
				
				String telemetryMac = event.getTelemetryMac();
				
				try {
					// get Location Sensor Configuration from Wifi Mac address					
					SensorTelemetry sensorTelemetry = ContextServices.getSensorTelemetryService().getByMac(telemetryMac);
					
					// set Sensor Location Context
					ContextApp.setSensorTelemetry(sensorTelemetry);
					
					if (sensorTelemetry.getSensorStatus().getCode().equals(Sensor.STATUS.ACTIVE.name()))
						active = true;
					else
						active = false;
					
					
					jsScript  = "TELEMETRY.init(" + active;
					jsScript += ", \"" + sensorTelemetry.getMac() + "\"";
					jsScript += ", \"" + sensorTelemetry.getMessageBrokerHost() + "\"";
					jsScript += ", " + sensorTelemetry.getMessageBrokerPort(); 
					jsScript += ", " + sensorTelemetry.getKeepAlive(); 
					jsScript += ", " + sensorTelemetry.getQualityOfService();
					jsScript += ", \"" + sensorTelemetry.getTopic() + "\");";					
					
					getApplication().getMainWindow().executeJavaScript(jsScript);					
					
				} catch (Exception e) {
					e.printStackTrace();
					return;
				
				}
			}
		});
        
        jsMacCallback.addJsCallbackLocationListener(new JsCallbackLocationListener() {			
			@Override
			public void JsCallback(CallbackLocationEvent event) {
				boolean active = false;
				String jsScript;
								
				String locationMac = event.getLocationMac();
				
				try {
					// get Location Sensor Configuration from Wifi Mac address					
					SensorLocation sensorLocation = ContextServices.getSensorLocationService().getByMac(locationMac);
					
					// set Sensor Location Context
					ContextApp.setSensorLocation(sensorLocation);
					
					if (sensorLocation.getSensorStatus().getCode().equals(Sensor.STATUS.ACTIVE.name()))
						active = true;
					else
						active = false;
										
					jsScript  = "LOCATION.init(" + active;
					jsScript += ", " + sensorLocation.getMinDistance();
					jsScript += ", " + sensorLocation.getMinTime();	
					jsScript += ", \"" + sensorLocation.getMac() + "\"";
					jsScript += ", \"" + sensorLocation.getMessageBrokerHost() + "\"";
					jsScript += ", " + sensorLocation.getMessageBrokerPort(); 
					jsScript += ", " + sensorLocation.getKeepAlive(); 
					jsScript += ", " + sensorLocation.getQualityOfService();
					jsScript += ", \"" + sensorLocation.getTopic() + "\");";					
					
					getApplication().getMainWindow().executeJavaScript(jsScript);
										
					// get Message Sensor Configuration from Wifi Mac address
					SensorMessage sensorMessage = ContextServices.getSensorMessageService().getByMac(locationMac);
					
					// set Sensor Telemetry Context
					ContextApp.setSensorMessage(sensorMessage);
					
					if (sensorMessage.getSensorStatus().getCode().equals(Sensor.STATUS.ACTIVE.name()))
						active = true;
					
					jsScript  = "MESSAGE.init(" + active;
					jsScript += ", \"" + sensorMessage.getMac() + "\"";
					jsScript += ", \"" + sensorMessage.getMessageBrokerHost() + "\"";
					jsScript += ", " + sensorMessage.getMessageBrokerPort(); 
					jsScript += ", " + sensorMessage.getKeepAlive(); 
					jsScript += ", " + sensorMessage.getQualityOfService();
					jsScript += ", \"" + sensorMessage.getTopic() + "\");";
					
					getApplication().getMainWindow().executeJavaScript(jsScript);
																				
				} catch (Exception e) {
					e.printStackTrace();
					return;
					
				}
				
			}
		});
        
        root.addComponent(jsMacCallback);
        
        // user textbox
        txtUsername = new TextField("Usuario:");
        txtUsername.setWidth("100%");              
        logonGroup.addComponent(txtUsername);
        
        // password textbox
        txtPassword = new TextField("Clave:");
        txtPassword.setWidth("100%");
        txtPassword.setSecret(true);      
        logonGroup.addComponent(txtPassword);
        
        VerticalComponentGroup logonButtonGroup = new VerticalComponentGroup();        
        logonButtonGroup.setStyleName("logon");
        
        logon = new Button("Entrar");
        logon.setStyleName("logon");
        logon.setWidth("100%");
        logon.addStyleName("blue");
        logon.addListener(new ClickListener() {		
			@Override
			public void buttonClick(ClickEvent event) {
				logon();
				
			}
		});
        
        logonButtonGroup.addComponent(logon);
        
        root.addComponent(logonGroup);
        root.addComponent(logonButtonGroup);
        
        setContent(root);        
       
	}

	private void logon() {
		try {
			userAuthenticated = ContextServices.getSecurityService().authenticate(txtUsername.getValue().toString(), txtPassword.getValue().toString());
			
			user = ContextServices.getUserService().getByUsername(userAuthenticated.getUsername());
			
		} catch (Exception e) {
			e.printStackTrace();
			
			getApplication().getMainWindow().showNotification("", "Usuario no válido", Window.Notification.TYPE_ERROR_MESSAGE);
			
			userAuthenticated = null;
			employeeAgent = null;
			
			txtPassword.setValue("");
			
			return;
		}
		
		// test get mac
		//getApplication().getMainWindow().executeJavaScript("getMac(\"00-B0-D0-86-BB-F7\");");		
		
		// save user in the Application Context
		ContextApp.setUser(user);
		
	 	// recover the default organization from user if exist
		try {
			employeeAgent = ContextServices.getEmployeeAgentService().getByUser(user);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			getApplication().getMainWindow().showNotification("Error", e.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
			
			userAuthenticated = null;
			employeeAgent = null;
			
			return;
		}
		
		ContextApp.setEmployeeAgent(employeeAgent);
		
		// save organization in the Application Context
		if (employeeAgent.getDefaultOrganization() != null)
			ContextApp.setOrganization(employeeAgent.getDefaultOrganization());
		else
			ContextApp.setOrganization(null);
		
		// send initialization event				
		String jsScript = "CONTEXT.init(" + employeeAgent.getDefaultOrganization().getOrganizationId()  + ", ";
		jsScript += user.getUserId() + ", ";
		jsScript += "0, ";
		jsScript += "0);";
		
		getApplication().getMainWindow().executeJavaScript(jsScript);
		
		// open services
		nav.navigateTo(new MainMenuView(nav));
			
		txtUsername.setValue("");
		txtPassword.setValue("");
	}

//	@Override
//	public void attach()  {
//		// get telemetry data
//		getApplication().getMainWindow().executeJavaScript("CONFIGURE.getTelemetryMac();");
//		
//		// get location data
//		getApplication().getMainWindow().executeJavaScript("CONFIGURE.getLocationMac();");
//		
//	}
}
