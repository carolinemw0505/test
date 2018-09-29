package com.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapper.ThirdHotPubInfoMapper;
import com.model.ThirdHotPubInfo;

@Service
@Transactional
public class ThirdHotPubInfoServiceImpl implements ThirdHotPubInfoService{

	@Resource
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
