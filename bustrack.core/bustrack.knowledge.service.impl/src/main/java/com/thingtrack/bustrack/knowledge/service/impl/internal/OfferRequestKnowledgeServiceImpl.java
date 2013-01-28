/*
 * Copyright 2011 Thingtrack, S.L.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.thingtrack.bustrack.knowledge.service.impl.internal;

import java.util.HashMap;

import org.drools.runtime.StatefulKnowledgeSession;

import org.springframework.beans.factory.annotation.Autowired;


import com.thingtrack.konekti.service.api.AddressService;
import com.thingtrack.konekti.service.api.OfferRequestService;
import com.thingtrack.konekti.service.api.OfferService;
import com.thingtrack.konekti.service.api.UserService;

import com.thingtrack.bustrack.service.api.OfferRequestLineFixServiceService;
import com.thingtrack.bustrack.service.api.OfferRequestLineRegularServiceService;

import com.thingtrack.konekti.domain.OfferRequest;
import com.thingtrack.bustrack.knowledge.service.api.OfferRequestKnowledgeService;
/**
 * @author Thingtrack S.L.
 *
 */
public class OfferRequestKnowledgeServiceImpl implements OfferRequestKnowledgeService {
	@Autowired
	private StatefulKnowledgeSession statefulKnowledgeSession;
		
	@Autowired
	private UserService userService;

	@Autowired
	private AddressService addressService;
	
	@Autowired
	private OfferRequestService offerRequestService;

	@Autowired
	private OfferRequestLineFixServiceService offerRequestLineFixServiceService;

	@Autowired
	private OfferRequestLineRegularServiceService offerRequestLineRegularServiceService;

	@Autowired
	private OfferService offerService;
	
	@Override
	public void setPending(OfferRequest offerRequest) throws Exception {		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("offerRequestService", offerRequestService);
		params.put("offerRequestLineFixServiceService", offerRequestLineFixServiceService);
		params.put("offerRequestLineRegularServiceService", offerRequestLineRegularServiceService);
		params.put("offerRequest", offerRequest);
		
		// execute BP
		statefulKnowledgeSession.startProcess("com.konekti.knowledge.bpmn2.bppendingofferrequest", params);
		statefulKnowledgeSession.dispose();
		
	}
	
	@Override
	public void setReOpen(OfferRequest offerRequest) throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("offerRequestService", offerRequestService);
		params.put("offerRequestLineFixServiceService", offerRequestLineFixServiceService);
		params.put("offerRequestLineRegularServiceService", offerRequestLineRegularServiceService);
		params.put("offerRequest", offerRequest);
		
		// execute BP
		statefulKnowledgeSession.startProcess("com.konekti.knowledge.bpmn2.bpreopenofferrequest", params);
		statefulKnowledgeSession.dispose();
				
	}
	
	@Override
	public void setTransfer(OfferRequest offerRequest) throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("offerRequestService", offerRequestService);
		params.put("offerRequestLineFixServiceService", offerRequestLineFixServiceService);
		params.put("offerRequestLineRegularServiceService", offerRequestLineRegularServiceService);
		params.put("offerService", offerService);
		params.put("organization", offerRequest.getOrganization());		
		params.put("offerRequest", offerRequest);
		
		// execute BP
		statefulKnowledgeSession.startProcess("com.konekti.knowledge.bpmn2.bptransferofferrequest", params);
		statefulKnowledgeSession.dispose();	
		
	}
	
	@Override
	public void setRejected(OfferRequest offerRequest) throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("offerRequestService", offerRequestService);
		params.put("offerRequestLineFixServiceService", offerRequestLineFixServiceService);
		params.put("offerRequestLineRegularServiceService", offerRequestLineRegularServiceService);
		params.put("offerRequest", offerRequest);
		
		// execute BP
		statefulKnowledgeSession.startProcess("com.konekti.knowledge.bpmn2.bprejectofferrequest", params);
		statefulKnowledgeSession.dispose();
		
	}

	@Override
	public void setTEST() throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("addressService", addressService);
		params.put("userService", userService);
		
		statefulKnowledgeSession.startProcess("com.konekti.knowledge.bpmn2.test03", params);
		statefulKnowledgeSession.dispose();
		
	}
	
}
