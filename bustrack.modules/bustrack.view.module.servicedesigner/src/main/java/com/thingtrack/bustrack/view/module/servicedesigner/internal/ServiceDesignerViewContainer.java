package com.thingtrack.bustrack.view.module.servicedesigner.internal;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.service.dto.api.ServiceServiceDto;
import com.thingtrack.konekti.service.api.OfferLineService;
import com.thingtrack.konekti.service.api.OfferService;
import com.thingtrack.konekti.service.api.ServiceService;
import com.thingtrack.konekti.view.addon.ui.AbstractViewContainer;
import com.thingtrack.konekti.view.addon.ui.SliderView;
import com.thingtrack.konekti.view.kernel.IWorkbenchContext;
import com.thingtrack.konekti.view.kernel.ui.layout.ISliderView;
import com.thingtrack.konekti.view.kernel.ui.layout.IView;
import com.thingtrack.konekti.view.kernel.ui.layout.IViewChangeListener;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class ServiceDesignerViewContainer extends AbstractViewContainer {

	@AutoGenerated
	private VerticalLayout mainLayout;

	@AutoGenerated
	private SliderView sliderView;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	/** Konekti Workbench Context **/
	private IWorkbenchContext context;

	public IWorkbenchContext getContext() {
		return context;
	}

	/** Views **/
	private ServiceView serviceView;
	private ServiceDesignerView serviceDesignerView;
	
	/** Business services **/
	@Autowired
	private ServiceServiceDto serviceServiceDto;
	
	@Autowired
	private OfferService offerService;
	
	@Autowired
	private OfferLineService offerLineService;
	
	@Autowired
	private ServiceService serviceService;
	
	
	// Thread Local Bundle Business Services
	
	private static ThreadLocal<ServiceService> threadServiceService = new ThreadLocal<ServiceService>();
	
	private static ThreadLocal<OfferService> threadOfferService = new ThreadLocal<OfferService>();
	
	private static ThreadLocal<OfferLineService> threadOfferLineService = new ThreadLocal<OfferLineService>();
	
	private static ThreadLocal<ServiceServiceDto> threadServiceServiceDto = new ThreadLocal<ServiceServiceDto>();
	
	
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public ServiceDesignerViewContainer() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
	}
	
	
	public ServiceDesignerViewContainer(IWorkbenchContext context){
		
		this();
		this.context = context;
	}
	
	@Override
	public IView getSelectedView() {
		return sliderView.getSelectedView();
	}

	@Override
	public ISliderView getSliderView() {
		return sliderView;
	}

	@Override
	public void addListener(IViewChangeListener listener) {
		sliderView.addListener(listener);
	}
	
	@SuppressWarnings("unused")
	@PreDestroy
	private void destroyViews() {
		// destroy thread local bundle services
		threadServiceService.set(null);
		threadOfferService.set(null);
		threadOfferLineService.set(null);
		threadServiceServiceDto.set(null);
	}
	
	public static ServiceService getServiceService() {
		return threadServiceService.get();
	}
	
	public static OfferService getOfferService(){
		return threadOfferService.get(); 
	}
	
	public static OfferLineService getOfferLineService(){
		return threadOfferLineService.get();
	}
	
	public static ServiceServiceDto getServiceServiceDto(){
		return threadServiceServiceDto.get();
	}
	
	@SuppressWarnings("unused")
	@PostConstruct
	private void createViews() {
		// initialize thread local bundle services
		threadServiceService.set(serviceService);
		threadOfferService.set(offerService);
		threadOfferLineService.set(offerLineService);
		threadServiceServiceDto.set(serviceServiceDto);

		// add all views controlled by SliderView Component
		try {
			
			serviceView = new ServiceView(this);
			sliderView.addView(serviceView);
			views.put(0, serviceView);
			
			serviceDesignerView = new ServiceDesignerView(this);
			sliderView.addView(serviceDesignerView);
			views.put(1, serviceDesignerView);

		} catch (Exception e) {
			throw new RuntimeException(e);
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
		
		// sliderView_1
		sliderView = new SliderView();
		sliderView.setImmediate(false);
		sliderView.setWidth("100.0%");
		sliderView.setHeight("100.0%");
		mainLayout.addComponent(sliderView);
		mainLayout.setExpandRatio(sliderView, 1.0f);
		
		return mainLayout;
	}

}
