package com.dco.eureka.client.service;

import com.dco.eureka.client.model.UserAccount;

public interface UserAccountService {
    String findAge(String id);
    
    int insertUser(UserAccount u);
    
    int updateUser(UserAccount u);
}