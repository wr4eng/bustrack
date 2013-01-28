/*
 * Copyright 2011 Thingtrack, S.L.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.thingtrack.bustrack.dao.impl.internal;

import org.springframework.stereotype.Repository;

import com.thingtrack.bustrack.dao.api.VehicleStatusDao;
import com.thingtrack.bustrack.domain.VehicleStatus;
import com.thingtrack.konekti.dao.template.JpaDao;

/**
 * @author Thingtrack S.L.
 *
 */
@Repository
public class VehicleStatusDaoImpl extends JpaDao<VehicleStatus, Integer> implements VehicleStatusDao {
	/* (non-Javadoc)
	 * @see com.thingtrack.bustrack.dao.api.VehicleStatusDao#getByCode(java.lang.String)
	 */
	@Override
	public VehicleStatus getByCode(String code) throws Exception {
		VehicleStatus vehicleStatus = (VehicleStatus)getEntityManager()
				.createQuery("SELECT p FROM " + getEntityName() + " p WHERE p.code = :code")
				.setParameter("code", code).getSingleResult();

		return vehicleStatus;
		
	}

}
