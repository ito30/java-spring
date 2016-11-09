package com.snail.core.beans;

import java.util.List;

public class TTrip implements Deliverable {
	
	private FareAggregate adultFare;
	private FareAggregate childFare;
	private FareAggregate infantFare;
	
	public FareAggregate getAdultFare() {
		return adultFare;
	}

	public void setAdultFare(FareAggregate adultFare) {
		this.adultFare = adultFare;
	}

	public FareAggregate getChildFare() {
		return childFare;
	}

	public void setChildFare(FareAggregate childFare) {
		this.childFare = childFare;
	}
	
	public FareAggregate getInfantFare() {
		return infantFare;
	}

	public void setInfantFare(FareAggregate infantFare) {
		this.infantFare = infantFare;
	}

	// GET FARE
	public double getTotalFare() {
		return adultFare.getSum() + childFare.getSum() + infantFare.getSum();
	}
	
	public TTrip() {
		
	}
	
	public TTrip(List<? extends TAvailability> listAvJourney) {
		
		double adultSum = 0;
		double childSum = 0;
		double infantSum = 0;
		
		int adultCount = 0;
		int childCount = 0;
		int infantCount = 0;
		
		for (TAvailability av : listAvJourney) {
			adultSum += av.getAdultFare().getSum();
			childSum += av.getChildFare().getSum();
			infantSum += av.getInfantFare().getSum();			
		}
		
		if (listAvJourney.size() > 0) {
			adultCount = listAvJourney.get(0).getAdultFare().getCount();
			childCount = listAvJourney.get(0).getChildFare().getCount();
			infantCount = listAvJourney.get(0).getInfantFare().getCount();
		}
		
		this.setAdultFare(FareAggregate.createBySum(adultSum, adultCount));
		this.setChildFare(FareAggregate.createBySum(childSum, childCount));
		this.setInfantFare(FareAggregate.createBySum(infantSum, infantCount));
	}
	
	@Override
	public DeliveryMap deliver() {
		DeliveryMap map = new DeliveryMap();
		map.put("fare", getTotalFare());
		map.put("adult_fare", adultFare.getUnitPrice());
		map.put("child_fare", childFare.getUnitPrice());
		map.put("infant_fare", infantFare.getUnitPrice());
		return map;
	}	
}
