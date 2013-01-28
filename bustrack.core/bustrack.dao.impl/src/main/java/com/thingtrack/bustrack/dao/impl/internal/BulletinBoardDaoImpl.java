package com.thingtrack.bustrack.dao.impl.internal;

import org.springframework.stereotype.Repository;

import com.thingtrack.konekti.dao.template.JpaDao;
import com.thingtrack.bustrack.dao.api.BulletinBoardDao;
import com.thingtrack.bustrack.domain.BulletinBoard;

/**
 * @author Thingtrack S.L.
 *
 */
@Repository
public class BulletinBoardDaoImpl extends JpaDao<BulletinBoard, Integer> implements BulletinBoardDao {
	/* (non-Javadoc)
	 * @see com.thingtrack.bustrack.dao.api.BulletinBoardDao#getByName(java.lang.String)
	 */
	@Override
	public BulletinBoard getByName(String name) throws Exception {
		BulletinBoard bulletinBoard = (BulletinBoard)getEntityManager()
		.createQuery("SELECT p FROM " + getEntityName() + " p WHERE p.name = :name")
		.setParameter("name", name).getSingleResult();

		return bulletinBoard;
	}

}
