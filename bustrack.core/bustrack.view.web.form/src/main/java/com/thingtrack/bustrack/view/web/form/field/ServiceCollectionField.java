package com.thingtrack.bustrack.view.web.form.field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.vaadin.addon.customfield.CustomField;

import com.thingtrack.konekti.domain.Service;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;

@SuppressWarnings("serial")
public class ServiceCollectionField extends CustomField {
	private VerticalLayout mainLayout;
	private VerticalLayout vlTurn;
	private HorizontalLayout hlToolbar;
	private Button btnRemove;
	private Button btnAdd;	
	private Table tbService;
	
	private BeanItemContainer<Service> servicesTableContainer;
	
	public ServiceCollectionField() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
				
		servicesTableContainer = new BeanItemContainer<Service>(Service.class);		
		servicesTableContainer.addNestedContainerProperty("serviceType.description");
		servicesTableContainer.addNestedContainerProperty("client.name");
		
		tbService.setContainerDataSource(servicesTableContainer);
		
		tbService.setVisibleColumns(new String[] { "serviceType.description", "code", "description", "client.name" } );       
		tbService.setColumnHeaders(new String[] { "Tipo", "Código", "Descriptión", "Cliente" } );
		
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
		
		servicesTableContainer.removeAllItems();
		servicesTableContainer.addAll(collectionsValue);
				
		super.setPropertyDataSource(newDataSource);
	}
	
	@Override
	public Object getValue() {
		
		return new ArrayList<Service>(
				(Collection<? extends Service>) servicesTableContainer.getItemIds());
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
		vlTurn = buildVlOrganization();
		mainLayout.addComponent(vlTurn);
		mainLayout.setExpandRatio(vlTurn, 1.0f);
		
		return mainLayout;
	}

	private VerticalLayout buildVlOrganization() {
		// common part: create layout
		vlTurn = new VerticalLayout();
		vlTurn.setImmediate(false);
		vlTurn.setWidth("100.0%");
		vlTurn.setHeight("100.0%");
		vlTurn.setMargin(false);
		
		// tbTable
		tbService = new Table();
		tbService.setImmediate(true);
		tbService.setSelectable(true);
		tbService.setMultiSelect(false);
		tbService.setColumnCollapsingAllowed(true);
		tbService.setWidth("100.0%");
		tbService.setHeight("100.0%");
		tbService.setEditable(true);
		tbService.setTableFieldFactory(new TableFieldFactory() {					
			@Override
			public Field createField(Container container, Object itemId, Object propertyId, Component uiContext) {
				if("active".equals(propertyId)) {
					
					CheckBox field = new CheckBox();
					field.setReadOnly(true);
					return field;
				}
				
				return null;
			}
		});
		
		vlTurn.addComponent(tbService);
		vlTurn.setExpandRatio(tbService, 1.0f);
		
		// hlToolbar
		hlToolbar = buildHlToolbar();
		vlTurn.addComponent(hlToolbar);
		
		return vlTurn;
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
