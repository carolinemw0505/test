package com.util;

import java.util.ResourceBundle;

public class PropertiesUtil {
	private ResourceBundle rb=null;
	
	public PropertiesUtil(String path){
		this.rb=ResourceBundle.getBundle(path);
	}
	
	public String getValue(String key){
		if(rb!=null){
			return rb.getString(key);
		}
		return "";
	}
}
