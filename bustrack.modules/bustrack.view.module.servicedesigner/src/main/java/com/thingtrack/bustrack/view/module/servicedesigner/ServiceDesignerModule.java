package com.thingtrack.bustrack.view.module.servicedesigner;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.thingtrack.konekti.view.kernel.IModule;
import com.thingtrack.konekti.view.kernel.IWorkbenchContext;
import com.thingtrack.konekti.view.kernel.ui.layout.IViewContainer;

public class ServiceDesignerModule implements IModule, BeanFactoryAware {

	private BeanFactory beanFactory;

	private final static String MODULE_NAME = "Diseñador de servicios";
	private final static String MODULE_DESCRIPTION = "Diseñador de servicios";
	public final static String MODULE_ICONS_PATH = "images/icons/servicedesigner-module/";

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

		return (IViewContainer) beanFactory.getBean(
				"serviceDesignerViewContainer", context);

	}

}
