package com.dco.eureka.client.dao;

import java.util.Map;

import com.dco.eureka.client.model.ThirdBasicInfo;


public interface ThirdBasicInfoMapper {
	public ThirdBasicInfo queryByTid(int tid);
	public int insertAndUpdate(ThirdBasicInfo t);
	public Map<String,Object> getThirdCountByUserId(int userId);
	public Map<String, Object> getThirdCountByThirdAccountId(String thirdAccountId);
}
