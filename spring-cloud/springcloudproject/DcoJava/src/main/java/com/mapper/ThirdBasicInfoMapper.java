package com.mapper;

import java.util.Map;

import com.model.ThirdBasicInfo;

public interface ThirdBasicInfoMapper {
	public ThirdBasicInfo queryByTid(int tid);
	public int insertAndUpdate(ThirdBasicInfo t);
	public Map<String,Object> getThirdCountByUserId(int userId);
	public Map<String, Object> getThirdCountByThirdAccountId(String thirdAccountId);
}
