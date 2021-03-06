package com.thingtrack.bustrack.view.module.scheduler.internal.form;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.domain.Vehicle;
import com.thingtrack.bustrack.service.api.VehicleService;
import com.thingtrack.konekti.domain.EmployeeAgent;
import com.thingtrack.konekti.service.api.EmployeeAgentService;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class RouteView extends CustomComponent {

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private HorizontalLayout resourcesLayout;
	@AutoGenerated
	private ComboBox driverField;
	@AutoGenerated
	private ComboBox vehicleField;
	@AutoGenerated
	private HorizontalLayout stopDatesLayout;
	@AutoGenerated
	private DateField stopArrivalDateField;
	@AutoGenerated
	private DateField stopCheckoutDateField;
	@AutoGenerated
	private StopsField stopsField;
	@AutoGenerated
	private TextArea descriptionField;
	@AutoGenerated
	private TextField codeField;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	/** Enterprise Services **/
	private EmployeeAgentService employeeAgentService;

	private VehicleService vehicleService;

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 * 
	 * @throws Exception
	 * @throws IllegalArgumentException
	 */
	@Autowired
	public RouteView() {

		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		stopArrivalDateField.setImmediate(true);
		stopArrivalDateField.setValue(null);
		stopCheckoutDateField.setImmediate(true);
		stopCheckoutDateField.setValue(null);
		
		getServices();
		
		try {
			loadData();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void getServices(){
		
		BundleContext bundleContext = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		
		ServiceReference employeeAgentServiceReference = (ServiceReference) bundleContext.getServiceReference(EmployeeAgentService.class.getName());
		employeeAgentService =  (EmployeeAgentService) bundleContext.getService(employeeAgentServiceReference);
		
		ServiceReference vehicleServiceServiceReference = bundleContext.getServiceReference(VehicleService.class.getName());
		vehicleService = (VehicleService) bundleContext.getService(vehicleServiceServiceReference);
	}

	@SuppressWarnings("unused")
	private void loadData() throws IllegalArgumentException, Exception {

		driverField
				.setItemCaptionMode(Select.ITEM_CAPTION_MODE_EXPLICIT_DEFAULTS_ID);
		BeanItemContainer<EmployeeAgent> driverDatasource;

		driverDatasource = new BeanItemContainer<EmployeeAgent>(
				EmployeeAgent.class, employeeAgentService.getAll());

		driverField.setContainerDataSource(driverDatasource);

		for (EmployeeAgent employeeAgent : driverDatasource.getItemIds())
			driverField.setItemCaption(employeeAgent, employeeAgent.getName()
					+ " " + employeeAgent.getSurname());

		vehicleField
				.setItemCaptionMode(Select.ITEM_CAPTION_MODE_EXPLICIT_DEFAULTS_ID);

		BeanItemContainer<Vehicle> vehicleDatasource;

		vehicleDatasource = new BeanItemContainer<Vehicle>(Vehicle.class,
				vehicleService.getAll());

		vehicleField.setContainerDataSource(vehicleDatasource);

		for (Vehicle vehicle : vehicleDatasource.getItemIds())
			vehicleField.setItemCaption(vehicle, vehicle.getVehicleNumber()
					+ " " + vehicle.getVehicleType().getDescription() + " "
					+ vehicle.getSupplier().getName());

	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("-1px");
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);

		// top-level component properties
		setWidth("100.0%");
		setHeight("-1px");

		// codeField
		codeField = new TextField();
		codeField.setCaption("Código");
		codeField.setImmediate(false);
		codeField.setWidth("50.0%");
		codeField.setHeight("-1px");
		codeField.setSecret(false);
		codeField.setRequired(true);
		codeField.setRequiredError(codeField.getCaption()
				+ " es un campo requerido");
		codeField.setNullRepresentation("");
		mainLayout.addComponent(codeField);

		// descriptionField
		descriptionField = new TextArea();
		descriptionField.setCaption("Descripción");
		descriptionField.setImmediate(false);
		descriptionField.setWidth("100.0%");
		descriptionField.setHeight("120px");
		descriptionField.setNullRepresentation("");
		mainLayout.addComponent(descriptionField);

		// stopsField
		stopsField = new StopsField(); 
		stopsField.setImmediate(false);
		stopsField.setWidth("100.0%");
		stopsField.setHeight("250px");
		stopsField.setRequired(true);
		mainLayout.addComponent(stopsField);

		// stopDatesLayout
		stopDatesLayout = buildStopDatesLayout();
		mainLayout.addComponent(stopDatesLayout);

		// resourcesLayout
		resourcesLayout = buildResourcesLayout();
		mainLayout.addComponent(resourcesLayout);

		return mainLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildStopDatesLayout() {
		// common part: create layout
		stopDatesLayout = new HorizontalLayout();
		stopDatesLayout.setImmediate(false);
		stopDatesLayout.setWidth("100.0%");
		stopDatesLayout.setHeight("-1px");
		stopDatesLayout.setMargin(false);
		stopDatesLayout.setSpacing(true);

		// startDateField
		stopCheckoutDateField = new DateField();
		stopCheckoutDateField.setCaption("Salida");
		stopCheckoutDateField.setImmediate(false);
		stopCheckoutDateField.setWidth("100.0%");
		stopCheckoutDateField.setHeight("-1px");
		stopCheckoutDateField.setInvalidAllowed(false);
		stopCheckoutDateField.setResolution(2);
		stopCheckoutDateField.setRequired(true);
		stopCheckoutDateField.setRequiredError(stopCheckoutDateField.getCaption()
				+ " es un campo requerido");
		stopDatesLayout.addComponent(stopCheckoutDateField);
		stopDatesLayout.setExpandRatio(stopCheckoutDateField, 1.0f);

		// stopDateField
		stopArrivalDateField = new DateField();
		stopArrivalDateField.setCaption("Llegada");
		stopArrivalDateField.setImmediate(false);
		stopArrivalDateField.setWidth("100.0%");
		stopArrivalDateField.setHeight("-1px");
		stopArrivalDateField.setInvalidAllowed(false);
		stopArrivalDateField.setResolution(2);
		stopArrivalDateField.setRequired(true);
		stopArrivalDateField.setRequiredError(stopArrivalDateField.getCaption()
				+ " es un campo requerido");
		stopDatesLayout.addComponent(stopArrivalDateField);
		stopDatesLayout.setExpandRatio(stopArrivalDateField, 1.0f);

		return stopDatesLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildResourcesLayout() {
		// common part: create layout
		resourcesLayout = new HorizontalLayout();
		resourcesLayout.setImmediate(false);
		resourcesLayout.setWidth("100.0%");
		resourcesLayout.setHeight("-1px");
		resourcesLayout.setMargin(false);
		resourcesLayout.setSpacing(true);

		// vehicleField
		vehicleField = new ComboBox();
		vehicleField.setCaption("Vehículo");
		vehicleField.setImmediate(false);
		vehicleField.setWidth("100.0%");
		vehicleField.setHeight("-1px");
		resourcesLayout.addComponent(vehicleField);
		resourcesLayout.setExpandRatio(vehicleField, 1.0f);

		// driverField
		driverField = new ComboBox();
		driverField.setCaption("Conductor");
		driverField.setImmediate(false);
		driverField.setWidth("100.0%");
		driverField.setHeight("-1px");
		resourcesLayout.addComponent(driverField);
		resourcesLayout.setExpandRatio(driverField, 1.0f);

		return resourcesLayout;
	}

	
}
