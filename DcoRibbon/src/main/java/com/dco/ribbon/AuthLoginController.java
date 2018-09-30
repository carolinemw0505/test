package com.dco.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class AuthLoginController {
	@Autowired
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod="failCallBack")
	public String AuthContent(){
		return restTemplate.getForObject("http://DCO-CONFIG-SERVER/", String.class);
	}
	
	public String failCallBack(){
		return "auth service callBack failure";
	}
}
