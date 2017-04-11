package com.ito.bean;

import java.util.ArrayList;
import java.util.List;
import com.snail.core.util.SoapHandler;
import com.snail.core.util.StringUtil;

public class PaxFare {
	private String paxType;
	private List<ServiceCharge> listCharge;
	private int paxCount;	

	public int getPaxCount() {
		return paxCount;
	}

	public String getPaxType() {
		return paxType;
	}

	public List<ServiceCharge> getListCharge() {
		return listCharge;
	}

	//
	public void addCharge(ServiceCharge charge) {
		if (listCharge == null) {
			listCharge = new ArrayList<ServiceCharge>();
		}

		listCharge.add(charge);
	}

	// GET TOTAL AMOUNT
	public double getTotalAmount() {
		double result = 0;
		for (ServiceCharge charge : listCharge) {
			result += charge.getAmount();
		}

		return result;
	}

	// GET BASE AMOUNT
	public double getBaseAmount() {
		return getAmount("FarePrice");
	}
	
	// GET UPSELLING
	public double getUpsellingAmount() {
		return getAmount("MarkedUpPrice");
	}

	// GET AMOUNT
	public double getAmount(String detail) {
		for (ServiceCharge charge : listCharge) {
			String chargeDetail = charge.getChargeDetail();
			if (StringUtil.isSame(chargeDetail, detail)) {
				return charge.getAmount();
			}
		}

		return 0;
	}

	// GET AMOUNT
	public double getAmountByType(String type) {
		for (ServiceCharge charge : listCharge) {
			String chargeType = charge.getChargeType();

			if (StringUtil.isSame(chargeType, type)) {
				return charge.getAmount();
			}
		}

		return 0;
	}

	// CREATE
	public static PaxFare create(SoapHandler soapPaxFare, Upselling upSelling) {

		PaxFare avFare = new PaxFare();
		SoapHandler passengerTypeQuantity = soapPaxFare.find("PassengerTypeQuantity");
		String paxType = passengerTypeQuantity.getAttributeValueAsString("Code");
		int paxCount = (int)passengerTypeQuantity.getAttributeValueAsDouble("Quantity");
		avFare.paxType = paxType;
		avFare.paxCount = paxCount;
		
		try {
			List<SoapHandler> listSoapCharge = soapPaxFare.find("Taxes")
			.findAsList("Tax");

			for (SoapHandler soapCharge : listSoapCharge) {
				ServiceCharge charge = ServiceCharge.create(soapCharge);
				avFare.addCharge(charge);			
			}	
		} catch (Exception e) {

		}
			
		double amount = 0;
		try {
			amount = soapPaxFare.find("EquivFare").getAttributeValueAsDouble("Amount");	
		} catch (Exception e) {
			amount = soapPaxFare.find("BaseFare").getAttributeValueAsDouble("Amount");	
		}
		ServiceCharge charge = ServiceCharge.create("FarePrice","FarePrice","FarePrice","FarePrice",amount);
		avFare.addCharge(charge);
		
		if( !paxType.equals("INF") && upSelling.getValue() > 0 )
		{			
			double upsellingValue = 0;
			if(upSelling.getType().equals(Upselling.Type.PERCENTAGE))
				upsellingValue = amount * upSelling.getValue() / 100;
			else
				upsellingValue = upSelling.getValue();
			
			ServiceCharge markedUp = ServiceCharge.create("MarkedUpPrice","MarkedUpPrice","MarkedUpPrice","MarkedUpPrice",upsellingValue);
			avFare.addCharge(markedUp);
		}
		
		return avFare;
	}
}
