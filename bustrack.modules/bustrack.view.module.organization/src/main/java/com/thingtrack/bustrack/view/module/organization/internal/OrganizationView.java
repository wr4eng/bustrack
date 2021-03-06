package com.thingtrack.bustrack.view.module.organization.internal;

import org.vaadin.dialogs.ConfirmDialog;

import com.thingtrack.konekti.domain.Organization;
import com.thingtrack.konekti.service.api.OrganizationService;
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
import com.thingtrack.konekti.view.addon.ui.NavigationToolbar.ClickDownButtonListener;
import com.thingtrack.konekti.view.addon.ui.NavigationToolbar.ClickNavigationEvent;
import com.thingtrack.konekti.view.addon.ui.NavigationToolbar.ClickRefreshButtonListener;
import com.thingtrack.konekti.view.addon.ui.WindowDialog;
import com.thingtrack.konekti.view.addon.ui.WindowDialog.DialogResult;
import com.thingtrack.konekti.view.kernel.ui.layout.IViewContainer;
import com.thingtrack.konekti.view.web.form.OrganizationViewForm;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.MethodProperty;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class OrganizationView extends AbstractView 
	implements ClickDownButtonListener, ClickRefreshButtonListener, ClickAddButtonListener, ClickEditButtonListener, ClickRemoveButtonListener, ClickFilterButtonListener, ClickPrintButtonListener {

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private DataGridView dgOrganization;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */
	
	private OrganizationService organizationService;
	
	private BindingSource<Organization> bsOrganization = new BindingSource<Organization>(Organization.class, 0);
		
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
	public OrganizationView(IViewContainer viewContainer) {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		// TODO add user code here				
		// set Slide View Services and ViewContainer to navigate
		this.viewContainer = viewContainer;
		this.organizationService = OrganizationViewContainer.getOrganizationService();
		
		// initialize datasource views		
		initView();	
		
	}
	
	private void initView() {
		// initialize Slide View Organization View
		dgOrganization.setImmediate(true);
		dgOrganization.setSelectable(true);
		
		refreshBindindSource();
		
		// STEP 01: create grid view for slide Organization View
		try {
			bsOrganization.addNestedContainerProperty("socialAddress.street");
			
			dgOrganization.setBindingSource(bsOrganization);
			dgOrganization.setVisibleColumns(new String[] { "code", "name", "description", "cif", "socialAddress.street", "comment", "active" } );       
			dgOrganization.setColumnHeaders(new String[] { "Código", "Nombre", "Descriptión", "CIF", "Dirección Social", "Comentarios", "Activa" } );
			dgOrganization.setEditable(true);
			dgOrganization.setTableFieldFactory(new TableFieldFactory() {					
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
			dgOrganization.setColumnCollapsed("comment", true);
		}
		catch(Exception ex) {
			ex.getMessage();
		}
			
		// STEP 02: create toolbar for slide Organization View
		navigationToolbar = new NavigationToolbar(0, bsOrganization, viewContainer);
		editionToolbar = new EditionToolbar(1, bsOrganization);
		boxToolbar = new BoxToolbar(2, bsOrganization);
		
		navigationToolbar.addListenerDownButton(this);	
		navigationToolbar.addListenerRefreshButton(this);
		
		editionToolbar.addListenerAddButton(this);
		editionToolbar.addListenerEditButton(this);
		editionToolbar.addListenerDeleteButton(this);
		
		boxToolbar.addListenerFilterButton(this);
		boxToolbar.addListenerPrintButton(this);
		
		dgOrganization.addListenerAddButton(this);
		dgOrganization.addListenerEditButton(this);
		dgOrganization.addListenerDeleteButton(this);
		
		removeAllToolbar();
		
		addToolbar(navigationToolbar);
		addToolbar(editionToolbar);
		addToolbar(boxToolbar);

	}
	
	private void refreshBindindSource() {
		try {		
			bsOrganization.removeAllItems();
			bsOrganization.addAll(organizationService.getAll());		
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(
					"¡No se pudo consultar las organizaciones!",
					e);
		} catch (Exception e) {
			throw new RuntimeException(
					"¡No se pudo consultar las organizaciones!",
					e);
		}		
	}
		
	@Override
	public void downButtonClick(ClickNavigationEvent event) {
		Organization organizationSelected  = null;
		
		if (bsOrganization.getItemIds().size() > 0) {
			// get selected Organization
			organizationSelected = (Organization)bsOrganization.getItemId();
			
			// inject the locations data from organization in the detail Location View
			LocationView locationView = (LocationView)viewContainer.getNext();
			locationView.setOrganization(organizationSelected);
			
			// roll to the detail Location View
			viewContainer.getSliderView().rollNext();
		}
		
	}

	@Override
	public void refreshButtonClick(ClickNavigationEvent event) {
		refreshBindindSource();
		
	}
	
	private void refreshDataGridView(Organization savedOrganization) {
		if(bsOrganization.containsId(savedOrganization)){			
			Organization previousOrganization = bsOrganization.prevItemId(savedOrganization);
			
			bsOrganization.removeItem(savedOrganization);
			bsOrganization.addItemAfter(previousOrganization, savedOrganization);
			bsOrganization.setItemId(savedOrganization);
		}
		else
			bsOrganization.addItem(savedOrganization);
		
	}
	
	@Override
	public void addButtonClick(EditionToolbar.ClickNavigationEvent event) {
		Organization organization;
		try {
			organization = organizationService.createEntity();
		} catch (Exception e) {
			throw new RuntimeException(
					"¡No se pudo crear el nuevo código de organización!",
					e);
		}
		
		try {
			@SuppressWarnings("unused")
			WindowDialog<Organization> windowDialog = new WindowDialog<Organization>(getWindow(), "Nueva Organización", 
					"Guardar", DialogResult.SAVE, "Cancelar", DialogResult.CANCEL, 
					new OrganizationViewForm(), organization, 
					new WindowDialog.CloseWindowDialogListener<Organization>() {
			    public void windowDialogClose(WindowDialog<Organization>.CloseWindowDialogEvent<Organization> event) {
			    	if (event.getDialogResult() != WindowDialog.DialogResult.SAVE)
			    		return ;
			    	
			    	try {
			    		Organization savedOrganization = organizationService.save(event.getDomainEntity());
			    		
			    		refreshDataGridView(savedOrganization);			    		
					} catch (Exception e) {
						throw new RuntimeException(
								"¡No se pudo crear la nueva organización!",
								e);
						
					}
			    }

			});
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(
					"¡No se pudo abrir el formulario Nueva Organización!", e);
		} catch (Exception e) {
			throw new RuntimeException(
					"¡No se pudo abrir el formulario Nueva Organización!", e);
		} 
		
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void editButtonClick(EditionToolbar.ClickNavigationEvent event) {						
		Organization editingOrganization = (Organization) event.getRegister();
		
		BeanItem<Organization> organizationBeanItem = new BeanItem<Organization>(editingOrganization);
		organizationBeanItem.addItemProperty("childOrganizationCollection", new MethodProperty(editingOrganization, "childOrganizations"));		
		organizationBeanItem.addItemProperty("locationCollection", new MethodProperty(editingOrganization, "locations"));
		
		try {
			@SuppressWarnings("unused")
			WindowDialog<Organization> windowDialog = new WindowDialog<Organization>(getApplication().getMainWindow() , "Editor Organización", 
					"Guardar", DialogResult.SAVE, "Cancelar", DialogResult.CANCEL, 
					new OrganizationViewForm(),organizationBeanItem, 
					new WindowDialog.CloseWindowDialogListener<Organization>() {
			    public void windowDialogClose(WindowDialog<Organization>.CloseWindowDialogEvent<Organization> event) {
			    	if (event.getDialogResult() != WindowDialog.DialogResult.SAVE)
			    		return ;
			    	
			    	try {
			    		
			    		Organization savingOrganization = event.getDomainEntity();
			    		
			    		Organization savedOrganization = organizationService.save(savingOrganization);
			    		
			    		refreshDataGridView(savedOrganization);
			    		
					} catch (Exception e) {
						throw new RuntimeException(
								"¡No se pudo modificar la organización!", e);
						
					}
			    }

			});
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(
					"¡No se pudo abrir el formulario Editor Organización!", e);
		} catch (Exception e) {
			throw new RuntimeException(
					"¡No se pudo abrir el formulario Editor Organización!", e);
		} 
		
	}

	@Override
	public void deleteButtonClick(EditionToolbar.ClickNavigationEvent event) {
		final Organization editingOrganization = (Organization) event.getRegister();
		
		if (editingOrganization == null)
			return;
		
		ConfirmDialog.show(getWindow(), "Borrar Organización",
		        "¿Estás seguro?", "Si", "No",
		        new ConfirmDialog.Listener() {

		            public void onClose(ConfirmDialog dialog) {
		                if (dialog.isConfirmed()) {
		            		try {
		            			organizationService.delete(editingOrganization);
		            			
		            			// refresh
		            			refreshBindindSource();
		            			
		            		} catch (IllegalArgumentException e) {
								throw new RuntimeException(
										"¡No se pudo borrar la organización!", e);
		            		} catch (Exception e) {
								throw new RuntimeException(
										"¡No se pudo borrar la organización!", e);
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
		
		// dgOrganization
		dgOrganization = new DataGridView();
		dgOrganization.setImmediate(false);
		dgOrganization.setWidth("100.0%");
		dgOrganization.setHeight("100.0%");
		mainLayout.addComponent(dgOrganization);
		mainLayout.setExpandRatio(dgOrganization, 1.0f);
		
		return mainLayout;
	}

	@Override
	public void filterButtonClick(BoxToolbar.ClickNavigationEvent event) {
		dgOrganization.setFilterBarVisible();

	}

	@Override
	public void printButtonClick(BoxToolbar.ClickNavigationEvent event) {
		try {
			dgOrganization.print("Listado Organizaciones");
		}
		catch (Exception e) {
			throw new RuntimeException("¡No se pudo imprimir el informe!", e);
		}
		
	}
	
}
