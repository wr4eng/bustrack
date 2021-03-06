package com.thingtrack.bustrack.view.web.form.field;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.vaadin.addon.customfield.CustomField;
import org.vaadin.cssinject.CSSInject;

import com.thingtrack.bustrack.domain.Route;
import com.thingtrack.bustrack.domain.Vehicle;
import com.thingtrack.bustrack.domain.WorksheetLine;
import com.thingtrack.bustrack.service.api.VehicleService;
import com.thingtrack.konekti.domain.Address;
import com.thingtrack.konekti.domain.EmployeeAgent;
import com.thingtrack.konekti.domain.Organization;
import com.thingtrack.konekti.domain.Service;
import com.thingtrack.konekti.service.api.EmployeeAgentService;
import com.thingtrack.konekti.service.api.ServiceService;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Field;
import com.vaadin.ui.Label;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
public class WorksheetLineCollectionField extends CustomField {

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private TreeTable assignableServiceTreeTable;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	private Hashtable<Object, CheckBox> columnFields;

	private Date worksheetDate;

	private HierarchicalContainer assignableServiceContainer;

	/* Enterprise services */
	private ServiceService serviceService;

	private VehicleService vehicleService;

	private EmployeeAgentService employeeAgentService;

	private Organization organization;

	private List<WorksheetLine> worksheetLines;

	private EmployeeAgent driver;

	public void setDriver(EmployeeAgent driver) {
		this.driver = driver;
	}

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public WorksheetLineCollectionField() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		CSSInject cssInject = new CSSInject("" +
				".v-sa .v-button-info:focus { " +
					"outline-offset: -3px;" +
				"}" +
				"" +
				".v-button-info .v-button-caption{ " +
					"color: inherit; " +
					"text-align: left; " +
					"text-decoration: none; " +
				"}" +
				".v-button.v-button-info, .v-button.v-button-info:focus, .v-button.v-button-info:active, .v-button-info.v-pressed, .v-disabled.v-button.v-button-info, .v-button.v-button-info .v-button-wrap, .v-button.v-button-link:focus .v-button-wrap, .v-button.v-button-link:active .v-button-wrap, .v-button-info.v-pressed .v-button-wrap, .v-disabled.v-button.v-button-info .v-button-wrap {" +
					"background: none repeat scroll 0 0 transparent;" +
					"height: auto;" +
					"line-height: inherit;" +
					"padding: 0;" +
				"}" +
				".v-button.v-button-info.v-disabled, .v-button.v-button-info.v-disabled .v-button-wrap {" +
					"cursor: default;" +
				"}" +
				".v-button-info .v-button-caption, .v-nativebutton-info .v-nativebutton-caption {" +
					"font-size: 12px;" +
					"font-weight: normal;" +
					"line-height: inherit;" +
					"text-shadow: none;" +
				"}" +
				"+.v-button-info:focus .v-button-caption, .v-nativebutton-info:focus .v-nativebutton-caption {" +
				"}" +
				".v-ie6 .v-button-info, .v-ie6 .black .v-button-info {" +
					"background: none repeat scroll 0 0 transparent;" +
					"border: medium none;" +
					"height: auto;" +
					"line-height: normal;" +
					"padding: 0;" +
				"}" +
				".v-ie6 .v-button-info .v-button-wrap, .v-ie6 .black .v-button-info .v-button-wrap {" +
					"height: auto;" +
					"padding: 0" +
				"}"
		);
	
		mainLayout.addComponent(cssInject);

		this.worksheetLines = new ArrayList<WorksheetLine>();

		columnFields = new Hashtable<Object, CheckBox>();

		assignableServiceContainer = new HierarchicalContainer();

		assignableServiceContainer.addContainerProperty("code", String.class,
				null);

		assignableServiceContainer.addContainerProperty("description",
				String.class, null);

		assignableServiceContainer.addContainerProperty("passengers",
				String.class, null);

		assignableServiceContainer.addContainerProperty("serviceType",
				String.class, null);

		assignableServiceContainer.addContainerProperty("client", String.class,
				null);

		getServices();
	}

	@Override
	public Class<?> getType() {

		if (getPropertyDataSource() instanceof Property)
			getPropertyDataSource().getType();

		return List.class;
	}

	@Override
	public void setPropertyDataSource(Property newDataSource) {

		if (newDataSource.getValue() instanceof List)
			for (WorksheetLine worksheetLine : (List<WorksheetLine>) newDataSource
					.getValue())
				this.worksheetLines.add(worksheetLine);

		fillAssignableServiceTreeTable();
		assignableServiceTreeTable
				.setContainerDataSource(assignableServiceContainer);

		assignableServiceTreeTable.addGeneratedColumn(
				InfoColumn.INFO_COLUMN_ID, new InfoColumn());

		assignableServiceTreeTable.addGeneratedColumn(
				DriverAssignmentColumn.DRIVER_ASSIGNMENT_COLUMN_ID,
				new DriverAssignmentColumn());

		assignableServiceTreeTable.addGeneratedColumn(
				VehicleAssignmentColumn.VEHICLE_ASSIGNMENT_COLUMN_ID,
				new VehicleAssignmentColumn());

		assignableServiceTreeTable.addGeneratedColumn(
				StopDateColumnGenerator.CHECKOUT_STOP_COLUMN_ID,
				new StopDateColumnGenerator());

		assignableServiceTreeTable.addGeneratedColumn(
				StopAddressColumnGenerator.ORIGIN_STOP_COLUMN_ID,
				new StopAddressColumnGenerator());

		assignableServiceTreeTable.addGeneratedColumn(
				StopDateColumnGenerator.ARRIVAL_STOP_COLUMN_ID,
				new StopDateColumnGenerator());

		assignableServiceTreeTable.addGeneratedColumn(
				StopAddressColumnGenerator.DESTINATION_STOP_COLUMN_ID,
				new StopAddressColumnGenerator());

		assignableServiceTreeTable.setVisibleColumns(new String[] {
				InfoColumn.INFO_COLUMN_ID, "passengers", "serviceType",
				"client", StopDateColumnGenerator.CHECKOUT_STOP_COLUMN_ID,
				StopAddressColumnGenerator.ORIGIN_STOP_COLUMN_ID,
				StopDateColumnGenerator.ARRIVAL_STOP_COLUMN_ID,
				StopAddressColumnGenerator.DESTINATION_STOP_COLUMN_ID,
				DriverAssignmentColumn.DRIVER_ASSIGNMENT_COLUMN_ID,
				VehicleAssignmentColumn.VEHICLE_ASSIGNMENT_COLUMN_ID });

		assignableServiceTreeTable.setColumnHeaders(new String[] { "Codigo",
				"Pasajeros", "Tipo", "Cliente", "Salida", "Origen", "Llegada",
				"Destinao", "Conductor", "Vehiculo" });

		// Column width
		assignableServiceTreeTable
				.setColumnWidth(InfoColumn.INFO_COLUMN_ID, 200);
		assignableServiceTreeTable.setColumnWidth("client", 250);

		assignableServiceTreeTable.setColumnWidth(
				DriverAssignmentColumn.DRIVER_ASSIGNMENT_COLUMN_ID, 200);
		assignableServiceTreeTable.setColumnWidth(
				VehicleAssignmentColumn.VEHICLE_ASSIGNMENT_COLUMN_ID, 200);
		assignableServiceTreeTable.setColumnWidth(
				StopDateColumnGenerator.CHECKOUT_STOP_COLUMN_ID, 100);
		assignableServiceTreeTable.setColumnWidth(
				StopAddressColumnGenerator.ORIGIN_STOP_COLUMN_ID, 200);
		assignableServiceTreeTable.setColumnWidth(
				StopDateColumnGenerator.ARRIVAL_STOP_COLUMN_ID, 100);
		assignableServiceTreeTable.setColumnWidth(
				StopAddressColumnGenerator.DESTINATION_STOP_COLUMN_ID, 200);

		assignableServiceTreeTable.setEditable(true);

		assignableServiceTreeTable
				.setTableFieldFactory(new TableFieldFactory() {

					@Override
					public Field createField(Container container,
							Object itemId, Object propertyId,
							Component uiContext) {

						if (!"code".equals(propertyId))
							return null;

						TextField codeField = new TextField();
						codeField.setReadOnly(true);

						if (itemId instanceof Service) {

							Service serviceItem = (Service) itemId;
							codeField.setValue(serviceItem.getCode());
							codeField
									.setIcon(new ThemeResource(
											"images/icons/worksheet-module/calendar-select.png"));
						}

						if (itemId instanceof Route) {

							Route routeItem = (Route) itemId;
							codeField.setValue(routeItem.getCode());
							codeField
									.setIcon(new ThemeResource(
											"images/icons/worksheet-module/road-sign.png"));

						}

						return codeField;
					}
				});
		assignableServiceTreeTable.setEditable(false);

		super.setPropertyDataSource(newDataSource);
	}

	@Override
	public Object getValue() {
		return this.worksheetLines;
	}

	@Override
	protected boolean isEmpty() {
		return this.worksheetLines.isEmpty();
	}

	public void setWorksheetDate(Date worksheetDate) {

		this.worksheetDate = worksheetDate;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
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

		// assignableTreeTable
		assignableServiceTreeTable = new TreeTable();
		assignableServiceTreeTable.setImmediate(false);
		assignableServiceTreeTable.setWidth("100.0%");
		assignableServiceTreeTable.setHeight("100.0%");
		mainLayout.addComponent(assignableServiceTreeTable);
		mainLayout.setExpandRatio(assignableServiceTreeTable, 1.0f);

		return mainLayout;
	}

	private void getServices() {

		BundleContext bundleContext = FrameworkUtil.getBundle(this.getClass())
				.getBundleContext();

		ServiceReference serviceServiceReference = bundleContext
				.getServiceReference(ServiceService.class.getName());

		ServiceReference vehicleServiceReference = bundleContext
				.getServiceReference(VehicleService.class.getName());

		ServiceReference employeeAgentReference = bundleContext
				.getServiceReference(EmployeeAgentService.class.getName());

		this.serviceService = ServiceService.class.cast(bundleContext
				.getService(serviceServiceReference));

		this.vehicleService = VehicleService.class.cast(bundleContext
				.getService(vehicleServiceReference));

		this.employeeAgentService = EmployeeAgentService.class
				.cast(bundleContext.getService(employeeAgentReference));

	}

	private void fillAssignableServiceTreeTable() {

		try {
			List<Service> assignableServices = serviceService
					.getCandidatesForAssignment(organization, driver,
							worksheetDate);

			for (Service service : assignableServices) {

				assignableServiceContainer.addItem(service);

				assignableServiceContainer
						.getContainerProperty(service, "code").setValue(
								service.getCode());
				assignableServiceContainer.getContainerProperty(service,
						"description").setValue(service.getDescription());
				assignableServiceContainer.getContainerProperty(service,
						"description").setValue(service.getDescription());
				assignableServiceContainer.getContainerProperty(service,
						"passengers").setValue(service.getPassengers());
				assignableServiceContainer.getContainerProperty(service,
						"serviceType").setValue(
						service.getServiceType().getDescription());

				if (service.getClient() != null)
					assignableServiceContainer.getContainerProperty(service,
							"client").setValue(service.getClient().getName());

				for (Route route : service.getRoutes()) {

					assignableServiceContainer.addItem(route);

					assignableServiceContainer.getContainerProperty(route,
							"code").setValue(route.getCode());
					assignableServiceContainer.getContainerProperty(route,
							"description").setValue(route.getDescription());

					assignableServiceContainer.setParent(route, service);

					assignableServiceContainer.setChildrenAllowed(route, false);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/** PRIVATE CLASSES TO GENERATE DYNAMIC COLUMNS **/
	private class DriverAssignmentColumn implements ColumnGenerator {

		static final String DRIVER_ASSIGNMENT_COLUMN_ID = "driver-assignment-column-id";

		@Override
		public Object generateCell(Table source, Object itemId, Object columnId) {

			if (itemId instanceof Route) {

				final Route routeItem = (Route) itemId;

				ComboBox availableDriverField = new ComboBox();

				availableDriverField
						.setInputPrompt("Asignar conductor a la ruta");
				availableDriverField.setImmediate(true);
				availableDriverField.setWidth("100%");
				availableDriverField
						.setItemCaptionMode(Select.ITEM_CAPTION_MODE_EXPLICIT_DEFAULTS_ID);

				BeanItemContainer<EmployeeAgent> availableDriverBeanItemContainer = new BeanItemContainer<EmployeeAgent>(
						EmployeeAgent.class);

				try {

					List<EmployeeAgent> availableDrivers = employeeAgentService
							.getDrivers(organization);

					availableDriverBeanItemContainer.addAll(availableDrivers);
					availableDriverField
							.setContainerDataSource(availableDriverBeanItemContainer);

					for (EmployeeAgent driver : availableDriverBeanItemContainer
							.getItemIds())
						availableDriverField.setItemCaption(driver,
								driver.getWorkNumber() + " (" + driver.getNif()
										+ ") " + driver.getName() + ", "
										+ driver.getSurname());

					availableDriverField.addListener(new ValueChangeListener() {

						@Override
						public void valueChange(
								com.vaadin.data.Property.ValueChangeEvent event) {

							EmployeeAgent driver = (EmployeeAgent) event
									.getProperty().getValue();
							
							routeItem.setDriver(driver);
							
							if(WorksheetLineCollectionField.this.driver.equals(driver)){
								addRouteToWorksheet(routeItem);
							}								
							else{
								removeRouteToWorksheet(routeItem);
							}
							
							
							
						}
					});

				} catch (Exception e) {
					e.printStackTrace();
				}

				return availableDriverField;
			}

			return null;
		}

	}

	private class VehicleAssignmentColumn implements ColumnGenerator {

		static final String VEHICLE_ASSIGNMENT_COLUMN_ID = "vehicle-assignment-column-id";

		@Override
		public Object generateCell(Table source, Object itemId, Object columnId) {

			if (itemId instanceof Route) {

				final Route routeItem = (Route) itemId;

				ComboBox availableVehiclesField = new ComboBox();
				availableVehiclesField
						.setInputPrompt("Asignar vehiculo a la ruta");
				availableVehiclesField.setImmediate(true);
				availableVehiclesField.setWidth("100%");

				availableVehiclesField
						.setItemCaptionMode(Select.ITEM_CAPTION_MODE_EXPLICIT_DEFAULTS_ID);

				BeanItemContainer<Vehicle> availableVehicleBeanItemContainer = new BeanItemContainer<Vehicle>(
						Vehicle.class);

				try {
					List<Vehicle> availableVehicles = vehicleService
							.getAvailableVehicles(organization, routeItem);
					availableVehicleBeanItemContainer.addAll(availableVehicles);
					availableVehiclesField
							.setContainerDataSource(availableVehicleBeanItemContainer);

					for (Vehicle vehicle : availableVehicleBeanItemContainer
							.getItemIds())
						availableVehiclesField.setItemCaption(
								vehicle,
								vehicle.getVehicleNumber() + " ("
										+ vehicle.getEnrollment() + ") "
										+ vehicle.getSupplier().getName());

					availableVehiclesField
							.addListener(new ValueChangeListener() {

								@Override
								public void valueChange(
										com.vaadin.data.Property.ValueChangeEvent event) {

									Vehicle vehicle = (Vehicle) event
											.getProperty().getValue();
									
									routeItem.setVehicle(vehicle);
									
									if(routeItem.getDriver() == driver)
										addRouteToWorksheet(routeItem);
								}	
							});

				} catch (Exception e) {
					e.printStackTrace();
				}

				return availableVehiclesField;
			}

			return null;
		}
	}

	private class InfoColumn implements ColumnGenerator {

		static final String INFO_COLUMN_ID = "info-column-id";

		@Override
		public Object generateCell(Table source, Object itemId, Object columnId) {

			Button infoCellField = new Button();
			infoCellField.setStyleName("info");
			
			if (itemId instanceof Service) {

				infoCellField.setCaption(((Service)itemId).getCode());
				infoCellField.setIcon(new ThemeResource(
						"images/icons/worksheet-module/calendar-select.png"));
			}
			if (itemId instanceof Route) {

				infoCellField.setCaption(((Route)itemId).getCode());
				infoCellField.setIcon(new ThemeResource(
						"images/icons/worksheet-module/road-sign.png"));
			}

			return infoCellField;

		}
	}

	private class StopAddressColumnGenerator implements Table.ColumnGenerator {

		static final String ORIGIN_STOP_COLUMN_ID = "origin";
		static final String DESTINATION_STOP_COLUMN_ID = "destination";

		@Override
		public Object generateCell(Table source, Object itemId, Object columnId) {

			Label stopAdressLabel = new Label();

			if (!(itemId instanceof Route))
				return stopAdressLabel;

			Route route = (Route) itemId;

			if (route.getStops() == null)
				return stopAdressLabel;

			if (route.getStops().size() < 2)
				return stopAdressLabel;

			Address stopAddress = null;

			if (ORIGIN_STOP_COLUMN_ID.equals(columnId)
					&& route.getStops().get(0).getStopAddress() != null) {

				stopAddress = route.getStops().get(0).getStopAddress();
				stopAdressLabel.setValue(stopAddress.getStreet());
				return stopAdressLabel;
			}
			if (DESTINATION_STOP_COLUMN_ID.equals(columnId)
					&& route.getStops().get(1).getStopAddress() != null) {

				stopAddress = route.getStops().get(1).getStopAddress();
				stopAdressLabel.setValue(stopAddress.getStreet());
				return stopAdressLabel;
			}

			return stopAdressLabel;
		}

	}

	private class StopDateColumnGenerator implements Table.ColumnGenerator {

		static final String ARRIVAL_STOP_COLUMN_ID = "arrival";
		static final String CHECKOUT_STOP_COLUMN_ID = "checkout";

		private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"dd/MM/yyyy HH:mm");

		@Override
		public Object generateCell(Table source, Object itemId, Object columnId) {

			Label stopDateField = new Label();

			if (!(itemId instanceof Route))
				return stopDateField;

			Route route = (Route) itemId;

			if (route.getStops() == null)
				return stopDateField;

			if (route.getStops().size() < 2)
				return stopDateField;

			String formattedDate = null;
			if (CHECKOUT_STOP_COLUMN_ID.equals(columnId)
					&& route.getStops().get(0).getStopCheckoutDate() != null) {

				formattedDate = simpleDateFormat.format(route.getStops().get(0)
						.getStopCheckoutDate());
				stopDateField.setValue(formattedDate);
			}
			if (ARRIVAL_STOP_COLUMN_ID.equals(columnId)
					&& route.getStops().get(1).getStopArrivalDate() != null) {

				formattedDate = simpleDateFormat.format(route.getStops().get(1)
						.getStopArrivalDate());
				stopDateField.setValue(formattedDate);
			}

			return stopDateField;
		}
	}

	private boolean isAssigned(Service service) {

		for (WorksheetLine worksheetLine : worksheetLines) {

			if (worksheetLine.getService().equals(service))
				return true;
		}

		return false;
	}

	private boolean isAssigned(Route route) {

		for (WorksheetLine worksheetLine : worksheetLines) {

			if (route.getServices().contains(worksheetLine.getService()))
				return true;
		}

		return false;

	}

	private WorksheetLine getWorksheetLine(Service service) {

		for (WorksheetLine worksheetLine : worksheetLines) {

			if (worksheetLine.getService().equals(service))
				return worksheetLine;
		}

		return null;
	}

	private void addRouteToWorksheet(Route route) {

		route.setDriver(driver);

		Service parentService = (Service) assignableServiceTreeTable
				.getParent(route);

		WorksheetLine parentWorksheetLine = getWorksheetLine(parentService);

		if (parentWorksheetLine == null) {

			parentWorksheetLine = new WorksheetLine();
			parentWorksheetLine.setService(parentService);
		}

		worksheetLines.add(parentWorksheetLine);
	}

	private void removeRouteToWorksheet(Route route) {

		route.setDriver(null);

		Service parentService = (Service) assignableServiceTreeTable
				.getParent(route);

		WorksheetLine parentWorksheetLine = getWorksheetLine(parentService);

		worksheetLines.remove(parentWorksheetLine);
	}

}
