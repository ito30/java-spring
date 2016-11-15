package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.ito.config.AppConfig;
import com.ito.config.bean.Config;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.ito")
public class ReactiveApplication {
	
	public static Config config;

	public static void main(String[] args) {
		config = AppConfig.initConfig(args);
		
		SpringApplication.run(ReactiveApplication.class, args);
	}
}
