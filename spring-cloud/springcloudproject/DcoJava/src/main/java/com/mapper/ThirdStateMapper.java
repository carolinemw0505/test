package com.mapper;

import com.model.ThirdState;

public interface ThirdStateMapper {
	public int save(ThirdState t);
	public int query(String state);
	public int delete(String state);
}
