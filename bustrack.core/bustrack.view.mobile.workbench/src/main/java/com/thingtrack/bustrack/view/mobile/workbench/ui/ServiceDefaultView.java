package com.thingtrack.bustrack.view.mobile.workbench.ui;

import java.util.ArrayList;
import java.util.List;

import com.thingtrack.bustrack.view.mobile.workbench.ContextApp;
import com.thingtrack.bustrack.view.mobile.workbench.ContextServices;
import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ServiceDefaultView extends NavigationView {
	private static final long serialVersionUID = 1L;
	
    private Resource serviceIcon = new ThemeResource("../runo/icons/64/globe.png");
    
	@SuppressWarnings("unused")
	private BustrackHierarchyManager nav;
	
	private List<String> servicesDefault = new ArrayList<String>();
	
	@SuppressWarnings("serial")
	public ServiceDefaultView(final BustrackHierarchyManager nav) {
        setCaption("Otros Servicios");
        setWidth("100%");
        setHeight("100%");
        
        this.nav = nav;
        
        Button logout = new Button("Servicios");
        logout.addListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				nav.navigateBack();
				
			}
		});
        
        setLeftComponent(logout);
        
        // get user services from organization without vehicles
        getServicesDefault();
        
        CssLayout root = new CssLayout();
        root.setWidth("100%");
        root.setMargin(true);
        VerticalComponentGroup serviceVerticalComponentGroup = new VerticalComponentGroup();

        for (String serviceDefault : servicesDefault) {
        	NavigationButton btn = new NavigationButton(serviceDefault);

        	btn.setIcon(serviceIcon);
            btn.addListener(new Button.ClickListener() {
                private static final long serialVersionUID = 1L;

                public void buttonClick(ClickEvent event) {
                	try {
						ContextServices.getWorksheetService().addDefaultService(ContextApp.getOrganization(), ContextApp.getWorksheet(), ContextApp.getVehicle(), ContextApp.getEmployeeAgent(), event.getButton().getCaption());
					} catch (Exception e) {						
						e.printStackTrace();
						
					}
                	                	
                    nav.navigateBack();
                    
                }
            });
            
            serviceVerticalComponentGroup.addComponent(btn);
        }
        
        root.addComponent(serviceVerticalComponentGroup);
        setContent(root);
	}
	
	private void getServicesDefault() {
		servicesDefault.add("Descanso Planificado");
		servicesDefault.add("Servicio no Planificado");
		servicesDefault.add("Traslado en vacío");
		servicesDefault.add("Avería/Taller");
		servicesDefault.add("Otros");
		
	}
}
