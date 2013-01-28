/*
 * Copyright 2009 IT Mill Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.thingtrack.bustrack.service.web.client;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.thingtrack.bustrack.service.web.api.AddressServiceFacade;
import com.thingtrack.konekti.domain.Address;
import com.vaadin.Application;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;

/**
 * The Application's "main" class
 */
@Configurable(autowire = Autowire.BY_TYPE)
@SuppressWarnings("serial")
public class WsClientApplication extends Application {
	private Window window;
	private Table addressTable;

	@Autowired
	private AddressServiceFacade addressService;

	@Override
	public void init() {
		window = new Window("Bustrack Web Service Client Application");
		setMainWindow(window);		
		buildComponents();

	}

	private void buildComponents() {
		addressTable = new Table("Bustrack - Address List");
		window.addComponent(addressTable);
		
		fillData();
	}

	private void fillData() {

		BeanItemContainer<Address> beans = new BeanItemContainer<Address>(Address.class);

		try {
			for (Address address : addressService.getAll()) {
				beans.addBean(address);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		addressTable.setContainerDataSource(beans);
		
		addressTable.setVisibleColumns(new String[]{"street", "number", "zipCode", "city", "country"});
		addressTable.setColumnHeaders(new String[]{"Street", "Number", "ZipCode", "City", "Country"});
	}

}
