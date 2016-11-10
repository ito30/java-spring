package com.ito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.ito.config.AppConfig;
import com.ito.config.bean.Config;

@EnableAutoConfiguration
@ComponentScan
public class App {
	
	public static Config config;
	
	public static void main(String[] args) {
		config = AppConfig.initConfig(args);
		
		SpringApplication.run(App.class, args);
	}
} 
