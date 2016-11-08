package com.ito.app.beans;

import java.util.List;

public class NegotiatedFareCode {
	private String code;
	private List<String> suppliers;
	
	public NegotiatedFareCode(String code, List<String> suppliers) {
		this.code = code;
		this.suppliers = suppliers;
	}
	
	public String getCode() {
		return code;
	}
	
	public List<String> getSuppliers() {
		return suppliers;
	}
}
