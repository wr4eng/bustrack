package com.thingtrack.bustrack.view.web.form.field;

import org.vaadin.addon.customfield.CustomField;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;

@SuppressWarnings("serial")
public class WeekDayCollectionField extends CustomField {
	@AutoGenerated
	private HorizontalLayout mainLayout;
	@AutoGenerated
	private Button btnSave;
	@AutoGenerated
	private CheckBox chkSunday;
	@AutoGenerated
	private CheckBox chkSaturday;
	@AutoGenerated
	private CheckBox chkFriday;
	@AutoGenerated
	private CheckBox chkThursday;
	@AutoGenerated
	private CheckBox chkWednesday;
	@AutoGenerated
	private CheckBox chkTuesday;
	@AutoGenerated
	private CheckBox chkMonday;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public WeekDayCollectionField() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
	}

	@Override
	public void setPropertyDataSource(Property newDataSource) {
		String collectionsValue = (String) newDataSource.getValue();

		char[] stringArray;
		
		if (collectionsValue == null)
			return;
		
		stringArray = collectionsValue.toCharArray();
		   
	    //display the array
	    for(int index=0; index < stringArray.length; index++) {
	    	if (stringArray[index] == 'L')
	    		chkMonday.setValue(true);
	    	
	    	if (stringArray[index] == 'M')
	    		chkTuesday.setValue(true);
	    	
	    	if (stringArray[index] == 'X')
	    		chkWednesday.setValue(true);
	    	
	    	if (stringArray[index] == 'J')
	    		chkThursday.setValue(true);
	    	
	    	if (stringArray[index] == 'V')
	    		chkFriday.setValue(true);
	    	
	    	if (stringArray[index] == 'S')
	    		chkSaturday.setValue(true);
	    	
	    	if (stringArray[index] == 'D')
	    		chkSunday.setValue(true);
	    	
	    }

		super.setPropertyDataSource(newDataSource);
	}
	
	@Override
	public Object getValue() {		
		StringBuilder weekdays = new StringBuilder();
		
		if ((Boolean)chkMonday.getValue())
			weekdays = weekdays.append("L");

		if ((Boolean)chkTuesday.getValue())
			weekdays = weekdays.append("M");

		if ((Boolean)chkWednesday.getValue())
			weekdays = weekdays.append("X");

		if ((Boolean)chkThursday.getValue())
			weekdays = weekdays.append("J");

		if ((Boolean)chkFriday.getValue())
			weekdays = weekdays.append("V");

		if ((Boolean)chkSaturday.getValue())
			weekdays = weekdays.append("S");

		if ((Boolean)chkSunday.getValue())
			weekdays = weekdays.append("D");
				
		return weekdays.toString();
	}
	
	@Override
	public Class<?> getType() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@AutoGenerated
	private HorizontalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new HorizontalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("535px");
		mainLayout.setHeight("28px");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("535px");
		setHeight("28px");
		
		// chkMonday
		chkMonday = new CheckBox();
		chkMonday.setCaption("Lunes");
		chkMonday.setImmediate(false);
		chkMonday.setWidth("-1px");
		chkMonday.setHeight("-1px");
		mainLayout.addComponent(chkMonday);
		mainLayout.setComponentAlignment(chkMonday, new Alignment(33));
		
		// chkTuesday
		chkTuesday = new CheckBox();
		chkTuesday.setCaption("Martes");
		chkTuesday.setImmediate(false);
		chkTuesday.setWidth("-1px");
		chkTuesday.setHeight("-1px");
		mainLayout.addComponent(chkTuesday);
		mainLayout.setComponentAlignment(chkTuesday, new Alignment(33));
		
		// chkWednesday
		chkWednesday = new CheckBox();
		chkWednesday.setCaption("Miércoles");
		chkWednesday.setImmediate(false);
		chkWednesday.setWidth("-1px");
		chkWednesday.setHeight("-1px");
		mainLayout.addComponent(chkWednesday);
		mainLayout.setComponentAlignment(chkWednesday, new Alignment(33));
		
		// chkThursday
		chkThursday = new CheckBox();
		chkThursday.setCaption("Jueves");
		chkThursday.setImmediate(false);
		chkThursday.setWidth("-1px");
		chkThursday.setHeight("-1px");
		mainLayout.addComponent(chkThursday);
		mainLayout.setComponentAlignment(chkThursday, new Alignment(33));
		
		// chkFriday
		chkFriday = new CheckBox();
		chkFriday.setCaption("Viernes");
		chkFriday.setImmediate(false);
		chkFriday.setWidth("-1px");
		chkFriday.setHeight("-1px");
		mainLayout.addComponent(chkFriday);
		mainLayout.setComponentAlignment(chkFriday, new Alignment(33));
		
		// chkSaturday
		chkSaturday = new CheckBox();
		chkSaturday.setCaption("Sábado");
		chkSaturday.setImmediate(false);
		chkSaturday.setWidth("-1px");
		chkSaturday.setHeight("-1px");
		mainLayout.addComponent(chkSaturday);
		mainLayout.setComponentAlignment(chkSaturday, new Alignment(33));
		
		// chkSunday
		chkSunday = new CheckBox();
		chkSunday.setCaption("Domingo");
		chkSunday.setImmediate(false);
		chkSunday.setWidth("-1px");
		chkSunday.setHeight("-1px");
		mainLayout.addComponent(chkSunday);
		mainLayout.setComponentAlignment(chkSunday, new Alignment(33));
		
		// btnSave
		btnSave = new Button();
		btnSave.setCaption("Guardar");
		btnSave.setImmediate(true);
		btnSave.setWidth("-1px");
		btnSave.setHeight("-1px");
		mainLayout.addComponent(btnSave);
		mainLayout.setExpandRatio(btnSave, 1.0f);
		
		return mainLayout;
	}

}
