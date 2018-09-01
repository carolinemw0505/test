package com.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapper.UserMapper;
import com.model.User;
import com.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{   
    @Resource
    public UserMapper userMapper;
    @Override
    public String findAge(String id) {
        // TODO Auto-generated method stub
        String age =userMapper.findAge(id);
        return age;
    }

}