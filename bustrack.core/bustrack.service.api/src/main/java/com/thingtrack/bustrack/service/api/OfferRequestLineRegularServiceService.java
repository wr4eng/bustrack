package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.OfferRequestLineRegularService;
import com.thingtrack.konekti.domain.Offer;
import com.thingtrack.konekti.domain.OfferRequest;
import com.thingtrack.konekti.domain.Organization;

/**
 * @author Thingtrack S.L.
 *
 */
public interface OfferRequestLineRegularServiceService {
	public List<OfferRequestLineRegularService> getAll() throws Exception;
	public OfferRequestLineRegularService get( Integer offerRequestLineRegularServiceId ) throws Exception;
	public OfferRequestLineRegularService getByNumber( Integer number ) throws Exception;
	public OfferRequestLineRegularService save(OfferRequestLineRegularService offerRequestLineRegularService) throws Exception;
	public void delete(OfferRequestLineRegularService offerRequestLineRegularService) throws Exception;
	public void setOpenedStatus(OfferRequestLineRegularService offerRequestLineRegularService) throws Exception;
	public void setPendingStatus(OfferRequestLineRegularService offerRequestLineRegularService) throws Exception;
	public void setTransferredStatus(OfferRequestLineRegularService offerRequestLineRegularService) throws Exception;
	public void setRejectStatus(OfferRequestLineRegularService offerRequestLineRegularService) throws Exception;
	public void setCloseStatus(OfferRequestLineRegularService offerRequestLineRegularService) throws Exception;
	public List<OfferRequestLineRegularService> getByOpenedStatus(OfferRequest offerRequest) throws Exception;
	public List<OfferRequestLineRegularService> getByPendingStatus(OfferRequest offerRequest) throws Exception;
	public List<OfferRequestLineRegularService> getByTransferedStatus(OfferRequest offerRequest) throws Exception;
	public List<OfferRequestLineRegularService> getByRejetedStatus(OfferRequest offerRequest) throws Exception;
	public List<OfferRequestLineRegularService> getByClosedStatus(OfferRequest offerRequest) throws Exception;
	public List<OfferRequestLineRegularService> getByOpenedOrPendingStatus(OfferRequest offerRequest) throws Exception;
	public Offer createOfferLine(Organization organization, Offer offer, OfferRequestLineRegularService offerRequestLineRegularService, int number) throws Exception;
	public OfferRequestLineRegularService createNewOfferRequestLineFixService(OfferRequest offerRequest) throws Exception;
}
