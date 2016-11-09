package com.ito.bean;

import com.snail.core.beans.Deliverable;
import com.snail.core.beans.DeliveryMap;

public class AbacusBookingCodeInfo implements Deliverable {

	private String flightNumber;
	private String bookingCode;
	
	public AbacusBookingCodeInfo(String flightNumber, String bookingCode) {
		super();
		this.flightNumber = flightNumber;
		this.bookingCode = bookingCode;		
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getBookingCode() {
		return bookingCode;
	}

	public void setBookingCode(String bookingCode) {
		this.bookingCode = bookingCode;
	}

	@Override
	public DeliveryMap deliver() {
		DeliveryMap map = new DeliveryMap();
		map.put("flight_number", flightNumber);		
		map.put("booking_code", bookingCode);		
		return map;
	}
}