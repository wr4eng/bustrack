package com.thingtrack.bustrack.view.mobile.workbench.ui;

import java.text.DateFormat;
import java.util.Hashtable;
import java.util.Iterator;

import com.thingtrack.bustrack.domain.Route;
import com.thingtrack.bustrack.domain.Worksheet;
import com.thingtrack.bustrack.domain.WorksheetLine;
import com.thingtrack.bustrack.view.mobile.workbench.ContextApp;
import com.thingtrack.bustrack.view.mobile.workbench.ContextServices;
import com.thingtrack.konekti.domain.Service;
import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.addon.touchkit.ui.NavigationManager.NavigationEvent;
import com.vaadin.addon.touchkit.ui.NavigationManager.NavigationListener;
import com.vaadin.addon.touchkit.ui.NavigationManager.NavigationEvent.Direction;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;

public class ServiceView extends NavigationView {
	private static final long serialVersionUID = 1L;
	
    private Resource serviceIcon = new ThemeResource("../runo/icons/64/globe.png");

	private BustrackHierarchyManager nav;
	
	private CssLayout root;
	private Worksheet worksheet;
	private VerticalComponentGroup serviceVerticalComponentGroup ;
	
	private NavigationButton closeWorksheetButton;
	private NavigationButton btnOthers;
	
	@SuppressWarnings("rawtypes")
	private Hashtable menuService = new Hashtable();
	
	@SuppressWarnings("serial")
	public ServiceView(final BustrackHierarchyManager nav) {
		setCaption("Parte Trabajo:");
        setWidth("100%");
        setHeight("100%");
        
        this.nav = nav;
        
        // set layout window
        Button logout = new Button("Salir");
        logout.addListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				nav.navigateBack();
				
			}
		});
        
        setLeftComponent(logout);
        
        // get user services from organization without vehicles
        getWorksheetEmployeeAgent();                
        if (worksheet == null)
        	return;
        
		setCaption("Parte Trabajo: " + DateFormat.getDateInstance(DateFormat.SHORT).format(worksheet.getWorksheetDate()));
        
        root = new CssLayout();
        root.setWidth("100%");
        root.setMargin(true);
        
        // build the Service list selector
        root.addComponent(buildServiceSelector());
        
        // build the observations
        root.addComponent(buildObservations());
        
        // build worksheet menu selector
        root.addComponent(buildWorksheetMenuSelector());
        
        // set Content root
        setContent(root);
        
        nav.addListener(new NavigationListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void navigate(NavigationEvent event) {				
				if (event.getDirection() == Direction.BACK) {
					if (ContextApp.getWorksheet().getWorksheetstatus().getCode().equals(Worksheet.STATUS.CLOSE.name()))
						nav.navigateBack();
					else if (nav.getCurrentComponent().getClass().toString() == "") {
						// TODO: 
						getWorksheetEmployeeAgent();
					
						// refresh build the Service list selector
						root.removeComponent(serviceVerticalComponentGroup);						
			        	root.addComponent(buildServiceSelector(), 0);
					}
						
				}
			}
        });
        
        // remove routes for other drivers
    	getDriverRoutes();
	}
	
	 // build interface
    @SuppressWarnings("unchecked")
	private Component buildServiceSelector() {
        serviceVerticalComponentGroup = new VerticalComponentGroup();
        serviceVerticalComponentGroup.setCaption("Servicios");
        
        for (WorksheetLine workSheetLine : worksheet.getWorksheetLines()) {
        	NavigationButton btn = new NavigationButton(workSheetLine.getService().getDescription());
        	
        	// create the menu hashTable
        	menuService.put(btn, workSheetLine.getService());
        	
        	if (workSheetLine.getService().getDescription() != null && workSheetLine.getService().getDescription().length() > 20)
                btn.setCaption(workSheetLine.getService().getDescription().substring(0, 20) + "…");
            
            btn.setIcon(serviceIcon);
            btn.addListener(new Button.ClickListener() {
                private static final long serialVersionUID = 1L;

                public void buttonClick(ClickEvent event) {     
                    nav.navigateTo(new RouteView(nav, (Service) menuService.get(event.getButton())));
                    
                }
            });
        	
            if (workSheetLine.getService().getRoutes().size() > 0)
                btn.setDescription(workSheetLine.getService().getRoutes().size() + "");
            
            btn.addStyleName("pill");
            serviceVerticalComponentGroup.addComponent(btn);
	        	
        }
        // add the las type services
        btnOthers = new NavigationButton("Otros");
        btnOthers.setIcon(serviceIcon);
        btnOthers.addListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;

            public void buttonClick(ClickEvent event) {                    
                nav.navigateTo(new ServiceDefaultView(nav));
                
            }
        });        
        
        serviceVerticalComponentGroup.addComponent(btnOthers);
        
        return serviceVerticalComponentGroup;
        
    }
    
    private void getDriverRoutes() {    
    	for (WorksheetLine whl : ContextApp.getWorksheet().getWorksheetLines()) {
    		Iterator<Route> route = whl.getService().getRoutes().iterator();
    	
    		//TODO
    		// remove the routes for other drivers
        	/*while (route.hasNext()) {
        		if (!route.next().getDriver().getWorkNumber().equals(ContextApp.getEmployeeAgent().getWorkNumber()))
        			route.remove();
        			        			
        	}*/
    	}
    	
    }
    
    private Component buildObservations() {
        VerticalComponentGroup observationGroup = new VerticalComponentGroup();
        observationGroup.setCaption("Observaciones");
        observationGroup.addComponent(new Label("<div style='color:#333;'><p>" + worksheet.getObservation() + "</p></div>", Label.CONTENT_XHTML));
        
        return observationGroup;
        
    }
    
    private Component buildWorksheetMenuSelector() {
    	VerticalComponentGroup closeWorksheetGroup = new VerticalComponentGroup();
    	closeWorksheetGroup.setCaption("Menú");
    	
    	closeWorksheetButton = new NavigationButton("Cerrar Parte Trabajo");
    	closeWorksheetButton.addListener(new ClickListener() {			
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				nav.navigateTo(new CloseWorksheetView(nav));
				
			}
		});
    	
    	closeWorksheetGroup.addComponent(closeWorksheetButton);    	
        
        return closeWorksheetGroup;
        
    }
    
	private void getWorksheetEmployeeAgent() {
		// if we have more than one worksheet per driver (NOT implemented)
		//services = ContextServices.getWorksheetService().getOpenServicesFromUserToday(ContextApp.getEmployeeAgent(), ContextApp.getOrganization(), null);
		
		// only exist one Worksheet per driver per day!!!
		try {
			worksheet = ContextServices.getWorksheetService().getOpenWorksheetFromUserToday(ContextApp.getEmployeeAgent(), ContextApp.getOrganization(), null);
			
			ContextApp.setWorksheet(worksheet);
		} catch (Exception ex) {
			worksheet = null;
			
			ex.getMessage();
		}
						
	}

}
