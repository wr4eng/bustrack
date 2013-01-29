package com.thingtrack.bustrack.view.module.servicedesigner.internal;

import org.tepi.filtertable.FilterGenerator;

import com.thingtrack.bustrack.view.module.servicedesigner.internal.ServiceView.OfferCodeColumn;
import com.thingtrack.bustrack.view.module.servicedesigner.internal.ServiceView.OfferLineCodeColumn;
import com.thingtrack.bustrack.view.module.servicedesigner.internal.ServiceView.VehicleTypeColumn;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.TextField;

public class ServiceFilterGenerator implements FilterGenerator {

	@Override
	public Filter generateFilter(Object propertyId, Object value) {

		if (OfferCodeColumn.OFFER_CODE_COLUMN.equals(propertyId)) {

			/* Create an 'equals' filter for the ID field */
			if (value != null && value instanceof String) {

				return new Compare.Equal(propertyId, value.toString());
			}
		}

		if (OfferLineCodeColumn.OFFER_LINE_CODE_COLUMN.equals(propertyId)) {

			/* Create an 'equals' filter for the ID field */
			if (value != null && value instanceof String) {

				return new Compare.Equal(propertyId, value.toString());
			}

		}

		if (VehicleTypeColumn.VEHICLE_TYPE_COLUMN.equals(propertyId)) {

			/* Create an 'equals' filter for the ID field */
			if (value != null && value instanceof String) {

				return new Compare.Equal(propertyId, value.toString());
			}
		}

		// For other properties, use the default filter
		return null;
	}

	@Override
	public AbstractField getCustomFilterComponent(Object propertyId) {
		
		
		if(OfferCodeColumn.OFFER_CODE_COLUMN.equals(propertyId) || 
			OfferLineCodeColumn.OFFER_LINE_CODE_COLUMN.equals(propertyId) ||
			VehicleTypeColumn.VEHICLE_TYPE_COLUMN.equals(propertyId)){
			
			return new TextField();
		}
		
		// For other properties, use the default filter
		return null;
	}

	@Override
	public void filterRemoved(Object propertyId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void filterAdded(Object propertyId,
			Class<? extends Filter> filterType, Object value) {
		// TODO Auto-generated method stub

	}

}
