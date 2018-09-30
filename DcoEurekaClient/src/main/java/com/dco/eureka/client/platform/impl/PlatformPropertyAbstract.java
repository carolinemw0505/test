package com.dco.eureka.client.platform.impl;

import java.util.Map;

import com.dco.eureka.client.platform.PlatformInterface;

public abstract class PlatformPropertyAbstract implements PlatformInterface{
	private Map<String,Object> paramValue;
	
	@Override
	public void setParamMap(Map<String,Object> paramValue){
		this.paramValue=paramValue;
	}
	
	@Override
	public Map<String,Object> getParamMap(){
		return paramValue;
	}
}
