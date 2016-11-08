package com.ito.app.beans;

import com.ito.app.controller.SoapHandler;

public class ServiceCharge {
	private String chargeDetail;
	private String chargeCode;
	private String chargeType;
	private String collectType;
	private double amount;

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getChargeDetail() {
		return chargeDetail;
	}

	public String getChargeType() {
		return chargeType;
	}

	public String getCollectType() {
		return collectType;
	}

	public double getAmount() {
		return amount;
	}

	// **CREATE
	public static ServiceCharge create(SoapHandler soapCharge) {
		String chargeDetail = soapCharge.getAttributeValueAsString("TaxName");
		String chargeCode = soapCharge.getAttributeValueAsString("TaxCode");
		double amount = soapCharge.getAttributeValueAsDouble("Amount");

		ServiceCharge charge = new ServiceCharge();
		charge.chargeDetail = chargeDetail;
		charge.amount = amount;
		charge.chargeCode = chargeCode;			
		return charge;
	}
	
	public static ServiceCharge create(String chargeDetail,String chargeType ,String chargeCode,String collectType,
			double amount) {

		ServiceCharge charge = new ServiceCharge();
		charge.chargeDetail = chargeDetail;
		charge.chargeType = chargeType;
		charge.collectType = collectType;
		charge.amount = amount;
		charge.chargeCode = chargeCode;

		return charge;
	}

	public String getChargeCode() {		
		return chargeCode;
	}
}
