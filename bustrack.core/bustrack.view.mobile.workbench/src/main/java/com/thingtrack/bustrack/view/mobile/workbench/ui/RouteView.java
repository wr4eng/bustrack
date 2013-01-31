package com.thingtrack.bustrack.view.mobile.workbench.ui;

import java.text.DateFormat;
import java.util.Date;

import com.thingtrack.bustrack.domain.Route;
import com.thingtrack.bustrack.domain.RouteStatus;
import com.thingtrack.bustrack.domain.WorksheetLine;
import com.thingtrack.bustrack.view.mobile.workbench.ContextApp;
import com.thingtrack.bustrack.view.mobile.workbench.ContextServices;
import com.thingtrack.konekti.domain.Service;
import com.thingtrack.konekti.domain.ServiceType;
import com.vaadin.addon.touchkit.ui.HorizontalComponentGroup;
import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationManager.NavigationEvent;
import com.vaadin.addon.touchkit.ui.NavigationManager.NavigationEvent.Direction;
import com.vaadin.addon.touchkit.ui.NavigationManager.NavigationListener;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class RouteView extends NavigationView implements ClickListener {
	private static final long serialVersionUID = 1L;
	
	private Service service;
	private int index = 0;
	
	private HorizontalComponentGroup navigationActions = new HorizontalComponentGroup();
    private Button prevButton;
    private Button nextButton;

    private Button routeButton; 
    private Button finishButton;
    
    private VerticalComponentGroup headerArrivalGroup;
    private TextField clientText;
    private TextField routeDescription;
    private TextField vehicleText;
    private NavigationButton vehicleButton;
    
    private VerticalComponentGroup routeArrivalGroup;
    private TextField stopArrival;
    private TextField timeArrival;
    
    private VerticalComponentGroup routeCheckOutGroup;
    private TextField stopCheckout;
    private TextField timeCheckout;
    
	private BustrackHierarchyManager nav;
	
	private CssLayout content = new CssLayout();		
	
	private HorizontalLayout toolbar = new HorizontalLayout();
	
	public RouteView(final BustrackHierarchyManager nav, final Service service) {
        setCaption("Rutas");
        setWidth("100%");
        setHeight("100%");
        
        this.nav = nav;
        this.service = service;
        
        getLeftComponent().setCaption("Servicios");
        
        // create start/stop route toolbar
        setToolbar(buildRouteToolbar());
        
        // build Navigation Toolbar
        buildNavigationToolBar();
                
        // build interface
        buildInterface();
                                
        // initialize Service binding
        checkNavigationButtons();
        
        bindData(service.getRoutes().get(0));
        
        setContent(content);
        
        nav.addListener(new NavigationListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void navigate(NavigationEvent event) {				
				if (event.getDirection() == Direction.BACK) {
					if (ContextApp.getVehicle() != null)
						try {
							// set vehicle for route
							headerArrivalGroup.removeComponent(vehicleButton);
							
							vehicleButton = new NavigationButton("Vehículo Número: " + ContextApp.getVehicle().getVehicleNumber(), new VehicleView(nav));
							vehicleButton.setWidth("100%");
							headerArrivalGroup.addComponent(vehicleButton);		
														
							service.getRoutes().get(index).setVehicle(ContextApp.getVehicle());
							
							// save the vehicle to the route
							ContextServices.getRouteService().save(service.getRoutes().get(index));

						}
						catch(Exception ex) {
							ex.getMessage();
						}
					
				}
				
			}
		});
	}
		
	private void startRoute(final Route route) {		
		try {
			toolbar.removeComponent(routeButton);
		}
		catch(Exception ex) {}
		
        routeButton = new Button();
        routeButton.setWidth("100%");        
    	routeButton.setCaption("Stop");
    	routeButton.setStyleName("red");
    	routeButton.addListener(new ClickListener() {			
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (route.getRouteStatus().getCode().equals(RouteStatus.STATUS.PENDING.name()))					
				{		
					if (checkRouteStatus()) {
						getApplication().getMainWindow().showNotification("", "Existe una ruta en ejecución", Window.Notification.TYPE_ERROR_MESSAGE);
						
						return;
					}
					
					// start Location Sensor					
					getApplication().getMainWindow().executeJavaScript("LOCATION.start(" + route.getRouteId() + ");");
					
					// start Telemetry Sensor
					getApplication().getMainWindow().executeJavaScript("TELEMETRY.start(" + route.getRouteId() + ");");
					
					try {
						ContextServices.getRouteService().setRunningStatus(route);
					} catch (Exception e) {					
						e.printStackTrace();
					}
					
					// set new status
					startRoute(route);
					
					// set start worksheet status
					openWorksheet();
				}
				else if (route.getRouteStatus().getCode().equals(RouteStatus.STATUS.RUNNING.name())) {
					// stop Location Sensor
					getApplication().getMainWindow().executeJavaScript("LOCATION.stop(" + route.getRouteId() + ");");
						
					// stop Telemetry Sensor
					getApplication().getMainWindow().executeJavaScript("TELEMETRY.stop(" + route.getRouteId() + ");");
					
					try {
						ContextServices.getRouteService().setStopStatus(route);
					} catch (Exception e) {					
						e.printStackTrace();
					}
					
					// set new status					
					stopRoute(route);
					
					// set close worksheet status
					closeWorksheet();
				}
				
			}
		});
    			
    	toolbar.addComponent(routeButton);
    	toolbar.setComponentAlignment(routeButton, Alignment.MIDDLE_LEFT);

    	if (route.getRouteStatus().getCode().equals(RouteStatus.STATUS.FINISH.name())) {
    		routeButton.setEnabled(false);
			finishButton.setEnabled(false);
			
    	}
    	else {
    		routeButton.setEnabled(true);
			finishButton.setEnabled(true);
    	}
    		
	}
	
	private void stopRoute(final Route route) {		
		try {
			toolbar.removeComponent(routeButton);
		}
		catch(Exception ex) {}
		
        routeButton = new Button();
        routeButton.setWidth("100%");
    	routeButton.setCaption("Start");
    	routeButton.setStyleName("green");  	
    	routeButton.addListener(new ClickListener() {			
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (route.getRouteStatus().getCode().equals(RouteStatus.STATUS.PENDING.name()))					
				{				
					if (checkRouteStatus()) {
						getApplication().getMainWindow().showNotification("", "Existe una ruta en ejecución", Window.Notification.TYPE_ERROR_MESSAGE);
						
						return;
					}
						
					// start Location Sensor				
					getApplication().getMainWindow().executeJavaScript("LOCATION.start(" + route.getRouteId() + ");");
						
					// start Telemetry Sensor 
					getApplication().getMainWindow().executeJavaScript("TELEMETRY.start(" + route.getRouteId() + ");");
					
					try {
						ContextServices.getRouteService().setRunningStatus(route);
					} catch (Exception e) {					
						e.printStackTrace();
					}
					
					// set new status
					startRoute(route);
					
					// set start worksheet status
					openWorksheet();
				}
				else if (route.getRouteStatus().getCode().equals(RouteStatus.STATUS.RUNNING.name())) {					
					// stop Location Sensor
					getApplication().getMainWindow().executeJavaScript("LOCATION.stop(" + route.getRouteId() + ");");
						
					// stop Telemetry Sensor
					getApplication().getMainWindow().executeJavaScript("TELEMETRY.stop(" + route.getRouteId() + ");");
					
					try {
						ContextServices.getRouteService().setStopStatus(route);
					} catch (Exception e) {					
						e.printStackTrace();
					}
					
					// set new status					
					stopRoute(route);
					
					// set close worksheet status
					closeWorksheet();
				}
					
			}
		});
    	
    	toolbar.addComponent(routeButton);
        toolbar.setComponentAlignment(routeButton, Alignment.MIDDLE_LEFT);

        if (route.getRouteStatus().getCode().equals(RouteStatus.STATUS.FINISH.name())) {    		
    		routeButton.setEnabled(false);
			finishButton.setEnabled(false);
			
    	}
    	else {
    		routeButton.setEnabled(true);
			finishButton.setEnabled(true);
    	}
    	
	}	
	
	private void buildNavigationToolBar() {
        prevButton = new Button("Up", this);
        prevButton.addStyleName("icon-arrow-up");
        prevButton.setEnabled(false);       
        
        nextButton = new Button("Down", this);
        nextButton.addStyleName("icon-arrow-down");
        nextButton.setEnabled(true);
        
        navigationActions.addComponent(prevButton);
        navigationActions.addComponent(nextButton);
        
        // set status navigation buttons
        setRightComponent(navigationActions);
        
        // set status route button
        final Route route = service.getRoutes().get(index);
        
        // set the active vehicle in the app context
        ContextApp.setVehicle(service.getRoutes().get(index).getVehicle());
        
        if (route.getRouteStatus().getCode().equals(RouteStatus.STATUS.PENDING.name()))        	
        	stopRoute(route);
        else
        	startRoute(route); 
	        
	}
		
	private void buildInterface() {
        content.setWidth("100%");
        
        // ----------------- Header  ----------------------
        headerArrivalGroup = new VerticalComponentGroup();
        headerArrivalGroup.setCaption("Ruta");
		clientText = new TextField("Cliente: ");
		clientText.setWidth("100%");
        routeDescription = new TextField("Description:");
        routeDescription.setWidth("100%");
		
        headerArrivalGroup.addComponent(clientText);
        headerArrivalGroup.addComponent(routeDescription);
        
        content.addComponent(headerArrivalGroup);
        
        // ----------------- Arrival Stop ----------------------
        routeArrivalGroup = new VerticalComponentGroup();
        routeArrivalGroup.setCaption("Paradas");
        
        // Stop arrival
        stopArrival = new TextField("Origen:");
        stopArrival.setWidth("100%");
        
        routeArrivalGroup.addComponent(stopArrival);
        
        // Time arrival
        timeArrival = new TextField("Fecha/Hora:");
        timeArrival.setWidth("100%");
                
        routeArrivalGroup.addComponent(timeArrival);
        
        content.addComponent(routeArrivalGroup);
        
        // ----------------- checkout Stop ----------------------
        routeCheckOutGroup = new VerticalComponentGroup();        
        
        // Stop checkout
        stopCheckout = new TextField("Destino:");
        stopCheckout.setWidth("100%");
        
        routeCheckOutGroup.addComponent(stopCheckout);
        
        // Time checkout
        timeCheckout = new TextField("Fecha/Hora:");
        timeCheckout.setWidth("100%");
        
        routeCheckOutGroup.addComponent(timeCheckout);
        
        content.addComponent(routeCheckOutGroup);
        
	}
	
	private Component buildRouteToolbar() {
        toolbar.setMargin(false, true, false, true);
        toolbar.setSpacing(true);
        toolbar.setSizeFull();
        toolbar.setStyleName("v-touchkit-navbar");

        finishButton = new Button();
        finishButton.setWidth("100%");        
        finishButton.setCaption("Finalizar");
        finishButton.setStyleName("blue");
        finishButton.addListener(new ClickListener() {			
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				// disable start/stop route button
				routeButton.setEnabled(false);
				finishButton.setEnabled(false);
				
				// set FINALIZE route status
				try {
					ContextServices.getRouteService().setFinalizeStatus(service.getRoutes().get(index));
					
					if (service.getRoutes().get(index).getServices().size() == 1 && service.getRoutes().get(index).getServices().get(0).getServiceType().getCode().equals(ServiceType.TYPE.UNPLANNED.name())) {
						service.getRoutes().get(index).getStops().get(1).setStopArrivalDate(new Date());
						
						ContextServices.getStopService().save(service.getRoutes().get(index).getStops().get(1));
					}
						
				} catch (Exception e) {
					e.printStackTrace();
					
				}
			}
    	});
    	
        toolbar.addComponent(finishButton);
        toolbar.setComponentAlignment(finishButton, Alignment.MIDDLE_LEFT);
        
        return toolbar;
	}
		
	private void bindData(Route route) {
		setCaption("Ruta " + (index + 1) + " de " + service.getRoutes().size());
		
		DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT);
		DateFormat timeFormatter = DateFormat.getTimeInstance(DateFormat.SHORT);
		
		try {
			setRouteReadOnly(false);
			routeDescription.setValue(route.getDescription());
			clientText.setValue(service.getClient().getName());
			
			try {
				headerArrivalGroup.removeComponent(vehicleText);
			}
			catch(Exception ex) { }
			
			try {
				headerArrivalGroup.removeComponent(vehicleButton);
			}
			catch(Exception ex) { }
			
			if (route.getVehicle() == null) {
				vehicleButton = new NavigationButton("Selecciona Vehículo", new VehicleView(nav));
				vehicleButton.setWidth("100%");
				
				headerArrivalGroup.addComponent(vehicleButton);							
				
			}
			else {
				vehicleText = new TextField("Vehículo: ");
				vehicleText.setWidth("100%");
				headerArrivalGroup.addComponent(vehicleText);
				
				vehicleText.setValue(route.getVehicle().getVehicleNumber());
				
			}
				
			stopArrival.setValue(route.getStops().get(0).getStopAddress().getStreet());
			timeArrival.setValue(dateFormatter.format(route.getStops().get(0).getStopCheckoutDate()) + " - " + timeFormatter.format(route.getStops().get(0).getStopCheckoutDate()));
			stopCheckout.setValue(route.getStops().get(1).getStopAddress().getStreet());
			timeCheckout.setValue(dateFormatter.format(route.getStops().get(1).getStopArrivalDate()) + " - " + timeFormatter.format(route.getStops().get(1).getStopArrivalDate()));
			setRouteReadOnly(true);
		}
		catch( Exception ex) {
			ex.getMessage();
		}
	}
	
	private void setRouteReadOnly(boolean readOnly) {
		clientText.setReadOnly(readOnly);
		routeDescription.setReadOnly(readOnly);
		
		try {
			vehicleText.setReadOnly(readOnly);
		}
		catch(Exception ex) {}
		stopArrival.setReadOnly(readOnly);
		timeArrival.setReadOnly(readOnly);
		stopCheckout.setReadOnly(readOnly);
		timeCheckout.setReadOnly(readOnly);
		
	}
	
	public void buttonClick(ClickEvent event) {        
        if (event.getButton() == prevButton) {
        	index = index - 1;
        	bindData(service.getRoutes().get(index));
        	
        	checkNavigationButtons();
        	    		
            // set route status
            if (service.getRoutes().get(index).getRouteStatus().getCode().equals(RouteStatus.STATUS.PENDING.name()))
            	stopRoute(service.getRoutes().get(index));
            else
            	startRoute(service.getRoutes().get(index));
            
        	return;
        }        

        if (event.getButton() == nextButton) {     
        	index = index + 1;
        	bindData(service.getRoutes().get(index));
        	
        	checkNavigationButtons();
    		
            // set route status
            if (service.getRoutes().get(index).getRouteStatus().getCode().equals(RouteStatus.STATUS.PENDING.name()))	                        
            	stopRoute(service.getRoutes().get(index));
            else
            	startRoute(service.getRoutes().get(index));
            
            return;

        }
         
    }
	
	private void checkNavigationButtons() {
		if (service.getRoutes().size() == 1) {
			prevButton.setEnabled(false);
			nextButton.setEnabled(false);
			
			return;			
		}
		
		if (index == 0) {
			prevButton.setEnabled(false);
			nextButton.setEnabled(true);			
		}		
		else if (index == service.getRoutes().size() - 1) {
			prevButton.setEnabled(true);
			nextButton.setEnabled(false);			
		}
		else {
			prevButton.setEnabled(true);
			nextButton.setEnabled(true);
		}
						
	}
	
	private boolean checkRouteStatus() {
		for(Route route : service.getRoutes()) {
			if (route.getRouteStatus().getCode().equals(RouteStatus.STATUS.RUNNING.name()))
				return true;
		}
		
		return false;
	}
	
	private void openWorksheet() {
		int routesCount = 0;
		
		for (WorksheetLine wl : ContextApp.getWorksheet().getWorksheetLines()) {
			for (Route rt : wl.getService().getRoutes())
				if (rt.getRouteStatus().equals(RouteStatus.STATUS.RUNNING.name()))					
					routesCount++;
		}
			
		// open the worksheet for the first close route
		try {
			if (routesCount == 1)
				ContextServices.getWorksheetService().setOpenWorksheet(ContextApp.getWorksheet());
		} catch (Exception e) {			
			e.printStackTrace();
			
		}
	}
	
	private void closeWorksheet() {
		int routesCount = 0;
				
		for (WorksheetLine wl : ContextApp.getWorksheet().getWorksheetLines()) {
			for (Route rt : wl.getService().getRoutes())
				if (rt.getRouteStatus().equals(RouteStatus.STATUS.FINISH.name()))
					routesCount++;
		}
				
		// set a warning when all routes are finished
		if (routesCount == ContextApp.getWorksheet().getWorksheetLines().size())
			getApplication().getMainWindow().showNotification("", "Cerrar Parte de trabajo", Window.Notification.TYPE_WARNING_MESSAGE);
	}
}
