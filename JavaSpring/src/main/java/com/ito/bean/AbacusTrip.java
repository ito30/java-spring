package com.ito.bean;

import java.util.List;

import com.snail.core.beans.DeliveryMap;
import com.snail.core.beans.FareAggregate;
import com.snail.core.beans.TFlightSelect;
import com.snail.core.beans.TTrip;

public class AbacusTrip extends TTrip {

	private String flightSelect;
	
	public AbacusTrip(List<AbacusAvailability> listAvJourney) {
		super(listAvJourney);
		
		double infantSum = 0;
		int infantCount = 0;
		
		if (listAvJourney.size() > 0) {
			AbacusAvailability av = listAvJourney.get(0);
			infantCount = av.getInfantFare().getCount();			
			infantSum = av.getInfantFare().getSum();			
		}
				
		this.setInfantFare(FareAggregate.createBySum(infantSum, infantCount));
		
		boolean isFirst = true;
		for (AbacusAvailability av : listAvJourney) {
			if (isFirst) {				
				flightSelect = TFlightSelect.createFlightSelectString(av.getListSegment());
				isFirst = false;
			} else {
				flightSelect += ("~" + TFlightSelect.createFlightSelectString(av.getListSegment()));
			}
		}
	}
	
	public void setFareByFareInfo(AbacusFareInfo fareInfo, int countAdult, int countChild)
	{
		this.setAdultFare(FareAggregate.createByUnitFare(fareInfo.getAdultSpecificFare(""), countAdult));
		this.setChildFare(FareAggregate.createByUnitFare(fareInfo.getChildSpecificFare(""), countChild));
	}
	
	public AbacusTrip() {
		
	}

	public static AbacusTrip createFromList(List<AbacusAvailability> listAvJourney)
	{
		AbacusTrip trip = new AbacusTrip();
		
		double adultSum = 0;
		double childSum = 0;
		double infantSum = 0;
		
		int adultCount = 0;
		int childCount = 0;
		int infantCount = 0;
		
		boolean isFirst = true;
		for (AbacusAvailability av : listAvJourney) {
			adultSum += av.getAdultFare().getSum();
			childSum += av.getChildFare().getSum();
			infantSum += av.getInfantFare().getSum();	
			
			if (isFirst) {				
				trip.flightSelect = TFlightSelect.createFlightSelectString(av.getListSegment());
				isFirst = false;
			} else {
				trip.flightSelect += ("~" + TFlightSelect.createFlightSelectString(av.getListSegment()));
			}
		}
		
		if (listAvJourney.size() > 0) {
			adultCount = listAvJourney.get(0).getAdultFare().getCount();
			childCount = listAvJourney.get(0).getChildFare().getCount();
			infantCount = listAvJourney.get(0).getInfantFare().getCount();
		}
		
		trip.setAdultFare(FareAggregate.createBySum(adultSum, adultCount));
		trip.setChildFare(FareAggregate.createBySum(childSum, childCount));
		trip.setInfantFare(FareAggregate.createBySum(infantSum, infantCount));
		
		return trip;
	}

	@Override
	public DeliveryMap deliver() {

		DeliveryMap map = super.deliver();
		map.put("flight_select", flightSelect);
		return map;
	}
}
