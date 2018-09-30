package com.dco.eureka.client.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dco.eureka.client.dao.ThirdBasicInfoMapper;
import com.dco.eureka.client.model.ThirdBasicInfo;
import com.dco.eureka.client.service.ThirdBasicInfoService;

@Service
public class ThirdBasicInfoServiceImpl  implements ThirdBasicInfoService{

	@Autowired
	ThirdBasicInfoMapper thirdBasicInfoMapper;
	
	@Override
	public int insertIntoThirdBasicInfo(ThirdBasicInfo t) {
		return thirdBasicInfoMapper.insertAndUpdate(t);
	}
	
	
	@Override
	public ThirdBasicInfo queryByTid(int tid){
		return thirdBasicInfoMapper.queryByTid(tid);
	}


	@Override
	public Map<String, Object> getThirdCountByUserId(int userId) {
		return thirdBasicInfoMapper.getThirdCountByUserId(userId);
	}
	
	@Override
	public Map<String,Object> getThirdCountByThirdAccountId(String thirdAccountId){
		return thirdBasicInfoMapper.getThirdCountByThirdAccountId(thirdAccountId);
	}
}
