package com.ito.bean;

public class AbacusPriceQuote {
	private String passengerType;
	private int quantity;
	
	public AbacusPriceQuote(String passengerType, int quantity) {
		super();
		this.passengerType = passengerType;
		this.quantity = quantity;
	}
	public String getPassengerType() {
		return passengerType;
	}
	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
