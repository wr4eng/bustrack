package com.thingtrack.bustrack.view.web.form;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import com.thingtrack.bustrack.domain.JobOfferStatus;
import com.thingtrack.bustrack.domain.JobOfferType;
import com.thingtrack.bustrack.service.api.JobOfferStatusService;
import com.thingtrack.bustrack.service.api.JobOfferTypeService;
import com.thingtrack.konekti.view.addon.data.BindingSource;
import com.thingtrack.konekti.view.web.form.field.FileField;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class JobOfferViewForm extends CustomComponent {

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private FileField cvField;
	@AutoGenerated
	private TextField tittleField;
	@AutoGenerated
	private ComboBox jobOfferTypeField;
	@AutoGenerated
	private ComboBox jobOfferStatusField;
	@AutoGenerated
	private TextField surnameField;
	@AutoGenerated
	private TextField phoneField;
	@AutoGenerated
	private TextField observationField;
	@AutoGenerated
	private TextField nameField;
	@AutoGenerated
	private TextField mobileField;
	@AutoGenerated
	private DateField jobOfferDateField;
	@AutoGenerated
	private TextField emailField;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */
	
	// form services
	private JobOfferTypeService jobOfferTypeService;
	private JobOfferStatusService jobOfferStatusService;
	
	// job offer type datasource
	private BeanItemContainer<JobOfferType> bcJobOfferType = new BindingSource<JobOfferType>(JobOfferType.class);
	private BeanItemContainer<JobOfferStatus> bcJobOfferStatus = new BindingSource<JobOfferStatus>(JobOfferStatus.class);
	
	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 * @throws Exception 
	 * @throws IllegalArgumentException 
	 */
	public JobOfferViewForm() throws IllegalArgumentException, Exception {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		initComponents();
		
		// configure Employee Agent Type data
		jobOfferTypeField.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
		jobOfferTypeField.setItemCaptionPropertyId("description");
		
		jobOfferStatusField.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
		jobOfferStatusField.setItemCaptionPropertyId("description");			
		
		// get form services from OSGi Service Registry
		getServices();
		
		// load data sources
		loadData();		
	}
		
	private void initComponents() {
		tittleField.setNullRepresentation("");
		surnameField.setNullRepresentation("");
		phoneField.setNullRepresentation("");
		observationField.setNullRepresentation("");
		phoneField.setNullRepresentation("");
		nameField.setNullRepresentation("");
		mobileField.setNullRepresentation("");
		emailField.setNullRepresentation("");
		
		jobOfferDateField.setDateFormat("dd/MM/yyyy HH:mm");
		
		jobOfferTypeField.setNullSelectionAllowed(false);
		jobOfferStatusField.setNullSelectionAllowed(false);
		
		tittleField.focus();
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void getServices() {
		try {
			BundleContext bundleContext = FrameworkUtil.getBundle(JobOfferViewForm.class).getBundleContext();

			ServiceReference jobOfferTypeServiceReference = bundleContext.getServiceReference(JobOfferTypeService.class.getName());
			jobOfferTypeService = JobOfferTypeService.class.cast(bundleContext.getService(jobOfferTypeServiceReference));
			
			ServiceReference jobOfferStatusServiceReference = bundleContext.getServiceReference(JobOfferStatusService.class.getName());
			jobOfferStatusService = JobOfferStatusService.class.cast(bundleContext.getService(jobOfferStatusServiceReference));
		
			
		}
		catch (Exception e) {
			e.getMessage();
			
		}
		
	}
	
	private void loadData() throws IllegalArgumentException, Exception {
		bcJobOfferType.removeAllItems();
		bcJobOfferType.addAll(jobOfferTypeService.getAll());

		jobOfferTypeField.setContainerDataSource(bcJobOfferType);
		
		bcJobOfferStatus.removeAllItems();
		bcJobOfferStatus.addAll(jobOfferStatusService.getAll());		
		
		jobOfferStatusField.setContainerDataSource(bcJobOfferStatus);
				
	}
	
	
	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("640px");
		mainLayout.setHeight("300px");
		mainLayout.setMargin(true);
		
		// top-level component properties
		setWidth("640px");
		setHeight("300px");
		
		// emailField
		emailField = new TextField();
		emailField.setCaption("Email");
		emailField.setImmediate(false);
		emailField.setWidth("440px");
		emailField.setHeight("-1px");
		emailField.setTabIndex(9);
		mainLayout.addComponent(emailField, "top:136.0px;left:20.0px;");
		
		// jobOfferDateField
		jobOfferDateField = new DateField();
		jobOfferDateField.setCaption("Fecha oferta trabajo");
		jobOfferDateField.setImmediate(false);
		jobOfferDateField.setWidth("180px");
		jobOfferDateField.setHeight("-1px");
		jobOfferDateField.setInvalidAllowed(false);
		jobOfferDateField.setTabIndex(3);
		jobOfferDateField.setRequired(true);
		mainLayout.addComponent(jobOfferDateField, "top:20.0px;left:440.0px;");
		
		// mobileField
		mobileField = new TextField();
		mobileField.setCaption("Teléfono movil");
		mobileField.setImmediate(false);
		mobileField.setWidth("120px");
		mobileField.setHeight("-1px");
		mobileField.setTabIndex(8);
		mainLayout.addComponent(mobileField, "top:100.0px;left:340.0px;");
		
		// nameField
		nameField = new TextField();
		nameField.setCaption("Nombre");
		nameField.setImmediate(false);
		nameField.setWidth("323px");
		nameField.setHeight("-1px");
		nameField.setTabIndex(2);
		nameField.setRequired(true);
		mainLayout.addComponent(nameField, "top:17.0px;left:97.0px;");
		
		// observationField
		observationField = new TextField();
		observationField.setCaption("Observaciones");
		observationField.setImmediate(false);
		observationField.setWidth("600px");
		observationField.setHeight("100px");
		observationField.setTabIndex(10);
		mainLayout.addComponent(observationField, "top:180.0px;left:20.0px;");
		
		// phoneField
		phoneField = new TextField();
		phoneField.setCaption("Teléfono fijo");
		phoneField.setImmediate(false);
		phoneField.setWidth("100px");
		phoneField.setHeight("-1px");
		phoneField.setTabIndex(7);
		phoneField.setRequired(true);
		mainLayout.addComponent(phoneField, "top:100.0px;left:220.0px;");
		
		// surnameField
		surnameField = new TextField();
		surnameField.setCaption("Apellidos");
		surnameField.setImmediate(false);
		surnameField.setWidth("64.61%");
		surnameField.setHeight("-1px");
		surnameField.setTabIndex(4);
		surnameField.setRequired(true);
		mainLayout.addComponent(surnameField, "top:56.0px;left:20.0px;");
		
		// jobOfferStatusField
		jobOfferStatusField = new ComboBox();
		jobOfferStatusField.setCaption("Estado");
		jobOfferStatusField.setImmediate(false);
		jobOfferStatusField.setWidth("180px");
		jobOfferStatusField.setHeight("-1px");
		jobOfferStatusField.setTabIndex(5);
		jobOfferStatusField.setRequired(true);
		mainLayout
				.addComponent(jobOfferStatusField, "top:56.0px;left:440.0px;");
		
		// jobOfferTypeField
		jobOfferTypeField = new ComboBox();
		jobOfferTypeField.setCaption("Tipo");
		jobOfferTypeField.setImmediate(false);
		jobOfferTypeField.setWidth("180px");
		jobOfferTypeField.setHeight("-1px");
		jobOfferTypeField.setTabIndex(6);
		jobOfferTypeField.setRequired(true);
		mainLayout.addComponent(jobOfferTypeField, "top:100.0px;left:20.0px;");
		
		// tittleField
		tittleField = new TextField();
		tittleField.setCaption("Tratamiento");
		tittleField.setImmediate(false);
		tittleField.setWidth("60px");
		tittleField.setHeight("-1px");
		tittleField.setTabIndex(1);
		mainLayout.addComponent(tittleField, "top:17.0px;left:20.0px;");
		
		// btnCV
		cvField = new FileField();
		cvField.setCaption("Adjuntar C.V");
		cvField.setImmediate(true);
		cvField.setWidth("120px");
		cvField.setHeight("-1px");
		mainLayout.addComponent(cvField, "top:100.0px;left:480.0px;");
		
		return mainLayout;
	}
}