package com.ito.config.bean;

import java.util.List;
import com.ito.config.bean.Environment.Type;

public class EnvironmentTarget {
	private Type target;
	private List<Environment> env_list;
	private Environment env;
	
	public void setEnv(Environment env) {
		this.env = env;
	}
	
	public void setEnv_list(List<Environment> env_list) {
		this.env_list = env_list;
	}
	
	public void setTarget(Type target) {
		this.target = target;
	}

	public Environment getEnv() {
		return env;
	}
	
	public List<Environment> getEnv_list() {
		return env_list;
	}
	
	public Type getTarget() {
		return target;
	}
}
