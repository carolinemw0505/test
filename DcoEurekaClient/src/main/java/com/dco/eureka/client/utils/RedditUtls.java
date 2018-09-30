package com.dco.eureka.client.utils;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dco.eureka.client.common.Const;
import com.dco.eureka.client.common.GlobalVariables;


public class RedditUtls {
	
	private static final CloseableHttpClient httpclient;
	private static final Logger logger=LoggerFactory.getLogger(RedditUtls.class);
	public static final String CHARSET="UTF-8";
	
	
	static{
		RequestConfig config=null;
		if(GlobalVariables.proxyopenflag==1){
			HttpHost httpHost=new HttpHost(GlobalVariables.proxyHost,GlobalVariables.proxyPort);
			config=RequestConfig.custom().setProxy(httpHost).setConnectTimeout(5000).setSocketTimeout(3000).
					setConnectionRequestTimeout(15000).build();
		}else{
		    config=RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(3000).
					setConnectionRequestTimeout(15000).build();
		}
		httpclient=HttpClientBuilder.create().setDefaultRequestConfig(config).build();
	}
	
	public static String sendGet(String url ,Map<String,Object> params) throws ParseException, UnsupportedEncodingException, IOException{
		if(params!=null & !params.isEmpty()){
			List<NameValuePair> pairs=new ArrayList<NameValuePair>(params.size());
			
			for(String key:params.keySet()){
				pairs.add(new BasicNameValuePair(key,params.get(key).toString()));
			}
			
			url+="?"+EntityUtils.toString(new UrlEncodedFormEntity(pairs),CHARSET);
		}
		
		HttpGet httpGet=new HttpGet(url);
		CloseableHttpResponse response=httpclient.execute(httpGet);
		int status=response.getStatusLine().getStatusCode();
		if(status!=200){
			throw new RuntimeException("HttpClient,error status code:"+status);
		}
		
		HttpEntity entity=response.getEntity();
		String result="";
		if(entity!=null){
			result=EntityUtils.toString(entity,CHARSET);
			EntityUtils.consume(entity);
			response.close();
			return result;
		}
		return null;
	}
	
	public static String sendPost(String url,Map<String,Object> params) throws ClientProtocolException, IOException{
		List<NameValuePair> list=null;
		if(params!=null && !params.isEmpty()){
			list=new ArrayList<NameValuePair>(params.size());
			
			for(String key:params.keySet()){
				list.add(new BasicNameValuePair(key,params.get(key).toString()));
			}
		}
		
		HttpPost httpPost=new HttpPost(url);
		if(list!=null && list.size()>0){
			httpPost.setEntity(new UrlEncodedFormEntity(list,CHARSET));
		}
		
		CloseableHttpResponse response=httpclient.execute(httpPost);
		int status=response.getStatusLine().getStatusCode();
		
		if(status!=200){
			httpPost.abort();
			throw new RuntimeException("HttpClient,error code is "+status);
		}
		
		HttpEntity entity=response.getEntity();
		String result="";
		if(entity!=null){
			result=EntityUtils.toString(entity,CHARSET);
			EntityUtils.consume(entity);
			response.close();
			return result;
		}
		return result;
	}
	
	@SuppressWarnings("deprecation")
	public static String getUserInfo(String access_token) throws ClientProtocolException, IOException {
			DefaultHttpClient h=new DefaultHttpClient();
		try {
			if(GlobalVariables.proxyopenflag==1){
				HttpHost httphost=new HttpHost(GlobalVariables.proxyHost,GlobalVariables.proxyPort);
				h.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, httphost);
			}
			h.getCredentialsProvider().setCredentials(
                    new AuthScope("ssl.reddit.com", 443),
                    new UsernamePasswordCredentials(GlobalVariables.REDDIT_CLIENT_ID,GlobalVariables.REDDIT_CLIENT_SECRET));

			//System.out.println("access_token:"+access_token);
			logger.info("access_token:"+access_token);
			
        	HttpPost httppost=new HttpPost(Const.REDDITUSER);
            httppost.setHeader("Authorization", "bearer "+access_token+"");
            httppost.setHeader("User-Agent", "ChangeMeClient/0.1 by YouruuuuUsername");
            HttpResponse response=h.execute(httppost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {            	
            	return EntityUtils.toString(entity,CHARSET);
            }
            EntityUtils.consume(entity);
        } finally {
        	h.close();
        }
        return null;
	}
	
	@SuppressWarnings("deprecation")
	public static String getAccessToken(String code, String redirectUrl) throws ClientProtocolException, IOException {
		DefaultHttpClient h=new DefaultHttpClient();
		try {
//			httpclient.get
			if(GlobalVariables.proxyopenflag==1){
				HttpHost httpHost=new HttpHost(GlobalVariables.proxyHost,GlobalVariables.proxyPort);
				h.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, httpHost);
			}
			h.getCredentialsProvider().setCredentials(
                    new AuthScope("ssl.reddit.com", 443),
                    new UsernamePasswordCredentials(GlobalVariables.REDDIT_CLIENT_ID,GlobalVariables.REDDIT_CLIENT_SECRET));

			HttpPost httppost=new HttpPost(Const.REDDITTOKEN);
            
            List <NameValuePair> nvps = new ArrayList <NameValuePair>(3);
            nvps.add(new BasicNameValuePair("code", code));
            nvps.add(new BasicNameValuePair("grant_type", "authorization_code"));
            nvps.add(new BasicNameValuePair("redirect_uri", redirectUrl));

            httppost.setEntity(new UrlEncodedFormEntity(nvps));
            httppost.addHeader("User-Agent", "a unique user agent");
            httppost.setHeader("Accept","any;");
       
            HttpResponse response = h.execute(httppost);
            HttpEntity entity = response.getEntity();
            
            if (entity != null) {
            	String result=EntityUtils.toString(entity,CHARSET);
            	//System.out.println(result);
            	logger.info("result:"+result);
            	return result;
            	
            }
            EntityUtils.consume(entity);
        } finally {
        	h.close();
        }
        return null;
	}
	
	public static void main(String[] args) throws Exception {
		String accessToken = RedditUtls.getAccessToken("PhDY19HfcXbc6IHVFIKXkUyg9PU","https://www.duckdns.org/login");
		logger.info("Access Token is : " + accessToken);
		logger.info("Name is : " + RedditUtls.getUserInfo(accessToken));
		//System.out.println("Access Token is : " + accessToken);
		//System.out.println("Name is : " + RedditUtls.getUserInfo(accessToken));
	}
}