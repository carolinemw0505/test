package com.dco.eureka.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ApplicationDcoEurekaClient {
	public static void main(String[] args){
		SpringApplication.run(ApplicationDcoEurekaClient.class, args);
	}
}
