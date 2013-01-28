package com.thingtrack.bustrack.dao.api;

import java.util.List;

import com.thingtrack.bustrack.domain.OfferRequestLineFixService;
import com.thingtrack.konekti.dao.template.Dao;
import com.thingtrack.konekti.domain.OfferRequest;
import com.thingtrack.konekti.domain.OfferRequestLineStatus;

/**
 * @author Thingtrack S.L.
 *
 */
public interface OfferRequestLineFixServiceDao extends Dao<OfferRequestLineFixService, Integer> {
	public OfferRequestLineFixService getByNumber( Integer number ) throws Exception;	
	public List<OfferRequestLineFixService> getByStatus(OfferRequest offerRequest, OfferRequestLineStatus offerRequestLineStatus) throws Exception;
	public List<OfferRequestLineFixService> getByStatusCollection(OfferRequest offerRequest, List<OfferRequestLineStatus> statusCollection) throws Exception;
}
