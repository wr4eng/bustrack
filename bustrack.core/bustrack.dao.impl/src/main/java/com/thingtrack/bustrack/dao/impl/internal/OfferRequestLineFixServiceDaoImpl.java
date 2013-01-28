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
import com.thingtrack.bustrack.domain.OfferRequestLineFixService;
import com.thingtrack.bustrack.dao.api.OfferRequestLineFixServiceDao;

/**
 * @author Thingtrack S.L.
 *
 */
@Repository
public class OfferRequestLineFixServiceDaoImpl extends JpaDao<OfferRequestLineFixService, Integer> implements OfferRequestLineFixServiceDao {
	/* (non-Javadoc)
	 * @see com.thingtrack.bustrack.dao.api.OfferRequestLineFixServiceDao#getByNumber(java.lang.Integer)
	 */
	@Override
	public OfferRequestLineFixService getByNumber(Integer number) {
		OfferRequestLineFixService offerRequestLineFixService = (OfferRequestLineFixService)getEntityManager()
		.createQuery("SELECT p FROM " + getEntityName() + " p WHERE p.number = :number")
		.setParameter("number", number).getSingleResult();
	
		return offerRequestLineFixService;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfferRequestLineFixService> getByStatus(OfferRequest offerRequest, OfferRequestLineStatus offerRequestLineStatus) {		
		return (List<OfferRequestLineFixService>)getEntityManager()
		.createQuery("SELECT p FROM " + getEntityName() + " p WHERE p.offerRequestLineStatus = :offerRequestLineStatus AND p.offerRequest = :offerRequest")
		.setParameter("offerRequest", offerRequest)
		.setParameter("offerRequestLineStatus", offerRequestLineStatus).getResultList();
	
	}
	
	@Override
	public List<OfferRequestLineFixService> getByStatusCollection(OfferRequest offerRequest, List<OfferRequestLineStatus> statusCollection) {		
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<OfferRequestLineFixService> q = cb.createQuery(OfferRequestLineFixService.class);
		
		// root query builder
		Root<OfferRequestLineFixService> c = q.from(OfferRequestLineFixService.class);
		
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
