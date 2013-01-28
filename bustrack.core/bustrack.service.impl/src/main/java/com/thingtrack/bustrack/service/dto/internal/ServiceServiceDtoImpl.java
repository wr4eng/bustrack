package com.thingtrack.bustrack.service.dto.internal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.bustrack.domain.dto.ServiceDto;
import com.thingtrack.bustrack.service.dto.api.ServiceServiceDto;
import com.thingtrack.konekti.dao.api.ServiceDao;
import com.thingtrack.konekti.domain.Service;

public class ServiceServiceDtoImpl implements ServiceServiceDto {
	@Autowired
	private ServiceDao serviceDao;
	
	@Override
	public List<ServiceDto> getAll() throws Exception {
		List<ServiceDto> dtos = new ArrayList<ServiceDto>();

		List<Service> services = serviceDao.getAll();
		
		for (Service service : services) {
			ServiceDto serviceDto = null;
			
			String clientVat = null;
			String clientName = null;
			
			if (service.getClient() != null) {
				clientVat = service.getClient().getVat();
				clientName = service.getClient().getName();
			}
			
			// generate DTO
			if (service.getOfferLine() != null) {
				serviceDto = new ServiceDto(service.getOfferLine().getOffer(), 
											service.getOfferLine(),
										    service, 
										    service.getOfferLine().getOffer().getCode(), 
										    service.getOfferLine().getOffer().getRevision(),
										    service.getOfferLine().getOffer().getOfferDate(), 
										    service.getOfferLine().getOffer().getOfferType().getDescription(),
										    clientName,
										    clientVat, 
										    service.getOfferLine().getOffer().getOfferStatus().getDescription(),
										    service.getOfferLine().getNumber(), 
										    service.getOfferLine().getOfferLineStatus().getDescription(),
			                                service.getCode());			
			}
			else {
				serviceDto = new ServiceDto(null, 
										    null,
										    service, 
			                                null, 
			                                null,
			                                null, 
			                                clientName,
			                                clientVat,
			                                null, 
			                                null,
			                                null, 
			                                null,
			                                service.getCode());
			}
				
			// add new DTO to the collection
			dtos.add(serviceDto);				
			
		}

		return dtos;

	}
}
