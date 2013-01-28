package com.thingtrack.bustrack.dao.impl.internal;
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

import org.springframework.stereotype.Repository;

import com.thingtrack.konekti.dao.template.JpaDao;
import com.thingtrack.bustrack.dao.api.GasTypeDao;
import com.thingtrack.bustrack.domain.GasType;

/**
 * @author Thingtrack S.L.
 *
 */
@Repository
public class GasTypeDaoImpl extends JpaDao<GasType, Integer> implements GasTypeDao {
	/* (non-Javadoc)
	 * @see com.thingtrack.bustrack.dao.api.GasTypeDao#getByCode(java.lang.String)
	 */
	@Override
	public GasType getByCode(String code) throws Exception {
		GasType gasType = (GasType)getEntityManager()
				.createQuery("SELECT p FROM " + getEntityName() + " p WHERE p.code = :code")
				.setParameter("code", code).getSingleResult();

		return gasType;
	}

}
