package com.thingtrack.bustrack.view.module.servicedesigner.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.vaadin.addon.formbinder.ViewBoundForm;
import org.vaadin.vol.Bounds;
import org.vaadin.vol.GoogleStreetMapLayer;
import org.vaadin.vol.Marker;
import org.vaadin.vol.MarkerLayer;
import org.vaadin.vol.OpenLayersMap;
import org.vaadin.vol.Point;
import org.vaadin.vol.PolyLine;
import org.vaadin.vol.Popup;
import org.vaadin.vol.Popup.PopupStyle;
import org.vaadin.vol.Style;
import org.vaadin.vol.VectorLayer;

import com.invient.vaadin.charts.InvientCharts;
import com.invient.vaadin.charts.InvientCharts.DecimalPoint;
import com.invient.vaadin.charts.InvientCharts.Series;
import com.invient.vaadin.charts.InvientCharts.SeriesType;
import com.invient.vaadin.charts.InvientCharts.XYSeries;
import com.invient.vaadin.charts.InvientChartsConfig;
import com.invient.vaadin.charts.InvientChartsConfig.AxisBase.AxisTitle;
import com.invient.vaadin.charts.InvientChartsConfig.CategoryAxis;
import com.invient.vaadin.charts.InvientChartsConfig.ColumnConfig;
import com.invient.vaadin.charts.InvientChartsConfig.HorzAlign;
import com.invient.vaadin.charts.InvientChartsConfig.Legend;
import com.invient.vaadin.charts.InvientChartsConfig.NumberYAxis;
import com.invient.vaadin.charts.InvientChartsConfig.Position;
import com.invient.vaadin.charts.InvientChartsConfig.Stacking;
import com.invient.vaadin.charts.InvientChartsConfig.VertAlign;
import com.invient.vaadin.charts.InvientChartsConfig.XAxis;
import com.invient.vaadin.charts.InvientChartsConfig.YAxis;
import com.thingtrack.bustrack.domain.Stop;
import com.thingtrack.bustrack.view.module.servicedesigner.internal.model.Leg;
import com.thingtrack.bustrack.view.module.servicedesigner.internal.model.Location;
import com.thingtrack.bustrack.view.module.servicedesigner.internal.model.Maneuver;
import com.thingtrack.bustrack.view.module.servicedesigner.internal.model.MapCoordinates;
import com.thingtrack.bustrack.view.module.servicedesigner.internal.model.Route;
import com.thingtrack.bustrack.view.module.servicedesigner.internal.ui.CustomizedLocationTextField;
import com.thingtrack.bustrack.view.module.servicedesigner.internal.ui.OfferRequestLineViewForm;
import com.thingtrack.bustrack.view.module.servicedesigner.internal.ui.RouteForm;
import com.thingtrack.konekti.domain.Address;
import com.thingtrack.konekti.domain.Service;
import com.thingtrack.konekti.service.api.ServiceService;
import com.thingtrack.konekti.view.addon.data.BindingSource;
import com.thingtrack.konekti.view.addon.ui.AbstractView;
import com.thingtrack.konekti.view.addon.ui.NavigationToolbar;
import com.thingtrack.konekti.view.addon.ui.SliderView;
import com.thingtrack.konekti.view.addon.ui.NavigationToolbar.ClickNavigationEvent;
import com.thingtrack.konekti.view.addon.ui.NavigationToolbar.ClickUpButtonListener;
import com.thingtrack.konekti.view.kernel.IWorkbenchContext;
import com.thingtrack.konekti.view.kernel.ui.layout.IToolbar;
import com.thingtrack.konekti.view.kernel.ui.layout.IViewChangeListener;
import com.thingtrack.konekti.view.kernel.ui.layout.IViewContainer;
import com.thingtrack.konekti.view.kernel.ui.layout.ViewEvent;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.MouseEvents;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
public class ServiceDesignerView extends AbstractView implements
		com.vaadin.ui.Button.ClickListener, ClickUpButtonListener, IViewChangeListener {

	@AutoGenerated
	private HorizontalLayout mainLayout;
	@AutoGenerated
	private Panel panel_1;
	@AutoGenerated
	private VerticalLayout verticalLayout_2;
	@AutoGenerated
	private VerticalSplitPanel verticalSplitPanel_2;
	@AutoGenerated
	private TabSheet tabSheet;
	@AutoGenerated
	private OpenLayersMap routeOpenLayersMap;
	@AutoGenerated
	private VerticalLayout verticalLayout_1;
	@AutoGenerated
	private ViewBoundForm viewBoundForm;

	private Table maneuversTable;
	// Load the route's maneuvers
	private BeanItemContainer<Maneuver> maneuverBeanItemContainer;

	private GoogleStreetMapLayer googleStreetMapLayer; 
	private MarkerLayer stopsMakerLayer;
	private VectorLayer routeStrokeVectorLayer;

	private InvientCharts routeDistancesChart;
	private InvientCharts routeTimesChart;
	private InvientCharts routeFuelUsedChart;

	private RouteForm routeForm_1;

	private ServiceService serviceService;

	private IWorkbenchContext context;

	private NavigationToolbar navigationToolbar;

	private ServiceDesignerToolbar serviceDesignerToolbar;

	private Service selectedService;

	private Route designedRoute;

	private BindingSource<com.thingtrack.bustrack.domain.Route> bsRoutes = new BindingSource<com.thingtrack.bustrack.domain.Route>(
			com.thingtrack.bustrack.domain.Route.class, 1);

	private OfferRequestLineViewForm offerRequestLineViewForm;

	public void setSelectedService(Service selectedService) {
		this.selectedService = selectedService;

		// refresh datasource
		refreshBindindSource();
		injectBindingSource();
		
		//load the offer request for the designing instructions
		offerRequestLineViewForm.setService(selectedService);
	}

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public ServiceDesignerView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		verticalSplitPanel_2.setSplitPosition(100);

		// Map definition
		googleStreetMapLayer = new GoogleStreetMapLayer();
		stopsMakerLayer = new MarkerLayer();
		routeStrokeVectorLayer = new VectorLayer();

		// Apply Map Layers
		routeOpenLayersMap.addLayer(googleStreetMapLayer);
		routeOpenLayersMap.addLayer(routeStrokeVectorLayer);
		routeOpenLayersMap.addLayer(stopsMakerLayer);

		stopsMakerLayer.addListener(new ComponentAttachListener() {

			@Override
			public void componentAttachedToContainer(ComponentAttachEvent event) {

				Marker marker = (Marker) event.getAttachedComponent();
				routeForm_1.addStop(marker.getLon(), marker.getLat());

			}
		});

		routeOpenLayersMap.setCenter(43.32, 5.42);

		routeOpenLayersMap.addActionHandler(new OpenLayersActionHandler());

		// Build route charts
		routeDistancesChart = buildCharts("Distancia por ruta",
				"Kilómetros (km)");

		HorizontalLayout chartsLayout = new HorizontalLayout();
		chartsLayout.setMargin(true);

		chartsLayout.addComponent(routeDistancesChart);

		routeTimesChart = buildCharts("Tiempo por ruta", "Tiempo (seg)");
		chartsLayout.addComponent(routeTimesChart);

		// Service charts
		tabSheet.setStyleName(Reindeer.TABSHEET_MINIMAL);

		offerRequestLineViewForm = new OfferRequestLineViewForm();
		tabSheet.addTab(offerRequestLineViewForm, "Información de diseño");

		tabSheet.addTab(chartsLayout, "Gráficas");

		// Maneuvers Table
		maneuverBeanItemContainer = new BeanItemContainer<Maneuver>(
				Maneuver.class);

		maneuversTable = new Table();
		maneuversTable.setSizeFull();

		maneuversTable.setContainerDataSource(maneuverBeanItemContainer);

		maneuversTable.addGeneratedColumn(
				DirectionIconColumnGenerator.DIRECTION_ICON_COLUMN_ID,
				new DirectionIconColumnGenerator());

		maneuversTable.setVisibleColumns(new String[] {
				DirectionIconColumnGenerator.DIRECTION_ICON_COLUMN_ID,
				"narrative", "distance" });

		maneuversTable.setColumnHeaders(new String[] { "Indicación",
				"Descripción", "Distancia" });

		maneuversTable.setColumnAlignment(
				DirectionIconColumnGenerator.DIRECTION_ICON_COLUMN_ID,
				Table.ALIGN_CENTER);
		maneuversTable.setColumnAlignment("distance", Table.ALIGN_RIGHT);

		maneuversTable.addListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {

				Maneuver maneuver = (Maneuver) event.getItemId();

				ServiceDesignerView routePlannerLayout = (ServiceDesignerView) ServiceDesignerView.this
						.getData();

				// Draw in the map
				routePlannerLayout.createManeuverPopup(maneuver);
			}
		});

	}

	public ServiceDesignerView(IViewContainer container) {

		this();
		this.viewContainer = container;
		
		if(this.viewContainer.getSliderView() instanceof SliderView){
			
			SliderView sliderView = (SliderView) this.viewContainer.getSliderView();
			sliderView.addListener(this);
		}
			

		if (container instanceof ServiceDesignerViewContainer) {
			this.context = ((ServiceDesignerViewContainer) this.viewContainer)
					.getContext();

			routeForm_1 = new RouteForm(this.context, this);
			routeForm_1.setSizeFull();
			routeForm_1.setData(this);

			viewBoundForm.setContent(routeForm_1);
		}

		this.serviceService = ServiceDesignerViewContainer.getServiceService();

		tabSheet.addTab(maneuversTable, "Indicaciones");

	}

	public void drawRouteStroke(Date routeStartDate, Route route) {

		List<Point> routePoints = new ArrayList<Point>();

		routeStrokeVectorLayer.removeAllComponents();
		for (MapCoordinates coordinates : route.getShape().getShapePoints())
			routePoints.add(new Point(coordinates.getLng(), coordinates
					.getLat()));

		PolyLine routePath = new PolyLine();
		Style routeStrokeStyle = new Style();
		routeStrokeStyle.setStrokeWidth(5);
		routeStrokeStyle.setStrokeColor("#7D26CD");
		routeStrokeStyle.setStrokeOpacity(0.5);
		routePath.setCustomStyle(routeStrokeStyle);
		Point[] points = new Point[routePoints.size()];
		routePoints.toArray(points);
		routePath.setPoints(points);
		routeStrokeVectorLayer.addVector(routePath);

		// Create Stop Markers
		createStopMarkers(routeStartDate, route);

		// Extend the map to the current route
		Bounds bounds = new Bounds(points);
		routeOpenLayersMap.zoomToExtent(bounds);

		getApplication().getMainWindow().showNotification(
				"Kilómetros totales: " + route.getDistance()
						+ " y consumo aproximado:" + route.getFuelUsed());
	}

	private void createStopMarkers(Date startingRouteDate, Route route) {

		int stopAsciiCounter = 65;

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(startingRouteDate != null ? startingRouteDate
				: new Date());

		List<Marker> createdMarkers = new ArrayList<Marker>();

		stopsMakerLayer.removeAllComponents();

		for (int i = 0; i < route.getLocations().size(); i++) {

			Location location = route.getLocations().get(i);

			// Create the marker
			Marker stopMarker = new Marker(
					location.getDisplayLatLng().getLng(), location
							.getDisplayLatLng().getLat());

			if (i == 0)
				stopMarker.setIcon(new ExternalResource(
						"http://icons.mqcdn.com/icons/stop.png?text="
								+ new String(
										new char[] { (char) stopAsciiCounter })
								+ "&color=green1"), 22, 28);

			else if (i == route.getLocations().size() - 1)
				stopMarker.setIcon(new ExternalResource(
						"http://icons.mqcdn.com/icons/stop.png?text="
								+ new String(
										new char[] { (char) stopAsciiCounter })
								+ "&color=red1"), 22, 28);
			else
				stopMarker
						.setIcon(
								new ExternalResource(
										"http://icons.mqcdn.com/icons/stop.png?text="
												+ new String(
														new char[] { (char) stopAsciiCounter })),
								22, 28);

			createdMarkers.add(stopMarker);

			stopsMakerLayer.addMarker(stopMarker);

			stopAsciiCounter++;

		}

		// Create Pop up markers
		createMarkerPopups(route, startingRouteDate, createdMarkers);

	}

	private void createMarkerPopups(Route route, Date startingRouteDate,
			List<Marker> markers) {

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(startingRouteDate != null ? startingRouteDate
				: new Date());

		List<CustomizedLocationTextField> customizedLocationTextFields = routeForm_1
				.getCustomizedLocationTextFields();

		for (int i = 0; i < markers.size(); i++) {

			Marker marker = markers.get(i);

			final Popup markerPopup = new Popup();
			markerPopup.setPopupStyle(PopupStyle.FRAMED_CLOUD);
			markerPopup.setLon(marker.getLon());
			markerPopup.setLat(marker.getLat());

			VerticalLayout popupLayout = new VerticalLayout();
			popupLayout.setMargin(true);
			popupLayout.setSpacing(true);
			popupLayout.setHeight("150px");
			popupLayout.setWidth("250px");
			markerPopup.addComponent(popupLayout);

			markerPopup.setAnchor(marker);

			Label addressStopLabel = new Label();
			addressStopLabel.setStyleName(Reindeer.LABEL_H2);
			addressStopLabel.setContentMode(Label.CONTENT_XHTML);
			DateField arrivalStopDate = new DateField("LLegada");
			arrivalStopDate.setResolution(DateField.RESOLUTION_MIN);
			DateField checkoutStopDate = new DateField("Salida");
			checkoutStopDate.setResolution(DateField.RESOLUTION_MIN);

			popupLayout.addComponent(addressStopLabel);
			popupLayout.addComponent(arrivalStopDate);
			popupLayout.addComponent(checkoutStopDate);
			popupLayout.setExpandRatio(checkoutStopDate, 1.0F);

			Location location = route.getLocations().get(i);
			addressStopLabel.setValue(getStopAddress(location));

			if (i == 0) {

				arrivalStopDate.setValue(calendar.getTime());
				checkoutStopDate.setValue(calendar.getTime());

				customizedLocationTextFields.get(i).setCheckoutStopDate(
						calendar.getTime());
			}

			else {
				Leg leg = route.getLegs().get(i - 1);
				calendar.add(Calendar.SECOND, leg.getTime());

				arrivalStopDate.setValue(calendar.getTime());
				checkoutStopDate.setValue(calendar.getTime());

				if (markers.size() - i == 1)
					customizedLocationTextFields.get(i).setArrivalStopDate(
							calendar.getTime());

				else
					setStopDate(customizedLocationTextFields.get(i),
							calendar.getTime());

			}

			marker.addClickListener(new ClickListener() {

				@Override
				public void click(com.vaadin.event.MouseEvents.ClickEvent event) {

					routeOpenLayersMap.addPopup(markerPopup);
				}
			});
		}

	}

	private String getStopAddress(Location location) {

		StringBuilder addressStringBuilder = new StringBuilder();

		if (location.getStreet() != null) {
			addressStringBuilder.append(location.getStreet());
			addressStringBuilder.append('\n');
		}

		if (location.getAdminArea5() != null)
			addressStringBuilder.append(location.getAdminArea5());

		if (location.getAdminArea4() != null)
			addressStringBuilder.append(location.getAdminArea4());

		if (location.getAdminArea3() != null)
			addressStringBuilder.append(location.getAdminArea3());

		if (location.getAdminArea1() != null)
			addressStringBuilder.append(location.getAdminArea1());

		return addressStringBuilder.toString();
	}

	private void setStopDate(CustomizedLocationTextField field, Date stopDate) {

		if (field.getArrivalStopDate() == null)
			field.setArrivalStopDate(stopDate);

		if (field.getCheckoutStopDate() == null)
			field.setCheckoutStopDate(stopDate);
	}

	public void createManeuverPopup(Maneuver maneuver) {

		Popup maneuverPopup = new Popup(maneuver.getStartPoint().getLng(),
				maneuver.getStartPoint().getLat(), "<p><b>"
						+ maneuver.getNarrative() + "</b></p>");
		maneuverPopup.setPopupStyle(PopupStyle.FRAMED_CLOUD);

		routeOpenLayersMap.addPopup(maneuverPopup);

	}

	private InvientCharts buildCharts(String chartTitle, String yAxisCaption) {

		InvientChartsConfig chartConfig = new InvientChartsConfig();
		chartConfig.getGeneralChartConfig().setType(SeriesType.COLUMN);

		chartConfig.getTitle().setText(chartTitle);

		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setCategories(Arrays.asList("Servicio"));
		LinkedHashSet<XAxis> xAxesSet = new LinkedHashSet<InvientChartsConfig.XAxis>();
		xAxesSet.add(xAxis);
		chartConfig.setXAxes(xAxesSet);

		NumberYAxis yAxis = new NumberYAxis();
		yAxis.setMin(0.0);
		yAxis.setTitle(new AxisTitle(yAxisCaption));
		LinkedHashSet<YAxis> yAxesSet = new LinkedHashSet<InvientChartsConfig.YAxis>();
		yAxesSet.add(yAxis);
		chartConfig.setYAxes(yAxesSet);

		Legend legend = new Legend();
		legend.setPosition(new Position());
		legend.getPosition().setAlign(HorzAlign.RIGHT);
		legend.getPosition().setVertAlign(VertAlign.TOP);
		legend.getPosition().setX(-100);
		legend.getPosition().setY(20);
		legend.setFloating(true);
		legend.setBackgroundColor(new com.invient.vaadin.charts.Color.RGB(255,
				255, 255));
		legend.setBorderWidth(1);
		legend.setShadow(true);
		chartConfig.setLegend(legend);

		chartConfig
				.getTooltip()
				.setFormatterJsFunc(
						"function() {"
								+ " return '<b>'+ this.x +'</b><br/>'+ this.series.name +': '+ this.y +'<br/>'+"
								+ "        'Total: '+ this.point.stackTotal; "
								+ "}");

		ColumnConfig colCfg = new ColumnConfig();
		colCfg.setStacking(Stacking.NORMAL);
		chartConfig.addSeriesConfig(colCfg);

		InvientCharts chart = new InvientCharts(chartConfig);
		chart.setSizeFull();
		return chart;

	}

	public void loadManeuverTable(Route route) {

		List<Maneuver> maneuvers = new ArrayList<Maneuver>();
		for (Leg leg : route.getLegs())
			maneuvers.addAll(leg.getManeuvers());

		maneuverBeanItemContainer.removeAllItems();
		maneuverBeanItemContainer.addAll(maneuvers);

	}

	public void drawCharts(Route route) {

		drawRouteDistancesChart(route);
		drawRouteTimesChart(route);
	}

	private void drawRouteDistancesChart(Route route) {

		int letterCounter = 65;
		removeChartSeries(routeDistancesChart);

		for (Leg leg : route.getLegs()) {

			XYSeries seriesData = new XYSeries(buildLabel(letterCounter));
			seriesData
					.setSeriesPoints(getPoints(seriesData, leg.getDistance()));
			routeDistancesChart.addSeries(seriesData);
			letterCounter++;
		}
	}

	private void drawRouteTimesChart(Route route) {

		int letterCounter = 65;
		removeChartSeries(routeTimesChart);

		for (Leg leg : route.getLegs()) {

			XYSeries seriesData = new XYSeries(buildLabel(letterCounter));
			seriesData.setSeriesPoints(getPoints(seriesData, leg.getTime()));
			routeTimesChart.addSeries(seriesData);
			letterCounter++;
		}
	}

	private void removeChartSeries(InvientCharts chart) {

		Set<Series> seriesSet = new CopyOnWriteArraySet(chart.getAllSeries());

		for (Series series : seriesSet)
			chart.removeSeries(series);
	}

	private String buildLabel(int letterCounter) {

		StringBuilder label = new StringBuilder();
		label.append(new String(new char[] { (char) letterCounter }));
		label.append("-");
		letterCounter++;
		label.append(new String(new char[] { (char) letterCounter }));

		return label.toString();
	}

	private static LinkedHashSet<DecimalPoint> getPoints(Series series,
			double... values) {
		LinkedHashSet<DecimalPoint> points = new LinkedHashSet<DecimalPoint>();
		for (double value : values) {
			points.add(new DecimalPoint(series, value));
		}
		return points;
	}

	@Override
	public void buttonClick(ClickEvent event) {

		try {

			viewBoundForm.commit();
			routeForm_1.routeCalculation();

		} catch (InvalidValueException e) {

		}

	}

	private class DirectionIconColumnGenerator implements ColumnGenerator {

		static final String DIRECTION_ICON_COLUMN_ID = "direction-icon";

		@Override
		public Object generateCell(Table source, Object itemId, Object columnId) {

			Maneuver maneuverItem = (Maneuver) itemId;

			if (maneuverItem.getIconUrl() == null)
				return null;

			Embedded directionEmbedded = new Embedded("", new ExternalResource(
					maneuverItem.getIconUrl()));
			return directionEmbedded;
		}

	}

	private class ServiceDesignerToolbar extends CssLayout implements IToolbar {

		private Button saveServiceButton;

		public ServiceDesignerToolbar() {
			super();
			addComponent(buildNavigationbar());
		}

		@Override
		public int getPosition() {
			return 0;
		}

		@Override
		public ComponentContainer getContent() {
			return this;
		}

		private HorizontalLayout buildNavigationbar() {

			HorizontalLayout toolbarLayout = new HorizontalLayout();
			toolbarLayout.setImmediate(false);
			toolbarLayout.setSpacing(true);

			saveServiceButton = new Button("Guardar servicio");
			toolbarLayout.addComponent(saveServiceButton);

			saveServiceButton.addListener(new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {

					Route route = routeForm_1.getDesignedRoute();

					if (route == null)
						throw new RuntimeException(
								"There is no route to persist");

					Service savingService = generateService(route);
					
					// Load the designer options
					savingService.setRouteType((String)routeForm_1.getRouteTypeField().getValue());
					savingService.setRouteAvoids((String)routeForm_1.getRouteAvoidsField().getValue());
					
					try {
						//Persist the editing service
						serviceService.save(savingService);
					} catch (Exception e) {

						throw new RuntimeException(e);
					}

				}
			});

			return toolbarLayout;
		}

		private Service generateService(Route mapquestRoute) {

			if (selectedService == null)
				selectedService = new Service();

			//Remove previous routes  
			selectedService.removeAllRoutes();

			// Create stops
			List<Stop> stops = new ArrayList<Stop>();
			for (Location location : mapquestRoute.getLocations()) {

				Stop stop = new Stop();
				Address address = new Address();
				address.setStreet(location.getStreet());
				address.setZipCode(location.getPostalCode());
				address.setCity(location.getAdminArea5());
				address.setCounty(location.getAdminArea4());
				address.setProvince(location.getAdminArea3());
				address.setCountry(location.getAdminArea1());
				address.setLongitude(location.getLatLng().getLng());
				address.setLatitude(location.getLatLng().getLat());
				
				stop.setStopAddress(address);

				stops.add(stop);
			}

			// Create routes
			int characterCounter = 65;
			
			for (int i = 0; i < stops.size() - 1; i++) {

				com.thingtrack.bustrack.domain.Route route = new com.thingtrack.bustrack.domain.Route();

				String code = new Character((char) (characterCounter+i)).toString() + '-' + new Character((char) (characterCounter+i+1)).toString(); 
				route.setCode(code);	
						
				try {
					
					route.setCode(code);
					
					// Origin
					Stop origin = stops.get(i);
					route.addStop(origin);
					
					// Destination
					Stop destination = stops.get(i+1);
					route.addStop(destination);
					
					selectedService.addRoute(route);
					
				} catch (Exception e) {
				
				}

			}

			return selectedService;
		}
	}

	@Override
	public void upButtonClick(ClickNavigationEvent event) {

		viewContainer.getSliderView().rollPrevious();
	}

	private void refreshBindindSource() {

		bsRoutes.removeAllItems();
		bsRoutes.addAll(selectedService.getRoutes());

		routeForm_1.setSelectedService(selectedService);
	}

	private void injectBindingSource() {

		removeAllToolbar();

		navigationToolbar = new NavigationToolbar(0, bsRoutes, viewContainer);
		navigationToolbar.setNavigationButtons(false);
		navigationToolbar.addListenerUpButton(this);
		serviceDesignerToolbar = new ServiceDesignerToolbar();

		addToolbar(navigationToolbar);
		addToolbar(serviceDesignerToolbar);

	}

	private class OpenLayersActionHandler implements Handler {

		private Action addStopAction = new Action("Añadir parada");
		private Action[] actions = new Action[] { addStopAction };

		@Override
		public Action[] getActions(Object target, Object sender) {

			return actions;
		}

		@Override
		public void handleAction(Action action, Object sender, Object target) {

			if (target instanceof Point) {

				Point point = (Point) target;

				final Marker stopMarker = new Marker(point.getLon(),
						point.getLat());
				stopMarker.setIcon(new ExternalResource(
						"http://icons.mqcdn.com/icons/stop.png"), 22, 28);

				stopMarker.addClickListener(new MouseEvents.ClickListener() {

					@Override
					public void click(
							com.vaadin.event.MouseEvents.ClickEvent event) {

						stopsMakerLayer.removeMarker(stopMarker);
						routeForm_1.removeStop(stopMarker.getLon(),
								stopMarker.getLat());
					}
				});

				// Add to layers
				stopsMakerLayer.addMarker(stopMarker);

			}
		}

	}

	@AutoGenerated
	private HorizontalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new HorizontalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// verticalLayout_1
		verticalLayout_1 = buildVerticalLayout_1();
		mainLayout.addComponent(verticalLayout_1);
		
		// panel_1
		panel_1 = buildPanel_1();
		mainLayout.addComponent(panel_1);
		mainLayout.setExpandRatio(panel_1, 1.0f);
		
		return mainLayout;
	}

	@AutoGenerated
	private VerticalLayout buildVerticalLayout_1() {
		// common part: create layout
		verticalLayout_1 = new VerticalLayout();
		verticalLayout_1.setImmediate(false);
		verticalLayout_1.setWidth("515px");
		verticalLayout_1.setHeight("100.0%");
		verticalLayout_1.setMargin(false);
		
		// viewBoundForm
		viewBoundForm = new ViewBoundForm();
		viewBoundForm.setImmediate(false);
		viewBoundForm.setWidth("100.0%");
		viewBoundForm.setHeight("100.0%");
		verticalLayout_1.addComponent(viewBoundForm);
		verticalLayout_1.setExpandRatio(viewBoundForm, 1.0f);
		
		return verticalLayout_1;
	}

	@AutoGenerated
	private Panel buildPanel_1() {
		// common part: create layout
		panel_1 = new Panel();
		panel_1.setImmediate(false);
		panel_1.setWidth("100.0%");
		panel_1.setHeight("100.0%");
		
		// verticalLayout_2
		verticalLayout_2 = buildVerticalLayout_2();
		panel_1.setContent(verticalLayout_2);
		
		return panel_1;
	}

	@AutoGenerated
	private VerticalLayout buildVerticalLayout_2() {
		// common part: create layout
		verticalLayout_2 = new VerticalLayout();
		verticalLayout_2.setImmediate(false);
		verticalLayout_2.setWidth("100.0%");
		verticalLayout_2.setHeight("100.0%");
		verticalLayout_2.setMargin(false);
		
		// verticalSplitPanel_2
		verticalSplitPanel_2 = buildVerticalSplitPanel_2();
		verticalLayout_2.addComponent(verticalSplitPanel_2);
		
		return verticalLayout_2;
	}

	@AutoGenerated
	private VerticalSplitPanel buildVerticalSplitPanel_2() {
		// common part: create layout
		verticalSplitPanel_2 = new VerticalSplitPanel();
		verticalSplitPanel_2.setImmediate(false);
		verticalSplitPanel_2.setWidth("100.0%");
		verticalSplitPanel_2.setHeight("100.0%");
		verticalSplitPanel_2.setMargin(true);
		
		// routeOpenLayersMap
		routeOpenLayersMap = new OpenLayersMap();
		routeOpenLayersMap.setImmediate(false);
		routeOpenLayersMap.setWidth("100.0%");
		routeOpenLayersMap.setHeight("100.0%");
		verticalSplitPanel_2.addComponent(routeOpenLayersMap);
		
		// tabSheet
		tabSheet = new TabSheet();
		tabSheet.setImmediate(false);
		tabSheet.setWidth("100.0%");
		tabSheet.setHeight("100.0%");
		verticalSplitPanel_2.addComponent(tabSheet);
		
		return verticalSplitPanel_2;
	}

	@Override
	public void viewChanged(ViewEvent event) {
		
		if(event.getViewTo() instanceof ServiceView){
			routeForm_1.resetStopAsciiCounter();
			
			//Clean the plotted route
			routeStrokeVectorLayer.removeAllComponents();
			stopsMakerLayer.removeAllComponents();
		}
		
	}

}
