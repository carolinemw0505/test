package com.dco.eureka.client.service;

import com.dco.eureka.client.model.ThirdState;

public interface ThirdStateService {
	public int save(ThirdState t);
	public int query(String state);
	public String GenState();
	public boolean CheckLoginState(String state) ;
}
