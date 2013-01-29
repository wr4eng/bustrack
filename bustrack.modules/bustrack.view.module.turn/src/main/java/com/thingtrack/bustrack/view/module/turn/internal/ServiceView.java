package com.thingtrack.bustrack.view.module.turn.internal;

import java.util.List;

import com.thingtrack.bustrack.domain.Turn;

import com.thingtrack.konekti.domain.Service;
import com.thingtrack.konekti.service.api.ServiceService;
import com.thingtrack.konekti.view.addon.data.BindingSource;
import com.thingtrack.konekti.view.addon.ui.AbstractView;
import com.thingtrack.konekti.view.addon.ui.BoxToolbar;
import com.thingtrack.konekti.view.addon.ui.BoxToolbar.ClickFilterButtonListener;
import com.thingtrack.konekti.view.addon.ui.BoxToolbar.ClickPrintButtonListener;
import com.thingtrack.konekti.view.addon.ui.DataGridView;
import com.thingtrack.konekti.view.addon.ui.NavigationToolbar;
import com.thingtrack.konekti.view.addon.ui.NavigationToolbar.ClickNavigationEvent;
import com.thingtrack.konekti.view.addon.ui.NavigationToolbar.ClickRefreshButtonListener;
import com.thingtrack.konekti.view.addon.ui.NavigationToolbar.ClickUpButtonListener;
import com.thingtrack.konekti.view.kernel.ui.layout.IViewContainer;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class ServiceView extends AbstractView 
	implements ClickUpButtonListener, ClickRefreshButtonListener, ClickFilterButtonListener, ClickPrintButtonListener {

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private DataGridView dgService;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	private Turn turn;
	private ServiceService serviceService;
	
	private BindingSource<Service> bsService = new BindingSource<Service>(Service.class, 1);
	
	private NavigationToolbar navigationToolbar;
	private BoxToolbar boxToolbar;
	
	private List<Service> services;
	
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public ServiceView(IViewContainer viewContainer) {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		dgService.setImmediate(true);
		
		this.viewContainer = viewContainer;
		this.serviceService = TurnViewContainer.getServiceService();
		
	}

	public void setTurn(Turn turn) {
		this.turn = turn;
		this.services = turn.getServices();
		
		// refresh datasource
		refreshBindindSource();
		
		injectBindingSource();
		
	}
		
	private void refreshBindindSource() {
		try {		
			bsService.removeAllItems();
			bsService.addAll(services);
			
			bsService.addNestedContainerProperty("serviceType.description");
			bsService.addNestedContainerProperty("client.name");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private void injectBindingSource() {
		try {
			dgService.setBindingSource(bsService);
			dgService.setVisibleColumns(new String[] { "serviceType.description", "code", "description", "client.name" } );       
			dgService.setColumnHeaders(new String[] { "Tipo", "Código", "Descriptión", "Cliente" } );
						
			// create toolbar for slide
			navigationToolbar = new NavigationToolbar(0, bsService, viewContainer);
			boxToolbar = new BoxToolbar(1, bsService);
			
			navigationToolbar.addListenerUpButton(this);
			
			boxToolbar.addListenerFilterButton(this);
			boxToolbar.addListenerPrintButton(this);
			
			removeAllToolbar();
			
			addToolbar(navigationToolbar);
			addToolbar(boxToolbar);
		} catch (Exception ex) {
			ex.getMessage();
			
		}
		
	}
	
	@Override
	public void upButtonClick(ClickNavigationEvent event) {
		// roll to the detail Organization Parent View
		viewContainer.getSliderView().rollPrevious();
		
	}
	
	@Override
	public void refreshButtonClick(ClickNavigationEvent event) {
		refreshBindindSource();
		
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
		
		// dataGridView
		dgService = new DataGridView();
		dgService.setImmediate(false);
		dgService.setWidth("100.0%");
		dgService.setHeight("100.0%");
		mainLayout.addComponent(dgService);
		mainLayout.setExpandRatio(dgService, 1.0f);
		
		return mainLayout;
	}

	@Override
	public void filterButtonClick(BoxToolbar.ClickNavigationEvent event) {
		this.dgService.setFilterBarVisible();
		
	}

	@Override
	public void printButtonClick(BoxToolbar.ClickNavigationEvent event) {
		try {
			dgService.print("Listado Servicios del Turno " + turn.getCode());
		}
		catch (Exception e) {
			throw new RuntimeException("¡No se pudo imprimir el informe!", e);
		}
		
		
	}

}
