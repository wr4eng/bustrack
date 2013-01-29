package com.thingtrack.bustrack.view.module.service.internal;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.thingtrack.konekti.domain.Service;
import com.thingtrack.konekti.service.api.ServiceService;
import com.thingtrack.konekti.view.addon.data.BindingSource;
import com.thingtrack.konekti.view.addon.ui.AbstractView;
import com.thingtrack.konekti.view.addon.ui.BoxToolbar;
import com.thingtrack.konekti.view.addon.ui.DataGridView;
import com.thingtrack.konekti.view.addon.ui.NavigationToolbar;
import com.thingtrack.konekti.view.addon.ui.NavigationToolbar.ClickNavigationEvent;
import com.thingtrack.konekti.view.addon.ui.NavigationToolbar.ClickRefreshButtonListener;
import com.thingtrack.konekti.view.kernel.ui.layout.IViewContainer;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class ServiceView extends AbstractView 
	implements ClickRefreshButtonListener {

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private DataGridView dgService;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	private ServiceService serviceService;
	
	private BindingSource<Service> bsService =  new BindingSource<Service>(Service.class, 1);
	
	private NavigationToolbar navigationToolbar;
	private BoxToolbar printToolbar;
		
	private IViewContainer viewContainer;
	
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
		// set Slide View Services and ViewContainer to navigate
		this.viewContainer = viewContainer;
		this.serviceService = ServiceViewContainer.getServiceService();
		
		// initialize datasource views		
		initView();	
		
	}

	private void initView() {
		// initialize Slide View Organization View
		dgService.setImmediate(true);		
		
		refreshBindindSource();
		
		// STEP 01: create grid view for slide Organization View
		try {
			dgService.setBindingSource(bsService);
			dgService.setVisibleColumns(new String[]  { "offerLine.offer.code", "code", "serviceType.description", "description", "passengers", "vehicleType.description", "driverRPMQuality", "intermediateStops", "kmOffer", "kmReal", "gasOffer", "gasReal", "startService", "stopService", "lunch", "dinner", "breackfast", "accomodation", "observation", "reservationDate", "scheduleDate", "serviceStatus.description"} );        
			dgService.setColumnHeaders(new String[] { "Oferta", "Código", "Tipo Servicio", "Descripción", "Pasajeros", "Tipo Vehículo", "RPM Conductor", "Paradas Intermedias", "Km Ofertados", "Km Real", "Combustible Ofertado", "Combustible Real", "Fecha Inicio", "Fecha Final", "Almuerzo", "Cena", "Desayuno", "Alojamiento", "Observaciones", "Fecha Reserva", "Fecha Planificación", "Estado" } );
			
			dgService.setEditable(true);
			dgService.setTableFieldFactory(new TableFieldFactory() {					
				@Override
				public Field createField(Container container, Object itemId, Object propertyId, Component uiContext) {
					if("lunch".equals(propertyId) || "dinner".equals(propertyId) || "breackfast".equals(propertyId) || "accomodation".equals(propertyId)) {						
						CheckBox field = new CheckBox();
						field.setReadOnly(true);						
						return field;
					}
					
					return null;
				}
			});

			dgService.setColumnCollapsed("startService", true);
			dgService.setColumnCollapsed("stopService", true);
			dgService.setColumnCollapsed("driverRPMQuality", true);
			dgService.setColumnCollapsed("kmOffer", true);
			dgService.setColumnCollapsed("kmReal", true);
			dgService.setColumnCollapsed("gasOffer", true);
			dgService.setColumnCollapsed("gasReal", true);
			dgService.setColumnCollapsed("lunch", true);
			dgService.setColumnCollapsed("dinner", true);
			dgService.setColumnCollapsed("breackfast", true);
			dgService.setColumnCollapsed("accomodation", true);
		}
		catch(Exception ex) {
			ex.getMessage();
		}
			
		// STEP 02: create toolbar for slide Organization View
		navigationToolbar = new NavigationToolbar(0, bsService, viewContainer);
		printToolbar = new BoxToolbar(1, bsService);
		
		navigationToolbar.addListenerRefreshButton(this);
		navigationToolbar.setUpButton(false);
		navigationToolbar.setDownButton(false);
				
		removeAllToolbar();
		
		addToolbar(navigationToolbar);
		addToolbar(printToolbar);

	}
	
	private void refreshBindindSource() {
		try {		
			bsService.removeAllItems();
			bsService.addAll(serviceService.getAll());
			
			bsService.addNestedContainerProperty("serviceStatus.description");
			bsService.addNestedContainerProperty("serviceType.description");
			bsService.addNestedContainerProperty("vehicleType.description");
			bsService.addNestedContainerProperty("offerLine.offer.code");
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
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
		
		// dataGridView_1
		dgService = new DataGridView() {
		    @Override
		    protected String formatPropertyValue(Object rowId,
		            Object colId, Property property) {
		    	// Format by property type
		        if (property.getType() == Date.class) {
		            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		            
		            if (property.getValue() != null)
		            	return df.format((Date)property.getValue());
		            else
		            	return null;
		        }

		        return super.formatPropertyValue(rowId, colId, property);
		    }
		};
		
		dgService.setImmediate(false);
		dgService.setWidth("100.0%");
		dgService.setHeight("100.0%");
		mainLayout.addComponent(dgService);
		mainLayout.setExpandRatio(dgService, 1.0f);
		
		return mainLayout;
	}

}
