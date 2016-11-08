package com.snail.core.beans;

public class TAvailability implements Deliverable {
	private String currency;

	private FareAggregate adultFare;
	private FareAggregate childFare;
	private FareAggregate infantFare;

	private String airlineFamily;
	private String serviceClass;
	// private String journeySellKey;
	// private String fareSellKey;

	private int international;
	private int promo;

	// CONSTRUCTOR
	public TAvailability() {
		this.adultFare = new FareAggregate();
		this.infantFare = new FareAggregate();
		this.childFare = new FareAggregate();

		this.international = -1;
		this.promo = -1;

	}

	// // SET JOURNEY SELL KEY
	// public void setJourneySellKey(String journeySellKey) {
	// this.journeySellKey = journeySellKey;
	// }
	//
	// // SET FARE SELL KEY
	// public void setFareSellKey(String fareSellKey) {
	// this.fareSellKey = fareSellKey;
	// }
	//
	// // GET JOURNEY SELL KEY
	// public String getJourneySellKey() {
	// return journeySellKey;
	// }
	//
	// // GET FARE SELL KEY
	// public String getFareSellKey() {
	// return fareSellKey;
	// }

	// SET PROMO
	public void setPromo(boolean promo) {
		this.promo = promo ? 1 : 0;
	}

	// IS PROMO
	public boolean isPromo() {
		return promo == 1;
	}

	// SET INTERNATIONAL
	public void setInternational(boolean international) {
		this.international = international ? 1 : 0;
	}

	// SET COMPANY
	public void setAirlineFamily(String airlineFamily) {
		this.airlineFamily = airlineFamily;
	}

	// SET FLIGHT CLASS
	public void setServiceClass(String serviceClass) {
		this.serviceClass = serviceClass;
	}

	// GET SERVICE CLASS
	public String getServiceClass() {
		return serviceClass;
	}

	// GET CURRENCY
	public String getCurrency() {
		return currency;
	}

	// SET CURRENCY
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	// SET ADULT
	public void setAdultFare(FareAggregate adult) {
		this.adultFare = adult;
	}

	// SET ADULT FARE
	public void setAdultFare(double fare, int count) {
		this.adultFare = FareAggregate.createByUnitFare(fare, count);
	}

	// SET CHILD
	public void setChildFare(FareAggregate child) {
		this.childFare = child;
	}

	// SET CHILD FARE
	public void setChildFare(double fare, int count) {
		this.childFare = FareAggregate.createByUnitFare(fare, count);
	}

	// SET INFANT
	public void setInfantFare(FareAggregate infant) {
		this.infantFare = infant;
	}

	// SET INFANT FARE
	public void setInfantFare(double fare, int count) {
		this.infantFare = FareAggregate.createByUnitFare(fare, count);
	}

	// GET ADULT FARE
	public FareAggregate getAdultFare() {
		return adultFare;
	}

	// GET CHILD FARE
	public FareAggregate getChildFare() {
		return childFare;
	}

	// GET FARE
	public double getTotalFare() {
		return adultFare.getSum() + childFare.getSum() + infantFare.getSum();
	}
	
	// NUGROHO.H Add [2014-11-28]
	// GET INFANT FARE
	public FareAggregate getInfantFare() {
		return infantFare;
	}

	// DELIVER
	@Override
	public DeliveryMap deliver() {
		DeliveryMap map = new DeliveryMap();

		map.put("flight_class", serviceClass);
		map.put("is_international", international);
		// NUGROHO.H Modify [2014-12-02]
//		map.put("is_promo", promo);
		map.put("airline_family", airlineFamily);
		map.put("active", 1);

		map.put("curr", currency);
		map.put("fare", getTotalFare());
		map.put("adult_fare", adultFare.getUnitPrice());
		map.put("child_fare", childFare.getUnitPrice());
		map.put("infant_fare", infantFare.getUnitPrice());
		map.put("adult_count", adultFare.getCount());
		map.put("child_count", childFare.getCount());
		map.put("infant_count", infantFare.getCount());

		return map;
	}

	public int getInternational() {
		return international;
	}
	
	

	// INSTRUCTION
	// public static Instruction instruction() {
	// Instruction doc = new Instruction();
	//
	// doc.put("fare", "total fare");
	// doc.put("adult_fare", "fare for adult passenger");
	// doc.put("child_fare", "fare for child passenger");
	// doc.put("infant_fare", "fare for infant");
	// doc.put("adult_count");
	// doc.put("child_count");
	// doc.put("infant_count");
	// doc.put("flight_class", "class of service");
	// doc.put("curr", "currency");
	// doc.put("is_international",
	// "more than 0 is international flight, 0 is non international, less than 0 is unknown");
	// doc.put("is_promo", "1 is promo, 0 is else");
	// doc.put("company");
	// doc.put("active", "value is 1");
	// doc.put("flight_select");
	// doc.put("[flight_info]", TSegment.documentation());
	//
	// return doc;
	// }

}
