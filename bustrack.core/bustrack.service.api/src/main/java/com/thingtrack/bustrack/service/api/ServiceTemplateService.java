package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.ServiceTemplate;
import com.thingtrack.konekti.domain.Service;

public interface ServiceTemplateService {
	public List<ServiceTemplate> getAll() throws Exception;
	public ServiceTemplate get( Integer serviceTemplatId ) throws Exception;
	public ServiceTemplate getByCode( String code ) throws Exception;
	public ServiceTemplate save(ServiceTemplate serviceTemplat) throws Exception;
	public void delete(ServiceTemplate serviceTemplat) throws Exception;
	public void saveAllServiceTemplate(List<Service> services) throws Exception;
}
