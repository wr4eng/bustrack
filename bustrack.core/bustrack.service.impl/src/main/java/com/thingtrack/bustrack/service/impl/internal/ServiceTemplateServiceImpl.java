package com.thingtrack.bustrack.service.impl.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.dao.api.ServiceTemplateDao;
import com.thingtrack.bustrack.domain.ServiceTemplate;
import com.thingtrack.bustrack.service.api.ServiceTemplateService;
import com.thingtrack.konekti.dao.api.ServiceDao;
import com.thingtrack.konekti.domain.Service;

public class ServiceTemplateServiceImpl  implements ServiceTemplateService {
	@Autowired
	private ServiceTemplateDao serviceTemplateDao;
	@Autowired
	private ServiceDao serviceDao;
	
	@Override
	public List<ServiceTemplate> getAll() throws Exception {
		return this.serviceTemplateDao.getAll();
	}

	@Override
	public ServiceTemplate get(Integer serviceTemplatId) throws Exception {
		return this.serviceTemplateDao.get(serviceTemplatId);
	}

	@Override
	public ServiceTemplate getByCode(String code) throws Exception {
		return this.serviceTemplateDao.getByCode(code);
	}

	@Override
	public ServiceTemplate save(ServiceTemplate serviceTemplate) throws Exception {
		return this.serviceTemplateDao.save(serviceTemplate);
	}

	@Override
	public void delete(ServiceTemplate serviceTemplate) throws Exception {
		this.serviceTemplateDao.delete(serviceTemplate);
		
	}

	@Override
	public void saveAllServiceTemplate(List<Service> services) throws Exception {
		for (Service service : services)
			serviceDao.save(service);
					
	}

}
