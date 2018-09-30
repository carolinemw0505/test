package com.dco.eureka.client.common;

public class Const {
	
	//reddit��scope����
	public static final String REDDITSCOPEIDENTITY="identity";
	public static final String REDDITSCOPEEDIT="edit";
	public static final String REDDITSCOPEFLAIR="flair";
	public static final String REDDITSCOPEHISTORY="history";
	public static final String REDDITSCOPEMODCONFIG="modconfig";
	public static final String REDDITSCOPEMODFLAIR="modflair";
	
	//reddit��response_type
	public static final String REDDITRESPONSE_TYPE="code";
	
	//reddit��duration
	public static final String REDDITDURATIONTEMPORARY="temporary";
	public static final String REDDITDURATIONPERMANENT="permanent";
	
	//linkedIn��scope
	public static final String LINKEDINBASICPROFILE="r_basicprofile";
	public static final String LINKEDINEMAILE="r_emailaddress";
	public static final String LINKEDINRSADMINCOMPANY="rw_company_admin";
	public static final String LINKEDINWSHARE="w_share";
	
	//linkedIn��URL
	public static final String LINKEDINOAUTHURL="https://www.linkedin.com/oauth/v2/authorization";
	public static final String LINKEDINACCESSTOKENURL="https://www.linkedin.com/oauth/v2/accessToken";
	public static final String LINKEDINPROFILEURL="https://api.linkedin.com/v1/people/~";
	
	//linkedin��response_type
	public static final String LINKEDINRESPONSECODE="code";
	
	//LINKEDIN��ȡ�û���Ϣ�ֶ��б�
	public static final String LINKEDINSCOPEIN="id";
	public static final String LINKEDINSCOPEFN="first-name";
	public static final String LINKEDINSCOPELN="last-name";
	public static final String LINKEDINSCOPEMN="maiden-name";
	public static final String LINKEDINSCOPEFMN="formatted-name";
	public static final String LINKEDINSCOPEPFN="phonetic-first-name";
	public static final String LINKEDINSCOPEPLN="phonetic-last-name";
	public static final String LINKEDINSCOPEFPN="formatted-phonetic-name";
	public static final String LINKEDINSCOPEHDL="headline";
	public static final String LINKEDINSCOPELT="location";
	public static final String LINKEDINSCOPEIY="industry";
	public static final String LINKEDINSCOPECS="current-share";
	public static final String LINKEDINSCOPENMC="num-connections";
	public static final String LINKEDINSCOPENCSC="num-connections-capped";
	public static final String LINKEDINSCOPESUMY="summary";
	public static final String LINKEDINSCOPESPS="specialties";
	public static final String LINKEDINSCOPEPTS="positions";
	public static final String LINKEDINSCOPEPCUL="picture-url";
	public static final String LINKEDINSCOPESSPL="site-standard-profile-request";
	public static final String LINKEDINSCOPEASPR="api-standard-profile-request";
	public static final String LINKEDINSCOPEPPU="public-profile-url";
	
	//slack����ӿ�ʱ��Ҫ��������ΧScope
	public static final String SCOPEFILESREAD="files:read";
	public static final String SCOPEREAD="read";
	public static final String SCOPESTARREAD="stars:read";
	public static final String SCOPEUSERSPROFILEREAD="users.profile:read";
	public static final String SCOPEREACTIONSREAD="reactions:read";
	public static final String SCOPEUSERSREAD="users:read";
	public static final String SCOPEREMINDER="reminders:read";
	public static final String SLACKUSERFIELD="users.identity";
	public static final String SLACKUSERFIELDBASIC="identity.basic";
	public static final String SLACKUSERFIELDEMAIL="identity.email";
	public static final String SLACKUSERFIELDTEAM="identity.team";
	public static final String SLACKUSERFIELDAVATER="identity.avatar";
	
	//GitHub��������¼URL
	public static final String GITHUBURL="https://github.com/login/oauth/authorize";
	public static final String GITHUBTOKEN="https://github.com/login/oauth/access_token";
	public static final String GITHUBUSER="https://api.github.com/user";
	
	//slack��������¼URL
	public static final String SLACKURL="https://slack.com/oauth/authorize";
	public static final String SLACKTOKEN="https://slack.com/api/oauth.access";
	public static final String SLACKUSER="https://slack.com/api/users.identity";
	public static final String SLACKSTARSLIST="https://slack.com/api/stars.list";
	public static final String SLACKREACTIONSLIST="https://slack.com/api/reactions.list";
	public static final String SLACKUSERSINFO="https://slack.com/api/users.info";
	public static final String SLACKREMINDERSLIST="https://slack.com/api/reminders.list";
	public static final String SLACKUSERLIST="https://slack.com/api/users.list";
	public static final String SLACKUSERSPROFILEGET="https://slack.com/api/users.profile.get";
	public static final String SLACKFILESLIST="https://slack.com/api/files.list";
	
	//Reddit��������¼����ز�������
	public static final String REDDITURL="https://ssl.reddit.com/api/v1/authorize";
	public static final String REDDITTOKEN="https://ssl.reddit.com/api/v1/access_token";
	public static final String REDDITUSER="https://oauth.reddit.com/api/v1/me.json";
	
	//csdn��������¼����ز�������
	public static final String CSDNURL="http://api.csdn.net/oauth2/authorize";
	public static final String CSDNTOKEN="http://api.csdn.net/oauth2/access_token";
	public static final String CSDNUSER="http://api.csdn.net/user/getinfo";
	public static final String CSDNBLOGGETINFO="http://api.csdn.net/blog/getinfo";
	public static final String CSDNBLOGGETSTATS="http://api.csdn.net/blog/getstats";
	public static final String CSDNBLOGGETMEDAL="http://api.csdn.net/blog/getmedal";
	public static final String CSDNBLOGGETCOLUMN="http://api.csdn.net/blog/getcolumn";
	public static final String CSDNBLOGGETARTICLELIST="http://api.csdn.net/blog/getarticlelist";
	public static final String CSDNGETARTICLE="http://api.csdn.net/blog/getarticle";
	public static final String CSDNGETCATEGORYLIST="http://api.csdn.net/blog/getcategorylist";
	public static final String CSDNBLOGGETTAGLIST="http://api.csdn.net/blog/gettaglist";
	public static final String CSDNCOMMENTLIST="http://api.csdn.net/blog/getcommentlist";
	public static final String CSDNMYCOMMENTLIST="http://api.csdn.net/blog/getmycommentlist";
	public static final String CSDNGETARTICLECOMENTLIST="http://api.csdn.net/blog/getarticlecomment";
	
	//��ȡ�û���Ϣ��������
	public static final String PARANAMEUSERID="userId";
	public static final String PARANAMETHIRDACCOUNTID="thirdAccountId";
}
