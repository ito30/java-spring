package com.ito.config;

import com.google.gson.Gson;
import com.ito.config.bean.Config;
import com.ito.config.bean.Environment;
import com.ito.config.bean.EnvironmentTarget;
import com.snail.core.util.FileUtil;

public class AppConfig {
	
	private AppConfig() {}
	
	public static Config initConfig(String... args) {
		Gson gson = new Gson();
		String json = FileUtil.read(args[0]);
		
		Config config = gson.fromJson(json, Config.class);
		EnvironmentTarget envTarget = config.getEnv_target();
		
		for (Environment env : envTarget.getEnv_list()) {
			if (envTarget.getTarget().equals(env.getType())) {
				config.getEnv_target().setEnv(env);
			}
		}
		
		return config;
	}
}
