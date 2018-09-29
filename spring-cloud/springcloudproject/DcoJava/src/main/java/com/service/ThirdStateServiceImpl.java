package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapper.ThirdStateMapper;
import com.model.ThirdState;
import com.util.Utils;

@Service
@Transactional
public class ThirdStateServiceImpl implements ThirdStateService{
	@Resource
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
