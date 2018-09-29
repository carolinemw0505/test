package com.test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Properties;
import java.util.ResourceBundle;

import junit.framework.Test;

public class test {
	public static void main(String[] args) throws ParseException, IOException{
//		String testString="scode=";
//		String[] arr=testString.split("=");
//		for(int i=0;i<arr.length;i++){
//			System.out.println(i+":"+arr[i]);
//		}
//		
//		String testStringAgin="scode=3";
//		String[] arrAgin=testStringAgin.split("=");
//		for(int i=0;i<arrAgin.length;i++){
//			System.out.println(i+":"+arrAgin[i]);
//		}
//		
//		Map<String,String> test=new HashMap<String,String>();
//		test.put("xx", "ttt");
//		test.put("tt", "pp");
//		
//		if(test.containsKey("xx")){
//			System.out.println("ppppp");
//		}
		
		
//		ThirdAccount ta=new ThirdAccount();
//
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		
//		String timeStamp=sdf.format(new Date());
//		
//		ta.setCtTime(new Date());
//		
//		ta.setThirdType(3);
//		
//		System.out.println("thirdType:"+ta.getThirdType());
//		
//		ta.setThirdType("github");
//		
//		System.out.println("thirdType1111:"+ta.getThirdType());
		
//		String result=HttpClientUtils.sendGet(Const.CSDNBLOGGETARTICLELIST, "access_token=fe4ee18586b34a19bca8a71e2a8fb232");
//		
//		System.out.println("1:"+result);
//		
//        result=HttpClientUtils.sendGet(Const.CSDNBLOGGETCOLUMN, "access_token=fe4ee18586b34a19bca8a71e2a8fb232");
//		
//		System.out.println("2:"+result);
//		
//        result=HttpClientUtils.sendGet(Const.CSDNBLOGGETINFO, "access_token=fe4ee18586b34a19bca8a71e2a8fb232");
//		
//		System.out.println("3:"+result);
//		
//        result=HttpClientUtils.sendGet(Const.CSDNBLOGGETMEDAL, "access_token=fe4ee18586b34a19bca8a71e2a8fb232");
//		
//		System.out.println("4:"+result);
//		
//        result=HttpClientUtils.sendGet(Const.CSDNBLOGGETSTATS, "access_token=fe4ee18586b34a19bca8a71e2a8fb232");
//		
//		System.out.println("5:"+result);
//		
//        result=HttpClientUtils.sendGet(Const.CSDNBLOGGETTAGLIST, "access_token=fe4ee18586b34a19bca8a71e2a8fb232");
//		
//		System.out.println("6:"+result);
//		
//        result=HttpClientUtils.sendGet(Const.CSDNCOMMENTLIST, "access_token=fe4ee18586b34a19bca8a71e2a8fb232");
//		
//		System.out.println("7:"+result);
//		
//        result=HttpClientUtils.sendGet(Const.CSDNGETARTICLE, "access_token=fe4ee18586b34a19bca8a71e2a8fb232&id=82145315");
//		
//		System.out.println("8:"+result);
//		
//        result=HttpClientUtils.sendGet(Const.CSDNGETARTICLECOMENTLIST, "access_token=fe4ee18586b34a19bca8a71e2a8fb232&article=82145315");
//		
//		System.out.println("9:"+result);
//		
//        result=HttpClientUtils.sendGet(Const.CSDNGETCATEGORYLIST, "access_token=fe4ee18586b34a19bca8a71e2a8fb232");
//		
//		System.out.println("10:"+result);
		
//        result=HttpClientUtils.sendGet(Const.CSDNMYCOMMENTLIST, "access_token=fe4ee18586b34a19bca8a71e2a8fb232");
//		
//		System.out.println("11:"+result);
//		
//		result=HttpClientUtils.sendGet(Const.CSDNUSER, "access_token=fe4ee18586b34a19bca8a71e2a8fb232");
//		
//		System.out.println("12:"+result);
		
//		System.out.println(new Date(1535453953));
		
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
//		System.out.println(sdf.parse("2018-08-01T15:30:58Z").getTime());
		
//		Map<String,Object> map=new HashMap<String,Object>();
//		map.put("1","tt");
//		map.put("2",3);
		
		//JSONObject j=(JSONObject)map;
//		JSONObject j=JSONObject.parseObject(JSON.toJSONString(map));
		
//		System.out.println(j.toString());
		
//		String str="https://api.github.com/users/CarolineMagic/following{/owner}{/repo}";
//		System.out.println("result:"+str.replaceAll("[{]/[a-zA-Z_]{0,}[}]", ""));
		
		//方法一
//		Properties pro=new Properties();
//		BufferedReader br=new BufferedReader(new FileReader("src/main/java/com/test/test.properties"));
//		pro.load(br);
//		String keyvalue=pro.getProperty("key1");
//		System.out.println("keyvalue:"+keyvalue);
		
		//方法二
//		Properties pro=new Properties();
//		InputStream in=test.class.getClassLoader().getResourceAsStream("com/test/test.properties");
//		pro.load(in);
//		System.out.println("keyvalue:"+pro.getProperty("key1"));
		
		
//		System.out.println(test.class.getResource(""));
		
//		ResourceBundle rb=ResourceBundle.getBundle("com/test/test");
//		System.out.println(rb.getString("key1"));
		
		StringBuilder sb=new StringBuilder();
		sb.append("keyvalue:"+"\n");
		sb.append("pop");
		
		System.out.println(sb.toString());
		ResourceBundle rb=ResourceBundle.getBundle("com/test/test");
		System.out.println(rb.getString("key1"));
		
	}
}
