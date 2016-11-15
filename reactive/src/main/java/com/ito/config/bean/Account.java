package com.ito.config.bean;

public class Account {
	private String epr;
	private String ipcc;
	private String password;
	
	public void setEpr(String epr) {
		this.epr = epr;
	}
	
	public void setIpcc(String ipcc) {
		this.ipcc = ipcc;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEpr() {
		return epr;
	}
	
	public String getIpcc() {
		return ipcc;
	}
	
	public String getPassword() {
		return password;
	}
}
