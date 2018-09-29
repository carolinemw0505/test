package com.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapper.ThirdBasicInfoMapper;
import com.model.ThirdBasicInfo;

@Service
@Transactional
public class ThirdBasicInfoServiceImpl  implements ThirdBasicInfoService{

	@Resource
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
