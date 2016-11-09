package com.snail.core.beans;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommonAvailability extends TAvailability {

	private List<TSegment> listSegment;
	private TFareInfo detailFare;	
		
	// SET DETAIL FARE
	public void setDetailFare(TFareInfo detailFare) {
		this.detailFare = detailFare;
	}

	// CLEAR SEGMENT
	public void clearSegment() {
		this.listSegment = null;
	}

	// ADD FLIGHT DETAIL
	public void addSegment(TSegment segment) {
		if (listSegment == null) {
			listSegment = new ArrayList<TSegment>();
		}

		listSegment.add(segment);
	}

	// SET FLIGHT DETAIL
	public void setListSegment(List<? extends TSegment> listSegment) {
		this.listSegment = new ArrayList<TSegment>();
		for (TSegment leg : listSegment) {
			this.listSegment.add(leg);
		}
	}

	// GET LEG LIST
	public List<TSegment> getListSegment() {
		return listSegment;
	}

	// GET LEG SIZE
	public int getSegmentCount() {
		return listSegment.size();
	}

	// GET SEGMENT
	public TSegment getSegment(int index) {
		if (listSegment != null) {
			return listSegment.get(index);
		} else {
			return null;
		}
	}

	// IS SELECTED
	public boolean isMatched(TFlightSelect select) {
		return select.isMatched(listSegment);
	}

	@Override
	public DeliveryMap deliver() {
		String flightSelect = TFlightSelect
				.createFlightSelectString(listSegment);
		
		DeliveryMap map = super.deliver();
		map.put("flight_select", flightSelect);
		map.put("flight_info", listSegment);
		map.put("fare_info", detailFare);

		return map;
	}
}
