package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapper.ThirdAccountMapper;
import com.model.ThirdAccount;

@Service
@Transactional
public class ThirdAccountServiceImpl implements ThirdAccountService{

	@Resource
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
