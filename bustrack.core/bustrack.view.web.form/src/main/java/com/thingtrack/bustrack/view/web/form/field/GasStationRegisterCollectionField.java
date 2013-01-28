package com.thingtrack.bustrack.view.web.form.field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.vaadin.addon.customfield.CustomField;

import com.thingtrack.bustrack.domain.GasStationRegister;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
public class GasStationRegisterCollectionField extends CustomField {
	private VerticalLayout mainLayout;
	private VerticalLayout vlGasStationRegister;
	private HorizontalLayout hlToolbar;
	private Button btnRemove;
	private Button btnAdd;	
	private Table tbGasStationRegister;
	
	private BeanItemContainer<GasStationRegister> gasStationRegisterTableContainer;
	
	public GasStationRegisterCollectionField() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		gasStationRegisterTableContainer = new BeanItemContainer<GasStationRegister>(GasStationRegister.class);
		tbGasStationRegister.setContainerDataSource(gasStationRegisterTableContainer);
		
		tbGasStationRegister.setVisibleColumns(new String[] { "volume", "price", "comment"} );       
		tbGasStationRegister.setColumnHeaders(new String[] { "Volumen [L]", "Precio [e]", "Comentarios"} );		
		
		// set button event handlers
		btnAdd.addListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO
			}
		});
		
		btnRemove.addListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO
			}
		});
	}
	
	@Override
	public Class<?> getType() {
		return List.class;
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void setPropertyDataSource(Property newDataSource) {
		List collectionsValue = (List) newDataSource.getValue();
		
		gasStationRegisterTableContainer.removeAllItems();
		gasStationRegisterTableContainer.addAll(collectionsValue);
				
		super.setPropertyDataSource(newDataSource);
	}
	
	@Override
	public Object getValue() {		
		return new ArrayList<GasStationRegister>(
				(Collection<? extends GasStationRegister>) gasStationRegisterTableContainer.getItemIds());
	}
	
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
		
		// vlOrganization
		vlGasStationRegister = buildVlOrganization();
		mainLayout.addComponent(vlGasStationRegister);
		mainLayout.setExpandRatio(vlGasStationRegister, 1.0f);
		
		return mainLayout;
	}

	private VerticalLayout buildVlOrganization() {
		// common part: create layout
		vlGasStationRegister = new VerticalLayout();
		vlGasStationRegister.setImmediate(false);
		vlGasStationRegister.setWidth("100.0%");
		vlGasStationRegister.setHeight("100.0%");
		vlGasStationRegister.setMargin(false);
		
		// tbTable
		tbGasStationRegister = new Table();
		tbGasStationRegister.setImmediate(true);
		tbGasStationRegister.setSelectable(true);
		tbGasStationRegister.setMultiSelect(false);
		tbGasStationRegister.setColumnCollapsingAllowed(true);
		tbGasStationRegister.setWidth("100.0%");
		tbGasStationRegister.setHeight("100.0%");
		
		vlGasStationRegister.addComponent(tbGasStationRegister);
		vlGasStationRegister.setExpandRatio(tbGasStationRegister, 1.0f);
		
		// hlToolbar
		hlToolbar = buildHlToolbar();
		vlGasStationRegister.addComponent(hlToolbar);
		
		return vlGasStationRegister;
	}

	private HorizontalLayout buildHlToolbar() {
		// common part: create layout
		hlToolbar = new HorizontalLayout();
		hlToolbar.setImmediate(false);
		hlToolbar.setWidth("100.0%");
		hlToolbar.setHeight("26px");
		hlToolbar.setMargin(false);
		
		// btnAdd
		btnAdd = new Button();
		btnAdd.setCaption("Añadir");
		btnAdd.setImmediate(true);
		btnAdd.setWidth("-1px");
		btnAdd.setHeight("-1px");
		hlToolbar.addComponent(btnAdd);
		
		// btnRemove
		btnRemove = new Button();
		btnRemove.setCaption("Borrar");
		btnRemove.setImmediate(true);
		btnRemove.setWidth("-1px");
		btnRemove.setHeight("-1px");
		hlToolbar.addComponent(btnRemove);
		hlToolbar.setExpandRatio(btnRemove, 1.0f);
		hlToolbar.setComponentAlignment(btnRemove, new Alignment(33));
		
		return hlToolbar;
	}
	
}
