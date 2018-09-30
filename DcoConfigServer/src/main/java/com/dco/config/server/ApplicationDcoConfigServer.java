package com.dco.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@EnableConfigServer
@RefreshScope
@SpringBootApplication
public class ApplicationDcoConfigServer {
	public static void main(String[] args){
		SpringApplication.run(ApplicationDcoConfigServer.class, args);
	}
}
