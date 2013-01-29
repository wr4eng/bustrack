package com.thingtrack.bustrack.view.module.invoice.internal;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.vaadin.dialogs.ConfirmDialog;

import com.thingtrack.bustrack.view.module.invoice.addon.InvoiceToolbar;
import com.thingtrack.bustrack.view.module.invoice.addon.InvoiceToolbar.ClickBillButtonListener;
import com.thingtrack.bustrack.view.module.invoice.addon.InvoiceToolbar.ClickCloseButtonListener;
import com.thingtrack.konekti.domain.Invoice;
import com.thingtrack.konekti.service.api.InvoiceService;
import com.thingtrack.konekti.view.addon.data.BindingSource;
import com.thingtrack.konekti.view.addon.ui.AbstractView;
import com.thingtrack.konekti.view.addon.ui.BoxToolbar;
import com.thingtrack.konekti.view.addon.ui.BoxToolbar.ClickPrintButtonListener;
import com.thingtrack.konekti.view.addon.ui.DataGridView;
import com.thingtrack.konekti.view.addon.ui.EditionToolbar;
import com.thingtrack.konekti.view.addon.ui.BoxToolbar.ClickFilterButtonListener;
import com.thingtrack.konekti.view.addon.ui.EditionToolbar.ClickNavigationEvent;
import com.thingtrack.konekti.view.addon.ui.NavigationToolbar;
import com.thingtrack.konekti.view.addon.ui.EditionToolbar.ClickAddButtonListener;
import com.thingtrack.konekti.view.addon.ui.EditionToolbar.ClickEditButtonListener;
import com.thingtrack.konekti.view.addon.ui.EditionToolbar.ClickRemoveButtonListener;
import com.thingtrack.konekti.view.addon.ui.NavigationToolbar.ClickDownButtonListener;
import com.thingtrack.konekti.view.addon.ui.NavigationToolbar.ClickRefreshButtonListener;
import com.thingtrack.konekti.view.addon.ui.WindowDialog;
import com.thingtrack.konekti.view.addon.ui.WindowDialog.DialogResult;
import com.thingtrack.konekti.view.kernel.IWorkbenchContext;
import com.thingtrack.konekti.view.kernel.ui.layout.IViewContainer;
import com.thingtrack.konekti.view.web.form.InvoiceViewForm;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class InvoiceView extends AbstractView 
	implements ClickDownButtonListener, ClickRefreshButtonListener, ClickAddButtonListener, 
			   ClickEditButtonListener, ClickRemoveButtonListener, 
			   ClickFilterButtonListener, ClickPrintButtonListener,
			   ClickBillButtonListener, ClickCloseButtonListener {
			
	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private DataGridView dgInvoice;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	private IWorkbenchContext context;
	private InvoiceService invoiceService;
	
	private BindingSource<Invoice> bsInvoice =  new BindingSource<Invoice>(Invoice.class, 0);
	
	private NavigationToolbar navigationToolbar;
	private EditionToolbar editionToolbar;
	private BoxToolbar boxToolbar;
	private InvoiceToolbar invoiceToolbar;
		
	private IViewContainer viewContainer;
		
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public InvoiceView(IViewContainer viewContainer) {		
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here	
		// set Slide View Services and ViewContainer to navigate
		this.viewContainer = viewContainer;
		this.invoiceService = InvoiceViewContainer.getInvoiceService();
		this.context = InvoiceViewContainer.getContext();
		
		// initialize datasource views		
		initView();	
	}

	private void initView() {
		// initialize Slide View Organization View
		dgInvoice.setImmediate(true);		
		
		refreshBindindSource();
		
		// STEP 01: create grid view for slide Organization View
		try {
			dgInvoice.setBindingSource(bsInvoice);
			dgInvoice.setVisibleColumns(new String[] { "organization.name", "code", "invoiceType.description", "invoiceClient.name", "observation", "invoiceStatus.description", "invoiceDate" } );       
			dgInvoice.setColumnHeaders(new String[] { "Organización", "Código", "Tipo", "Cliente", "Observaciones", "Estado", "Fecha" } );
			
			dgInvoice.setColumnCollapsed("organization.name", true);
		}
		catch(Exception ex) {
			ex.getMessage();
		}
			
		// STEP 02: create toolbar for slide Employee Agent View
		navigationToolbar = new NavigationToolbar(0, bsInvoice, viewContainer);
		editionToolbar = new EditionToolbar(1, bsInvoice);
		boxToolbar = new BoxToolbar(2, bsInvoice);
		invoiceToolbar = new InvoiceToolbar(3, bsInvoice, viewContainer);
		
		navigationToolbar.addListenerDownButton(this);
		
		navigationToolbar.addListenerRefreshButton(this);
		
		editionToolbar.addListenerAddButton(this);
		editionToolbar.addListenerEditButton(this);
		editionToolbar.addListenerDeleteButton(this);
		
		boxToolbar.addListenerFilterButton(this);
		boxToolbar.addListenerPrintButton(this);
		
		dgInvoice.addListenerAddButton(this);
		dgInvoice.addListenerEditButton(this);
		dgInvoice.addListenerDeleteButton(this);
		
		invoiceToolbar.addListenerBillButton(this);
		
		removeAllToolbar();
		
		addToolbar(navigationToolbar);
		addToolbar(boxToolbar);
		addToolbar(editionToolbar);
		addToolbar(invoiceToolbar);

	}
	
	private void refreshBindindSource() {
		try {		
			bsInvoice.removeAllItems();
			bsInvoice.addAll(invoiceService.getAll());	
			
			bsInvoice.addNestedContainerProperty("organization.name");
			bsInvoice.addNestedContainerProperty("invoiceClient.name");
			bsInvoice.addNestedContainerProperty("invoiceType.description");
			bsInvoice.addNestedContainerProperty("invoiceStatus.description");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	@Override
	public void downButtonClick(NavigationToolbar.ClickNavigationEvent event) {
		Invoice invoiceSelected  = null;
		
		if (bsInvoice.getItemIds().size() > 0) {
			// get selected Organization
			invoiceSelected = (Invoice)bsInvoice.getItemId();
			
			// inject the locations data from organization in the detail Location View
			InvoiceLineView invoiceLineView = (InvoiceLineView)viewContainer.getNext();
			invoiceLineView.setInvoice(invoiceSelected);
			
			// roll to the detail Location View
			viewContainer.getSliderView().rollNext();
		}
		
	}
	
	@Override
	public void refreshButtonClick(NavigationToolbar.ClickNavigationEvent event) {
		refreshBindindSource();
		
	}

	private void refreshDataGridView(Invoice savedInvoice) {
		if(bsInvoice.containsId(savedInvoice)){			
			Invoice previousInvoice = bsInvoice.prevItemId(savedInvoice);
			
			bsInvoice.removeItem(savedInvoice);
			bsInvoice.addItemAfter(previousInvoice, savedInvoice);
			bsInvoice.setItemId(savedInvoice);
		}
		else
			bsInvoice.addItem(savedInvoice);
		
	}
	
	@Override
	public void addButtonClick(ClickNavigationEvent event) {
		Invoice invoice = null;
		try {
			invoice = invoiceService.createNewInvoice(context.getOrganization());
		} catch (Exception e) {
			throw new RuntimeException(
					"¡No se pudo crear el nuevo código factura!",
					e);
		}
		
		try {
			@SuppressWarnings("unused")
			WindowDialog<Invoice> windowDialog = new WindowDialog<Invoice>(getWindow(), "Nueva Factura", 
					"Guardar", DialogResult.SAVE, "Cancelar", DialogResult.CANCEL, 
					new InvoiceViewForm(), invoice, 
					new WindowDialog.CloseWindowDialogListener<Invoice>() {
			    public void windowDialogClose(WindowDialog<Invoice>.CloseWindowDialogEvent<Invoice> event) {
			    	if (event.getDialogResult() != WindowDialog.DialogResult.SAVE)
			    		return ;
			    	
			    	try {
			    		Invoice savingInvoice = event.getDomainEntity();
			    		
			    		Invoice savedInvoice = invoiceService.save(savingInvoice);
			    		
			    		refreshDataGridView(savedInvoice);
			    		
					} catch (Exception e) {
						throw new RuntimeException(
								"¡No se pudo crear el nueva factura!",
								e);
						
					}
			    }

			});
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(
					"¡No se pudo abrir el formulario Nueva Factura!", e);
		} catch (Exception e) {
			throw new RuntimeException(
					"¡No se pudo abrir el formulario Nueva Factura!", e);
		} 
		
	}
	
	@Override
	public void editButtonClick(ClickNavigationEvent event) {
		Invoice editingInvoice = (Invoice) event.getRegister();

		try {
			@SuppressWarnings("unused")
			WindowDialog<Invoice> windowDialog = new WindowDialog<Invoice>(
					getWindow(), "Editor Factura", 
					"Guardar", DialogResult.SAVE, "Cancelar", DialogResult.CANCEL, 
					new InvoiceViewForm(), editingInvoice, 
					new WindowDialog.CloseWindowDialogListener<Invoice>() {
			    public void windowDialogClose(WindowDialog<Invoice>.CloseWindowDialogEvent<Invoice> event) {
			    	if (event.getDialogResult() != WindowDialog.DialogResult.SAVE)
			    		return ;
			    	
			    	try {
			    		Invoice savingInvoice = event.getDomainEntity();
			    		
			    		Invoice savedInvoice = invoiceService.save(savingInvoice);
			    		
			    		refreshDataGridView(savedInvoice);
			    		
					} catch (Exception e) {
						throw new RuntimeException(
								"¡No se pudo modificar la factura!", e);
						
					}
			    }

			});
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(
					"¡No se pudo abrir el formulario Editor Factura!", e);
		} catch (Exception e) {
			throw new RuntimeException(
					"¡No se pudo abrir el formulario Editor Factura!", e);
		} 
		
	}
	
	@Override
	public void deleteButtonClick(ClickNavigationEvent event) {
		final Invoice editingInvoice = (Invoice) event.getRegister();
		
		if (editingInvoice == null)
			return;
		
		ConfirmDialog.show(getWindow(), "Borrar Factura",
		        "¿Estás seguro?", "Si", "No",
		        new ConfirmDialog.Listener() {

		            public void onClose(ConfirmDialog dialog) {
		                if (dialog.isConfirmed()) {
		            		try {
		            			invoiceService.delete(editingInvoice);
		            			
		            			// refresh
		            			refreshBindindSource();
		            			
		            		} catch (IllegalArgumentException e) {
								throw new RuntimeException(
										"¡No se pudo borrar la factura!", e);
		            		} catch (Exception e) {
								throw new RuntimeException(
										"¡No se pudo borrar la factura!", e);
		            		}
		                } 
		            }
		        });
		
	}
	
	@Override
	public void billButtonClick(InvoiceToolbar.ClickNavigationEvent event) {
		Invoice invoice = (Invoice) event.getRegister();
		
		if (invoice == null)
			return;
		
		try {
			//offerRequestKnowledgeService.setTransfer(offerRequest);
		} catch (Exception e) {
			throw new RuntimeException(
					"¡No se pudo enviar la factura!", e);
		}
		
	}
	
	@Override
	public void closeButtonClick(InvoiceToolbar.ClickNavigationEvent event) {
		Invoice invoice = (Invoice) event.getRegister();
		
		if (invoice == null)
			return;
		
		try {
			//offerRequestKnowledgeService.setTransfer(offerRequest);
		} catch (Exception e) {
			throw new RuntimeException(
					"¡No se pudo cerrar la factura!", e);
		}
		
	}
	
	@Override
	public void filterButtonClick(BoxToolbar.ClickNavigationEvent event) {		
		dgInvoice.setFilterBarVisible();
		
	}

	@Override
	public void printButtonClick(BoxToolbar.ClickNavigationEvent event) {
		try {
			dgInvoice.print("Listado Facturas");
		}
		catch (Exception e) {
			throw new RuntimeException("¡No se pudo imprimir el informe!", e);
		}
		
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
		
		// dgEmployee
		dgInvoice = new DataGridView() {
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
		
		dgInvoice.setImmediate(false);
		dgInvoice.setWidth("100.0%");
		dgInvoice.setHeight("100.0%");
		mainLayout.addComponent(dgInvoice);
		mainLayout.setExpandRatio(dgInvoice, 1.0f);
		
		return mainLayout;
	}
	
}