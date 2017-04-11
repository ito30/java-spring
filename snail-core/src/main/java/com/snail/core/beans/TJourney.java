package com.snail.core.beans;

public class TJourney implements Deliverable {

	private TAvailability2 av;
	private TAvailability2 avReturn;
	private FareSummary fareSum;
	private String journeyKey;

	// CONSTRUCTOR
	private TJourney() {
	}

	// SET FARE SUM
	public void setFareSum(FareSummary fareSum) {
		this.fareSum = fareSum;
	}

	//

	@Override
	public DeliveryMap deliver() {
		DeliveryMap result = new DeliveryMap();
		result.put("journey_key", journeyKey);
		result.put("fare_sum", fareSum);
		result.put("availability_depart", av);
		result.put("availability_return", avReturn);

		return result;
	}

	// ***

	public static TJourney create(TAvailability2 av, TAvailability2 avReturn) {
		TJourney journey = new TJourney();
		journey.av = av;
		journey.journeyKey = av.getAvKey();

		if (avReturn != null) {
			journey.avReturn = avReturn;
			journey.journeyKey += "$" + avReturn.getAvKey();
		}

		return journey;
	}

	public static TJourney create(TAvailability2 av) {
		TJourney journey = new TJourney();
		journey.av = av;
		journey.journeyKey = av.getAvKey();
		return journey;
	}
}
