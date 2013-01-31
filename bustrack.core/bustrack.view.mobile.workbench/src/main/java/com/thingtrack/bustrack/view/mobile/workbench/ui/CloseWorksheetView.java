package com.thingtrack.bustrack.view.mobile.workbench.ui;

import com.thingtrack.bustrack.domain.Worksheet;
import com.thingtrack.bustrack.view.mobile.workbench.ContextApp;
import com.thingtrack.bustrack.view.mobile.workbench.ContextServices;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class CloseWorksheetView extends NavigationView {
	private static final long serialVersionUID = 1L;
	
	private BustrackHierarchyManager nav;
	
	private TextArea incidences;
	
	public CloseWorksheetView(final BustrackHierarchyManager nav) {
		setCaption("Cerrar Parte");
        setWidth("100%");
        setHeight("100%");
     
        this.nav = nav;
        
        // set layout window
        CssLayout root = new CssLayout();
        root.setWidth("100%");
        root.setMargin(true);
        
        // build Observations textarea
        root.addComponent(buildInterface());                

        // build Observations textarea
        root.addComponent(buildCloseButton());
        
        // set Content root
        setContent(root);
	}
	
	private Component buildInterface() {	
		VerticalComponentGroup observationGroup = new VerticalComponentGroup();
	    observationGroup.setCaption("Incidencias");
	        
        incidences = new TextArea();
        incidences.setWidth("100%");
        incidences.setHeight("200px");        
        
        observationGroup.addComponent(incidences);
             
        return observationGroup;
	}
	
	private Component buildCloseButton() {
		VerticalComponentGroup closeGroup = new VerticalComponentGroup();
        closeGroup.setStyleName("close");
        
        Button close = new Button("Cerrar");
        close.setStyleName("close");
        close.setWidth("100%");
        close.addStyleName("blue");
        close.addListener(new ClickListener() {		
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Worksheet worksheet = ContextApp.getWorksheet();
				
				worksheet.setIncidence(incidences.getValue().toString());
				
				try {
					ContextServices.getWorksheetService().setCloseWorksheet(worksheet);
				} catch (Exception e) {
					e.printStackTrace();
					
				}					
				
				nav.navigateBack();
				
			}
		});
        
        closeGroup.addComponent(close);
        
        return closeGroup;
	}
}
