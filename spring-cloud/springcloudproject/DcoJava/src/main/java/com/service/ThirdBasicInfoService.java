package com.service;

import java.util.Map;

import com.model.ThirdBasicInfo;

public interface ThirdBasicInfoService {
	public ThirdBasicInfo queryByTid(int tid);
	public int insertIntoThirdBasicInfo(ThirdBasicInfo t);
	public Map<String,Object> getThirdCountByUserId(int userId);
	public Map<String,Object> getThirdCountByThirdAccountId(String thirdAccountId);
}
