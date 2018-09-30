package com.dco.eureka.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dco.eureka.client.dao.ThirdStateMapper;
import com.dco.eureka.client.model.ThirdState;
import com.dco.eureka.client.service.ThirdStateService;
import com.dco.eureka.client.utils.Utils;

@Service
public class ThirdStateServiceImpl implements ThirdStateService{
	@Autowired
	ThirdStateMapper thirdStateMapper;
	
	@Override
	public String GenState() {
		String result=Utils.genState();
		ThirdState t=new ThirdState();
		t.setState(result);
		thirdStateMapper.save(t);
		return result;
	}
	@Override
	public boolean CheckLoginState(String state) {
		boolean result=thirdStateMapper.query(state)>0?true:false;
		if(result)thirdStateMapper.delete(state);
		return result;
	}
	@Override
	public int save(ThirdState t) {
		return thirdStateMapper.save(t);
	}
	@Override
	public int query(String state) {
		return thirdStateMapper.query(state);
	}
}
