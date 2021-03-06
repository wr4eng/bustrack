package com.thingtrack.bustrack.view.web.form;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;


@SuppressWarnings("serial")
public class BulletinBoardViewForm extends CustomComponent {

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private ComboBox bulletinTypeField;
	@AutoGenerated
	private TextField phoneField;
	@AutoGenerated
	private TextField nameField;
	@AutoGenerated
	private TextField mobileField;
	@AutoGenerated
	private TextField messageField;
	@AutoGenerated
	private TextField emailField;
	@AutoGenerated
	private DateField bulletinDateField;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public BulletinBoardViewForm() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
	}
	
	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("560px");
		mainLayout.setHeight("360px");
		mainLayout.setMargin(true);
		
		// top-level component properties
		setWidth("560px");
		setHeight("360px");
		
		// bulletinDateField
		bulletinDateField = new DateField();
		bulletinDateField.setCaption("Fecha consulta");
		bulletinDateField.setImmediate(false);
		bulletinDateField.setWidth("160px");
		bulletinDateField.setHeight("-1px");
		bulletinDateField.setInvalidAllowed(false);
		bulletinDateField.setRequired(true);
		mainLayout.addComponent(bulletinDateField, "top:20.0px;left:380.0px;");
		
		// emailField
		emailField = new TextField();
		emailField.setCaption("Email");
		emailField.setImmediate(false);
		emailField.setWidth("420px");
		emailField.setHeight("-1px");
		emailField.setSecret(false);
		mainLayout.addComponent(emailField, "top:96.0px;left:20.0px;");
		
		// messageField
		messageField = new TextField();
		messageField.setCaption("Mensaje");
		messageField.setImmediate(false);
		messageField.setWidth("520px");
		messageField.setHeight("200px");
		messageField.setSecret(false);
		mainLayout.addComponent(messageField, "top:140.0px;left:20.0px;");
		
		// mobileField
		mobileField = new TextField();
		mobileField.setCaption("Teléfono móvil");
		mobileField.setImmediate(false);
		mobileField.setWidth("140px");
		mobileField.setHeight("-1px");
		mobileField.setSecret(false);
		mainLayout.addComponent(mobileField, "top:57.0px;left:20.0px;");
		
		// nameField
		nameField = new TextField();
		nameField.setCaption("Nombre");
		nameField.setImmediate(false);
		nameField.setWidth("340px");
		nameField.setHeight("-1px");
		nameField.setRequired(true);
		nameField.setSecret(false);
		mainLayout.addComponent(nameField, "top:20.0px;left:20.0px;");
		
		// phoneField
		phoneField = new TextField();
		phoneField.setCaption("Teléfono fijo");
		phoneField.setImmediate(false);
		phoneField.setWidth("120px");
		phoneField.setHeight("-1px");
		phoneField.setRequired(true);
		phoneField.setSecret(false);
		mainLayout.addComponent(phoneField, "top:57.0px;left:180.0px;");
		
		// bulletinTypeField
		bulletinTypeField = new ComboBox();
		bulletinTypeField.setCaption("Tipo consulta");
		bulletinTypeField.setImmediate(false);
		bulletinTypeField.setWidth("160px");
		bulletinTypeField.setHeight("-1px");
		bulletinTypeField.setRequired(true);
		mainLayout.addComponent(bulletinTypeField, "top:60.0px;left:380.0px;");
		
		return mainLayout;
	}
}