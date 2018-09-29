package com.servlet;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.common.GlobalVariables;
import com.util.PropertiesUtil;

@Service
public class ApplicationStartListener implements ApplicationListener<ContextRefreshedEvent>{

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent()!=null){
			PropertiesUtil pro=new PropertiesUtil("config");
			GlobalVariables.GITHUB_CALLBACK_URL=pro.getValue("GITHUB_CALLBACK_URL");
			GlobalVariables.GITHUB_CLIENT_ID=pro.getValue("GITHUB_CLIENT_ID");
			GlobalVariables.GITHUB_CLIENT_SECRET=pro.getValue("GITHUB_CLIENT_SECRET");
			GlobalVariables.SLACK_CALLBACK_URL=pro.getValue("SLACK_CALLBACK_URL");
			GlobalVariables.SLACK_CLIENT_ID=pro.getValue("SLACK_CLIENT_ID");
			GlobalVariables.SLACK_CLIENT_SECRET=pro.getValue("SLACK_CLIENT_SECRET");
			GlobalVariables.CSDN_CALLBACK_URL=pro.getValue("CSDN_CALLBACK_URL");
			GlobalVariables.CSDN_CLIENT_ID=pro.getValue("CSDN_CLIENT_ID");
			GlobalVariables.CSDN_CLIENT_SECRET=pro.getValue("CSDN_CLIENT_SECRET");
			GlobalVariables.REDDIT_CALLBACK_URL=pro.getValue("REDDIT_CALLBACK_URL");
			GlobalVariables.REDDIT_CLIENT_ID=pro.getValue("REDDIT_CLIENT_ID");
			GlobalVariables.REDDIT_CLIENT_SECRET=pro.getValue("REDDIT_CLIENT_SECRET");
			GlobalVariables.LINKEDIN_CALLBACK_URL=pro.getValue("LINKEDIN_CALLBACK_URL");
			GlobalVariables.LINKEDIN_CLIENT_ID=pro.getValue("LINKEDIN_CLIENT_ID");
			GlobalVariables.LINKEDIN_CLIENT_SECRET=pro.getValue("LINKEDIN_CLIENT_SECRET");
			GlobalVariables.hotReposCount=Integer.parseInt(pro.getValue("hotReposCount"));
			GlobalVariables.proxyopenflag=Integer.parseInt(pro.getValue("proxy.open.flag"));
			GlobalVariables.proxyHost=pro.getValue("proxy.host");
			GlobalVariables.proxyPort=Integer.parseInt(pro.getValue("proxy.port"));
		}
	}

}
