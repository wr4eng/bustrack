package com.thingtrack.bustrack.view.module.joboffer.internal;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.vaadin.dialogs.ConfirmDialog;

import com.thingtrack.bustrack.domain.JobOffer;
import com.thingtrack.bustrack.service.api.JobOfferService;
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
import com.thingtrack.bustrack.view.web.form.JobOfferViewForm;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class JobOfferView extends AbstractView  implements ClickRefreshButtonListener, ClickAddButtonListener, 
	ClickEditButtonListener, ClickRemoveButtonListener, ClickFilterButtonListener, ClickPrintButtonListener {

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private DataGridView dgJobOffer;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	private JobOfferService jobOfferService;
	
	private BindingSource<JobOffer> bsJobOffer =  new BindingSource<JobOffer>(JobOffer.class, 0);
	
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
	public JobOfferView(IViewContainer viewContainer) {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		// set Slide View Services and ViewContainer to navigate
		this.viewContainer = viewContainer;
		this.jobOfferService = JobOfferViewContainer.getJobOfferService();
		
		// initialize datasource views		
		initView();	
		
	}

	private void initView() {
		// initialize Slide View Organization View
		dgJobOffer.setImmediate(true);		
		
		refreshBindindSource();
		
		// STEP 01: create grid view for slide Organization View
		try {
			dgJobOffer.setBindingSource(bsJobOffer);
			dgJobOffer.setVisibleColumns(new String[] { "name", "surname", "observation", "phone", "email", "jobOfferType.description", "jobOfferStatus.description", "jobOfferDate" } );       
			dgJobOffer.setColumnHeaders(new String[] { "Nombre", "Apellidos", "Observaciones", "Teléfono", "Email", "Tipo", "Estado", "Fecha Registro" } );
			
			dgJobOffer.setColumnCollapsed("cv", true);
		}
		catch(Exception ex) {
			ex.getMessage();
		}
			
		// STEP 02: create toolbar for slide Organization View
		navigationToolbar = new NavigationToolbar(0, bsJobOffer, viewContainer);
		editionToolbar = new EditionToolbar(1, bsJobOffer);
		boxToolbar = new BoxToolbar(2, bsJobOffer);
		
		navigationToolbar.addListenerRefreshButton(this);
		navigationToolbar.setUpButton(false);
		navigationToolbar.setDownButton(false);
		
		editionToolbar.addListenerAddButton(this);
		editionToolbar.addListenerEditButton(this);
		editionToolbar.addListenerDeleteButton(this);
		
		boxToolbar.addListenerFilterButton(this);
		boxToolbar.addListenerPrintButton(this);
		
		dgJobOffer.addListenerAddButton(this);
		dgJobOffer.addListenerEditButton(this);
		dgJobOffer.addListenerDeleteButton(this);
		
		removeAllToolbar();
		
		addToolbar(navigationToolbar);
		addToolbar(editionToolbar);
		addToolbar(boxToolbar);

	}
	
	private void refreshBindindSource() {
		try {		
			bsJobOffer.removeAllItems();
			bsJobOffer.addAll(jobOfferService.getAll());
			
			bsJobOffer.addNestedContainerProperty("jobOfferType.description");
			bsJobOffer.addNestedContainerProperty("jobOfferStatus.description");
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}		
	
	@Override
	public void refreshButtonClick(ClickNavigationEvent event) {
		refreshBindindSource();
		
	}
	
	private void refreshDataGridView(JobOffer jobOffersaved) {
		if(bsJobOffer.containsId(jobOffersaved)){			
			JobOffer previousJobOffer = bsJobOffer.prevItemId(jobOffersaved);
			
			bsJobOffer.removeItem(jobOffersaved);
			bsJobOffer.addItemAfter(previousJobOffer, jobOffersaved);
			bsJobOffer.setItemId(jobOffersaved);
		}
		else
			bsJobOffer.addItem(jobOffersaved);
		
	}
	
	@Override
	public void addButtonClick(EditionToolbar.ClickNavigationEvent event) {
		JobOffer jobOffer = new JobOffer();

		try {
			@SuppressWarnings("unused")
			WindowDialog<JobOffer> windowDialog = new WindowDialog<JobOffer>(getWindow(), "Nueva Oferta Trabajo", 
					"Guardar", DialogResult.SAVE, "Cancelar", DialogResult.CANCEL, 
					new JobOfferViewForm(), jobOffer, 
					new WindowDialog.CloseWindowDialogListener<JobOffer>() {
			    public void windowDialogClose(WindowDialog<JobOffer>.CloseWindowDialogEvent<JobOffer> event) {
			    	if (event.getDialogResult() != WindowDialog.DialogResult.SAVE)
			    		return ;
			    	
			    	try {
			    		JobOffer savingJobOffer = event.getDomainEntity();
			    		
			    		JobOffer savedJobOffer = jobOfferService.save(savingJobOffer);
			    		
			    		refreshDataGridView(savedJobOffer);
			    		
					} catch (Exception e) {
						throw new RuntimeException(
								"¡No se pudo crear el nueva oferta trabajo!",
								e);
						
					}
			    }

			});
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(
					"¡No se pudo abrir el formulario Nueva Oferta de Trabajo!", e);
		} catch (Exception e) {
			throw new RuntimeException(
					"¡No se pudo abrir el formulario Nueva Oferta de Trabajo!", e);
		} 
		
	}
	
	@Override
	public void editButtonClick(EditionToolbar.ClickNavigationEvent event) {
		JobOffer editingJobOffer = (JobOffer) event.getRegister();
				
		try {
			@SuppressWarnings("unused")
			WindowDialog<JobOffer> windowDialog = new WindowDialog<JobOffer>(
					getWindow() , "Editor Oferta Trabajo", 
					"Guardar", DialogResult.SAVE, "Cancelar", DialogResult.CANCEL, 
					new JobOfferViewForm(), editingJobOffer, 
					new WindowDialog.CloseWindowDialogListener<JobOffer>() {
			    public void windowDialogClose(WindowDialog<JobOffer>.CloseWindowDialogEvent<JobOffer> event) {
			    	if (event.getDialogResult() != WindowDialog.DialogResult.SAVE)
			    		return ;
			    	
			    	try {
			    		
			    		JobOffer savingJobOffer = event.getDomainEntity();
			    		
			    		JobOffer savedJobOffer = jobOfferService.save(savingJobOffer);			    					    		
			    		
			    		refreshDataGridView(savingJobOffer);
			    		
					} catch (Exception e) {
						throw new RuntimeException(
								"¡No se pudo modificar la oferta de trabajo!", e);
						
					}
			    }

			});
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(
					"¡No se pudo abrir el formulario Editor Oferta de Trabajo!", e);
		} catch (Exception e) {
			throw new RuntimeException(
					"¡No se pudo abrir el formulario Editor Oferta de Trabajo!", e);
		} 
		
	}
	
	@Override
	public void deleteButtonClick(EditionToolbar.ClickNavigationEvent event) {
		final JobOffer editingJobOffer = (JobOffer) event.getRegister();
		
		if (editingJobOffer == null)
			return;
		
		ConfirmDialog.show(getWindow(), "Borrar Oferta Trabajo",
		        "¿Estás seguro?", "Si", "No",
		        new ConfirmDialog.Listener() {

		            public void onClose(ConfirmDialog dialog) {
		                if (dialog.isConfirmed()) {
		            		try {
		            			jobOfferService.delete(editingJobOffer);
		            			
		            			// refresh
		            			refreshBindindSource();
		            			
		            		} catch (IllegalArgumentException e) {
								throw new RuntimeException(
										"¡No se pudo borrar la oferta de trabajo!", e);
		            		} catch (Exception e) {
								throw new RuntimeException(
										"¡No se pudo borrar la oferta de trabajo!", e);
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
		dgJobOffer = new DataGridView() {
		    @Override
		    protected String formatPropertyValue(Object rowId,
		            Object colId, Property property) {
		    	// Format by property type
		        if (property.getType() == Date.class) {
		            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		            
		            return df.format((Date)property.getValue());
		        }

		        return super.formatPropertyValue(rowId, colId, property);
		    }
		};
		
		dgJobOffer.setImmediate(false);
		dgJobOffer.setWidth("100.0%");
		dgJobOffer.setHeight("100.0%");
		mainLayout.addComponent(dgJobOffer);
		mainLayout.setExpandRatio(dgJobOffer, 1.0f);
		
		return mainLayout;
	}

	@Override
	public void filterButtonClick(BoxToolbar.ClickNavigationEvent event) {
		this.dgJobOffer.setFilterBarVisible();
		
	}

	@Override
	public void printButtonClick(BoxToolbar.ClickNavigationEvent event) {
		try {
			dgJobOffer.print("Listado Ofertas Trabajo");
		}
		catch (Exception e) {
			throw new RuntimeException("¡No se pudo imprimir el informe!", e);
		}
		
	}

}
