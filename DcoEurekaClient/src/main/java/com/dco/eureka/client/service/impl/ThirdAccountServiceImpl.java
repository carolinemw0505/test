package com.dco.eureka.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dco.eureka.client.dao.ThirdAccountMapper;
import com.dco.eureka.client.model.ThirdAccount;
import com.dco.eureka.client.service.ThirdAccountService;

@Service
public class ThirdAccountServiceImpl implements ThirdAccountService{

	@Autowired
	ThirdAccountMapper thirdAccountMapper;
	
	@Override
	public int findCount(){
		return thirdAccountMapper.findCount();
	}
	
	@Override
	public int insert(ThirdAccount t){
		return thirdAccountMapper.insert(t);
	}
	
	@Override
	public ThirdAccount query(int tId){
		return thirdAccountMapper.query(tId);
	}
	
	@Override
	public int queryCountByThirdUserId(String thirdUserId){
		return thirdAccountMapper.queryCountByThirdUserId(thirdUserId);
	}
	
	@Override
	public int updateThirdAccountByUserId(ThirdAccount t){
		return thirdAccountMapper.updateThirdAccountByUserId(t);
	}
	
	@Override
	public ThirdAccount queryByThirdUserId(String userId){
		return thirdAccountMapper.queryByThirdUserId(userId);
	}
	
	@Override
	public int insertAndUpdate(ThirdAccount t){
		return thirdAccountMapper.insertAndUpdate(t);
	}
}
