package com.ito.config;

public class SOAPConfig {
	private String epr;
	private String ipcc;
	private String password;
	private String uri;
	private String conversationId;
	
	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}
	
	public String getConversationId() {
		return conversationId;
	}
	
	public String getEpr() {
		return epr;
	}
	public void setEpr(String epr) {
		this.epr = epr;
	}
	
	public String getIpcc() {
		return ipcc;
	}
	
	public void setIpcc(String ipcc) {
		this.ipcc = ipcc;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUri() {
		return uri;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}
}
