<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<!-- <form action="hello.do" method="post">
    hello:<input type="text" name="userName"/>
    <input type="submit" value="提交" /> 
</form>-->

<!-- <a href="https://github.com/login/oauth/authorize?client_id=925e712b3641848eb310&
redirect_uri=http://172.18.255.246:8080/ThirdLogin/index.jsp&state=LiuYanzhaoLoveLuoQiNeverGiveUp" 
target="_blank">gitHub第三方登录</a>-->
<a href="https://github.com/login/oauth/authorize?client_id=925e712b3641848eb310&
redirect_uri=http://localhost:8080/ThirdLogin/index.jsp&state=LiuYanzhaoLoveLuoQiNeverGiveUp"
target="_blank">gitHub第三方登录</a>


<a href="https://slack.com/oauth/authorize?scope=identity.basic&client_id=409327576148.409650056193&
redirect_uri=http://localhost:8080/ThirdLogin/index.jsp&state=LiuYanzhaoLoveLuoQiNeverGiveUp" target="_blank">Slack第三方登录</a>


</body>
</html>