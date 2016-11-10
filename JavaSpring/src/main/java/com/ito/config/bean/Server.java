package com.ito.config.bean;

public class Server {
	private String instance;
	private String port;
	
	public void setInstance(String instance) {
		this.instance = instance;
	}
	
	public void setPort(String port) {
		this.port = port;
	}
	
	public String getInstance() {
		return instance;
	}
	
	public String getPort() {
		return port;
	}
}
