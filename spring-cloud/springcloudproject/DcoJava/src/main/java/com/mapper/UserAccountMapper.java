package com.mapper;

import com.model.UserAccount;

public interface UserAccountMapper {
    String findAge(String id);
    int insertUser(UserAccount u);
    int updateUser(UserAccount u);
}