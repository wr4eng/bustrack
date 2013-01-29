package com.thingtrack.bustrack.view.module.scheduler.internal.form;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.vaadin.addon.customfield.CustomField;
import org.vaadin.addons.locationtextfield.GeocodedLocation;
import org.vaadin.addons.locationtextfield.GoogleGeocoder;
import org.vaadin.addons.locationtextfield.LocationTextField;
import org.vaadin.vol.Bounds;
import org.vaadin.vol.Marker;
import org.vaadin.vol.MarkerLayer;
import org.vaadin.vol.OpenLayersMap;
import org.vaadin.vol.OpenStreetMapLayer;
import org.vaadin.vol.Point;
import org.vaadin.vol.Popup;
import org.vaadin.vol.Popup.PopupStyle;

import com.thingtrack.bustrack.domain.Stop;
import com.thingtrack.bustrack.view.module.scheduler.SchedulerModule;
import com.thingtrack.konekti.domain.Address;
import com.thingtrack.konekti.service.api.AddressService;
import com.vaadin.data.Property;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class StopsField extends CustomField {

	static final String CLASSNAME = "v-stopsfield";

	private VerticalLayout mainLayout;
	private OpenLayersMap routeOpenLayersMap;
	private OpenStreetMapLayer openStreetMapLayer;
	private MarkerLayer markerLayer;
	private Marker originStopMarker;
	private Marker destinationStopMarker;
	private LocationTextField<GeocodedLocation> originStopLocationTextField;
	private LocationTextField<GeocodedLocation> destinationStopLocationTextField;
	private final GoogleGeocoder geocoder;
	private Stop originStop;
	private Stop destinationStop;

	// Enterprise service
	private AddressService addressService;

	public StopsField() {

		// Style name
		setStyleName(CLASSNAME);

		// Google Geocoder to search the stop addresses
		geocoder = GoogleGeocoder.getInstance();
//		geocoder.setLimit(25);

		mainLayout = buildMainLayout();
		setCompositionRoot(mainLayout);

		//Enterprise services
		getServices();
		
		// Calculate latitude and longitude by the location text field handlers
		originStopLocationTextField
				.addListener(new Property.ValueChangeListener() {

					@Override
					public void valueChange(
							com.vaadin.data.Property.ValueChangeEvent event) {

						GeocodedLocation geocodedLocation = (GeocodedLocation) event
								.getProperty().getValue();

						if (geocodedLocation == null)
							return;

						if (originStopMarker != null)
							markerLayer.removeComponent(originStopMarker);

						originStopMarker = createStopMarker(STOP_TYPE.ORIGEN,
								geocodedLocation);
						markerLayer.addComponent(originStopMarker);

						// Center and Zoom the map
						Bounds bounds = null;

						if (destinationStopMarker != null) {

							Point originPoint = new Point(originStopMarker.getLon(), originStopMarker.getLat());
							Point destinationPoint = new Point(destinationStopMarker.getLon(),destinationStopMarker.getLat());
							bounds = new Bounds(originPoint, originPoint,
									destinationPoint, destinationPoint);

							routeOpenLayersMap.zoomToExtent(bounds);

						} else {
							routeOpenLayersMap.setCenter(originStopMarker.getLon(), originStopMarker.getLat());
							routeOpenLayersMap.setZoom(18);
						}

						// Check if the address already exists in the database
						Address address = getAddressByLontideLatitude(
								geocodedLocation.getLon(),
								geocodedLocation.getLat());

						// Found an address with the same longitude and latitude
						if (address != null) {
							originStop.setStopAddress(address);
							return;
						}

						address = fulfillAddress(geocodedLocation);
						originStop.setStopAddress(address);

					}
				});

		destinationStopLocationTextField
				.addListener(new Property.ValueChangeListener() {

					@Override
					public void valueChange(
							com.vaadin.data.Property.ValueChangeEvent event) {

						GeocodedLocation geocodedLocation = (GeocodedLocation) event
								.getProperty().getValue();

						if (geocodedLocation == null)
							return;

						if (destinationStopMarker != null)
							markerLayer.removeComponent(destinationStopMarker);

						destinationStopMarker = createStopMarker(
								STOP_TYPE.DESTINO, geocodedLocation);

						markerLayer.addComponent(destinationStopMarker);

						// Center and Zoom the map
						Bounds bounds = null;

						if (originStopMarker != null) {
							
							Point originPoint = new Point(originStopMarker.getLon(), originStopMarker.getLat());
							Point destinationPoint = new Point(destinationStopMarker.getLon(),destinationStopMarker.getLat());
							
							bounds = new Bounds(originPoint, originPoint,destinationPoint, destinationPoint);

							routeOpenLayersMap.zoomToExtent(bounds);
							
						} else {
							routeOpenLayersMap.setCenter(originStopMarker.getLon(), originStopMarker.getLat());
							routeOpenLayersMap.setZoom(18);
						}

						// routeOpenLayersMap.setCenter(bounds);
						routeOpenLayersMap.zoomToExtent(bounds);
						routeOpenLayersMap.setZoom(7);

						Address address = getAddressByLontideLatitude(
								geocodedLocation.getLon(),
								geocodedLocation.getLat());

						// Found an address with the same longitude and latitude
						if (address != null) {
							destinationStop.setStopAddress(address);
							return;
						}

						address = fulfillAddress(geocodedLocation);
						destinationStop.setStopAddress(address);

					}
				});

	}

	private VerticalLayout buildMainLayout() {

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.setSpacing(true);

		// Map definition
		routeOpenLayersMap = new OpenLayersMap(true);
		routeOpenLayersMap.setWidth("100%");
		routeOpenLayersMap.setHeight("100%");
		routeOpenLayersMap.setImmediate(false);

		mainLayout.addComponent(routeOpenLayersMap);
		mainLayout.setExpandRatio(routeOpenLayersMap, 1.0f);
		// Apply Map Layers
		openStreetMapLayer = new OpenStreetMapLayer();
		routeOpenLayersMap.addLayer(openStreetMapLayer);
		markerLayer = new MarkerLayer();
		routeOpenLayersMap.addLayer(markerLayer);

		HorizontalLayout stopLocationTextFieldLayout = buildLocationTextFieldLayout();

		mainLayout.addComponent(stopLocationTextFieldLayout);

		return mainLayout;
	}

	private HorizontalLayout buildLocationTextFieldLayout() {

		HorizontalLayout stopLocationTextFieldLayout = new HorizontalLayout();
		stopLocationTextFieldLayout = new HorizontalLayout();
		stopLocationTextFieldLayout.setImmediate(false);
		stopLocationTextFieldLayout.setWidth("100.0%");
		stopLocationTextFieldLayout.setHeight("-1px");
		stopLocationTextFieldLayout.setMargin(false);
		stopLocationTextFieldLayout.setSpacing(true);

		// LocationTextField definition
		originStopLocationTextField = new LocationTextField<GeocodedLocation>(
				geocoder, GeocodedLocation.class, "Origen");

		originStopLocationTextField.setWidth("100%");
		originStopLocationTextField.setHeight("-1px");
		originStopLocationTextField.setRequired(true);
		originStopLocationTextField
				.setRequiredError(originStopLocationTextField.getCaption()
						+ " es un campo requerido");

		stopLocationTextFieldLayout.addComponent(originStopLocationTextField);
		stopLocationTextFieldLayout.setExpandRatio(originStopLocationTextField,
				1.0f);

		destinationStopLocationTextField = new LocationTextField<GeocodedLocation>(
				geocoder, GeocodedLocation.class, "Destino");

		destinationStopLocationTextField.setWidth("100%");
		destinationStopLocationTextField.setHeight("-1px");
		destinationStopLocationTextField.setRequired(true);
		destinationStopLocationTextField
				.setRequiredError(destinationStopLocationTextField.getCaption()
						+ " es un campo requerido");

		stopLocationTextFieldLayout
				.addComponent(destinationStopLocationTextField);
		stopLocationTextFieldLayout.setExpandRatio(
				destinationStopLocationTextField, 1.0f);

		return stopLocationTextFieldLayout;
	}

	private void getServices(){
		
		BundleContext bundleContext = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		
		ServiceReference addressServiceReference = (ServiceReference) bundleContext.getServiceReference(AddressService.class.getName());
		addressService =  (AddressService) bundleContext.getService(addressServiceReference);
		
	}
	
	@Override
	public void setPropertyDataSource(Property newDataSource) {

		List<Stop> stops = (List<Stop>) newDataSource.getValue();

		// Remove prexisting markers from the map
		if (originStopMarker != null)
			markerLayer.removeComponent(originStopMarker);
		if (destinationStopMarker != null)
			markerLayer.removeMarker(destinationStopMarker);

		// Create stops
		if (stops == null) {
			stops = new ArrayList<Stop>();
			stops.add(new Stop());
			stops.add(new Stop());

			originStop = stops.get(0);
			destinationStop = stops.get(1);

			newDataSource.setValue(stops);

			// Reset the fields
			originStopLocationTextField.setValue(null);
			destinationStopLocationTextField.setValue(null);

			return;
		}

		originStop = stops.get(0);
		destinationStop = stops.get(1);

		// Origin Stop
		GeocodedLocation originGeocodedLocation = fulfillGeocodedLocation(originStop
				.getStopAddress());

		originStopLocationTextField.setLocation(originGeocodedLocation);

		// Destination stop
		GeocodedLocation destinationGeocodedLocation = fulfillGeocodedLocation(destinationStop
				.getStopAddress());

		destinationStopLocationTextField
				.setLocation(destinationGeocodedLocation);

		super.setPropertyDataSource(newDataSource);

	}

	@Override
	public Class<?> getType() {
		return List.class;
	}

	@Override
	protected boolean isEmpty() {

		if (originStopLocationTextField.getValue() == null
				|| destinationStopLocationTextField.getValue() == null) {
			setRequiredError("Origen y destino son campos obligatorios");
			return true;
		}
		return false;
	}

	private enum STOP_TYPE {

		ORIGEN, DESTINO
	}

	private Marker createStopMarker(STOP_TYPE stopType,
			GeocodedLocation geocodedLocation) {

		Marker marker = new Marker(geocodedLocation.getLon(),
				geocodedLocation.getLat());
		marker.setIcon(new ThemeResource(SchedulerModule.MODULE_ICONS_PATH + "marker.png"), 24, 24);

		String street = geocodedLocation.getGeocodedAddress() != null ? geocodedLocation
				.getGeocodedAddress() : "";
		// String locality = geocodedLocation.getLocality() != null ?
		// geocodedLocation
		// .getLocality() : "";
		// String postalCode = geocodedLocation.getPostalCode() != null ?
		// geocodedLocation
		// .getPostalCode() : "";
		// String province = geocodedLocation.getAdministrativeAreaLevel1() !=
		// null ? geocodedLocation
		// .getAdministrativeAreaLevel1() : "";
		// String country = geocodedLocation.getCountry() != null ?
		// geocodedLocation
		// .getCountry() : "";

		final Popup popup = new Popup(marker.getLon(), marker.getLat(),
				"<p><b>" + stopType.toString()
						+ "</b></p><p><b>Dirección: </b>" + street);
		// + "</p><p><b> Localidad: </b>" + locality
		// + "</p><p><b>Cósdigo Postal: </b>" + postalCode
		// + "</p><p><b> Provincia: </b>" + province
		// + "</p><p><b> Paíss: </b>" + country + "</p>");
		popup.setPopupStyle(PopupStyle.FRAMED_CLOUD);
		popup.setAnchor(marker);

		marker.addClickListener(new ClickListener() {

			@Override
			public void click(ClickEvent event) {

				routeOpenLayersMap.addPopup(popup);
			}
		});

		return marker;
	}

	private Address getAddressByLontideLatitude(double longitude,
			double latitude) {

		Address address = null;
		try {
			address = addressService
					.getByLongitudeLatitude(longitude, latitude);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return address;
	}

	private GeocodedLocation fulfillGeocodedLocation(Address address) {

		if (address == null)
			return null;

		GeocodedLocation stopGeocodedLocation = new GeocodedLocation();

		stopGeocodedLocation.setAdministrativeAreaLevel1(address.getProvince());
		stopGeocodedLocation.setCountry(address.getCountry());
		stopGeocodedLocation.setGeocodedAddress(address.getStreet());
		stopGeocodedLocation.setLocality(address.getCity());
		stopGeocodedLocation.setPostalCode(address.getZipCode());
		stopGeocodedLocation.setStreetNumber(address.getLetter());
		stopGeocodedLocation.setLon(address.getLongitude());
		stopGeocodedLocation.setLat(address.getLatitude());

		return stopGeocodedLocation;
	}

	private Address fulfillAddress(GeocodedLocation geocodedLocation) {

		Address address = new Address();
		address.setCity(geocodedLocation.getLocality());
		address.setCountry(geocodedLocation.getCountry());
		address.setLatitude(geocodedLocation.getLat());
		address.setLongitude(geocodedLocation.getLon());
		address.setProvince(geocodedLocation.getAdministrativeAreaLevel1());
		address.setStreet(geocodedLocation.getGeocodedAddress());
		address.setNumber(geocodedLocation.getStreetNumber());
		address.setZipCode(geocodedLocation.getPostalCode());

		return address;

	}

}
