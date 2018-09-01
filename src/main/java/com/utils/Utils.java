package com.utils;

import java.util.UUID;

public class Utils {
	public static String getCode(){
		return UUID.randomUUID().toString();
	}
}
