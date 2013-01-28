package com.thingtrack.bustrack.dao.api;

import java.util.List;

import com.thingtrack.bustrack.domain.OfferRequestLineRegularService;
import com.thingtrack.konekti.dao.template.Dao;
import com.thingtrack.konekti.domain.OfferRequest;
import com.thingtrack.konekti.domain.OfferRequestLineStatus;

/**
 * @author Thingtrack S.L.
 *
 */
public interface OfferRequestLineRegularServiceDao extends Dao<OfferRequestLineRegularService, Integer> {
	public OfferRequestLineRegularService getByNumber( Integer number ) throws Exception;
	public List<OfferRequestLineRegularService> getByStatus(OfferRequest offerRequest, OfferRequestLineStatus offerRequestLineStatus) throws Exception;
	public List<OfferRequestLineRegularService> getByStatusCollection(OfferRequest offerRequest, List<OfferRequestLineStatus> statusCollection) throws Exception;
}
