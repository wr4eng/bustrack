package com.thingtrack.bustrack.view.mobile.workbench.ui;

import java.util.Hashtable;
import java.util.List;

import com.thingtrack.bustrack.domain.Vehicle;
import com.thingtrack.bustrack.view.mobile.workbench.ContextApp;
import com.thingtrack.bustrack.view.mobile.workbench.ContextServices;
import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Button.ClickEvent;

public class VehicleView  extends NavigationView {
	private static final long serialVersionUID = 1L;

	private Resource serviceIcon = new ThemeResource("../runo/icons/64/globe.png");
	
	private List<Vehicle> vehicles;
	
	@SuppressWarnings("rawtypes")
	private Hashtable menuVehicle = new Hashtable();

	private BustrackHierarchyManager nav;
	
	public VehicleView(final BustrackHierarchyManager nav) {
        setCaption("Vehículo");
        setWidth("100%");
        setHeight("100%");
        
        this.nav = nav;     
    	
        CssLayout root = new CssLayout();
        root.setWidth("100%");
        root.setMargin(true);
        
        // get free vehicles for the actual organization
        getVehicles();
        
        // build vehicle selector
        root.addComponent(buildVehicleSelector());
        
        // set Content root
        setContent(root);
    }
	
	private void getVehicles() {
		try {
			vehicles = ContextServices.getVehicleService().getFreeVehicles(ContextApp.getOrganization());
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	@SuppressWarnings("unchecked")
	private Component buildVehicleSelector() {
		VerticalComponentGroup vehicleVerticalComponentGroup = new VerticalComponentGroup();
		vehicleVerticalComponentGroup.setCaption("Seleccione un vehículo:");
        
        for (Vehicle vehicle : vehicles) {
        	NavigationButton btn = new NavigationButton("Número: " + vehicle.getVehicleNumber());
        	
        	// create the menu hashTable
        	menuVehicle.put(btn, vehicle);
        	
        	btn.setIcon(serviceIcon);
        	btn.addListener(new Button.ClickListener() {
                 private static final long serialVersionUID = 1L;

                 public void buttonClick(ClickEvent event) {     
                	 ContextApp.setVehicle((Vehicle)menuVehicle.get(event.getButton()));
                	 
                     nav.navigateBack();
                     
                 }
            });
        	
        	vehicleVerticalComponentGroup.addComponent(btn);
        }
        
        return vehicleVerticalComponentGroup;
	}
}
