package com.thingtrack.bustrack.view.module.servicedesigner.internal.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.vaadin.addon.customfield.CustomField;
import org.vaadin.addons.locationtextfield.GeocodedLocation;

import com.thingtrack.bustrack.domain.Stop;
import com.thingtrack.bustrack.view.module.servicedesigner.ServiceDesignerModule;
import com.thingtrack.konekti.view.web.form.field.LocalizationField;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property.ValueChangeNotifier;
import com.vaadin.event.MouseEvents;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class CustomizedLocationTextField extends CustomField implements
		ValueChangeNotifier {
	
	@AutoGenerated
	private VerticalLayout mainLayout;

	@AutoGenerated
	private HorizontalLayout horizontalLayout_2;

	@AutoGenerated
	private LocalizationField stopLocalizationField;


	public enum StopType{
		
		ORIGIN,
		INTERMEDIATE,
		DESTINATION
	}
	
	private static final String STOP_ICON_URL = "http://icons.mqcdn.com/icons/stop.png";
	
	private Embedded closeEmbedded;
	private DateField checkoutDateField;
	private DateField arrivalDateField;
	private Embedded stopIcon;
	private String caption;
	private String stopColor;
	private StopType stopType;
	private List<ComponentDetachListener> componentDetachListeners;
	private List<ValueChangeListener> listeners = new ArrayList<ValueChangeListener>();
	
	public LocalizationField getStopLocalizationField() {
		return stopLocalizationField;
	}

	
	public CustomizedLocationTextField() {

		buildMainLayout();
		setCompositionRoot(mainLayout);

		//Add the stop icon
		stopIcon = new Embedded();
		horizontalLayout_2.addComponent(stopIcon, 0);
		horizontalLayout_2.setComponentAlignment(stopIcon, Alignment.MIDDLE_CENTER);
		
		// Add the Date intervals
		mainLayout.addComponent(buildDatesLayout());
		//Add the closeable button 
		buildCloseStopButton();
		
		
		//Type by default
		setStopType(StopType.INTERMEDIATE);
		
		componentDetachListeners = new ArrayList<ComponentContainer.ComponentDetachListener>();
		
		//Handlers
		closeEmbedded.addListener(new MouseEvents.ClickListener() {

			@Override
			public void click(ClickEvent event) {

				ComponentContainer parentContainer = (ComponentContainer) CustomizedLocationTextField.this
						.getParent();

				if (parentContainer != null){
					
					for(Object listener : componentDetachListeners)
						((ComponentDetachListener) listener).componentDetachedFromContainer(new ComponentDetachEvent(parentContainer, CustomizedLocationTextField.this));
				}
			}
		});
		
		stopLocalizationField.addListener(new Field.ValueChangeListener() {

			@Override
			public void valueChange(
					com.vaadin.data.Property.ValueChangeEvent event) {
				
				for (ValueChangeListener listener : listeners)
					listener.valueChange(event);
				
				stopLocalizationField.addStyleName("validated");
			}
		});
	}

	
	public String getStopCaption() {
		return this.caption;
	}
	
	@Override
	public void setCaption(String caption) {

		this.caption = caption;
		
		if (stopColor != null)
			stopIcon.setSource(new ExternalResource(STOP_ICON_URL + "?text="
					+ caption + "&color=" + stopColor));

		else
			stopIcon.setSource(new ExternalResource(STOP_ICON_URL + "?text="
					+ caption));
	}
	
	public void setCaptionAndColor(String caption, String color){
		
		this.stopColor = color;
		this.setCaption(caption);
	}
	
	
	public StopType getStopType(){
		return stopType;
	}
	
	public void setStopType(StopType stopType){
		
		this.stopType = stopType;
		
		switch(stopType){
		
		case ORIGIN:
			arrivalDateField.setEnabled(false);
			break;
		case DESTINATION:
			checkoutDateField.setEnabled(false);
		}
		
	} 

	private HorizontalLayout buildDatesLayout() {

		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setHeight("100%");
		horizontalLayout.setWidth("100%");
		horizontalLayout.setSpacing(true);

		arrivalDateField = new DateField();
		arrivalDateField.setWidth("118px");
		arrivalDateField.setResolution(DateField.RESOLUTION_MIN);

		horizontalLayout.addComponent(arrivalDateField);
		horizontalLayout.setComponentAlignment(arrivalDateField,
				Alignment.MIDDLE_RIGHT);
		
		checkoutDateField = new DateField();
		checkoutDateField.setWidth("118px");
		checkoutDateField.setResolution(DateField.RESOLUTION_MIN);

		horizontalLayout.addComponent(checkoutDateField);
		horizontalLayout.setComponentAlignment(checkoutDateField,
				Alignment.MIDDLE_RIGHT);


		return horizontalLayout;
	}

	private void buildCloseStopButton() {

		// Close button
		closeEmbedded = new Embedded();
		closeEmbedded.setImmediate(false);
		closeEmbedded.setWidth("-1px");
		closeEmbedded.setHeight("-1px");
		closeEmbedded.setDescription("Clickea si quieres eliminar la parada");
		closeEmbedded.setSource(new ThemeResource(ServiceDesignerModule.MODULE_ICONS_PATH +"grey_cross.gif"));

		horizontalLayout_2.addComponent(closeEmbedded);
		horizontalLayout_2.setComponentAlignment(closeEmbedded,
				Alignment.MIDDLE_LEFT);
	}

	@Override
	public void addListener(ValueChangeListener listener) {
		listeners.add((ValueChangeListener) listener);
	}

	@Override
	public void removeListener(ValueChangeListener listener) {
		listeners.remove((ValueChangeListener) listener);
	}

	@Override
	public void setDescription(String description) {
		stopIcon.setDescription(description);
	}

	@Override
	public Object getData() {
		return stopLocalizationField.getData();
	}

	@Override
	public void setData(Object data) {
		stopLocalizationField.setData(data);
	}
	
	@Override
	public void addListener(ComponentDetachListener listener) {
		
		componentDetachListeners.add(listener);
	}
	
	public void setArrivalStopDate(Date arrivalStopDate){
		arrivalDateField.setValue(arrivalStopDate);
	}
	
	public Date getArrivalStopDate(){
		return (Date) arrivalDateField.getValue();
	}

	public void setCheckoutStopDate(Date checkoutStopDate){
		checkoutDateField.setValue(checkoutStopDate);
	}
	
	public Date getCheckoutStopDate(){
		return (Date) checkoutDateField.getValue();
	}
	
	public void setLocation(double longitude, double latitude){
		
		GeocodedLocation geocodedLocation = new GeocodedLocation();
		geocodedLocation.setLon(longitude);
		geocodedLocation.setLat(latitude);
		
		//Reverse geocoding the stops 
		stopLocalizationField.setValue(geocodedLocation);
	}


	@Override
	public void setValue(Object newValue) throws ReadOnlyException,
			ConversionException {

		if(newValue  == null){
			stopLocalizationField.setValue(null);
			return;
		}
		
		if(!(newValue instanceof Stop))
			throw new ConversionException("The new value is not a Stop.class"); 
		
		Stop stop = (Stop) newValue;
		
		//Load the stop data
		setArrivalStopDate(stop.getStopArrivalDate());
		setCheckoutStopDate(stop.getStopCheckoutDate());
		
		double longitude = stop.getStopAddress().getLongitude();
		double latitude = stop.getStopAddress().getLatitude();
		setLocation(longitude, latitude);
	}
	
	
	@Override
	public Class<?> getType() {
		return null;
	}


	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("-1px");
		mainLayout.setHeight("-1px");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("-1px");
		setHeight("-1px");
		
		// horizontalLayout_2
		horizontalLayout_2 = buildHorizontalLayout_2();
		mainLayout.addComponent(horizontalLayout_2);
		
		return mainLayout;
	}


	@AutoGenerated
	private HorizontalLayout buildHorizontalLayout_2() {
		// common part: create layout
		horizontalLayout_2 = new HorizontalLayout();
		horizontalLayout_2.setImmediate(false);
		horizontalLayout_2.setWidth("-1px");
		horizontalLayout_2.setHeight("-1px");
		horizontalLayout_2.setMargin(false);
		horizontalLayout_2.setSpacing(true);
		
		// stopLocalizationField
		stopLocalizationField = new LocalizationField();
		stopLocalizationField.setImmediate(false);
		stopLocalizationField.setWidth("400px");
		stopLocalizationField.setHeight("-1px");
		horizontalLayout_2.addComponent(stopLocalizationField);
		
		return horizontalLayout_2;
	}

}