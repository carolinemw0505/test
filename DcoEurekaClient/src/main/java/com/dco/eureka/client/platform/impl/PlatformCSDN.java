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

public class PlatformCSDN extends PlatformPropertyAbstract{

	@Autowired
	ThirdStateService thirdStateService;
	@Autowired
	ThirdAccountService thirdAccountService;
	@Autowired
	ThirdHotPubInfoService thirdHotPubInfoService;
	@Autowired
	ThirdBasicInfoService thirdBasicInfoService;
	
	private static final Logger log=LoggerFactory.getLogger(PlatformPropertyAbstract.class);

	
	@Override
	public String getOauthUrl() {
		//生成并保存state,忽略该参数有可能导致csrf攻击
		String url=Const.CSDNURL;
		String param="client_id="+GlobalVariables.CSDN_CLIENT_ID+"&response_type=code"+
						"&redirect_uri="+GlobalVariables.CSDN_CALLBACK_URL;
		return url+"?"+param;
	}

	@Override
	public JSONObject getUserInfo(String type,String code) throws Exception{
		String resultInfo="";
		JSONObject resultJson=null;
		//slack获取用户token地址url
		String getTokenUrl=Const.SLACKTOKEN;
		//slack获取用户token参数
		String getTokenParam="scope="
				+Const.SLACKUSERFIELDBASIC
				+","				
				+Const.SCOPEFILESREAD
				+","
				+Const.SCOPEREACTIONSREAD
				+","
				+Const.SCOPESTARREAD
				+","
				+Const.SCOPEUSERSREAD		
				+","
				+Const.SCOPEREAD
				+","
				+Const.SCOPEREMINDER
				+","
				+Const.SCOPEUSERSPROFILEREAD
				+"&client_id="+GlobalVariables.SLACK_CLIENT_ID
				+"&client_secret="+GlobalVariables.SLACK_CLIENT_SECRET
				+"&code="+code;
						
    		    /*
    		     * slack:tokenResult示例:
		    	 * 成功：{"ok":true,"access_token":"xoxp-409327576148-409014565169-412514688132-65c39c5cff9c8eebb3af238a6b6008df",
		    	 * "scope":"identity.basic","user":{"name":"Ceili","id":"UC10EGM4Z"},
		    	 * "team":{"id":"TC19MGY4C"}}
		    	 * 失败:{"ok":false,"error":"invalid_code"}
		    	 */
				String tokenResult=HttpClientUtils.send(getTokenUrl, getTokenParam);
				JSONObject rj=JSONObject.parseObject(tokenResult);
				Map<String,Object> rm=rj;
				if(rm.containsKey("error")){
					throw new Exception(rm.get("error").toString());
				}
						
				if(!rm.containsKey("access_token")){
					throw new Exception("token值获取失败!");
				}
				String access_token=rm.get("access_token").toString();
						
				//slack获取用户信息url
				getTokenUrl=Const.SLACKUSER;
                //getTokenUrl=Const.SLACKSTARSLIST;

				getTokenParam="token="+access_token;
						
				resultInfo=HttpClientUtils.send(getTokenUrl, getTokenParam);
						
				resultJson=JSONObject.parseObject(resultInfo);
						
				String userId=resultJson.getJSONObject("user").getString("id");
				ThirdAccount t=thirdAccountService.queryByThirdUserId(userId);
				if(t==null) t=new ThirdAccount();
				t.setThirdUserId(userId);
				t.setThirdAccount(resultJson.getJSONObject("user").getString("name"));
				t.setThirdType(Utils.getLoginType(type));
				t.setThirdToken(access_token);
				t.setCtTime(Utils.dateConvert(new Date()));
						
				thirdAccountService.insertAndUpdate(t);
										
				log.info("resultInfo:"+resultInfo);
						
				ThirdBasicInfo b=thirdBasicInfoService.queryByTid(t.getTid());
				if(b==null)b=new ThirdBasicInfo();
				b.setTid(t.getTid());
				b.setName(resultJson.getJSONObject("user").getString("name"));
				b.setHomePage("");
				b.setFanCount(0);
				b.setWatchCount(0);

				getTokenUrl=Const.SLACKSTARSLIST;
				String bInfo=HttpClientUtils.send(getTokenUrl, getTokenParam);
				JSONObject bJson=JSONObject.parseObject(bInfo);
                //b.setStarCount(bJson.getJSONObject("paging").getIntValue("total"));
				b.setStarCount(bJson.getJSONArray("items").size());
				b.setReadCount(0);
				b.setCommentCount(0);
				getTokenUrl=Const.SLACKREACTIONSLIST;
				bInfo=HttpClientUtils.send(getTokenUrl, getTokenParam);
				bJson=JSONObject.parseObject(bInfo);
                //b.setReplyCount(bJson.getJSONObject("paging").getIntValue("total"));
				b.setReplyCount(bJson.getJSONArray("items").size());
				b.setSharedCount(0);
				b.setRegTime(null);
				getTokenUrl=Const.SLACKFILESLIST;
				bInfo=HttpClientUtils.send(getTokenUrl, getTokenParam);
				bJson=JSONObject.parseObject(bInfo);
				b.setPubCount(bJson.getJSONObject("paging").getIntValue("total"));

				thirdBasicInfoService.insertIntoThirdBasicInfo(b);

				thirdHotPubInfoService.deleteByTid(t.getTid());
				JSONArray pl=bJson.getJSONArray("files");
				int size=pl.size()>GlobalVariables.hotReposCount?GlobalVariables.hotReposCount:pl.size();
				List<ThirdHotPubInfo> hl=new ArrayList<ThirdHotPubInfo>(size);
				ThirdHotPubInfo h=null;
				JSONObject c=new JSONObject();
				for(int i=0;i<size;i++){
					c=pl.getJSONObject(i);
					h=new ThirdHotPubInfo();
					h.setTid(t.getTid());
					h.setTitle(c.getString("title"));
					h.setContent(c.getString("preview"));
					h.setLinkAddr(c.getString("url_private"));
					h.setCommentCount(c.getIntValue("comments_count"));
					h.setWatchCount(0);
					h.setStarCount(0);
					h.setForkCount(0);
					h.setSharedCount(0);
					h.setPubTime(Utils.dateConvert(c.getDate("timestamp")));
					hl.add(h);
				}
						
				if(size>0){
					thirdHotPubInfoService.insertBatchThirdHotPubInfo(hl);
				}
						
			return resultJson;
	}
}
