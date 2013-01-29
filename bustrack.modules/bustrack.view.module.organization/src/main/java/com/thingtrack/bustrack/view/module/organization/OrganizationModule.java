package com.thingtrack.bustrack.view.module.organization;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.thingtrack.konekti.view.kernel.IWorkbenchContext;
import com.thingtrack.konekti.view.kernel.ui.layout.AbstractModule;
import com.thingtrack.konekti.view.kernel.ui.layout.IViewContainer;

public class OrganizationModule extends AbstractModule implements ApplicationContextAware {

	private final static String MODULE_NAME = "Gestor Organizaciones";
	private final static String MODULE_DESCRIPTION = "Descripción Gestor Organizaciones";
	
	private ApplicationContext applicationContext;

	public String getName() {
		return MODULE_NAME;

	}

	public String getDescription() {
		return MODULE_DESCRIPTION;

	}


	@Override
	public IViewContainer createViewComponent(IWorkbenchContext context) {
		try {
			return (IViewContainer) this.applicationContext.getBean("organizationViewContainer", new Object[]{context});
		}
		catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		
		this.applicationContext = applicationContext;
		
	}

}