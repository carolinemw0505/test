package com.dco.eureka.client.platform.factory.impl;

import com.dco.eureka.client.factory.PlatformFactoryInterface;
import com.dco.eureka.client.platform.PlatformInterface;
import com.dco.eureka.client.platform.impl.PlatformCSDN;
import com.dco.eureka.client.platform.impl.PlatformGithub;
import com.dco.eureka.client.platform.impl.PlatformLinkin;
import com.dco.eureka.client.platform.impl.PlatformSlack;

public class PlatformFactory implements PlatformFactoryInterface{

	@Override
	public PlatformInterface createPlatform(String type) {
		PlatformInterface p=null;
		switch (type){
		  case "github":{
			  p=new PlatformGithub();
			  break;
		  }
		  case "slack":{
			  p=new PlatformSlack();
			  break;
		  }
		  case "csdn":{
			  p=new PlatformCSDN();
			  break;
		  }
		  case "linkin":{
			  p=new PlatformLinkin();
			  break;
		  }
		  default:{
			  break;
		  }
		}
		return p;
	}
}
