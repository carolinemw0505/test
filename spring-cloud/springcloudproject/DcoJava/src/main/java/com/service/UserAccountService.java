package com.service;

import com.model.UserAccount;

public interface UserAccountService {
    String findAge(String id);
    
    int insertUser(UserAccount u);
    
    int updateUser(UserAccount u);
}