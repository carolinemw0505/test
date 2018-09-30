package com.dco.eureka.client.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dco.eureka.client.common.Const;
import com.dco.eureka.client.platform.PlatformInterface;
import com.dco.eureka.client.platform.factory.impl.PlatformFactory;
import com.dco.eureka.client.service.ThirdBasicInfoService;
import com.dco.eureka.client.service.ThirdHotPubInfoService;
import com.dco.eureka.client.service.ThirdStateService;

@RestController
public class DcoEurekaController {

	private static final Logger log=LoggerFactory.getLogger(DcoEurekaController.class);
	
	@Autowired
	ThirdBasicInfoService thirdBasicInfoService;
	@Autowired
	ThirdHotPubInfoService thirdHotPubInfoService; 
	@Autowired
	ThirdStateService thirdStateService;
	
	
	@RequestMapping(value="/api/user_mgr/authLogin.json")
	public void githubLogin(HttpServletResponse response,String auth_type) throws Exception{
		//创建三方平台登录类
		PlatformInterface f=new PlatformFactory().createPlatform(auth_type); 
		//根据auth_type确定第三方登录认证地址
		String redirectUrl=f.getOauthUrl();
		//向第三方认证服务器发送请求
		response.sendRedirect(redirectUrl);
	}
	
	@RequestMapping(value="/api/user_mgr/oauth2/{type}/callback.json")
	@ResponseBody
	public String githubCallback(@PathVariable String type,String code, String state, HttpServletResponse response) throws Exception {
			String result="";
			// 验证state的值，如果和登录生成的不一致，防止被CSRF攻击
		    if(!type.equalsIgnoreCase("csdn") && !thirdStateService.CheckLoginState(state)) {
		        throw new Exception("和调用登录服务产生的State代码不一致，验证失败");
		    }
		    JSONObject r=new JSONObject();
		    //创建三方平台登录类
		    PlatformInterface p=new PlatformFactory().createPlatform(type);
		    result=p.getUserInfo(type, code);
		    r.put("userId", result);
		    return r.toString();

	  } 
	
	@RequestMapping(value="/api/user_mgr/getUserInfo.json",produces="application/json;charset=UTF-8",method=RequestMethod.POST)
	@ResponseBody
	public String getUserInfo(String paraName,String paraValue,HttpServletResponse response){
		String result="{}";
		int uId=-1;
		JSONObject r=null;
		try{
			Map<String,Object> uMap=null;
			if(paraName.equalsIgnoreCase(Const.PARANAMEUSERID)){
				uId=Integer.parseInt(paraValue);
				uMap=thirdBasicInfoService.getThirdCountByUserId(uId);
			}else if(paraName.equalsIgnoreCase(Const.PARANAMETHIRDACCOUNTID)){
				uMap=thirdBasicInfoService.getThirdCountByThirdAccountId(paraValue);
			}
			if(uMap!=null){
				int tid=Integer.parseInt(uMap.get("tid").toString());
				uMap.remove("tid");
				r=JSONObject.parseObject(JSON.toJSONString(uMap));
				List<Map<String,Object>> hl=thirdHotPubInfoService.queryHotPubByTid(tid);
				JSONArray jr=new JSONArray();
				if(hl.size()>0){
					jr=JSONArray.parseArray(JSON.toJSONString(hl));
				}
				r.put("hotPubList", jr.toString());
				result=r.toString();
			}
		}catch(NumberFormatException e){
			r=new JSONObject();
			r.put("error", "userId为正整数,请输入正确的userId:"+e.getMessage());
			result=r.toString();
			log.error(result);
		}
		return result;
	}

}
