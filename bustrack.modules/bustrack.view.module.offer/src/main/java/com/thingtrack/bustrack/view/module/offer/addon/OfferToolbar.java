package com.thingtrack.bustrack.view.module.offer.addon;

import java.io.Serializable;

import org.vaadin.peter.buttongroup.ButtonGroup;

import com.thingtrack.konekti.domain.Offer;
import com.thingtrack.konekti.view.addon.data.BindingSource;
import com.thingtrack.konekti.view.addon.data.BindingSource.IndexChangeEvent;
import com.thingtrack.konekti.view.addon.data.BindingSource.IndexChangeListener;
import com.thingtrack.konekti.view.addon.ui.AbstractToolbar;
import com.thingtrack.konekti.view.kernel.ui.layout.IViewContainer;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.terminal.gwt.client.MouseEventDetails;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;

@SuppressWarnings("serial")
public class OfferToolbar extends AbstractToolbar {
	@AutoGenerated
	private HorizontalLayout toolbarLayout;

	@AutoGenerated
	private Button btnSend;

	@AutoGenerated
	private Button btnAccepted;
	
	@AutoGenerated
	private Button btnRejection;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	private Object register;
	
	private int position = 0;
		
	// navigator button listeners
	private ClickRejectButtonListener listenerRejectButton = null;
	
	private IViewContainer viewContainer;
	
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */	
	public OfferToolbar(int position, final BindingSource<?> bindingSource, IViewContainer viewContainer) {
		super(position, bindingSource);
			
		buildMainLayout();

		// TODO add user code here
		this.position = position;
		this.viewContainer = viewContainer;
		
		setBindingSource(bindingSource);
		
		btnRejection.setDescription("Rechazo Oferta");
		
		// set reject button listener
		btnRejection.addListener(new ClickListener() {			
			public void buttonClick(ClickEvent event) {
				int index = bindingSource.getIndex();
				
				if (index == 0)
					return;
				
				register = bindingSource.getItemId();
				
				if (listenerRejectButton != null)
					listenerRejectButton.rejectButtonClick(new ClickNavigationEvent(event.getButton(), event.getComponent() , null, register, 0));					
				
			}
		});
		
	}
	
	@Override
	public int getPosition() {
		return this.position;
		
	}

	@Override
	public ComponentContainer getContent() {
		return this.toolbarLayout;
		
	}
	
	public void addListenerRejectButton(ClickRejectButtonListener listener) {
		this.listenerRejectButton = listener;
		
	}
	
	
	public interface ClickRejectButtonListener extends Serializable {
        public void rejectButtonClick(ClickNavigationEvent event);

    }
	
	public class ClickNavigationEvent extends ClickEvent {
		private int index;
		private Object register;
		
		public ClickNavigationEvent(Button button, Component source) {
			button.super(source);
			
		}

		public ClickNavigationEvent(Button button, Component source, MouseEventDetails details) {
			button.super(source, details);
			
		}

		public ClickNavigationEvent(Button button, Component source, MouseEventDetails details, Object register, int index) {
			button.super(source, details);
			
			this.index = index;
			this.register = register;
		}

		public int getIndex() {
			return this.index;
			
		}
		
		public Object getRegister() {
			return this.register;
			
		}
		
	  }
		
	@Override
	public void setBindingSource(BindingSource<?> bindingSource) {
		this.bindingSource = bindingSource;
		
		// add change index binding source
		if (bindingSource != null) {
			bindingSource.addListenerToolBar((IndexChangeListener)this);
			
		}
		
	}
	
	@Override
	public void bindingSourceIndexChange(IndexChangeEvent event) {
		if (bindingSource != null) {
			Offer offerSelected = (Offer)event.getRegister();
			
			if (offerSelected == null)
				return;
						
			if (offerSelected.getOfferStatus().getCode().equals(Offer.STATUS.OPENED.name())) {
				btnSend.setEnabled(true);
				btnAccepted.setEnabled(false);
				btnRejection.setEnabled(true);
			}
			else if (offerSelected.getOfferStatus().getCode().equals(Offer.STATUS.SENT.name())) { 
				btnSend.setEnabled(false);
				btnAccepted.setEnabled(true);
				btnRejection.setEnabled(true);
			}
			else if (offerSelected.getOfferStatus().getCode().equals(Offer.STATUS.ACCEPTED.name())) {
				btnSend.setEnabled(false);
				btnAccepted.setEnabled(false);
				btnRejection.setEnabled(false);
			}
			else if (offerSelected.getOfferStatus().getCode().equals(Offer.STATUS.REJECTED.name())) { 
				btnSend.setEnabled(false);
				btnAccepted.setEnabled(false);
				btnRejection.setEnabled(false);
			}
			else if (offerSelected.getOfferStatus().getCode().equals(Offer.STATUS.CLOSED.name())) { 
				btnSend.setEnabled(false);
				btnAccepted.setEnabled(false);
				btnRejection.setEnabled(false);
			}	
			else {
				btnSend.setEnabled(false);
				btnAccepted.setEnabled(false);
				btnRejection.setEnabled(false);
			}
		}
		
	}
	
	@AutoGenerated
	private void buildMainLayout() {
		toolbarLayout = buildToolbarLayout();
		addComponent(toolbarLayout);
		
	}

	@AutoGenerated
	private HorizontalLayout buildToolbarLayout() {		
		toolbarLayout = new HorizontalLayout();
		toolbarLayout.setImmediate(false);
		toolbarLayout.setSpacing(true);
		
		ButtonGroup editionButtonGroup = new ButtonGroup();
		toolbarLayout.addComponent(editionButtonGroup);

		// btnSend
		btnSend = new Button();
		btnSend.setCaption("Enviar");
		btnSend.setImmediate(true);
		btnSend.setWidth("-1px");
		btnSend.setHeight("-1px");
		btnSend.setIcon(new ThemeResource("../konekti/images/icons/offerrequest-toolbar/paper-bag--pencil.png"));
		
		editionButtonGroup.addButton(btnSend);
		
		// btnAccepted
		btnAccepted = new Button();
		btnAccepted.setCaption("Aceptar");
		btnAccepted.setImmediate(true);
		btnAccepted.setWidth("-1px");
		btnAccepted.setHeight("-1px");
		btnAccepted.setIcon(new ThemeResource("../konekti/images/icons/offerrequest-toolbar/paper-bag--arrow.png"));
		
		editionButtonGroup.addButton(btnAccepted);
		
		// btnRejection
		btnRejection = new Button();
		btnRejection.setCaption("Rechazo");
		btnRejection.setImmediate(true);
		btnRejection.setWidth("-1px");
		btnRejection.setHeight("-1px");
		btnRejection.setIcon(new ThemeResource("../konekti/images/icons/offerrequest-toolbar/paper-bag--exclamation.png"));
		
		editionButtonGroup.addButton(btnRejection);
		
		return toolbarLayout;
	}

}
