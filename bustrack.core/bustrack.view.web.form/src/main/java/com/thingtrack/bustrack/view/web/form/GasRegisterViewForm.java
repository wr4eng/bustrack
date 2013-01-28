package com.thingtrack.bustrack.view.web.form;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import com.thingtrack.bustrack.domain.GasStation;
import com.thingtrack.bustrack.domain.GasType;
import com.thingtrack.bustrack.service.api.GasStationService;
import com.thingtrack.bustrack.service.api.GasTypeService;
import com.thingtrack.konekti.view.addon.data.BindingSource;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;


@SuppressWarnings("serial")
public class GasRegisterViewForm extends CustomComponent {

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private ComboBox gasTypeField;
	@AutoGenerated
	private TextField volumeField;
	@AutoGenerated
	private TextField priceField;
	@AutoGenerated
	private DateField gasStationRegisterDateField;
	@AutoGenerated
	private ComboBox gasStationField;
	@AutoGenerated
	private TextField commentField;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	// form services
	private GasTypeService gasTypeService;
	private GasStationService gasStationService;
	
	// form datasources
	private BeanItemContainer<GasType> bcGasType = new BindingSource<GasType>(GasType.class);
	private BeanItemContainer<GasStation> bcGasStation = new BindingSource<GasStation>(GasStation.class);
	
	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 * @throws Exception 
	 * @throws IllegalArgumentException 
	 */
	public GasRegisterViewForm() throws IllegalArgumentException, Exception {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		initComponents();
					
		// configure Organization Type data
		gasTypeField.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
		gasTypeField.setItemCaptionPropertyId("description");
			
		gasStationField.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
		gasStationField.setItemCaptionPropertyId("description");
					
		// get form services from OSGi Service Registry
		getServices();
			
		// load data sources
		loadData();
	}
	
	private void initComponents() {
		//sensorTelemetryDescriptionField.setNullRepresentation("");
		volumeField.setNullRepresentation("");
		priceField.setNullRepresentation("");
		commentField.setNullRepresentation("");
		
		gasStationRegisterDateField.setDateFormat("dd/MM/yyyy HH:mm");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void getServices() {
		try {
			BundleContext bundleContext = FrameworkUtil.getBundle(GasRegisterViewForm.class).getBundleContext();

			ServiceReference gasTypeServiceReference = bundleContext.getServiceReference(GasTypeService.class.getName());
			gasTypeService = GasTypeService.class.cast(bundleContext.getService(gasTypeServiceReference));
			
			ServiceReference gasStationServiceReference = bundleContext.getServiceReference(GasStationService.class.getName());
			gasStationService = GasStationService.class.cast(bundleContext.getService(gasStationServiceReference));
		}
		catch (Exception e) {
			e.getMessage();
			
		}
		
	}
	
	private void loadData() throws IllegalArgumentException, Exception {		
		bcGasType.removeAllItems();
		bcGasType.addAll(gasTypeService.getAll());
		
		gasTypeField.setContainerDataSource(bcGasType);
		
		bcGasStation.removeAllItems();
		bcGasStation.addAll(gasStationService.getAll());
		
		gasStationField.setContainerDataSource(bcGasStation);	
	}
	
	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("560px");
		mainLayout.setHeight("240px");
		mainLayout.setMargin(true);
		
		// top-level component properties
		setWidth("560px");
		setHeight("240px");
		
		// commentField
		commentField = new TextField();
		commentField.setCaption("Comentario");
		commentField.setImmediate(false);
		commentField.setWidth("520px");
		commentField.setHeight("120px");
		commentField.setTabIndex(6);
		commentField.setSecret(false);
		mainLayout.addComponent(commentField, "top:100.0px;left:20.0px;");
		
		// gasStationField
		gasStationField = new ComboBox();
		gasStationField.setCaption("Estación carburante");
		gasStationField.setImmediate(false);
		gasStationField.setWidth("320px");
		gasStationField.setHeight("-1px");
		gasStationField.setTabIndex(1);
		gasStationField.setRequired(true);
		mainLayout.addComponent(gasStationField, "top:17.0px;left:20.0px;");
		
		// gasStationRegisterDateField
		gasStationRegisterDateField = new DateField();
		gasStationRegisterDateField.setCaption("Fecha registro carburante");
		gasStationRegisterDateField.setImmediate(false);
		gasStationRegisterDateField.setWidth("-1px");
		gasStationRegisterDateField.setHeight("-1px");
		gasStationRegisterDateField.setInvalidAllowed(false);
		gasStationRegisterDateField.setTabIndex(2);
		gasStationRegisterDateField.setRequired(true);
		mainLayout.addComponent(gasStationRegisterDateField,
				"top:17.0px;left:365.0px;");
		
		// priceField
		priceField = new TextField();
		priceField.setCaption("Precio");
		priceField.setImmediate(false);
		priceField.setWidth("100px");
		priceField.setHeight("-1px");
		priceField.setTabIndex(4);
		priceField.setSecret(false);
		mainLayout.addComponent(priceField, "top:57.0px;left:120.0px;");
		
		// volumeField
		volumeField = new TextField();
		volumeField.setCaption("Volumen");
		volumeField.setImmediate(false);
		volumeField.setWidth("80px");
		volumeField.setHeight("-1px");
		volumeField.setTabIndex(3);
		volumeField.setRequired(true);
		volumeField.setSecret(false);
		mainLayout.addComponent(volumeField, "top:57.0px;left:20.0px;");
		
		// gasTypeField
		gasTypeField = new ComboBox();
		gasTypeField.setCaption("Tipo carburante");
		gasTypeField.setImmediate(false);
		gasTypeField.setWidth("300px");
		gasTypeField.setHeight("-1px");
		gasTypeField.setTabIndex(5);
		gasTypeField.setRequired(true);
		mainLayout.addComponent(gasTypeField, "top:57.0px;left:240.0px;");
		
		return mainLayout;
	}
}