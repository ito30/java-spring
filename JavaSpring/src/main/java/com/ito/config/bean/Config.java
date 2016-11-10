package com.ito.config.bean;

public class Config {
	
	private Server server;
	private EnvironmentTarget env_target;
	
	public Server getServer() {
		return server;
	}
	
	public EnvironmentTarget getEnv_target() {
		return env_target;
	}
	
	public void setEnv_target(EnvironmentTarget airline) {
		this.env_target = airline;
	}
	
	public void setServer(Server server) {
		this.server = server;
	}
}
