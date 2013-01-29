package com.thingtrack.bustrack.view.module.scheduler.internal.form;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.vaadin.addon.customfield.CustomField;
import org.vaadin.addon.formbinder.ViewBoundForm;
import org.vaadin.cssinject.CSSInject;

import com.thingtrack.bustrack.domain.Route;
import com.thingtrack.bustrack.domain.Stop;
import com.thingtrack.bustrack.view.module.scheduler.SchedulerModule;
import com.thingtrack.konekti.domain.Address;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Validator.EmptyValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.MethodProperty;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DateField.UnparsableDateString;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class RoutesField extends CustomField {

	private VerticalLayout mainLayout;
	private final Table routeTable;
	private HorizontalLayout navigationLayout;
	private Button addRouteButton;
	private Button removeRouteButton;
	private BeanItemContainer<Route> routeTableContainer;

	@SuppressWarnings("unused")
	private Date startFirstRoute;

	@SuppressWarnings("unused")
	private Date endLastRoute;

	public RoutesField() {

		setCaption("Rutas");

		routeTable = new Table();
		routeTable.setImmediate(true);
		routeTable.setHeight("100px");
		routeTable.setWidth("100%");
		routeTable.setSelectable(true);

		routeTable.addListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {

				if (!event.isDoubleClick())
					return;

				BeanItem<Route> selectedRoute = (BeanItem<Route>) event
						.getItem();

				if (selectedRoute == null)
					return;

				Window window = null;
				try {
					window = routePopupWindow(selectedRoute.getBean());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				getApplication().getMainWindow().addWindow(window);

			}
		});

		// Define the routes data source
		routeTableContainer = new BeanItemContainer<Route>(Route.class);
		routeTableContainer
				.addNestedContainerProperty("routeStatus.description");

		routeTable.setContainerDataSource(routeTableContainer);

		routeTable.addGeneratedColumn(
				StopDateColumnGenerator.CHECKOUT_STOP_COLUMN_ID,
				new StopDateColumnGenerator());
		routeTable.addGeneratedColumn(
				StopAddressColumnGenerator.ORIGIN_STOP_COLUMN_ID,
				new StopAddressColumnGenerator());
		routeTable.addGeneratedColumn(
				StopDateColumnGenerator.ARRIVAL_STOP_COLUMN_ID,
				new StopDateColumnGenerator());
		routeTable.addGeneratedColumn(
				StopAddressColumnGenerator.DESTINATION_STOP_COLUMN_ID,
				new StopAddressColumnGenerator());
		routeTable.addGeneratedColumn(DriverColumnGenerator.DRIVER_COLUMN_ID,
				new DriverColumnGenerator());
		routeTable.addGeneratedColumn(VehicleColumnGenerator.VEHICLE_COLUMN_ID,
				new VehicleColumnGenerator());

		routeTable.setVisibleColumns(new String[] {
				StopDateColumnGenerator.CHECKOUT_STOP_COLUMN_ID,
				StopAddressColumnGenerator.ORIGIN_STOP_COLUMN_ID,
				StopDateColumnGenerator.ARRIVAL_STOP_COLUMN_ID,
				StopAddressColumnGenerator.DESTINATION_STOP_COLUMN_ID,
				DriverColumnGenerator.DRIVER_COLUMN_ID,
				VehicleColumnGenerator.VEHICLE_COLUMN_ID,
				"routeStatus.description" });

		routeTable.setColumnHeaders(new String[] { "Salida", "Origen",
				"LLegada", "Destino", "Conductor", "Vehículo", "Estado" });

		// Allow the user to collapse and uncollapse columns
		routeTable.setColumnCollapsingAllowed(true);
		routeTable.setColumnCollapsed(DriverColumnGenerator.DRIVER_COLUMN_ID,
				true);
		routeTable.setColumnCollapsed(VehicleColumnGenerator.VEHICLE_COLUMN_ID,
				true);
		routeTable.setColumnCollapsed("routeStatus.description", true);

		mainLayout = new VerticalLayout();
		mainLayout.setSpacing(true);

		mainLayout.addComponent(routeTable);

		navigationLayout = new HorizontalLayout();

		navigationLayout.setSpacing(true);

		addRouteButton = new Button("Añadir");
		addRouteButton.setIcon(new ThemeResource(SchedulerModule.MODULE_ICONS_PATH + "map--plus.png"));
		addRouteButton.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				// Create a new route domain object
				Route route = new Route();

				// Stops
				List<Stop> stops = new ArrayList<Stop>();

				Stop origin = null;
				Stop destination = new Stop();

				// Get the last route if there is any
				Route lastRoute = routeTableContainer.lastItemId();

				if (lastRoute != null) {

					// The origin stop of the next route will be the destination
					// stop of last route
					origin = lastRoute.getStops().get(1);

					// Define the next stop Location
					Address lastStopAddress = lastRoute.getStops().get(1)
							.getStopAddress();
					origin.setStopAddress(lastStopAddress);

					// Define checkout and arrival dates
					Date suggestedCheckoutDate = lastRoute.getStops().get(1)
							.getStopArrivalDate();
					origin.setStopCheckoutDate(suggestedCheckoutDate);

					Date suggestedArrivalDate = endLastRoute != null ? endLastRoute
							: suggestedCheckoutDate;
					destination.setStopArrivalDate(suggestedArrivalDate);

				} else {

					origin = new Stop();

					// Default dates
					Date defaultCheckoutDate = startFirstRoute != null ? startFirstRoute
							: new Date();

					origin.setStopCheckoutDate(defaultCheckoutDate);

					Date defaultArrivalDate = endLastRoute != null ? endLastRoute
							: new Date();
					destination.setStopArrivalDate(defaultArrivalDate);
				}

				try {
					route.addStop(origin);
					route.addStop(destination);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				Window window = null;
				try {
					window = routePopupWindow(route);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				getApplication().getMainWindow().addWindow(window);

			}
		});

		removeRouteButton = new Button("Borrar");
		removeRouteButton.setIcon(new ThemeResource(SchedulerModule.MODULE_ICONS_PATH +  "map--minus.png"));
		removeRouteButton.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				Object selected = routeTable.getValue();
				Item item = routeTable.getItem(selected);

				if (item != null && item instanceof BeanItem<?>) {
					// Change selection
					Object newselected = routeTable.nextItemId(selected);
					if (newselected == null)
						newselected = routeTable.prevItemId(selected);
					routeTable.select(newselected);
					routeTable.focus();

					// Remove the item from the container
					routeTableContainer.removeItem(selected);
				}
			}
		});

		navigationLayout.addComponent(addRouteButton);
		navigationLayout.addComponent(removeRouteButton);

		mainLayout.addComponent(navigationLayout);
		mainLayout.setComponentAlignment(navigationLayout,
				Alignment.BOTTOM_LEFT);

		setCompositionRoot(mainLayout);
	}

	// @SuppressWarnings("unchecked")
	@Override
	public void setPropertyDataSource(Property newDataSource) {
		List<Route> routes = (List<Route>) newDataSource.getValue();

		routeTableContainer.removeAllItems();
		routeTableContainer.addAll(routes);

		super.setPropertyDataSource(newDataSource);
	}

	@Override
	public Object getValue() {

		return new ArrayList<Route>(
				(Collection<? extends Route>) routeTable.getItemIds());
	}

	@Override
	public Class<?> getType() {
		
		Property propertyDataSource = getPropertyDataSource();
		
		if (propertyDataSource != null) {
			return propertyDataSource.getType();
		}
		
		return List.class;
	}

	@Override
	protected boolean isEmpty() {
		return routeTableContainer.getItemIds().isEmpty();
	}

	public void setStartFirstRoute(Date startFirstRoute) {
		this.startFirstRoute = startFirstRoute;
	}

	public void setEndLastRoute(Date endLastRoute) {
		this.endLastRoute = endLastRoute;
	}

	private Window routePopupWindow(Route route)
			throws IllegalArgumentException, Exception {

		// Create popup window and add a form in it.
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);

		CSSInject cssInject = new CSSInject(
				".v-caption-v-stopsfield{ display:none; } .olControlAttribution{ display:none; }");
		layout.addComponent(cssInject);
		final Window window = new Window("Añadir ruta", layout);
		window.setWidth("530px");
		window.setResizable(false);
		window.setModal(true);
		window.center();

		RouteView routeView = new RouteView();
		final ViewBoundForm form = new ViewBoundForm(routeView);

		// Wrap the calendar event to a BeanItem and pass it to the form
		final BeanItem<Route> item = new BeanItem<Route>(route);
		item.addItemProperty("stopCheckoutDate", new MethodProperty<Stop>(route
				.getStops().get(0), "stopCheckoutDate"));
		item.addItemProperty("stopArrivalDate", new MethodProperty<Stop>(route
				.getStops().get(1), "stopArrivalDate"));

		// Set the form to act immediately on user input. This is
		// necessary for the validation of the fields to occur immediately
		// when the input focus changes and not just on commit.
		form.setImmediate(true);
		form.setWriteThrough(false);
		form.setItemDataSource(item);

		layout.addComponent(form);
		HorizontalLayout footerLayout = (HorizontalLayout) form.getFooter();
		footerLayout.setWidth("100%");
		footerLayout.setMargin(true);

		HorizontalLayout buttonsLayout = new HorizontalLayout();
		buttonsLayout.setSpacing(true);

		footerLayout.addComponent(buttonsLayout);
		footerLayout.setComponentAlignment(buttonsLayout,
				Alignment.MIDDLE_RIGHT);

		buttonsLayout.addComponent(new Button("Guardar",
				new Button.ClickListener() {

					public void buttonClick(ClickEvent event) {

						try {
							form.commit();
						} catch (EmptyValueException e) {
							return;
						} catch (UnparsableDateString e) {
							return;
						}

						Route route = (Route) item.getBean();

						// Update event provider's data source
						if (routeTableContainer.containsId(route)) {
							Route prevRoute = routeTableContainer
									.prevItemId(route);
							routeTableContainer.removeItem(route);
							routeTableContainer.addItemAfter(prevRoute, route);
						} else
							routeTableContainer.addBean(route);

						getApplication().getMainWindow().removeWindow(window);
					}
				}));
		buttonsLayout.addComponent(new Button("Cancelar",
				new Button.ClickListener() {

					public void buttonClick(ClickEvent event) {
						getApplication().getMainWindow().removeWindow(window);
					}
				}));

		return window;
	}

	/** PRIVATE CLASSES TO GENERATE DYNAMIC COLUMNS FOR THE ROUTE STOPS **/
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

	private class DriverColumnGenerator implements Table.ColumnGenerator {

		static final String DRIVER_COLUMN_ID = "driver";

		@Override
		public Object generateCell(Table source, Object itemId, Object columnId) {

			Label driverLabel = new Label();

			if (!(itemId instanceof Route))
				return driverLabel;

			Route route = (Route) itemId;

			if (route.getDriver() == null)
				return driverLabel;

			String driverName = route.getDriver().getName() + " "
					+ route.getDriver().getSurname();
			driverLabel.setValue(driverName);

			return driverLabel;
		}

	}

	private class VehicleColumnGenerator implements Table.ColumnGenerator {

		static final String VEHICLE_COLUMN_ID = "vehicle";

		@Override
		public Object generateCell(Table source, Object itemId, Object columnId) {

			Label vehicleLabel = new Label();

			if (!(itemId instanceof Route))
				return vehicleLabel;

			Route route = (Route) itemId;

			if (route.getVehicle() == null)
				return vehicleLabel;

			String vehicleDescription = route.getVehicle().getVehicleNumber()
					+ " "
					+ route.getVehicle().getVehicleType().getDescription()
					+ " " + route.getVehicle().getSupplier().getName();
			vehicleLabel.setValue(vehicleDescription);

			return vehicleLabel;
		}

	}
}
