package com.dco.eureka.client.service;

import com.dco.eureka.client.model.ThirdAccount;

public interface ThirdAccountService {
	public int findCount();
	public int insert(ThirdAccount t);
	public ThirdAccount query(int tId);
	public int queryCountByThirdUserId(String thirdUserId);
	public int updateThirdAccountByUserId(ThirdAccount t);
	public ThirdAccount queryByThirdUserId(String userId);
	public int insertAndUpdate(ThirdAccount t);
	
}
