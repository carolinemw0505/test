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

public class PlatformLinkin extends PlatformPropertyAbstract{
	@Autowired
	ThirdStateService thirdStateService;
	@Autowired
    ThirdAccountService thirdAccountService;
    @Autowired
	ThirdHotPubInfoService thirdHotPubInfoService;
	@Autowired
    ThirdBasicInfoService thirdBasicInfoService;
	
	private static final Logger log=LoggerFactory.getLogger(PlatformLinkin.class);

	
	@Override
	public String getOauthUrl() {
		String state=thirdStateService.GenState();
		String url=Const.LINKEDINOAUTHURL;
		String param="response_type="+Const.LINKEDINRESPONSECODE+"&client_id="+GlobalVariables.LINKEDIN_CLIENT_ID
						+"&redirect_uri="+GlobalVariables.LINKEDIN_CALLBACK_URL
						+"&state="+state+"&scope="+Const.LINKEDINBASICPROFILE;
		return url+"?"+param;
	}
	
	@Override
	public String getUserInfo(String type,String code) throws Exception{
           JSONObject resultJson=null;

    	   String urlToken=Const.LINKEDINACCESSTOKENURL;
		   String urlParam="grant_type=authorization_code&client_id="+GlobalVariables.LINKEDIN_CLIENT_ID
							+"&client_secret="+GlobalVariables.LINKEDIN_CLIENT_SECRET+"&redirect_uri="+GlobalVariables.LINKEDIN_CALLBACK_URL
							+"&code="+code;
						
          //  "grant_type=authorization_code&code="+code+"&redirect_uri="+commonthings.redirect_url+"&client_id="+commonthings.client_id+"&client_secret="+commonthings.client_secret+"";
		   String tokenString=HttpClientUtils.send(urlToken, urlParam);
		   JSONObject jb=JSONObject.parseObject(tokenString);
		   Map<String,Object> jm=jb;
						
		   if(jm.containsKey("error")){
		       throw new Exception(jm.get("error").toString());
		   }
						
		   if(!jm.containsKey("access_token")){
		      throw new Exception("获取token失败!");
		   }
						
		   String tokenResult=jm.get("access_token").toString();
						
		   log.info("tokenResult:"+tokenResult);
						
		   urlToken=Const.LINKEDINPROFILEURL+":("+
					Const.LINKEDINSCOPEASPR+
					","+Const.LINKEDINSCOPECS+
					","+Const.LINKEDINSCOPEFMN+
					","+Const.LINKEDINSCOPEFN+
					","+Const.LINKEDINSCOPEFPN+
					","+Const.LINKEDINSCOPEHDL+
					","+Const.LINKEDINSCOPEIN+
					","+Const.LINKEDINSCOPEIY+
					","+Const.LINKEDINSCOPELN+
					","+Const.LINKEDINSCOPELT+
					","+Const.LINKEDINSCOPEMN+
					","+Const.LINKEDINSCOPENCSC+
					","+Const.LINKEDINSCOPENMC+
					","+Const.LINKEDINSCOPEPCUL+
					","+Const.LINKEDINSCOPEPFN+
					","+Const.LINKEDINSCOPEPLN+
					","+Const.LINKEDINSCOPEPPU+
					","+Const.LINKEDINSCOPEPTS+
					","+Const.LINKEDINSCOPESPS+
					","+Const.LINKEDINSCOPESSPL+
					","+Const.LINKEDINSCOPESUMY+")?format=json";
								
			String userInfoResult=HttpClientUtils.sendGet(urlToken, tokenResult, true);
			resultJson=JSONObject.parseObject(userInfoResult);
						
			log.info("userInfoJson:"+userInfoResult);
						
			String userName=resultJson.getString("id");
			ThirdAccount t=thirdAccountService.queryByThirdUserId(userName);
			if(t==null)t=new ThirdAccount();
			t.setThirdUserId(userName);
			t.setThirdAccount(resultJson.getString("formattedName"));
			t.setThirdToken(tokenResult);
			t.setThirdType(Utils.getLoginType(type));
			t.setCtTime(Utils.dateConvert(new Date()));
						
			thirdAccountService.insertAndUpdate(t);
						
			ThirdBasicInfo b=thirdBasicInfoService.queryByTid(t.getTid());
			if(b==null)b=new ThirdBasicInfo();
			b.setCommentCount(0);
			b.setFanCount(Integer.parseInt(resultJson.getString("numConnections")));
			b.setHomePage(resultJson.getString("publicProfileUrl"));
			b.setName(resultJson.getString("formattedName"));
			b.setPubCount(resultJson.getJSONObject("positions").containsKey("_total")?resultJson.getJSONObject("positions").getIntValue("_total"):0);
			b.setReadCount(0);
			b.setReplyCount(0);
			b.setSharedCount(0);
			b.setStarCount(0);
			b.setTid(t.getTid());
			b.setWatchCount(0);
						
			thirdBasicInfoService.insertIntoThirdBasicInfo(b);
						
			thirdHotPubInfoService.deleteByTid(t.getTid());
						
			List<ThirdHotPubInfo> pl=new ArrayList<ThirdHotPubInfo>();
						
			int size=resultJson.getJSONObject("positions").getJSONArray("values").size()>GlobalVariables.hotReposCount?GlobalVariables.hotReposCount:resultJson.getJSONObject("positions").getJSONArray("values").size();
						
			JSONArray jr=resultJson.getJSONObject("positions").getJSONArray("values");
						
			JSONObject c=null;
						
			for(int i=0;i<size;i++){
				ThirdHotPubInfo h=new ThirdHotPubInfo();
				c=new JSONObject();
				c=jr.getJSONObject(i);
				h.setCommentCount(0);
				h.setContent(c.getJSONObject("company").getString("name"));
				h.setForkCount(0);
				h.setTid(t.getTid());
				h.setTitle(c.getString("title"));
				h.setLinkAddr(resultJson.getString("publicProfileUrl"));
				pl.add(h);
			}

			if(size>0){
				thirdHotPubInfoService.insertBatchThirdHotPubInfo(pl);
			}

	    	String	result=resultJson.getString("id");
			//return resultJson;
	    	return result;
		}

}
