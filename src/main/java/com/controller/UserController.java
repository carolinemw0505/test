package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.model.User;
import com.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    
    private static String GITHUB_CLIENT_ID="";
    private static String GITHUB_CLIENT_SECRET="";

    @RequestMapping(value="/hello.do")
    public String find(HttpServletRequest request)
    {
        String age=userService.findAge("1");
        System.out.println(age);//如果实验成功，在控制台会打印年龄25
        return "index";
    }
    
    
}