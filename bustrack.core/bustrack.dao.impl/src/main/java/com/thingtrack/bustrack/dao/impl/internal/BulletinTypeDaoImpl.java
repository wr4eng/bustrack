package com.thingtrack.bustrack.dao.impl.internal;

import org.springframework.stereotype.Repository;

import com.thingtrack.konekti.dao.template.JpaDao;
import com.thingtrack.bustrack.dao.api.BulletinTypeDao;
import com.thingtrack.bustrack.domain.BulletinType;

/**
 * @author Thingtrack S.L.
 *
 */
@Repository
public class BulletinTypeDaoImpl extends JpaDao<BulletinType, Integer> implements BulletinTypeDao {
	/* (non-Javadoc)
	 * @see com.thingtrack.bustrack.dao.api.BulletinTypeDao#getByCode(java.lang.String)
	 */
	@Override
	public BulletinType getByCode(String code) throws Exception {
		BulletinType bulletinType = (BulletinType)getEntityManager()
		.createQuery("SELECT p FROM " + getEntityName() + " p WHERE p.code = :code")
		.setParameter("code", code).getSingleResult();

		return bulletinType;
		
	}

}
