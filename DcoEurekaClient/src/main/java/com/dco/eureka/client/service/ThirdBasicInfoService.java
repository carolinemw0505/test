package com.dco.eureka.client.service;

import java.util.Map;

import com.dco.eureka.client.model.ThirdBasicInfo;


public interface ThirdBasicInfoService {
	public ThirdBasicInfo queryByTid(int tid);
	public int insertIntoThirdBasicInfo(ThirdBasicInfo t);
	public Map<String,Object> getThirdCountByUserId(int userId);
	public Map<String,Object> getThirdCountByThirdAccountId(String thirdAccountId);
}
