
package com.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.Const;
import com.common.GlobalVariables;
import com.service.ThirdBasicInfoService;
import com.service.ThirdHotPubInfoService;
import com.service.ThirdStateService;
import com.util.AuthUtils;

@Controller
public class AuthApiController {
		
	@Autowired
	ThirdStateService thirdStateService;
	@Autowired
	ThirdBasicInfoService thirdBasicInfoService;
	@Autowired
	ThirdHotPubInfoService thirdHotPubInfoService;
	
	private static final Logger logger=LoggerFactory.getLogger(AuthApiController.class);
	
	@RequestMapping(value="/api/user_mgr/authLogin.json")
	public void githubLogin(HttpServletResponse response,String auth_type) throws Exception{
		//根据auth_type确定第三方登录认证地址
		String url="";
		String param="";
		//生成并保存state,忽略该参数有可能导致csrf攻击
		String state=thirdStateService.GenState();
		
		
		switch (auth_type) {
			case "github":{
				url=Const.GITHUBURL;
				//认证时需传递的参数response_type、client_id、state、redirect_uri
				param="response_type=code&"+"client_id="+GlobalVariables.GITHUB_CLIENT_ID+"&state="+state
						+"&redirect_uri="+GlobalVariables.GITHUB_CALLBACK_URL;
				break;
			}
			case "slack":{
				url=Const.SLACKURL;
				param="scope="
						+Const.SLACKUSERFIELDBASIC
//						+","
//						+Const.SCOPEFILESREAD
//						+","
//						+Const.SCOPEREACTIONSREAD
//						+","
//                        +Const.SCOPEUSERSREAD
//						+","
//						+Const.SCOPESTARREAD
//						+","
//						+Const.SCOPEREMINDER
//						+","
//						+Const.SCOPEUSERSPROFILEREAD
						+"&client_id="+GlobalVariables.SLACK_CLIENT_ID+"&redirect_uri="+GlobalVariables.SLACK_CALLBACK_URL
						+"&state="+state;
				break;
			}
			case "reddit":{
				url=Const.REDDITURL;
				param="client_id="+GlobalVariables.REDDIT_CLIENT_ID+"&response_type="+Const.REDDITRESPONSE_TYPE
						+"&state="+state+"&redirect_uri="+GlobalVariables.REDDIT_CALLBACK_URL+"&duration="+Const.REDDITDURATIONTEMPORARY
//						+"&scope="+Const.REDDITSCOPEIDENTITY;
						+"&scope=*";
				break;
			}
			case "csdn":{
				url=Const.CSDNURL;
				param="client_id="+GlobalVariables.CSDN_CLIENT_ID+"&response_type=code"+
						"&redirect_uri="+GlobalVariables.CSDN_CALLBACK_URL;
				break;
			}
			case "linkedin":{
				url=Const.LINKEDINOAUTHURL;
				param="response_type="+Const.LINKEDINRESPONSECODE+"&client_id="+GlobalVariables.LINKEDIN_CLIENT_ID
						+"&redirect_uri="+GlobalVariables.LINKEDIN_CALLBACK_URL
						+"&state="+state+"&scope="+Const.LINKEDINBASICPROFILE;
			}
			default:
				break;
		};
		
		//向第三方认证服务器发送请求
		response.sendRedirect(url+"?"+param);
	}
	
	@RequestMapping(value="/api/user_mgr/oauth2/{type}/callback.json")
	@ResponseBody
	public String githubCallback(@PathVariable String type,String code, String state, HttpServletResponse response) throws Exception {
			String result="";
			// 验证state的值，如果和登录生成的不一致，防止被CSRF攻击
		    if(!type.equalsIgnoreCase("csdn") && !thirdStateService.CheckLoginState(state)) {
		        throw new Exception("和调用登录服务产生的State代码不一致，验证失败");
		    }
		    
		    JSONObject userJson=AuthUtils.getUserInfo(type, code);
		    JSONObject r=new JSONObject();
		    
		    switch (type) {
		    	case "github":{
		    		result=userJson.getString("id");
		    		break;
		    	}
		    	case "slack":{
		    		result=userJson.getJSONObject("user").getString("id");
		    		break;
		    	}
		    	case "reddit":{
		    		result=userJson.getJSONObject("user").getString("id");
		    		break;
		    	}
		    	case "csdn":{
		    		result=userJson.getString("username");
		    		break;
		    	}
		    	case "linkedin":{
		    		result=userJson.getString("id");
		    		break;
		    	}
		    	default:
		    		break;
		    }
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
			logger.error(result);
		}
		return result;
	}
	
}
