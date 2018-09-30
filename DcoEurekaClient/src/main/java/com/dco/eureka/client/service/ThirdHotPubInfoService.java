package com.dco.eureka.client.service;

import java.util.List;
import java.util.Map;

import com.dco.eureka.client.model.ThirdHotPubInfo;


public interface ThirdHotPubInfoService {
	public int insertBatchThirdHotPubInfo(List<ThirdHotPubInfo> list);
	public int deleteByTid(int tid);
	public List<Map<String,Object>> queryHotPubByTid(int tid);
}
