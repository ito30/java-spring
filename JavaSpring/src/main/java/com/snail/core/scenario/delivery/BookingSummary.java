package com.snail.core.scenario.delivery;

import com.snail.core.beans.Deliverable;
import com.snail.core.beans.DeliveryMap;

public class BookingSummary implements Deliverable {

	private double balanceDue;
	private double bookedFare;
	private double commission;
	private double nta;
	private double agentFee;
	private double ssrFee;
	private double incentive;
	private double upselling;
	
	// NUGROHO.H Add setAgentFee [2014-10-14]
	public void setAgentFee(double agentFee) {
		this.agentFee = agentFee;
	}

	public void setBalanceDue(double balanceDue) {
		this.balanceDue = balanceDue;
	}

	public void setBookedFare(double bookedFare) {
		this.bookedFare = bookedFare;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public void setNta(double nta) {
		this.nta = nta;
	}

	public void setSsrFee(double ssrFee) {
		this.ssrFee = ssrFee;
	}

	public void setUpselling(double upselling) {
		this.upselling = upselling;
	}

	@Override
	public DeliveryMap deliver() {
		DeliveryMap map = new DeliveryMap();
		map.put("balance_due", balanceDue);
		map.put("booked_fare", bookedFare);
		map.put("commission", commission);
		map.put("nta", nta);
		map.put("agent_fee", agentFee);
		map.put("ssr_fee", ssrFee);
		map.put("incentive", incentive);
		map.put("upselling", upselling);

		return map;
	}

	// NUGROHO.H Add [2015-01-15]
	public void setIncentive(double incentive) {
		this.incentive = incentive;
	}

}
