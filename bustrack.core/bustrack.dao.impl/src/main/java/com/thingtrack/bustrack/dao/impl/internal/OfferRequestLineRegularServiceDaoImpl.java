package com.thingtrack.bustrack.dao.impl.internal;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.thingtrack.konekti.dao.template.JpaDao;
import com.thingtrack.konekti.domain.OfferRequest;
import com.thingtrack.konekti.domain.OfferRequestLineStatus;
import com.thingtrack.bustrack.domain.OfferRequestLineRegularService;
import com.thingtrack.bustrack.dao.api.OfferRequestLineRegularServiceDao;

/**
 * @author Thingtrack S.L.
 *
 */
@Repository
public class OfferRequestLineRegularServiceDaoImpl extends JpaDao<OfferRequestLineRegularService, Integer> implements OfferRequestLineRegularServiceDao {
	/* (non-Javadoc)
	 * @see com.thingtrack.bustrack.dao.api.OfferRequestLineRegularServiceDao#getByNumber(java.lang.Integer)
	 */
	@Override
	public OfferRequestLineRegularService getByNumber(Integer number)
			throws Exception {
		OfferRequestLineRegularService offerRequestLineRegularService = (OfferRequestLineRegularService)getEntityManager()
		.createQuery("SELECT p FROM " + getEntityName() + " p WHERE p.number = :number")
		.setParameter("number", number).getSingleResult();
	
		return offerRequestLineRegularService;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfferRequestLineRegularService> getByStatus(OfferRequest offerRequest, OfferRequestLineStatus offerRequestLineStatus) throws Exception {		
		return (List<OfferRequestLineRegularService>)getEntityManager()
		.createQuery("SELECT p FROM " + getEntityName() + " p WHERE p.offerRequestLineStatus = :offerRequestLineStatus AND p.offerRequest = :offerRequest")
		.setParameter("offerRequest", offerRequest)
		.setParameter("offerRequestLineStatus", offerRequestLineStatus).getResultList();
	
	}
	
	@Override
	public List<OfferRequestLineRegularService> getByStatusCollection(OfferRequest offerRequest, List<OfferRequestLineStatus> statusCollection) {		
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<OfferRequestLineRegularService> q = cb.createQuery(OfferRequestLineRegularService.class);
		
		// root query builder
		Root<OfferRequestLineRegularService> c = q.from(OfferRequestLineRegularService.class);
		
		// OfferRequest Predicate
		Predicate predicateOfferRequest = cb.equal(c.get("offerRequest"), offerRequest);
								
		// OfferLineStatus predicate collection
		List<Predicate> predicates = new ArrayList<Predicate>();
		for(OfferRequestLineStatus offerRequestLineStatus : statusCollection)
			predicates.add(cb.or(cb.equal(c.get("offerRequestLineStatus"), offerRequestLineStatus)));			
		
		q.select(c).where(cb.and(predicateOfferRequest, cb.or(predicates.toArray(new Predicate[predicates.size()]))));
		
		return getEntityManager().createQuery(q).getResultList();	
	
	}
}
