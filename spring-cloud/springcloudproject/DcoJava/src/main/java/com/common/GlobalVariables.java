package com.common;

public class GlobalVariables {
	//github三方登录相关参数
    public	static String GITHUB_CALLBACK_URL="";
    public	static String GITHUB_CLIENT_ID="";//Client ID
    public	static String GITHUB_CLIENT_SECRET="";//Client Secret
    //slack三方登录相关参数
    public	static String SLACK_CALLBACK_URL="";
    public	static String SLACK_CLIENT_ID="";
    public	static String SLACK_CLIENT_SECRET="";
    //redit三方登录相关参数
    public	static String REDDIT_CALLBACK_URL="";
    public	static String REDDIT_CLIENT_ID="";
    public	static String REDDIT_CLIENT_SECRET="";
    //CSDN三方登录相关参数
    public	static String CSDN_CALLBACK_URL="";
    public	static String CSDN_CLIENT_ID="";
    public	static String CSDN_CLIENT_SECRET="";
    //LinkedIn三方登录相关参数
    public	static String LINKEDIN_CALLBACK_URL="";
    public	static String LINKEDIN_CLIENT_ID="";
    public	static String LINKEDIN_CLIENT_SECRET="";
    
	//获取最近发文 的条数
	public static int hotReposCount=0;
	
	//代理是否打开标志
	public static int proxyopenflag=0;
	
	//代理配置
	public static String proxyHost="";
	public static int proxyPort=0;
}
