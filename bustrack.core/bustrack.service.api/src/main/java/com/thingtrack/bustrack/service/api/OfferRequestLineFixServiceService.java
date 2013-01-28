package com.thingtrack.bustrack.service.api;

import java.util.List;

import com.thingtrack.bustrack.domain.OfferRequestLineFixService;
import com.thingtrack.konekti.domain.Offer;
import com.thingtrack.konekti.domain.OfferRequest;
import com.thingtrack.konekti.domain.Organization;

/**
 * @author Thingtrack S.L.
 *
 */
public interface OfferRequestLineFixServiceService {
	public List<OfferRequestLineFixService> getAll() throws Exception;
	public OfferRequestLineFixService get( Integer offerRequestLineFixServiceId ) throws Exception;
	public OfferRequestLineFixService getByNumber( Integer number ) throws Exception;
	public OfferRequestLineFixService save(OfferRequestLineFixService offerRequestLineFixService) throws Exception;
	public void delete(OfferRequestLineFixService offerRequestLineFixService) throws Exception;
	public void setOpenedStatus(OfferRequestLineFixService offerRequestLineFixService) throws Exception;
	public void setPendingStatus(OfferRequestLineFixService offerRequestLineFixService) throws Exception;
	public void setTransferredStatus(OfferRequestLineFixService offerRequestLineFixService) throws Exception;
	public void setRejectStatus(OfferRequestLineFixService offerRequestLineFixService) throws Exception;
	public void setCloseStatus(OfferRequestLineFixService offerRequestLineFixService) throws Exception;
	public List<OfferRequestLineFixService> getByOpenedStatus(OfferRequest offerRequest) throws Exception;
	public List<OfferRequestLineFixService> getByPendingStatus(OfferRequest offerRequest) throws Exception;
	public List<OfferRequestLineFixService> getByTransferedStatus(OfferRequest offerRequest) throws Exception;
	public List<OfferRequestLineFixService> getByRejetedStatus(OfferRequest offerRequest) throws Exception;
	public List<OfferRequestLineFixService> getByClosedStatus(OfferRequest offerRequest) throws Exception;
	public List<OfferRequestLineFixService> getByOpenedOrPendingStatus(OfferRequest offerRequest) throws Exception;
	public Offer createOfferLine(Organization organization, Offer offer, OfferRequestLineFixService offerRequestLineFixService, int number) throws Exception;
	public OfferRequestLineFixService createNewOfferRequestLineFixService(OfferRequest offerRequest) throws Exception;
}
