package com.snail.core.beans;

import com.snail.core.util.StringUtil;

public class TFlightDesignator {

	private String carrierCode;
	private String flightNumber;

	// SET CARRIER CODE
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode.trim();
	}

	// SET FLIGHT NUMBER
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber.trim();
	}

	// GET FLIGHT NUMBER
	public String getFlightNumber() {
		return flightNumber;
	}

	// GET CARRIER CODE
	public String getCarrierCode() {
		return carrierCode;
	}

	// TO STRING
	@Override
	public String toString() {
		return carrierCode + " " + flightNumber;
	}

	// TO CLEAN STRING
	public String toCleanString() {
		return carrierCode + flightNumber;
	}

	// EQUALS
	public boolean equals(TSegment segment) {
		return equals(segment.getDesignator());
	}

	// EQUALS
	public boolean equals(TFlightDesignator designator) {
		return StringUtil.isSame(designator.carrierCode, carrierCode)
				&& StringUtil.isSame(designator.flightNumber, flightNumber);
	}

	// ** FACTORY **

	public static TFlightDesignator create(String carrierCode,
			String flightNumber) {

		TFlightDesignator designator = new TFlightDesignator();
		designator.setCarrierCode(carrierCode);
		designator.setFlightNumber(flightNumber);

		return designator;
	}

	// CREATE
	public static TFlightDesignator create(String string) {
		String carrierCode = string.substring(0, 2);
		String flightNumber = string.substring(2);

		return create(carrierCode, flightNumber);
	}

	// REFORMAT
	public static String format(String string) {
		TFlightDesignator flight = create(string);
		return flight.toString();
	}

}
