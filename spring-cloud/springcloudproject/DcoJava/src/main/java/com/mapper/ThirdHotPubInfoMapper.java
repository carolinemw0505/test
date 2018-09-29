package com.mapper;

import java.util.List;
import java.util.Map;

import com.model.ThirdHotPubInfo;

public interface ThirdHotPubInfoMapper {
	public int insertBatcchThirdHotPubInfo(List<ThirdHotPubInfo> list);
	public int deleteByTid(int tid);
	public List<Map<String,Object>> queryHotPubByTid(int tid);
}
