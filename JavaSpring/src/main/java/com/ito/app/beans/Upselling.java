package com.ito.app.beans;

public class Upselling {
	public static enum Type {
		AMOUNT("amount"), PERCENT("percentage");
		
		private final String value;
		
		private Type(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}
	
	private String airline;
	private double value;
	private Type type;

	public Upselling()
	{
		this.type = Type.AMOUNT;
		this.value = 0;
		this.airline = "";
	}
	
	public Upselling(
			String airline,
			double value,
			String type)
	{
		if(type.equalsIgnoreCase(Type.PERCENT.value))
			this.type = Type.PERCENT;
		else
			this.type = Type.AMOUNT;	
		this.value = value;
		this.airline = airline;
	}
	
	public String getAirline() {
		return airline;
	}
	
	public double getValue() {
		return value;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setAirline(String airline) {
		this.airline = airline;
	}
	
	public void setType(String type) {
		if(type.equalsIgnoreCase(Type.PERCENT.value))
			this.type = Type.PERCENT;
		else
			this.type = Type.AMOUNT;		
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
}
