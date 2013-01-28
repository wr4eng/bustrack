package com.thingtrack.bustrack.service.web.api;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;

import com.thingtrack.konekti.domain.Address;
@WebService
@SOAPBinding(parameterStyle=ParameterStyle.BARE)
public interface AddressServiceFacade {
	
	@WebMethod(operationName="getAll")
	public List<Address> getAll() throws Exception;
	@WebMethod(operationName="get")
	public Address get( Integer id ) throws Exception;
	@WebMethod(operationName="getByName")
	public Address getByName( String street ) throws Exception;
	@WebMethod(operationName="add")
	public void add(Address address) throws Exception;
	@WebMethod(operationName="delete")
	public void delete(Integer id) throws Exception;
	@WebMethod(operationName="edit")
	public void edit(Address address) throws Exception;

}
