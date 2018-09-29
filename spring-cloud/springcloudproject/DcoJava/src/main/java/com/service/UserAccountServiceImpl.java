package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapper.UserAccountMapper;
import com.model.UserAccount;

@Service
@Transactional
public class UserAccountServiceImpl implements UserAccountService{   
    @Resource
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