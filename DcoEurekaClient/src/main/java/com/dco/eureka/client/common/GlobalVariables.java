package com.dco.eureka.client.common;

import org.springframework.beans.factory.annotation.Value;

public class GlobalVariables {
	//github������¼��ز���
    @Value("GITHUB_CALLBACK_URL") public static String GITHUB_CALLBACK_URL;
    @Value("GITHUB_CLIENT_ID") public static String GITHUB_CLIENT_ID;//Client ID
    @Value("GITHUB_CLIENT_SECRET") public static String GITHUB_CLIENT_SECRET;//Client Secret
    //slack������¼��ز���
    @Value("SLACK_CALLBACK_URL") public static String SLACK_CALLBACK_URL;
    @Value("SLACK_CLIENT_ID") public static String SLACK_CLIENT_ID;
    @Value("SLACK_CLIENT_SECRET") public static String SLACK_CLIENT_SECRET;
    //redit������¼��ز���
    @Value("REDDIT_CALLBACK_URL") public static String REDDIT_CALLBACK_URL;
    @Value("REDDIT_CLIENT_ID") public static String REDDIT_CLIENT_ID;
    @Value("REDDIT_CLIENT_SECRET") public static String REDDIT_CLIENT_SECRET;
    //CSDN������¼��ز���
    @Value("CSDN_CALLBACK_URL") public static String CSDN_CALLBACK_URL;
    @Value("CSDN_CLIENT_ID") public static String CSDN_CLIENT_ID;
    @Value("CSDN_CLIENT_SECRET") public static String CSDN_CLIENT_SECRET;
    //LinkedIn������¼��ز���
    @Value("LINKEDIN_CALLBACK_URL") public static String LINKEDIN_CALLBACK_URL;
    @Value("LINKEDIN_CLIENT_ID") public static String LINKEDIN_CLIENT_ID;
    @Value("LINKEDIN_CLIENT_SECRET") public static String LINKEDIN_CLIENT_SECRET;
    
	//��ȡ������� ������
    @Value("hotReposCount") public static int hotReposCount;
	
	//�����Ƿ�򿪱�־
    @Value("proxyopenflag") public static int proxyopenflag;
	
	//��������
    @Value("proxyHost") public static String proxyHost;
	@Value("proxyPort") public static int proxyPort;
}
