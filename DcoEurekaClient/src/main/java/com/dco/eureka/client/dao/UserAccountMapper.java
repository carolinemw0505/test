package com.dco.eureka.client.dao;

import com.dco.eureka.client.model.UserAccount;

public interface UserAccountMapper {
    String findAge(String id);
    int insertUser(UserAccount u);
    int updateUser(UserAccount u);
}