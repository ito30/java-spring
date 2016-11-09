package com.snail.core.scenario.delivery;

import java.util.ArrayList;
import java.util.List;

import com.snail.core.beans.Deliverable;
import com.snail.core.beans.DeliveryMap;
import com.snail.core.beans.FareAggregate;
import com.snail.core.beans.TAvailability;

public class SearchDelivery implements Deliverable {

	private List<TAvailability> listAv;

	// SET AVAILABILITY
	public void setListAvailablity(List<TAvailability> listAv) {
		this.listAv = listAv;
	}

	// SET INFANT PRICE
	public void updateAllInfant(double fare, int count) {
		for (TAvailability av : listAv) {
			av.setInfantFare(FareAggregate.createByUnitFare(fare, count));
		}
	}

	// GET AVAILABLITIES
	public List<TAvailability> getAvailablities() {
		return listAv;
	}

	// ADD
	public void add(TAvailability av) {
		if (listAv == null) {
			listAv = new ArrayList<TAvailability>();
		}

		listAv.add(av);
	}

	// FIRST
	public TAvailability first() {
		if (listAv == null) {
			return null;
		}
		return listAv.get(0);
	}

	// CONVERT
	@Override
	public DeliveryMap deliver() {
		DeliveryMap map = new DeliveryMap();

		map.put("go", listAv);
		map.put("go_count", listAv.size());

		return map;
	}
}
