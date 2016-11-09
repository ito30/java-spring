package com.snail.core.beans;


public class FareSummary implements Deliverable {

	private String currency;

	private FareDetail adultFareDetail;
	private FareDetail childFareDetail;
	private FareDetail infantFareDetail;

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setAdultFareDetail(FareDetail adultFareDetail) {
		this.adultFareDetail = adultFareDetail;
	}

	public void setChildFareDetail(FareDetail childFareDetail) {
		this.childFareDetail = childFareDetail;
	}

	public void setInfantFareDetail(FareDetail infantFareDetail) {
		this.infantFareDetail = infantFareDetail;
	}

	public double getTotalFare() {
		double total = 0;

		if (adultFareDetail != null) {
			total += adultFareDetail.getSum();
		}

		if (childFareDetail != null) {
			total += childFareDetail.getSum();
		}

		if (infantFareDetail != null) {
			total += infantFareDetail.getSum();
		}

		return total;
	}

	@Override
	public DeliveryMap deliver() {

		double totalFare = getTotalFare();

		DeliveryMap map = new DeliveryMap();
		map.put("currency", currency);
		map.put("total_fare", totalFare);
		map.put("adult", adultFareDetail);
		map.put("child", childFareDetail);
		map.put("infant", infantFareDetail);

		return map;
	}

}
