package com.thingtrack.bustrack.view.mobile.workbench.ui;

import com.vaadin.addon.touchkit.ui.TabBarView;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.TabSheet.Tab;

public class MainMenuView extends TabBarView {
	private static final long serialVersionUID = 1L;
	
	public MainMenuView(final BustrackHierarchyManager nav) {
        setCaption("Menú");
        setWidth("100%");
        setHeight("100%");
            	      
	    ServiceView serviceView = new ServiceView(nav);
	    Tab serviceViewTab = addTab(serviceView);
	    serviceViewTab.setIcon(new ThemeResource("search.png"));
	    serviceViewTab.setCaption("Servicios");
	              
	    SettingView settingView = new SettingView(nav);
	    Tab settingViewTab = addTab(settingView);
	    settingViewTab.setIcon(new ThemeResource("tools.png"));
	    settingViewTab.setCaption("Configuración");
	      
	    setSelectedTab(serviceView);
    }
    
}
