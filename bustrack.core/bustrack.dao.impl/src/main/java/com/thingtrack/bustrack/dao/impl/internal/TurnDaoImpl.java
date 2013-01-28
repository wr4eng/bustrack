package com.thingtrack.bustrack.dao.impl.internal;

import org.springframework.stereotype.Repository;

import com.thingtrack.konekti.dao.template.JpaDao;
import com.thingtrack.bustrack.dao.api.TurnDao;
import com.thingtrack.bustrack.domain.Turn;

/**
 * @author Thingtrack S.L.
 *
 */
@Repository
public class TurnDaoImpl extends JpaDao<Turn, Integer> implements TurnDao {
	/* (non-Javadoc)
	 * @see com.thingtrack.bustrack.dao.api.TurnDao#getByCode(java.lang.String)
	 */
	@Override
	public Turn getByCode(String code) throws Exception {
		Turn turn = (Turn)getEntityManager()
		.createQuery("SELECT p FROM " + getEntityName() + " p where p.code = :code")
		.setParameter("code", code).getSingleResult();

		return turn;
	}
	
}