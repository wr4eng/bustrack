package com.thingtrack.bustrack.view.web.form.field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.vaadin.addon.customfield.CustomField;
import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import com.thingtrack.bustrack.domain.Tire;
import com.thingtrack.bustrack.domain.Vehicle;
import com.thingtrack.bustrack.view.web.form.TireViewForm;
import com.thingtrack.konekti.view.addon.ui.WindowDialog;
import com.thingtrack.konekti.view.addon.ui.WindowDialog.DialogResult;

@SuppressWarnings("serial")
public class TireRegisterCollectionField extends CustomField {
	private VerticalLayout mainLayout;
	private VerticalLayout vlTireRegister;
	private HorizontalLayout hlToolbar;
	private Button btnRemove;
	private Button btnAdd;	
	private Table tbTireRegister;
	
	private BeanItemContainer<Tire> tireRegisterTableContainer;
	private Vehicle vehicle;
	
	public TireRegisterCollectionField() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		tireRegisterTableContainer = new BeanItemContainer<Tire>(Tire.class);
		tireRegisterTableContainer.addNestedContainerProperty("tireStatus.description");
		
		tbTireRegister.setContainerDataSource(tireRegisterTableContainer);
		
		tbTireRegister.setVisibleColumns(new String[] { "serialNumber", "km", "comment", "tireStatus.description"} );       
		tbTireRegister.setColumnHeaders(new String[] { "Numero Serie", "Km", "Comentarios", "Estado"} );		
				
		// set button event handlers
		btnAdd.addListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Tire tire = new Tire();							
				tire.setVehicle(vehicle);
				
				try {
					@SuppressWarnings("unused")
					WindowDialog<Tire> windowDialog = new WindowDialog<Tire>(getApplication().getMainWindow(), "Nuevo Neumático", 
							"Guardar", DialogResult.SAVE, "Cancelar", DialogResult.CANCEL, 
							new TireViewForm(), tire, 
							new WindowDialog.CloseWindowDialogListener<Tire>() {
					    public void windowDialogClose(WindowDialog<Tire>.CloseWindowDialogEvent<Tire> event) {
					    	if (event.getDialogResult() != WindowDialog.DialogResult.SAVE)
					    		return ;
					    	
					    	try {
					    		Tire savingTire = event.getDomainEntity();
					    		
					    		tireRegisterTableContainer.addBean(savingTire);
							} catch (Exception e) {
								throw new RuntimeException("¡No se pudo crear el nuevo neumático!", e);
								
							}
					    }

					});
				} catch (IllegalArgumentException e) {
					throw new RuntimeException("¡No se pudo abrir el formulario Nuevo Neumático!", e);
				} catch (Exception e) {
					throw new RuntimeException("¡No se pudo abrir el formulario Nuevo Neumático!", e);
				} 
			}
		});
		
		btnRemove.addListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				final Tire tireSelected = (Tire) tbTireRegister.getValue();
				
				try {
					ConfirmDialog.show(getApplication().getMainWindow(), "Borrar Neumático",
					        "¿Estás seguro?", "Si", "No",
					        new ConfirmDialog.Listener() {
	
					            public void onClose(ConfirmDialog dialog) {
					                if (dialog.isConfirmed()) {
					            		try {
					            			tireRegisterTableContainer.removeItem(tireSelected);
					            			
					            		} catch (IllegalArgumentException e) {
					            			throw new RuntimeException("¡No se pudo borrar el nuevo neumático!", e);
					            		} catch (Exception e) {
					            			throw new RuntimeException("¡No se pudo borrar el nuevo neumático!", e);
					            		}
					                } 
					            }
					        });
				} catch (IllegalArgumentException e) {
					throw new RuntimeException("¡No se pudo borrar el nuevo neumático!", e);
				} catch (Exception e) {
					throw new RuntimeException("¡No se pudo borrar el nuevo neumático!", e);
				} 
			}
		});
	}
	
	@Override
    public void attach() {  
		// recover the parent entity (Vehicle) from parent view window (VehicleViewForm)
		if (getParent().getWindow() instanceof WindowDialog<?>) {
			@SuppressWarnings("unchecked")
			WindowDialog<Vehicle> vehicleWindowDialog = (WindowDialog<Vehicle>)getParent().getWindow();
			vehicle = vehicleWindowDialog.getDomainEntity();
    		
		}
	}
	
	@Override
	public Class<?> getType() {
		return List.class;
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void setPropertyDataSource(Property newDataSource) {
		List collectionsValue = (List) newDataSource.getValue();
		
		tireRegisterTableContainer.removeAllItems();
		tireRegisterTableContainer.addAll(collectionsValue);
				
		super.setPropertyDataSource(newDataSource);
	}
	
	@Override
	public Object getValue() {		
		return new ArrayList<Tire>(
				(Collection<? extends Tire>) tireRegisterTableContainer.getItemIds());
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
		vlTireRegister = buildVlOrganization();
		mainLayout.addComponent(vlTireRegister);
		mainLayout.setExpandRatio(vlTireRegister, 1.0f);
		
		return mainLayout;
	}

	private VerticalLayout buildVlOrganization() {
		// common part: create layout
		vlTireRegister = new VerticalLayout();
		vlTireRegister.setImmediate(false);
		vlTireRegister.setWidth("100.0%");
		vlTireRegister.setHeight("100.0%");
		vlTireRegister.setMargin(false);
		
		// tbTable
		tbTireRegister = new Table();
		tbTireRegister.setImmediate(true);
		tbTireRegister.setSelectable(true);
		tbTireRegister.setMultiSelect(false);
		tbTireRegister.setColumnCollapsingAllowed(true);
		tbTireRegister.setWidth("100.0%");
		tbTireRegister.setHeight("100.0%");
		
		vlTireRegister.addComponent(tbTireRegister);
		vlTireRegister.setExpandRatio(tbTireRegister, 1.0f);
		
		// hlToolbar
		hlToolbar = buildHlToolbar();
		vlTireRegister.addComponent(hlToolbar);
		
		return vlTireRegister;
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
