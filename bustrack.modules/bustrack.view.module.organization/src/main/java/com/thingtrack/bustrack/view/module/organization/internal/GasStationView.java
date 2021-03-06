package com.thingtrack.bustrack.view.module.organization.internal;

import java.util.List;

import org.vaadin.dialogs.ConfirmDialog;

import com.thingtrack.bustrack.domain.GasStation;
import com.thingtrack.bustrack.service.api.GasStationService;

import com.thingtrack.konekti.domain.Workshop;
import com.thingtrack.konekti.view.addon.data.BindingSource;
import com.thingtrack.konekti.view.addon.ui.AbstractView;
import com.thingtrack.konekti.view.addon.ui.BoxToolbar;
import com.thingtrack.konekti.view.addon.ui.BoxToolbar.ClickFilterButtonListener;
import com.thingtrack.konekti.view.addon.ui.BoxToolbar.ClickPrintButtonListener;
import com.thingtrack.konekti.view.addon.ui.DataGridView;
import com.thingtrack.konekti.view.addon.ui.EditionToolbar;
import com.thingtrack.konekti.view.addon.ui.NavigationToolbar;
import com.thingtrack.konekti.view.addon.ui.WindowDialog;
import com.thingtrack.konekti.view.addon.ui.EditionToolbar.ClickAddButtonListener;
import com.thingtrack.konekti.view.addon.ui.EditionToolbar.ClickEditButtonListener;
import com.thingtrack.konekti.view.addon.ui.EditionToolbar.ClickRemoveButtonListener;
import com.thingtrack.konekti.view.addon.ui.NavigationToolbar.ClickNavigationEvent;
import com.thingtrack.konekti.view.addon.ui.NavigationToolbar.ClickRefreshButtonListener;
import com.thingtrack.konekti.view.addon.ui.NavigationToolbar.ClickUpButtonListener;
import com.thingtrack.konekti.view.addon.ui.WindowDialog.DialogResult;
import com.thingtrack.konekti.view.kernel.ui.layout.IViewContainer;
import com.thingtrack.bustrack.view.web.form.GasStationViewForm;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class GasStationView extends AbstractView 
	implements ClickUpButtonListener, ClickRefreshButtonListener, ClickAddButtonListener, ClickEditButtonListener, ClickRemoveButtonListener, ClickFilterButtonListener, ClickPrintButtonListener {

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private DataGridView dgGasStation;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	private Workshop workshop;
	private GasStationService gasStationService;
	
	private BindingSource<GasStation> bsGasStation = new BindingSource<GasStation>(GasStation.class, 3);
	
	private NavigationToolbar navigationToolbar;
	private EditionToolbar editionToolbar;
	private BoxToolbar boxToolbar;
	
	private List<GasStation> gasStations;
	
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public GasStationView(IViewContainer viewContainer) {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		dgGasStation.setImmediate(true);
		
		this.viewContainer = viewContainer;
		this.gasStationService = OrganizationViewContainer.getGasStationService();
		
	}

	public void setGasStations(Workshop workshop) {
		this.workshop = workshop;
		this.gasStations = workshop.getGasStations();
		
		// refresh datasource
		refreshBindindSource();
		injectBindingSource();
		
	}
		
	private void refreshBindindSource() {
		try {		
			bsGasStation.removeAllItems();
			bsGasStation.addAll(gasStations);					
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private void injectBindingSource() {
		try {
			dgGasStation.setBindingSource(bsGasStation);
			dgGasStation.setVisibleColumns(new String[] { "code", "description", "comment" } );       
			dgGasStation.setColumnHeaders(new String[] { "Código", "Descriptión", "Comentarios" } );
						
			// create toolbar for slide
			navigationToolbar = new NavigationToolbar(0, bsGasStation, viewContainer);
			editionToolbar = new EditionToolbar(1, bsGasStation);
			boxToolbar = new BoxToolbar(2, bsGasStation);
			
			navigationToolbar.addListenerUpButton(this);
			
			editionToolbar.addListenerAddButton(this);
			editionToolbar.addListenerEditButton(this);
			editionToolbar.addListenerDeleteButton(this);
			
			boxToolbar.addListenerFilterButton(this);
			boxToolbar.addListenerPrintButton(this);
			
			dgGasStation.addListenerAddButton(this);
			dgGasStation.addListenerEditButton(this);
			dgGasStation.addListenerDeleteButton(this);
			
			removeAllToolbar();
			
			addToolbar(navigationToolbar);
			addToolbar(editionToolbar);
			addToolbar(boxToolbar);
		} catch (Exception ex) {
			ex.getMessage();
			
		}
		
	}
	
	@Override
	public void upButtonClick(ClickNavigationEvent event) {
		// roll to the detail Organization Parent View
		viewContainer.getSliderView().rollPrevious();
		
	}
	
	@Override
	public void refreshButtonClick(ClickNavigationEvent event) {
		refreshBindindSource();
		
	}
	
	@Override
	public void addButtonClick(EditionToolbar.ClickNavigationEvent event) {
		final GasStation gasStation;
		try {
			gasStation = gasStationService.createEntity(workshop);
		} catch (Exception e) {
			throw new RuntimeException(
					"¡No se pudo crear el nuevo código de estación de carburante!",
					e);
		}
		
		try {
			@SuppressWarnings("unused")
			WindowDialog<GasStation> windowDialog = new WindowDialog<GasStation>(getWindow(), "Nueva Estación Carburante", 
					"Guardar", DialogResult.SAVE, "Cancelar", DialogResult.CANCEL, 
					new GasStationViewForm(), gasStation, 
					new WindowDialog.CloseWindowDialogListener<GasStation>() {
			    public void windowDialogClose(WindowDialog<GasStation>.CloseWindowDialogEvent<GasStation> event) {
			    	if (event.getDialogResult() != WindowDialog.DialogResult.SAVE)
			    		return ;
			    	
			    	try {
			    		
						gasStationService.save(event.getDomainEntity());
						//workshop.addGasStation(gasStation);
			    		
					} catch (Exception e) {
						throw new RuntimeException(
								"¡No se pudo crear la nueva estación de carburante!",
								e);
						
					}
			    }

			});
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(
					"¡No se pudo abrir el formulario Nueva Estación de Carburante!", e);
		} catch (Exception e) {
			throw new RuntimeException(
					"¡No se pudo abrir el formulario Nueva Estación de Carburante!", e);
		}
		
	}


	@Override
	public void editButtonClick(EditionToolbar.ClickNavigationEvent event) {
		GasStation editingGasStation = (GasStation) event.getRegister();
				
		try {
			@SuppressWarnings("unused")
			WindowDialog<GasStation> windowDialog = new WindowDialog<GasStation>(getWindow(), "Editor Estación Carburante", 
					"Guardar", DialogResult.SAVE, "Cancelar", DialogResult.CANCEL, 
					new GasStationViewForm(), editingGasStation, 
					new WindowDialog.CloseWindowDialogListener<GasStation>() {
			    public void windowDialogClose(WindowDialog<GasStation>.CloseWindowDialogEvent<GasStation> event) {
			    	if (event.getDialogResult() != WindowDialog.DialogResult.SAVE)
			    		return ;
			    	
			    	try {
			    		
						gasStationService.save(event.getDomainEntity());			    		
			    		
					} catch (Exception e) {
						throw new RuntimeException(
								"¡No se pudo modificar la estación de carburante!", e);
						
					}
			    }

			});
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(
					"¡No se pudo abrir el formulario Editor Estación de Carburante!", e);
		} catch (Exception e) {
			throw new RuntimeException(
					"¡No se pudo abrir el formulario Editor Estación de Carburante!", e);
		}
		
	}
	
	@Override
	public void deleteButtonClick(EditionToolbar.ClickNavigationEvent event) {
		final GasStation editingGasStation = (GasStation) event.getRegister();
		
		if (editingGasStation == null)
			return;
		
		ConfirmDialog.show(getWindow(), "Borrar Estación Carburante",
		        "¿Estás seguro?", "Si", "No",
		        new ConfirmDialog.Listener() {

		            public void onClose(ConfirmDialog dialog) {
		                if (dialog.isConfirmed()) {
		            		try {
		            			gasStationService.delete(editingGasStation);
		            			
		            			// refresh
		            			refreshBindindSource();
		            			
		            		} catch (IllegalArgumentException e) {
								throw new RuntimeException(
										"¡No se pudo borrar la estación de carburante!", e);
		            		} catch (Exception e) {
								throw new RuntimeException(
										"¡No se pudo borrar la estación de carburante!", e);
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
		
		// dataGridView
		dgGasStation = new DataGridView();
		dgGasStation.setImmediate(false);
		dgGasStation.setWidth("100.0%");
		dgGasStation.setHeight("100.0%");
		mainLayout.addComponent(dgGasStation);
		mainLayout.setExpandRatio(dgGasStation, 1.0f);
		
		return mainLayout;
	}

	@Override
	public void filterButtonClick(BoxToolbar.ClickNavigationEvent event) {		
		this.dgGasStation.setFilterBarVisible();
		
	}

	@Override
	public void printButtonClick(BoxToolbar.ClickNavigationEvent event) {
		try {
			dgGasStation.print("Listado Estaciones de Carburante del Taller " + workshop.getName());
		}
		catch (Exception e) {
			throw new RuntimeException("¡No se pudo imprimir el informe!", e);
		}
				
	}

}
