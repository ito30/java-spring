package com.ito.config.bean;

import java.util.List;

public class Environment {
	public static enum Type {
		UAT, PROD
	}
	
	private Type type;
	private String url;
	private List<String> airlines;
	private Account account;
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public Account getAccount() {
		return account;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public List<String> getAirlines() {
		return airlines;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setAirlines(List<String> airlines) {
		this.airlines = airlines;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
}
