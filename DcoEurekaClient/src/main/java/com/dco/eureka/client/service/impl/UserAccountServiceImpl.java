package com.dco.eureka.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dco.eureka.client.dao.UserAccountMapper;
import com.dco.eureka.client.model.UserAccount;
import com.dco.eureka.client.service.UserAccountService;

@Service
public class UserAccountServiceImpl implements UserAccountService{   
    @Autowired
    public UserAccountMapper userAccountMapper;
    @Override
    public String findAge(String id) {
        String age =userAccountMapper.findAge(id);
        return age;
    }
    
    public int insertUser(UserAccount u){
    	return userAccountMapper.insertUser(u);
    }
    
    public int updateUser(UserAccount u){
    	return userAccountMapper.updateUser(u);
    }

}