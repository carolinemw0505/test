package com.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Utils {
	public static String genState(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static String dateConvert(Object dateObject){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if(dateObject instanceof String){
			result= ((String) dateObject).replaceAll("Z"," ").replaceAll("T", " ").toString();
		}else if(dateObject instanceof Date){
			result=sdf.format(dateObject);
		}
		return result;
	}
	
	public static int getLoginType(Object thirdType){
		int result=0;
		if(thirdType instanceof Integer){
			result=(Integer)thirdType;
		}else if(thirdType instanceof String){
			switch (thirdType.toString()) {
				case "csdn":
					result=0;
					break;
				case "github":
					result=1;
					break;
				case "reddit":
					result=2;
					break;
				case "slack":
					result=3;
					break;
				case "linkedin":
					result=4;
					break;
				default:
					break;
			}
		}
		return result;
	}
}
