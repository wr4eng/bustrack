package com.thingtrack.bustrack.view.module.scheduler.internal;

import com.thingtrack.konekti.domain.Service;
import com.thingtrack.konekti.service.api.ServiceService;
import com.thingtrack.konekti.view.addon.data.BindingSource;
import com.thingtrack.konekti.view.addon.ui.AbstractView;
import com.thingtrack.konekti.view.addon.ui.DataGridView;
import com.thingtrack.konekti.view.addon.ui.NavigationToolbar;
import com.thingtrack.konekti.view.kernel.ui.layout.IViewContainer;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class AgendaView extends AbstractView {
	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private DataGridView dgServices;
	
	private ServiceService serviceService;

	private BindingSource<Service> bsOrganization = new BindingSource<Service>(Service.class, 0);

	private NavigationToolbar navigationToolbar;
	
	private IViewContainer viewContainer;
	
	/**
	* The constructor should first build the main layout, set the
	* composition root and then do any custom initialization.
	*
	* The constructor will not be automatically regenerated by the
	* visual editor.
	*/	
	public AgendaView(IViewContainer viewContainer) {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		// set Slide View Services and ViewContainer to navigate
		this.viewContainer = viewContainer;
		this.serviceService = SchedulerViewContainer.getServiceService();
		
		// initialize datasource views
//		initView();	
		
	}
	
	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);
	
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
	
		// dgOrganization
		dgServices = new DataGridView();
		dgServices.setImmediate(false);
		dgServices.setWidth("100.0%");
		dgServices.setHeight("100.0%");
		mainLayout.addComponent(dgServices);
		mainLayout.setExpandRatio(dgServices, 1.0f);
	
		return mainLayout;
	}
}
