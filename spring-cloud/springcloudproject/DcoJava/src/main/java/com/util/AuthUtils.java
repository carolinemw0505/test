package com.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.Const;
import com.common.GlobalVariables;
import com.model.ThirdAccount;
import com.model.ThirdBasicInfo;
import com.model.ThirdHotPubInfo;
import com.service.ThirdAccountService;
import com.service.ThirdBasicInfoService;
import com.service.ThirdHotPubInfoService;

public class AuthUtils {
	
	private static ThirdAccountService thirdAccountService;
	private static ThirdHotPubInfoService thirdHotPubInfoService;
	private static ThirdBasicInfoService thirdBasicInfoService;
	
	
	private static final Logger log=LoggerFactory.getLogger(AuthUtils.class);

	
	public ThirdAccountService getThirdAccountService() {
		return thirdAccountService;
	}



	public void setThirdAccountService(ThirdAccountService thirdAccountService) {
		this.thirdAccountService = thirdAccountService;
	}



	public static ThirdHotPubInfoService getThirdHotPubInfoService() {
		return thirdHotPubInfoService;
	}



	public void setThirdHotPubInfoService(ThirdHotPubInfoService thirdHotPubInfoService) {
		this.thirdHotPubInfoService = thirdHotPubInfoService;
	}



	public static ThirdBasicInfoService getThirdBasicInfoService() {
		return thirdBasicInfoService;
	}



	public void setThirdBasicInfoService(ThirdBasicInfoService thirdBasicInfoService) {
		this.thirdBasicInfoService = thirdBasicInfoService;
	}



	/**
	 * @author yzeng
	 * @param type 类型-github、slack、reddit、csdn等
	 */
	public static JSONObject getUserInfo(String type,String code) throws Exception{
		String resultInfo="";
		JSONObject resultJson=null;
		
		switch (type) {
			case "github":{
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
				break;
			}
			case "slack":{
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
//				getTokenUrl=Const.SLACKSTARSLIST;

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
//				b.setStarCount(bJson.getJSONObject("paging").getIntValue("total"));
				b.setStarCount(bJson.getJSONArray("items").size());
				b.setReadCount(0);
				b.setCommentCount(0);
				getTokenUrl=Const.SLACKREACTIONSLIST;
				bInfo=HttpClientUtils.send(getTokenUrl, getTokenParam);
				bJson=JSONObject.parseObject(bInfo);
//				b.setReplyCount(bJson.getJSONObject("paging").getIntValue("total"));
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
				
				break;
			}
			case "reddit":{
				
				String tokenResult=RedditUtls.getAccessToken(code, GlobalVariables.REDDIT_CALLBACK_URL);
				
				JSONObject rj=JSONObject.parseObject(tokenResult);
				
				Map<String,Object> jm=rj;
				
				if(jm.containsKey("error")){
					throw new Exception(jm.get("error").toString());
				}
				
				if(!jm.containsKey("access_token")){
					throw new Exception("获取token失败!");
				}
				
				String access_token=jm.get("access_token").toString();
				
				resultInfo=RedditUtls.getUserInfo(access_token);
				resultJson=JSONObject.parseObject(resultInfo);
				
//				System.out.println("resultInfo:"+resultInfo);
				log.info("resultInfo:"+resultInfo);
				
				break;
			}
			case "csdn":{
				String urlToken=Const.CSDNTOKEN;
				String urlParam="client_id="+GlobalVariables.CSDN_CLIENT_ID+"&client_secret="+GlobalVariables.CSDN_CLIENT_SECRET
						+"&redirect_uri="+GlobalVariables.CSDN_CALLBACK_URL+"&code="+code+"&grant_type=authorization_code";
				
				/*
				 * csdn:resultToken实例
				 * 成功:{"access_token":"547004cc30264629af8af9731a01e9ba","expires_in":86400,"username":"zyengi897"}
				 * 失败:{"error_code":"4030","error":"invalid request"}
				 */
				String resultToken=HttpClientUtils.sendGet(urlToken, urlParam,false);
				JSONObject jb=JSONObject.parseObject(resultToken);
				Map<String,Object> jm=jb;
				if(jm.containsKey("error")){
					throw new Exception(jm.get("error").toString());
				}
				
				if(!jm.containsKey("access_token")){
					throw new Exception("token值获取失败!");
				}
				
				String accessToken=jm.get("access_token").toString();
				
				urlToken=Const.CSDNUSER;
				
				resultInfo=HttpClientUtils.sendGet(urlToken, "access_token="+accessToken,false);
				
//				System.out.println("access_token="+accessToken);
				
				log.info("access_token:"+accessToken);
				
				
				resultJson=JSONObject.parseObject(resultInfo);
				
				
				String userId=resultJson.getString("username");
				ThirdAccount t=thirdAccountService.queryByThirdUserId(userId);
				
				if(t==null) t=new ThirdAccount();
				
				t.setThirdUserId(resultJson.getString("username"));
				t.setThirdAccount(resultJson.getString("username"));
				t.setThirdType(Utils.getLoginType(type));
				t.setThirdToken(accessToken);
				t.setCtTime(Utils.dateConvert(new Date()));
				
//				System.out.println("resultInfo:"+resultInfo);
				
				log.info("resultInfo:"+resultInfo);
				
				thirdAccountService.insertAndUpdate(t);
		
				ThirdBasicInfo b=thirdBasicInfoService.queryByTid(t.getTid());
				if(b==null)b=new ThirdBasicInfo();
				String baseInfoStr=HttpClientUtils.sendGet(Const.CSDNBLOGGETSTATS, "access_token="+accessToken,false);
				JSONObject baseJson=JSONObject.parseObject(baseInfoStr);
				b.setTid(t.getTid());
				b.setName(resultJson.getString("username"));
				b.setHomePage(resultJson.getString("website"));
				b.setPubCount(baseJson.getIntValue("original_count")+baseJson.getIntValue("repost_count")+baseJson.getIntValue("translated_count"));
				b.setFanCount(0);
				b.setWatchCount(0);
				b.setStarCount(baseJson.getIntValue("point"));
				b.setReadCount(baseJson.getIntValue("view_count"));
				b.setCommentCount(baseJson.getIntValue("comment_count"));
				b.setReplyCount(0);
				b.setSharedCount(baseJson.getIntValue("repost_count"));
				baseInfoStr=HttpClientUtils.sendGet(Const.CSDNBLOGGETINFO, "access_token="+accessToken,false);
				baseJson=JSONObject.parseObject(baseInfoStr);
				b.setRegTime(baseJson.getString("create_at"));
				
				thirdBasicInfoService.insertIntoThirdBasicInfo(b);
				
				thirdHotPubInfoService.deleteByTid(t.getTid());
				
				baseInfoStr=HttpClientUtils.sendGet(Const.CSDNBLOGGETARTICLELIST, "access_token="+accessToken,false);
				baseJson=JSONObject.parseObject(baseInfoStr);
				JSONArray al=JSONArray.parseArray(baseJson.getString("list"));
				int size=al.size()>GlobalVariables.hotReposCount?GlobalVariables.hotReposCount:al.size();
				List<ThirdHotPubInfo> hl=new ArrayList<ThirdHotPubInfo>(size);
				ThirdHotPubInfo h=null;
				JSONObject c=new JSONObject();
				for(int i=0;i<size;i++){
					c=al.getJSONObject(i);
					h=new ThirdHotPubInfo();
					h.setTid(t.getTid());
					h.setTitle(c.getString("title"));
					h.setContent(c.getString("description"));
					h.setLinkAddr(c.getString("url"));
					h.setCommentCount(c.getIntValue("comment_count"));
					h.setWatchCount(c.getIntValue("view_count"));
					h.setStarCount(c.getIntValue("digg"));
					h.setForkCount(0);
					h.setSharedCount(0);
					h.setPubTime(c.getString("create_at"));
					hl.add(h);
				}
				
				if(size>0){
					thirdHotPubInfoService.insertBatchThirdHotPubInfo(hl);
				}
				break;
				
			}
			case "linkedin":{
				String urlToken=Const.LINKEDINACCESSTOKENURL;
				String urlParam="grant_type=authorization_code&client_id="+GlobalVariables.LINKEDIN_CLIENT_ID
						+"&client_secret="+GlobalVariables.LINKEDIN_CLIENT_SECRET+"&redirect_uri="+GlobalVariables.LINKEDIN_CALLBACK_URL
						+"&code="+code;
				
//				"grant_type=authorization_code&code="+code+"&redirect_uri="+commonthings.redirect_url+"&client_id="+commonthings.client_id+"&client_secret="+commonthings.client_secret+"";
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
			}
			default:
				break;
		}
			
		return resultJson;
	}
}
