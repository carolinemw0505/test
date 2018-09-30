package com.dco.eureka.client.dao;

import com.dco.eureka.client.model.ThirdState;

public interface ThirdStateMapper {
	public int save(ThirdState t);
	public int query(String state);
	public int delete(String state);
}
