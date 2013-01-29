package com.thingtrack.bustrack.view.module.scheduler;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.thingtrack.konekti.view.kernel.IModule;
import com.thingtrack.konekti.view.kernel.IWorkbenchContext;
import com.thingtrack.konekti.view.kernel.ui.layout.IViewContainer;

public class SchedulerModule implements IModule, BeanFactoryAware{

	private BeanFactory beanFactory;
	
	private final static String MODULE_NAME = "Calendario de servicios";
	private final static String MODULE_DESCRIPTION = "Calendario para la planificación de servicios";
	public final static String MODULE_ICONS_PATH = "images/icons/scheduler-module/";
	
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {	
		this.beanFactory = beanFactory;
		
	}

	@Override
	public String getName() {
		return MODULE_NAME;
		
	}
	
	@Override
	public String getDescription() {
		return MODULE_DESCRIPTION;
		
	}

	@Override
	public IViewContainer createViewComponent(IWorkbenchContext context) {
		try {
			return (IViewContainer) beanFactory.getBean("schedulerViewContainer", context);
		}
		catch (Exception e) {
			e.getMessage();
		}
		
		return null;
	}

}
