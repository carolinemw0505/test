package com.dco.eureka.client.platform.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dco.eureka.client.common.Const;
import com.dco.eureka.client.common.GlobalVariables;
import com.dco.eureka.client.model.ThirdAccount;
import com.dco.eureka.client.model.ThirdBasicInfo;
import com.dco.eureka.client.model.ThirdHotPubInfo;
import com.dco.eureka.client.service.ThirdAccountService;
import com.dco.eureka.client.service.ThirdBasicInfoService;
import com.dco.eureka.client.service.ThirdHotPubInfoService;
import com.dco.eureka.client.service.ThirdStateService;
import com.dco.eureka.client.utils.HttpClientUtils;
import com.dco.eureka.client.utils.Utils;

public class PlatformGithub extends PlatformPropertyAbstract{

	@Autowired
	ThirdStateService thirdStateService;
	@Autowired
	ThirdAccountService thirdAccountService;
	@Autowired
	ThirdHotPubInfoService thirdHotPubInfoService;
    @Autowired
	ThirdBasicInfoService thirdBasicInfoService;
	
	
	private static final Logger log=LoggerFactory.getLogger(PlatformGithub.class);

	
	@Override
	public String getOauthUrl() {
		String url=Const.GITHUBURL;
		String state=thirdStateService.GenState();
			//认证时需传递的参数response_type、client_id、state、redirect_uri
		String param="response_type=code&"+"client_id="+GlobalVariables.GITHUB_CLIENT_ID+"&state="+state
					+"&redirect_uri="+GlobalVariables.GITHUB_CALLBACK_URL;
		return url+"?"+param;
	}

	@Override
	public String getUserInfo(String type,String code) throws Exception{
        JSONObject resultJson=null;
        String resultInfo="";
		//github获取token地址
		String getTokenUrl=Const.GITHUBTOKEN;
		//github获取token参数
		String getTokenParam="grant_type=authorization_code&code="+code+"&client_id="+GlobalVariables.GITHUB_CLIENT_ID+"&client_secret="+GlobalVariables.GITHUB_CLIENT_SECRET;
		//github想申请token的URL发送post请求
	    /*
	     * github:tokenResult示例：
	     * 失败：error=incorrect_client_credentials&error_description=The+client_id+and%2For+client_secret+passed+are+incorrect.&
	     * error_uri=https%3A%2F%2Fdeveloper.github.com%2Fapps%2Fmanaging-oauth-apps%2Ftroubleshooting-oauth-app-access-token-request-errors%2F%23incorrect-client-credentials
	     * 成功：access_token=9p76186g67e26d6309654c2bcc16lk5e41bac9c61&scope=&token_type=bearer
	     */
    	 String tokenResult=HttpClientUtils.sendPost(getTokenUrl, getTokenParam,false);
		 Map<String,String> resultMap=HttpClientUtils.param2Map(tokenResult);
						
		 if(resultMap.containsKey("error")){
			throw new Exception(resultMap.get("error_description"));
		 }
						
		 if(!resultMap.containsKey("access_token")){
			throw new Exception("token值获取失败!");
		 }
						
		 //github获取token
		 String access_token=resultMap.get("access_token");
		 //github获取token类型
		 String token_type=resultMap.get("token_type");
						
		 //github获取用户信息url
		 getTokenUrl=Const.GITHUBUSER;
		 //github获取用户信息参数
		 getTokenParam="token_type="+token_type+"&access_token="+access_token;
		 resultInfo=HttpClientUtils.sendGet(getTokenUrl, getTokenParam,false);
		 resultJson=JSONObject.parseObject(resultInfo);
						
		 log.info("resultInfo:"+resultInfo);
						
		 String userId=resultJson.getString("id");
		 ThirdAccount t=thirdAccountService.queryByThirdUserId(userId);
		 if(t==null) t=new ThirdAccount();
		 	t.setThirdUserId(userId);
			t.setThirdAccount(resultJson.getString("login"));
			t.setThirdType(Utils.getLoginType(type));
			t.setThirdToken(access_token);
			t.setCtTime(Utils.dateConvert(new Date()));
						
			thirdAccountService.insertAndUpdate(t);

			ThirdBasicInfo b=thirdBasicInfoService.queryByTid(t.getTid());
			if(b==null)
			   b=new ThirdBasicInfo();
			b.setTid(t.getTid());
			b.setName(t.getThirdAccount());
			b.setHomePage(resultJson.getString("html_url"));
			b.setPubCount(resultJson.getInteger("public_repos"));
			b.setFanCount(resultJson.getInteger("followers"));
			b.setWatchCount(resultJson.getInteger("following"));
						
			String getStr=HttpClientUtils.sendGet(resultJson.getString("starred_url")
			    			.replaceAll("[{]/[a-zA-Z_]{0,}[}]", ""),"",false);
						
			JSONArray jr=JSONArray.parseArray(getStr);
			b.setStarCount(jr.size());
			getStr=HttpClientUtils.sendGet(resultJson.getString("subscriptions_url")
					.replaceAll("[{]/[a-zA-Z_]{0,}[}]", ""), "",false);
			jr=JSONArray.parseArray(getStr);
			b.setReadCount(jr.size());
			getStr=HttpClientUtils.sendGet(resultJson.getString("events_url")
					.replaceAll("[{]/[a-zA-Z_]{0,}[}]", ""), "",false);
			jr=JSONArray.parseArray(getStr);
			b.setCommentCount(jr.size());
			getStr=HttpClientUtils.sendGet(resultJson.getString("received_events_url")
					.replaceAll("[{]/[a-zA-Z_]{0,}[}]", ""),"",false);
			jr=JSONArray.parseArray(getStr);
			b.setReplyCount(jr.size());
		    b.setSharedCount(0);
			b.setRegTime(resultJson.getString("created_at"));
						
			thirdBasicInfoService.insertIntoThirdBasicInfo(b);
						
			thirdHotPubInfoService.deleteByTid(t.getTid());
			getStr=HttpClientUtils.sendGet(resultJson.getString("repos_url")
					.replaceAll("[{]/[a-zA-Z_]{0,}[}]", ""),"",false);
			jr=JSONArray.parseArray(getStr);
			int size=jr.size()>GlobalVariables.hotReposCount?GlobalVariables.hotReposCount:jr.size();
						
			List<ThirdHotPubInfo> list=new ArrayList<ThirdHotPubInfo>(size);
			ThirdHotPubInfo h=null;
			JSONObject cj=null;
			String getString ="";
			JSONArray jJson=null;
						
			for(int i=0;i<size;i++){
				cj=jr.getJSONObject(i);
				h=new ThirdHotPubInfo();
				h.setTid(t.getTid());
				h.setTitle(cj.getString("name"));
				h.setContent(HttpClientUtils.sendGet(cj.getString("contents_url").replaceAll("[{](.){1,}[}]", ""), "",false));
				h.setLinkAddr(cj.getString("html_url"));
				getString=HttpClientUtils.sendGet(cj.getString("comments_url").replaceAll("[{](.){1,}[}]",""),"",false);
				jJson=JSONArray.parseArray(getString);
				h.setCommentCount(jJson.size());
				h.setForkCount(Integer.parseInt(cj.getString("forks_count")));
				h.setWatchCount(Integer.parseInt(cj.getString("watchers_count")));
				h.setStarCount(Integer.parseInt(cj.getString("stargazers_count")));
				h.setSharedCount(0);
				h.setPubTime(cj.getString("created_at"));
				list.add(h);
			}
						
			if(size>0){
				thirdHotPubInfoService.insertBatchThirdHotPubInfo(list);
			}
	    	String	result=resultJson.getString("id");
		    //return resultJson;
	    	return result;
	}

}
