package com.thingtrack.bustrack.view.module.offer.internal;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.konekti.service.api.OfferLineService;
import com.thingtrack.konekti.service.api.OfferService;
import com.thingtrack.konekti.service.api.ServiceService;
import com.thingtrack.konekti.view.addon.ui.AbstractViewContainer;
import com.thingtrack.konekti.view.addon.ui.SliderView;
import com.thingtrack.konekti.view.kernel.IWorkbenchContext;
import com.thingtrack.konekti.view.kernel.ui.layout.ISliderView;
import com.thingtrack.konekti.view.kernel.ui.layout.IViewChangeListener;
import com.thingtrack.konekti.view.kernel.ui.layout.IView;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class OfferViewContainer extends AbstractViewContainer {

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private SliderView sliderView;

	/** Views **/
	private OfferView offerView;
	private OfferLineView offerLineView;
	private ServiceView serviceView;
	
	/** Business services **/
	@Autowired
	private OfferService offerService;

	@Autowired
	private OfferLineService offerLineService;
	
	@Autowired
	private ServiceService serviceService;
	
	private IWorkbenchContext context; 
	
	// Thread Local Bundle Business Services
	private static ThreadLocal<OfferService> threadOfferService = new ThreadLocal<OfferService>();
	private static ThreadLocal<OfferLineService> threadOfferLineService = new ThreadLocal<OfferLineService>();
	private static ThreadLocal<ServiceService> threadServiceService = new ThreadLocal<ServiceService>();
	private static ThreadLocal<IWorkbenchContext> threadContext = new ThreadLocal<IWorkbenchContext>();
	
	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */
	
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public OfferViewContainer(IWorkbenchContext context) {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		this.context = context;
	}
	
	@SuppressWarnings("unused")
	@PostConstruct
	private void createViews() {
		// initialize thread local bundle services
		threadOfferService.set(offerService);
		threadOfferLineService.set(offerLineService);
		threadServiceService.set(serviceService);
		threadContext.set(context);
		
		// add all views controlled by SliderView Component
		offerView = new OfferView(this);
		sliderView.addView(offerView);
		views.put(0, offerView);
		
		// add all views controlled by SliderView Component
		offerLineView = new OfferLineView(this, context);
		sliderView.addView(offerLineView);
		views.put(1, offerLineView);
		
		// add all views controlled by SliderView Component
		serviceView = new ServiceView(this, context);
		sliderView.addView(serviceView);
		views.put(2, serviceView);
	}
		
	@SuppressWarnings("unused")
	@PreDestroy
	private void destroyViews() {
		// destroy thread local bundle services
		threadOfferService.set(null);
		threadOfferLineService.set(null);
		threadServiceService.set(null);		
		threadContext.set(null);
	}
	
    public static OfferService getOfferService() {
        return threadOfferService.get();
        
    }
    
    public static OfferLineService getOfferLineService() {
        return threadOfferLineService.get();
        
    }
    
    public static ServiceService getServiceService() {
        return threadServiceService.get();
        
    }
    
    public static IWorkbenchContext getContext() {
        return threadContext.get();
        
    }  
    
	@Override
	public ISliderView getSliderView() {
		return sliderView;
		
	}
	
	@Override
	public IView getSelectedView() {
		return sliderView.getSelectedView();
		
	}
	
	@Override
	public void addListener(IViewChangeListener listener) {
		sliderView.addListener(listener);
		
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
		
		// sliderView
		sliderView = new SliderView();
		sliderView.setImmediate(false);
		sliderView.setWidth("100.0%");
		sliderView.setHeight("100.0%");
		mainLayout.addComponent(sliderView);
		mainLayout.setExpandRatio(sliderView, 1.0f);
		
		return mainLayout;
	}
}
