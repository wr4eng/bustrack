package com.thingtrack.bustrack.view.module.vehicle.internal;

import org.springframework.beans.factory.annotation.Autowired;

import org.vaadin.dialogs.ConfirmDialog;

import com.thingtrack.bustrack.domain.Vehicle;
import com.thingtrack.bustrack.service.api.VehicleService;
import com.thingtrack.konekti.view.addon.data.BindingSource;
import com.thingtrack.konekti.view.addon.ui.AbstractView;
import com.thingtrack.konekti.view.addon.ui.BoxToolbar;
import com.thingtrack.konekti.view.addon.ui.BoxToolbar.ClickFilterButtonListener;
import com.thingtrack.konekti.view.addon.ui.BoxToolbar.ClickPrintButtonListener;
import com.thingtrack.konekti.view.addon.ui.DataGridView;
import com.thingtrack.konekti.view.addon.ui.EditionToolbar;
import com.thingtrack.konekti.view.addon.ui.EditionToolbar.ClickAddButtonListener;
import com.thingtrack.konekti.view.addon.ui.EditionToolbar.ClickEditButtonListener;
import com.thingtrack.konekti.view.addon.ui.EditionToolbar.ClickRemoveButtonListener;
import com.thingtrack.konekti.view.addon.ui.NavigationToolbar;
import com.thingtrack.konekti.view.addon.ui.NavigationToolbar.ClickNavigationEvent;
import com.thingtrack.konekti.view.addon.ui.NavigationToolbar.ClickRefreshButtonListener;
import com.thingtrack.konekti.view.addon.ui.WindowDialog;
import com.thingtrack.konekti.view.addon.ui.WindowDialog.DialogResult;
import com.thingtrack.konekti.view.kernel.ui.layout.IViewContainer;
import com.thingtrack.bustrack.view.web.form.VehicleViewForm;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class VehicleView extends AbstractView 
	implements ClickRefreshButtonListener, ClickAddButtonListener, ClickEditButtonListener, ClickRemoveButtonListener, ClickFilterButtonListener, ClickPrintButtonListener {

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private DataGridView dgVehicle;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@Autowired
	private VehicleService vehicleService;
	
	private BindingSource<Vehicle> bsVehicle =  new BindingSource<Vehicle>(Vehicle.class, 0);
	
	private NavigationToolbar navigationToolbar;
	private EditionToolbar editionToolbar;
	private BoxToolbar boxToolbar;
		
	private IViewContainer viewContainer;
	
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public VehicleView(IViewContainer viewContainer) {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		// set Slide View Services and ViewContainer to navigate
		this.viewContainer = viewContainer;
		this.vehicleService = VehicleViewContainer.getVehicleService();
		
		// initialize datasource views		
		initView();	
		
	}

	private void initView() {
		// initialize Slide View Organization View
		dgVehicle.setImmediate(true);		
		dgVehicle.setSelectable(true);
		
		refreshBindindSource();
		
		// STEP 01: create grid view for slide Organization View
		try {
			dgVehicle.setBindingSource(bsVehicle);
			dgVehicle.setVisibleColumns(new String[] { "vehicleNumber", "enrollment", "seatings", "chassis" } );       
			dgVehicle.setColumnHeaders(new String[] { "Número Vehículo", "Matrícula", "Asientos", "Chasis" } );
		}
		catch(Exception e) {
			throw new RuntimeException("¡Error al crear el grid de Vehículos!", e);
		}
			
		// STEP 02: create toolbar for slide Organization View
		navigationToolbar = new NavigationToolbar(0, bsVehicle, viewContainer);
		editionToolbar = new EditionToolbar(1, bsVehicle);
		boxToolbar = new BoxToolbar(2, bsVehicle);
		
		navigationToolbar.addListenerRefreshButton(this);
		navigationToolbar.setDownButton(false);
		navigationToolbar.setUpButton(false);
		
		editionToolbar.addListenerAddButton(this);
		editionToolbar.addListenerEditButton(this);
		editionToolbar.addListenerDeleteButton(this);
		
		boxToolbar.addListenerFilterButton(this);
		boxToolbar.addListenerPrintButton(this);
		
		dgVehicle.addListenerAddButton(this);
		dgVehicle.addListenerEditButton(this);
		dgVehicle.addListenerDeleteButton(this);
		
		removeAllToolbar();
		
		addToolbar(navigationToolbar);
		addToolbar(editionToolbar);
		addToolbar(boxToolbar);

	}
	
	private void refreshBindindSource() {
		try {		
			bsVehicle.removeAllItems();
			bsVehicle.addAll(vehicleService.getAll());		
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("¡No se pudo refrescar los Vehículos!", e);
		} catch (Exception e) {
			throw new RuntimeException("¡No se pudo refrescar los Vehículos!", e);
		}		
	}		
	
	@Override
	public void refreshButtonClick(ClickNavigationEvent event) {
		refreshBindindSource();
		
	}
	
	private void refreshDataGridView(Vehicle vehicleSaved) {
		if(bsVehicle.containsId(vehicleSaved)){			
			Vehicle previousVehicle= bsVehicle.prevItemId(vehicleSaved);
			
			bsVehicle.removeItem(vehicleSaved);
			bsVehicle.addItemAfter(previousVehicle, vehicleSaved);
			bsVehicle.setItemId(vehicleSaved);
		}
		else
			bsVehicle.addItem(vehicleSaved);
		
	}
	
	@Override
	public void addButtonClick(EditionToolbar.ClickNavigationEvent event) {
		Vehicle vehicle = new Vehicle();

		try {
			@SuppressWarnings("unused")
			WindowDialog<Vehicle> windowDialog = new WindowDialog<Vehicle>(getWindow(), "Nuevo Vehículo", 
					"Guardar", DialogResult.SAVE, "Cancelar", DialogResult.CANCEL, 
					new VehicleViewForm(), vehicle, 
					new WindowDialog.CloseWindowDialogListener<Vehicle>() {
			    public void windowDialogClose(WindowDialog<Vehicle>.CloseWindowDialogEvent<Vehicle> event) {
			    	if (event.getDialogResult() != WindowDialog.DialogResult.SAVE)
			    		return ;
			    	
			    	try {
			    		Vehicle savingVehicle = event.getDomainEntity();
			    					    		
			    		Vehicle savedVehicle = vehicleService.save(savingVehicle);
			    		
			    		refreshDataGridView(savedVehicle);
					} catch (Exception e) {
						throw new RuntimeException("¡No se pudo crear el nuevo vehículo!", e);
						
					}
			    }

			});
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("¡No se pudo abrir el formulario Nuevo Vehículo!", e);

		} catch (Exception e) {
			throw new RuntimeException("¡No se pudo abrir el formulario Nuevo Vehículo!", e);
		}
		
	}
	
	@Override
	public void editButtonClick(EditionToolbar.ClickNavigationEvent event) {
		Vehicle editingVehicle = (Vehicle) event.getRegister();
				
		try {
			@SuppressWarnings("unused")
			WindowDialog<Vehicle> windowDialog = new WindowDialog<Vehicle>(getApplication().getMainWindow() , "Editor Vehículo", 
					"Guardar", DialogResult.SAVE, "Cancelar", DialogResult.CANCEL, 
					new VehicleViewForm(),editingVehicle, 
					new WindowDialog.CloseWindowDialogListener<Vehicle>() {
			    public void windowDialogClose(WindowDialog<Vehicle>.CloseWindowDialogEvent<Vehicle> event) {
			    	if (event.getDialogResult() != WindowDialog.DialogResult.SAVE)
			    		return ;
			    	
			    	try {			    		
			    		Vehicle savingVehicle = event.getDomainEntity();
			    		
			    		Vehicle savedVehicle = vehicleService.save(savingVehicle);			    					    		
			    		
			    		refreshDataGridView(savedVehicle);
					} catch (Exception e) {
						throw new RuntimeException("¡No se pudo modificar el vehículo!", e);
												
					}
			    }

			});
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("¡No se pudo abrir el formulario Editor Vehículo!", e);
		} catch (Exception e) {
			throw new RuntimeException("¡No se pudo abrir el formulario Editor Vehículo!", e);
		} 
		
	}
	
	@Override
	public void deleteButtonClick(EditionToolbar.ClickNavigationEvent event) {
		final Vehicle editingVehicle = (Vehicle) event.getRegister();
		
		if (editingVehicle == null)
			return;
		
		ConfirmDialog.show(getWindow(), "Borrar Vehículo", "¿Estás seguro?", "Si", "No",
		        new ConfirmDialog.Listener() {

		            public void onClose(ConfirmDialog dialog) {
		                if (dialog.isConfirmed()) {
		            		try {
		            			vehicleService.delete(editingVehicle);
		            			
		            			bsVehicle.removeItem(editingVehicle);		            			
		            		} catch (IllegalArgumentException e) {
		            			throw new RuntimeException("¡No se pudo borrar el vehículo!", e);
		            		} catch (Exception e) {
		            			throw new RuntimeException("¡No se pudo borrar el vehículo!", e);
		            		}
		                } 
		            }
		        });
		
	}
	
	@AutoGenerated
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
		
		// dataGridView_1
		dgVehicle = new DataGridView();
		dgVehicle.setImmediate(false);
		dgVehicle.setWidth("100.0%");
		dgVehicle.setHeight("100.0%");
		mainLayout.addComponent(dgVehicle);
		mainLayout.setExpandRatio(dgVehicle, 1.0f);
		
		return mainLayout;
	}

	@Override
	public void filterButtonClick(BoxToolbar.ClickNavigationEvent event) {
		dgVehicle.setFilterBarVisible();
		
	}

	@Override
	public void printButtonClick(BoxToolbar.ClickNavigationEvent event) {
		try {
			dgVehicle.print("Maestro Vehículos");
		}
		catch (Exception e) {
			throw new RuntimeException("¡No se pudo imprimir el informe!", e);
		}
		
	}

}
