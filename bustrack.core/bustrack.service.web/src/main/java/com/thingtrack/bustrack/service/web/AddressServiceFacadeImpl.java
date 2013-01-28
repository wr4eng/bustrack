package com.thingtrack.bustrack.service.web;

import java.util.List;

import javax.jws.WebService;

import com.thingtrack.bustrack.service.web.api.AddressServiceFacade;
import com.thingtrack.konekti.domain.Address;
import com.thingtrack.konekti.service.api.AddressService;

@WebService(name="AddressService", endpointInterface="com.thingtrack.bustrack.service.web.api.AddressServiceFacade" )
public class AddressServiceFacadeImpl implements AddressServiceFacade {

	private AddressService addressServiceReference;

	public void setAddressServiceReference(AddressService addressServiceReference) {
		this.addressServiceReference = addressServiceReference;
	}

	public List<Address> getAll() throws Exception {
		return addressServiceReference.getAll();
	}

	public Address get(Integer id) throws Exception {
		return addressServiceReference.get(id);
	}

	public Address getByName(String street) throws Exception {
		return addressServiceReference.getByName(street);
	}

	public void add(Address address) throws Exception {
		addressServiceReference.add(address);
	}

	public void delete(Integer id) throws Exception {
		addressServiceReference.delete(id);
	}

	public void edit(Address address) throws Exception {
		addressServiceReference.edit(address);
	}

}
