package com.dco.eureka.client.dao;

import java.util.List;
import java.util.Map;

import com.dco.eureka.client.model.ThirdHotPubInfo;

public interface ThirdHotPubInfoMapper {
	public int insertBatcchThirdHotPubInfo(List<ThirdHotPubInfo> list);
	public int deleteByTid(int tid);
	public List<Map<String,Object>> queryHotPubByTid(int tid);
}
