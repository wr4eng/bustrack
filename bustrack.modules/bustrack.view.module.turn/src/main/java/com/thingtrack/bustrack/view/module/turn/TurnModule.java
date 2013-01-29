package com.thingtrack.bustrack.view.module.turn;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.thingtrack.konekti.view.kernel.IWorkbenchContext;
import com.thingtrack.konekti.view.kernel.ui.layout.AbstractModule;
import com.thingtrack.konekti.view.kernel.ui.layout.IViewContainer;

public class TurnModule extends AbstractModule implements ApplicationContextAware {

	private final static String MODULE_NAME = "Gestor Turnos";
	private final static String MODULE_DESCRIPTION = "Descripción Gestor Turnos";
	
	private ApplicationContext applicationContext;

	public String getName() {
		return MODULE_NAME;

	}

	public String getDescription() {
		return MODULE_DESCRIPTION;

	}


	@Override
	public IViewContainer createViewComponent(IWorkbenchContext context) {		
		return (IViewContainer) this.applicationContext.getBean("turnViewContainer", new Object[]{context});
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		
		this.applicationContext = applicationContext;
		
	}

}