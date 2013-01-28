package com.thingtrack.bustrack.view.web.form;

import java.util.Date;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import com.thingtrack.bustrack.domain.WorksheetStatus;
import com.thingtrack.bustrack.service.api.WorksheetStatusService;
import com.thingtrack.bustrack.view.web.form.field.WorksheetLineCollectionField;
import com.thingtrack.konekti.domain.EmployeeAgent;
import com.thingtrack.konekti.domain.Organization;
import com.thingtrack.konekti.view.kernel.IWorkbenchContext;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class WorksheetViewForm extends CustomComponent {

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private VerticalLayout verticalLayout_3;
	@AutoGenerated
	private HorizontalLayout horizontalLayout_3;
	@AutoGenerated
	private TextField worksheetHours;
	@AutoGenerated
	private WorksheetLineCollectionField worksheetLinesField;
	@AutoGenerated
	private HorizontalLayout horizontalLayout_2;
	@AutoGenerated
	private TextField driverWorkNumberField;
	@AutoGenerated
	private TextField driverNifField;
	@AutoGenerated
	private HorizontalLayout horizontalLayout_1;
	@AutoGenerated
	private TextField driverSurnameField;
	@AutoGenerated
	private TextField driverNameField;
	@AutoGenerated
	private HorizontalLayout horizontalLayout_5;
	@AutoGenerated
	private TextArea incidenceField;
	@AutoGenerated
	private TextArea observationField;
	@AutoGenerated
	private HorizontalLayout horizontalLayout_4;
	@AutoGenerated
	private ComboBox worksheetstatusField;
	@AutoGenerated
	private PopupDateField worksheetEndDateField;
	@AutoGenerated
	private PopupDateField worksheetStartDateField;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	/* Enterprise service */
	private WorksheetStatusService worksheetStatusService;

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public WorksheetViewForm() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		getServices();

		BeanItemContainer<WorksheetStatus> worksheetStatusBeanItemContainer = new BeanItemContainer<WorksheetStatus>(
				WorksheetStatus.class);
		
		 try {
			worksheetStatusBeanItemContainer.addAll(worksheetStatusService.getAll() );
			worksheetstatusField.setContainerDataSource(worksheetStatusBeanItemContainer);
			worksheetstatusField.setItemCaptionPropertyId("code");
			worksheetstatusField.setNullSelectionAllowed(false);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		observationField.setNullRepresentation("");
		incidenceField.setNullRepresentation("");
		driverWorkNumberField.setNullRepresentation("");

		driverNameField.setReadOnly(true);
		driverSurnameField.setReadOnly(true);
		driverNifField.setReadOnly(true);
		driverWorkNumberField.setReadOnly(true);
		worksheetStartDateField.setReadOnly(true);
		worksheetEndDateField.setReadOnly(true);
		worksheetHours.setReadOnly(true);
		
		//Field Validators
//		worksheetLinesField.setRequired(true);
//		worksheetLinesField.setRequiredError(worksheetLinesField.getCaption() + " es un campo requerido"); 
//		worksheetLinesField.addValidator(new AssignedVehicleValidator());
//		worksheetLinesField.addValidator(new VehicleOverlapValidator());
	}

	public WorksheetViewForm(IWorkbenchContext context) {

		this();
		worksheetLinesField.setOrganization(context.getOrganization());
	}

	public WorksheetViewForm(Organization organization, Date worksheetDate,
			EmployeeAgent driver) {

		this();
		worksheetLinesField.setOrganization(organization);
		worksheetLinesField.setWorksheetDate(worksheetDate);
		worksheetLinesField.setDriver(driver);
	}

	private void getServices() {

		BundleContext bundleContext = FrameworkUtil.getBundle(this.getClass())
				.getBundleContext();

		ServiceReference worksheetStatusServiceReference = bundleContext
				.getServiceReference(WorksheetStatusService.class.getName());

		this.worksheetStatusService = WorksheetStatusService.class
				.cast(bundleContext.getService(worksheetStatusServiceReference));

	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setCaption("Servicios asignables");
		mainLayout.setImmediate(false);
		mainLayout.setWidth("1500px");
		mainLayout.setHeight("600px");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("1500px");
		setHeight("600px");
		
		// verticalLayout_3
		verticalLayout_3 = buildVerticalLayout_3();
		mainLayout.addComponent(verticalLayout_3);
		
		return mainLayout;
	}

	@AutoGenerated
	private VerticalLayout buildVerticalLayout_3() {
		// common part: create layout
		verticalLayout_3 = new VerticalLayout();
		verticalLayout_3.setImmediate(false);
		verticalLayout_3.setWidth("100.0%");
		verticalLayout_3.setHeight("100.0%");
		verticalLayout_3.setMargin(true);
		verticalLayout_3.setSpacing(true);
		
		// horizontalLayout_4
		horizontalLayout_4 = buildHorizontalLayout_4();
		verticalLayout_3.addComponent(horizontalLayout_4);
		
		// horizontalLayout_5
		horizontalLayout_5 = buildHorizontalLayout_5();
		verticalLayout_3.addComponent(horizontalLayout_5);
		
		// horizontalLayout_1
		horizontalLayout_1 = buildHorizontalLayout_1();
		verticalLayout_3.addComponent(horizontalLayout_1);
		
		// horizontalLayout_2
		horizontalLayout_2 = buildHorizontalLayout_2();
		verticalLayout_3.addComponent(horizontalLayout_2);
		
		// worksheetLinesField
		worksheetLinesField = new WorksheetLineCollectionField();
		worksheetLinesField.setCaption("Servicios asignables");
		worksheetLinesField.setImmediate(false);
		worksheetLinesField.setWidth("100.0%");
		worksheetLinesField.setHeight("100.0%");
		verticalLayout_3.addComponent(worksheetLinesField);
		verticalLayout_3.setExpandRatio(worksheetLinesField, 1.0f);
		
		// horizontalLayout_3
		horizontalLayout_3 = buildHorizontalLayout_3();
		verticalLayout_3.addComponent(horizontalLayout_3);
		
		return verticalLayout_3;
	}

	@AutoGenerated
	private HorizontalLayout buildHorizontalLayout_4() {
		// common part: create layout
		horizontalLayout_4 = new HorizontalLayout();
		horizontalLayout_4.setImmediate(false);
		horizontalLayout_4.setWidth("100.0%");
		horizontalLayout_4.setHeight("-1px");
		horizontalLayout_4.setMargin(false);
		horizontalLayout_4.setSpacing(true);
		
		// worksheetStartDateField
		worksheetStartDateField = new PopupDateField();
		worksheetStartDateField.setCaption("Inicio");
		worksheetStartDateField.setImmediate(false);
		worksheetStartDateField.setWidth("100.0%");
		worksheetStartDateField.setHeight("-1px");
		worksheetStartDateField.setInvalidAllowed(false);
		worksheetStartDateField.setResolution(4);
		horizontalLayout_4.addComponent(worksheetStartDateField);
		horizontalLayout_4.setExpandRatio(worksheetStartDateField, 1.0f);
		
		// worksheetEndDateField
		worksheetEndDateField = new PopupDateField();
		worksheetEndDateField.setCaption("Fin");
		worksheetEndDateField.setImmediate(false);
		worksheetEndDateField.setWidth("100.0%");
		worksheetEndDateField.setHeight("-1px");
		worksheetEndDateField.setInvalidAllowed(false);
		worksheetEndDateField.setResolution(4);
		horizontalLayout_4.addComponent(worksheetEndDateField);
		horizontalLayout_4.setExpandRatio(worksheetEndDateField, 1.0f);
		
		// worksheetstatusField
		worksheetstatusField = new ComboBox();
		worksheetstatusField.setCaption("Estado");
		worksheetstatusField.setImmediate(false);
		worksheetstatusField.setWidth("100.0%");
		worksheetstatusField.setHeight("-1px");
		horizontalLayout_4.addComponent(worksheetstatusField);
		horizontalLayout_4.setExpandRatio(worksheetstatusField, 1.0f);
		
		return horizontalLayout_4;
	}

	@AutoGenerated
	private HorizontalLayout buildHorizontalLayout_5() {
		// common part: create layout
		horizontalLayout_5 = new HorizontalLayout();
		horizontalLayout_5.setImmediate(false);
		horizontalLayout_5.setWidth("100.0%");
		horizontalLayout_5.setHeight("-1px");
		horizontalLayout_5.setMargin(false);
		horizontalLayout_5.setSpacing(true);
		
		// observationField
		observationField = new TextArea();
		observationField.setCaption("Observaciones");
		observationField.setImmediate(false);
		observationField.setWidth("100.0%");
		observationField.setHeight("-1px");
		horizontalLayout_5.addComponent(observationField);
		horizontalLayout_5.setExpandRatio(observationField, 1.0f);
		
		// incidenceField
		incidenceField = new TextArea();
		incidenceField.setCaption("Incidencias");
		incidenceField.setImmediate(false);
		incidenceField.setWidth("100.0%");
		incidenceField.setHeight("-1px");
		horizontalLayout_5.addComponent(incidenceField);
		horizontalLayout_5.setExpandRatio(incidenceField, 1.0f);
		
		return horizontalLayout_5;
	}

	@AutoGenerated
	private HorizontalLayout buildHorizontalLayout_1() {
		// common part: create layout
		horizontalLayout_1 = new HorizontalLayout();
		horizontalLayout_1.setImmediate(false);
		horizontalLayout_1.setWidth("100.0%");
		horizontalLayout_1.setHeight("-1px");
		horizontalLayout_1.setMargin(false);
		horizontalLayout_1.setSpacing(true);
		
		// driverNameField
		driverNameField = new TextField();
		driverNameField.setCaption("Nombre");
		driverNameField.setImmediate(false);
		driverNameField.setWidth("100.0%");
		driverNameField.setHeight("-1px");
		horizontalLayout_1.addComponent(driverNameField);
		horizontalLayout_1.setExpandRatio(driverNameField, 1.0f);
		
		// driverSurnameField
		driverSurnameField = new TextField();
		driverSurnameField.setCaption("Apellidos");
		driverSurnameField.setImmediate(false);
		driverSurnameField.setWidth("100.0%");
		driverSurnameField.setHeight("-1px");
		horizontalLayout_1.addComponent(driverSurnameField);
		horizontalLayout_1.setExpandRatio(driverSurnameField, 1.0f);
		
		return horizontalLayout_1;
	}

	@AutoGenerated
	private HorizontalLayout buildHorizontalLayout_2() {
		// common part: create layout
		horizontalLayout_2 = new HorizontalLayout();
		horizontalLayout_2.setImmediate(false);
		horizontalLayout_2.setWidth("100.0%");
		horizontalLayout_2.setHeight("-1px");
		horizontalLayout_2.setMargin(false);
		horizontalLayout_2.setSpacing(true);
		
		// driverNifField
		driverNifField = new TextField();
		driverNifField.setCaption("Documento de identidad");
		driverNifField.setImmediate(false);
		driverNifField.setWidth("100.0%");
		worksheetHours.setSecret(false);
		driverNifField.setHeight("-1px");
		horizontalLayout_2.addComponent(driverNifField);
		horizontalLayout_2.setExpandRatio(driverNifField, 1.0f);
		
		// driverWorkNumberField
		driverWorkNumberField = new TextField();
		driverWorkNumberField.setCaption("Número empleado");
		driverWorkNumberField.setImmediate(false);
		driverWorkNumberField.setWidth("100.0%");
		driverWorkNumberField.setHeight("-1px");
		horizontalLayout_2.addComponent(driverWorkNumberField);
		horizontalLayout_2.setExpandRatio(driverWorkNumberField, 1.0f);
		
		return horizontalLayout_2;
	}

	@AutoGenerated
	private HorizontalLayout buildHorizontalLayout_3() {
		// common part: create layout
		horizontalLayout_3 = new HorizontalLayout();
		horizontalLayout_3.setImmediate(false);
		horizontalLayout_3.setWidth("100.0%");
		horizontalLayout_3.setHeight("-1px");
		horizontalLayout_3.setMargin(false);
		
		// worksheetHours
		worksheetHours = new TextField();
		worksheetHours.setCaption("Horas Totales");
		worksheetHours.setImmediate(false);
		worksheetHours.setWidth("-1px");
		worksheetHours.setHeight("-1px");
		worksheetHours.setNullRepresentation("0 horas");
		horizontalLayout_3.addComponent(worksheetHours);
		
		return horizontalLayout_3;
	}

}