package com.thingtrack.bustrack.view.mobile.workbench.ui;

import com.vaadin.addon.touchkit.ui.NavigationManager;

public class BustrackHierarchyManager extends NavigationManager {
	private static final long serialVersionUID = 1L;
	
    public BustrackHierarchyManager() {
        setWidth("100%");
        navigateTo(new LogonView(this));
        
    }
}
