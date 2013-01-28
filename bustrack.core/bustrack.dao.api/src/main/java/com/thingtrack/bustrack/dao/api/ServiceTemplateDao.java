package com.thingtrack.bustrack.dao.api;

import com.thingtrack.bustrack.domain.ServiceTemplate;
import com.thingtrack.konekti.dao.template.Dao;

public interface ServiceTemplateDao extends Dao<ServiceTemplate, Integer> {
	public ServiceTemplate getByCode( String code ) throws Exception;
}
