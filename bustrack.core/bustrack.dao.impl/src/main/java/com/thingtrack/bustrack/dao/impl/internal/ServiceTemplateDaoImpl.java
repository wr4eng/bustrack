package com.thingtrack.bustrack.dao.impl.internal;

import com.thingtrack.bustrack.dao.api.ServiceTemplateDao;
import com.thingtrack.bustrack.domain.ServiceTemplate;
import com.thingtrack.konekti.dao.template.JpaDao;

public class ServiceTemplateDaoImpl extends JpaDao<ServiceTemplate, Integer> implements ServiceTemplateDao {

	@Override
	public ServiceTemplate getByCode(String code) throws Exception {
		ServiceTemplate serviceTemplate = (ServiceTemplate)getEntityManager()
		.createQuery("SELECT p FROM " + getEntityName() + " p WHERE p.code = :code")
		.setParameter("code", code).getSingleResult();

		return serviceTemplate;
	}

}
