package com.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.model.UserAccount;
import com.service.ThirdAccountService;
import com.service.UserAccountService;

@Controller
public class UserController {

    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private ThirdAccountService thirdAccountService;

    @RequestMapping(value="/hello.json")
    public String find(HttpServletRequest request)
    {
        String age=userAccountService.findAge("13");
        
        UserAccount u=new UserAccount();
        
        u.setAccount("testAccount");
        u.setCtTime("2018-08-18 00:05:30");
        u.setEmail("6279380@qq.com");
        u.setMdTime("2018-08-05 08:30:20");
        u.setPassword("123456");
        u.setPhone("13617161716");
        u.setStatus(0);
        
        
        System.out.println("insUserId:"+u.getUserId());
        
//        u.setUserId(insUserId);
        
        u.setAccount("testAccountUpdate");
        
        u.setPassword("0000000000000000000");
        
        int upUser=userAccountService.updateUser(u);
        
        System.out.println(upUser);
        
        System.out.println("userService:"+age);//如果实验成功，在控制台会打印年龄25
        System.out.println("thirdAccountService:"+thirdAccountService.findCount());
        return "index";
    }
}