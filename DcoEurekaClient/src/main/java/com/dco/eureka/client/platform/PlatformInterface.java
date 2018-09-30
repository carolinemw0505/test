package com.dco.eureka.client.platform;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface PlatformInterface {
	public void setParamMap(Map<String,Object> paramMap);
	public Map<String,Object> getParamMap();
	public String getOauthUrl();
	public JSONObject getUserInfo(String type,String code) throws Exception;
}
