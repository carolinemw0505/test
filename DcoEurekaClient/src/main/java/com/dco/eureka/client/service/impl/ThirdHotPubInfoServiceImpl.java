package com.dco.eureka.client.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dco.eureka.client.dao.ThirdHotPubInfoMapper;
import com.dco.eureka.client.model.ThirdHotPubInfo;
import com.dco.eureka.client.service.ThirdHotPubInfoService;

@Service
public class ThirdHotPubInfoServiceImpl implements ThirdHotPubInfoService{

	@Autowired
	ThirdHotPubInfoMapper thirdHotPubInfoMapper;
	
	
	@Override
	public int insertBatchThirdHotPubInfo(List<ThirdHotPubInfo> list) {
		return thirdHotPubInfoMapper.insertBatcchThirdHotPubInfo(list);
	}
	
	@Override
	public int deleteByTid(int tid){
		return thirdHotPubInfoMapper.deleteByTid(tid);
	}

	@Override
	public List<Map<String, Object>> queryHotPubByTid(int tid) {
		return thirdHotPubInfoMapper.queryHotPubByTid(tid);
	}
}
